package com.example.loltest;

import org.json.JSONObject;

public class MapNames implements RIOT_CLASS{
	private final String tableName = "map_names";
	private int mapId;
	private String name;
	private String notes;
	
	public MapNames(){}
	
	public MapNames(JSONObject map) {
		super();
		
		try {
			this.mapId = map.getInt("mapId");
			this.name = map.getString("name");
			this.notes = map.getString("notes");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "{\"map_names\": {\"mapId\":\""+mapId+"\", \"name\":\""+name+"\", \"notes\":\""+notes+"\"}}";
	}
	
	public String[] members() {
		String members[] = {"mapId", "name", "notes"};
		return members;
	}
	
	public String tableName() {
		return this.tableName;
	}
}