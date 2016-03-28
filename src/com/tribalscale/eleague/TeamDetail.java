package com.tribalscale.eleague;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class TeamDetail extends Activity {

	TextView tv1;
	TextView mktValue;
	ImageView im1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.team_detail);

		tv1 = (TextView) findViewById(R.id.tvName);
		mktValue = (TextView) findViewById(R.id.mktValue);
		im1 = (ImageView) findViewById(R.id.tvImage);
		Intent intent = getIntent();
		if (intent != null) {
			String teamName = intent.getStringExtra("tname");
			String teamImg = intent.getStringExtra("timage");
			String marketValue = intent.getStringExtra("mktValue");
			tv1.setText(teamName);
			mktValue.setText(marketValue);

			UrlImageViewHelper.setUrlDrawable(im1, teamImg, R.drawable.loading);

			getActionBar().setTitle(teamName);

			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);

		
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; goto parent activity.
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}