package com.example.loltest;



import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.loltest.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//test db code
		RiotDB db = new RiotDB(this);
		//db.loadChamps();
		//MapNames mapNames = new MapNames(1, "Summoner's Rift", "Summer Variant");
		//db.addRow(mapNames);
		
		//Log.d("Hmm...", db.getRow(mapNames.tableName(),mapNames.members()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void findSummoner(View view) {
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.summoner_name);
		String summonerName = editText.getText().toString();
		
		//form get url for summoner
		String summonerUrl = this.getString(R.string.get_url) + this.getString(R.string.get_url_summoner_v1_2_by_name)
				+ summonerName + this.getString(R.string.api_key);
		
		//call asynctask to get summoner id by name
		AsyncTaskSummoner ats = new AsyncTaskSummoner();
		String result = "";
		try {
			result = ats.execute(summonerUrl).get();
			JSONObject jResult = new JSONObject(result);
			Summoner summoner = new Summoner(this, jResult);
			Log.d("Summoner Created", summoner.summonerName());
			summoner.getRecentlyPlayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("RESULT RETURNED", result);
		
		
		
		
		intent.putExtra(EXTRA_MESSAGE, result);
		startActivity(intent);
	}
	

}
