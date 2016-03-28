package com.tribalscale.eleague;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tribalscale.eleague.TeamAdapter;
import com.tribalscale.eleague.Team;
import com.tribalscale.eleague.MainActivity;
import com.tribalscale.eleague.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	ArrayList<Team> teamList;
	ArrayList<Team> searchList;
	TeamAdapter adapter;
	private static String apiUrl = "http://api.football-data.org/v1/soccerseasons/398/teams/";

	EditText inputSearch;
	MainActivity main = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		teamList = new ArrayList<Team>();
		new JSONAsyncTask()
				.execute(apiUrl);

		searchList = new ArrayList<Team>(teamList);

		ListView listview = (ListView) findViewById(R.id.list);
		listview.setTextFilterEnabled(true);

		adapter = new TeamAdapter(getApplicationContext(), R.layout.team_row,
				searchList);

		listview.setAdapter(adapter);

		inputSearch = (EditText) findViewById(R.id.inputSearchBox);
		inputSearch.setText("manchester");
		
		
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {

				// When user changed the Text
				String searchString = cs.toString();
				main.serachQuery(searchString);

				adapter.notifyDataSetChanged();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

		});

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {

				Intent i = new Intent(getApplicationContext(), TeamDetail.class);
				i.putExtra("tname", teamList.get(position).getName());
				i.putExtra("timage", teamList.get(position).getImage("200px"));
				i.putExtra("mktValue", teamList.get(position).getMarketValue());
				startActivity(i);
				/*Toast.makeText(getApplicationContext(),
						teamList.get(position).getName(), Toast.LENGTH_LONG)
						.show();*/
			}
		});
	}

	class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(MainActivity.this);
			dialog.setMessage("Hang On, we're thinking");
			dialog.setTitle("Connecting server");
			dialog.show();
			dialog.setCancelable(false);
		}

		@Override
		protected Boolean doInBackground(String... urls) {
			try {

				// ------------------>>
				HttpGet httppost = new HttpGet(urls[0]);
				httppost.addHeader("X-Auth-Token",
						"16b8016930944808b1d35ad547300aee");
				httppost.addHeader("X-Response-Control", "minified");
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(httppost);

				
				int status = response.getStatusLine().getStatusCode();

				if (status == 200) {
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);

					// Log.i("JSON Sanjeev",data);
					JSONObject jsono = new JSONObject(data);
					JSONArray jarray = jsono.getJSONArray("teams");

					for (int i = 0; i < jarray.length(); i++) {
						JSONObject object = jarray.getJSONObject(i);

						Team team = new Team();

						team.setName(object.getString("name"));

						team.setMarketValue(object
								.getString("squadMarketValue"));
						team.setImage(object.getString("crestUrl"));

						teamList.add(team);
						searchList.add(team);

					}
					
					
					
					return true;
				}

				// ------------------>>

			} catch (ParseException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return false;
		}

		protected void onPostExecute(Boolean result) {
			dialog.cancel();
			String searchString = inputSearch.getText().toString();
			MainActivity.this.serachQuery(searchString);
			
			adapter.notifyDataSetChanged();
			if (result == false) {
					Toast.makeText(getApplicationContext(),
							R.string.error_msg_network, Toast.LENGTH_LONG)
							.show();
				
					new AlertDialog.Builder(MainActivity.this)
					.setTitle("Error")
					.setMessage(R.string.error_msg_network)
					.setPositiveButton("OK", null)
					.setCancelable(true)
					.create().show();
			}

		}
	}
	
	/*upfront search query*/
	public void serachQuery(String keyword) {
		// When user changed the Text
		String searchString = keyword;
		int textLength = searchString.length();

		searchList.clear();

		for (int i = 0; i < teamList.size(); i++) {

			String teamName = teamList.get(i).getName();
			
			if (textLength <= teamName.length()) {
				// compare the String in EditText with Names in the
				// ArrayList
				if (searchString.equalsIgnoreCase(teamName.substring(0,
						textLength)))
					searchList.add(teamList.get(i));
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
