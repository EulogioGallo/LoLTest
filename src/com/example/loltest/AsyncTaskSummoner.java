package com.example.loltest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncTaskSummoner extends AsyncTask<String, String, String> {
	private String api_response = "nada";
	
	
	@Override
	protected String doInBackground(String... params) {
		//Make get request
		try {
			HttpClient client = new DefaultHttpClient();
			String url = params[0];
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			HttpEntity resEntity = response.getEntity();
			if(resEntity != null){
				api_response = EntityUtils.toString(resEntity);
				Log.i("GET RESPONSE", api_response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return api_response;
		
	}
	
	@Override
	  protected void onPostExecute(String result) {
	   // execution of result of Long time consuming operation
	   super.onPostExecute(result);
	  }

	  /*
	   * (non-Javadoc)
	   * 
	   * @see android.os.AsyncTask#onPreExecute()
	   */
	  @Override
	  protected void onPreExecute() {
	   // Things to be done before execution of long running operation. For
	   super.onPreExecute();
	  }
	
	@Override
	protected void onProgressUpdate(String... text) {
		super.onProgressUpdate(text[0]);
	}
}