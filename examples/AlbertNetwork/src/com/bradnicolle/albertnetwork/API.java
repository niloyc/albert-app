package com.bradnicolle.albertnetwork;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface API {
	@GET("/customers")
	void getCustomers(Callback<List<Customer>> callback);
}
