package com.tribalscale.eleague;

import java.io.InputStream;
import java.util.ArrayList;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Filter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TeamAdapter extends ArrayAdapter<Team> {
	ArrayList<Team> teamList;

	LayoutInflater vi;
	int Resource;
	ViewHolder holder;

	public TeamAdapter(Context context, int resource, ArrayList<Team> objects) {
		super(context, resource, objects);
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		teamList = objects;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = vi.inflate(Resource, null);
			holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
			holder.tvName = (TextView) v.findViewById(R.id.tvName);
			holder.mktValue = (TextView) v.findViewById(R.id.mktValue);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		
		UrlImageViewHelper.setUrlDrawable(holder.imageview,
				teamList.get(position).getImage("50px"), R.drawable.loading);

		holder.tvName.setText(teamList.get(position).getName());
		holder.mktValue.setText(teamList.get(position).getMarketValue());
		return v;

	}

	static class ViewHolder {
		public ImageView imageview;
		public TextView tvName;
		public TextView mktValue;

	}

}
