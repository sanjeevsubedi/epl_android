package com.tribalscale.eleague;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.text.TextUtils;
import android.util.Log;

public class Team {

	private String name;
	private String image;
	private String marketValue;

	public Team() {
		
	}

	public Team(String name, String image, String mktValue) {
		super();
		this.name = name;

		this.image = image;
		this.marketValue = mktValue;
	}

	public String getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage(String size) {

		String imagePath = image;
		String[] urlParts = imagePath.split("/");
		String param5 = urlParts[5];
		String param6 = urlParts[6];
		String param7 = "";
		String thumb = "";

		try {
			param7 = URLDecoder.decode(urlParts[7], "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		thumb = "/" + size + "-" + param7 + ".png";

		if (param7.equals("Leicester02.png")
				|| param7.equals("Crystal_Palace_F.C._logo_(2013).png")) {

			thumb = "/" + size + "-" + param7;
		}

		urlParts[5] = "thumb";
		urlParts[6] = param5 + "/" + param6;

		String finalUrl = TextUtils.join("/", urlParts);
		finalUrl = finalUrl + thumb;
		return finalUrl;
	}

	public void setImage(String image) {

		this.image = image;
	}

}
