package org.requser;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class UsersJunit {

	public static void main(String[] args) {
	
	// Set Base path
	RestAssured.baseURI = "https://reqres.in/";

	//Create a Request
	RequestSpecification request = RestAssured.given();
	
	//Setup and Execute request
	//request.get().then().log().all();
	request.get("api/users?page=2").then().log().status();
	request.get("api/users?page=2").then().log().body();

	
	}

}
