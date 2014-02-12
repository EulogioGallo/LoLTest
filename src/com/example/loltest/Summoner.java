package com.example.loltest;

import java.util.ArrayList;
import java.util.Iterator;

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
	private ArrayList<Summoner> recentlyPlayed = new ArrayList<Summoner>();
	
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
			
			Log.d("Summoner Added", name);
			
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
		ArrayList<String> recentSummoners = new ArrayList<String>();
		
		try {
			result = ats.execute(recentGamesUrl).get();
			JSONArray jResult = new JSONObject(result).getJSONArray("games");
			
			for(int i = 0; i < jResult.length(); i++) {
				JSONArray curr = jResult.getJSONObject(i).getJSONArray("fellowPlayers");
				
				for(int j = 0; j < curr.length(); j++) {
					/*
					Summoner currSummoner = new Summoner(context, curr.getJSONObject(j).getInt("summonerId"));
					recentlyPlayed.add(currSummoner);
					Log.d("Recently Played", currSummoner.summonerName());
					*/
					//create list to store recent summoner ids and get info on them in one API call
					recentSummoners.add(String.valueOf(curr.getJSONObject(j).getInt("summonerId")).toString());
				} //end for j
			} //end for i
			
			//call API with summoner id list
			AsyncTaskSummoner ats2 = new AsyncTaskSummoner();
			String allSummoners = "";
			
			//capped at 40 per request
			for(int i = 0; i < 40 && i < recentSummoners.size(); i++) {
				allSummoners += recentSummoners.get(i) + ",";
			}
			
			String recentSummonersUrl = context.getString(R.string.get_url) + context.getString(R.string.get_url_summoner_v1_3_summonerId)
					+ allSummoners + context.getString(R.string.api_key);
			
			result = ats2.execute(recentSummonersUrl).get();
			
			//add summoners to recently played list
			JSONObject jRecent = new JSONObject(result);
			Iterator<?> jRecentSummoners = jRecent.keys();
			while(jRecentSummoners.hasNext()) {
				String currId = jRecentSummoners.toString();
				Log.d("json summoner", jRecent.getJSONObject(currId).toString());
				this.recentlyPlayed.add(new Summoner(this.context, jRecent.getJSONObject(currId)));
				jRecentSummoners.next();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}