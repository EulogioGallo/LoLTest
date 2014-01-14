package com.example.loltest;

public class MapNames implements RIOT_CLASS{
	private final String tableName = "map_names";
	private int mapId;
	private String name;
	private String notes;
	
	public MapNames(){}
	
	public MapNames(int id, String name, String notes) {
		super();
		this.mapId = id;
		this.name = name;
		this.notes = notes;
	}
	
	@Override
	public String toString() {
		return "{\"map_names\": {\"mapId\"=\""+mapId+"\", \"name\"=\""+name+"\", \"notes\"=\""+notes+"\"}}";
	}
	
	public String[] members() {
		String members[] = {"mapId", "name", "notes"};
		return members;
	}
	
	public String tableName() {
		return this.tableName;
	}
}