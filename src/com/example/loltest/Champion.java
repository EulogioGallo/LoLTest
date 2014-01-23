package com.example.loltest;

import org.json.JSONObject;

public class Champion implements RIOT_CLASS{
	private final String tableName = "champions";
	private int id;
	private String name;
	private int defenseRank;
	private int attackRank;
	private int difficultyRank;
	private int magicRank;
	
	public Champion(){}
	
	public Champion(JSONObject champion) {
		super();
		
		try{
			this.id = champion.getInt("id");
			this.name = champion.getString("name");
			this.defenseRank = champion.getInt("defenseRank");
			this.attackRank = champion.getInt("attackRank");
			this.difficultyRank = champion.getInt("difficultyRank");
			this.magicRank = champion.getInt("magicRank");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "{\"champions\": {\"id\":\""+id+"\", \"name\":\""+name+"\", \"defenseRank\":\""+defenseRank+"\", \"attackRank\":\"" + attackRank +"\", \"difficultyRank\":\""+ difficultyRank +"\", \"magicRank\":\""+ magicRank +"\"}}";
	}
	
	public String[] members() {
		String members[] = {"id", "name", "defenseRank", "attackRank", "difficultyRank", "magicRank"};
		return members;
	}
	
	public String tableName() {
		return this.tableName;
	}
}