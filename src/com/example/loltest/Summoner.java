package com.example.loltest;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

/**  This class contains all summoner info you'd need
 *	 TODO: Add masteries and runes
 *
 */

public class Summoner {
	private Context context;
	private int id;
	private String name;
	private int profileIconId;
	private int revisionDate;
	private int summonerLevel;
	private ArrayList<Summoner> recentlyPlayed;
	
	public Summoner() {}
	
	//initialize w/summonerId
	public Summoner(Context context, int summonerId) {
		this.context = context;
		
		//get summoner info by id first
		AsyncTaskSummoner ats = new AsyncTaskSummoner();
		JSONObject jResult;
		String summonerInfoUrl = context.getString(R.string.get_url) + context.getString(R.string.get_url_summoner_v1_3_summonerId)
				+ summonerId + context.getString(R.string.api_key);
		
		try {
			jResult = new JSONObject(ats.execute(summonerInfoUrl).get());
			
			jResult = jResult.getJSONObject(String.valueOf(summonerId));
			
			this.id = summonerId;
			this.name = jResult.getString("name");
			this.profileIconId = jResult.getInt("profileIconId");
			this.revisionDate = jResult.getInt("revisionDate");
			this.summonerLevel = jResult.getInt("summonerLevel");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//initialize with JSONObject
	public Summoner(Context context, JSONObject summoner) {
		try {
			this.context = context;
			this.id = summoner.getInt("id");
			this.name = summoner.getString("name");
			this.profileIconId = summoner.getInt("profileIconId");
			this.revisionDate = summoner.getInt("revisionDate");
			this.summonerLevel = summoner.getInt("summonerLevel");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String summonerName() {
		return this.name;
	}
	
	//populates recentlyPlayed
	public void getRecentlyPlayed() {
		AsyncTaskSummoner ats = new AsyncTaskSummoner();
		
		//URL for recent games
		String recentGamesUrl = context.getString(R.string.get_url) + context.getString(R.string.get_url_game_v1_3_1) + id
				+ context.getString(R.string.get_url_game_v1_3_2) + context.getString(R.string.api_key);
		String result = "";
		
		try {
			result = ats.execute(recentGamesUrl).get();
			JSONArray jResult = new JSONObject(result).getJSONArray("games");
			
			for(int i = 0; i < jResult.length(); i++) {
				JSONArray curr = jResult.getJSONObject(i).getJSONArray("fellowPlayers");
				
				for(int j = 0; j < curr.length(); j++) {
					Summoner currSummoner = new Summoner(context, curr.getJSONObject(j).getInt("summonerId"));
					recentlyPlayed.add(currSummoner);
					Log.d("Recently Played", currSummoner.summonerName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}