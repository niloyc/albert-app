package com.bradnicolle.albertnetwork;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface API {
	//// CUSTOMERS ////
	@GET("/customers")
	void getCustomers(Callback<List<Customer>> callback);
	
	@GET("/customers/{id}")
	void getCustomer(@Path("id") String id, Callback<Customer> callback);
	
	@POST("/customers")
	void addCustomer(@Body Customer customer, Callback<List<Customer>> callback);
	
	@PUT("/customers/{id}")
	void updateCustomer(@Path("id") String id, @Body Customer customer, Callback<Customer> callback);
	
	@DELETE("/customers/{id}")
	void deleteCustomer(@Path("id") String id, Callback<Customer> callback);
	
	//// ORDERS ////
	
	@GET("/orders")
	void getOrders(Callback<List<Order>> callback);
	
	@GET("/orders/{id}")
	void getOrder(@Path("id") String id, Callback<Order> callback);
	
	@POST("/orders")
	void addOrder(@Body Order order, Callback<List<Order>> callback);
	
	@PUT("/orders/{id}")
	void updateOrder(@Path("id") String id, @Body Order order, Callback<Order> callback);
	
	@DELETE("/orders/{id}")
	void deleteOrder(@Path("id") String id, Callback<Order> callback);
}
