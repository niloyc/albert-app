package com.company.mypaymentapplication.qrcode;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class RestClient {
	private static API REST_CLIENT;
	private static String ROOT = "http://scribbler.io:3000/api";
	
	static {
		setupRestClient();
	}
	
	private RestClient() {
	}

	public static API get() {
		return REST_CLIENT;
	}

	private static void setupRestClient() {
		RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(
				ROOT).setClient(new OkClient(new OkHttpClient()));
		builder.setLogLevel(RestAdapter.LogLevel.FULL);

		RestAdapter restAdapter = builder.build();
		REST_CLIENT = restAdapter.create(API.class);
	}
}
