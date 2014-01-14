package com.example.loltest;



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
		MapNames mapNames = new MapNames(1, "Summoner's Rift", "Summer Variant");
		db.addRow(mapNames);
		
		Log.d("Hmm...", db.getRow(mapNames.tableName(),mapNames.members()));
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
		String message = editText.getText().toString();
		
		//call asynctask
		AsyncTaskSummoner ats = new AsyncTaskSummoner();
		String result = "";
		try {
			result = ats.execute(message).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("RESULT RETURNED", result);
		
		intent.putExtra(EXTRA_MESSAGE, result);
		startActivity(intent);
	}

}
