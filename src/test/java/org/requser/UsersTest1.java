package org.requser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UsersTest1 {

	//Setting the Base URI Path for the test
	@Before
	public void before() {
		// Set Base path
		RestAssured.baseURI = "https://reqres.in/";
	}
	
	//Listing the user and checking for the Status Code
	@Test
	public void listUsers() {
		//Create a Request
		RequestSpecification request = RestAssured.given();
		//Setup and Execute request
		//request.get("api/users?page=2").then().log().status();
		
		Response response= request.get("api/users?page=2");
		JsonPath jsonData = response.jsonPath();
		System.out.println("Total Data : " + jsonData.getInt("total"));
		int perPage = jsonData.getInt("per_page");
		System.out.println("Per Page Data : " +perPage);
		System.out.println("id's in the page = " + jsonData.getString("data.id"));	
		int statusCode = response.getStatusCode();
		assertEquals(200,statusCode);
		System.out.println("Test listUsers Successful");
			
	}
	
	
	//List user, get body and print as String
	@Test
	public void listUserBody() {
		//Create a Request
		RequestSpecification request = RestAssured.given();
		//Setup and Execute request
		System.out.println("Body of Request in string format \n");
		String body = request.get("api/users?page=2").getBody().asString();
		body.length();
		System.out.println(body);
	}
	
	//Creating user using the Post Request
	@Test
	public void postUser() {
		//Create a Request
		RequestSpecification request = RestAssured.given();
		//Setup and Execute request
		String user = "{\"name\": \"krishna\",\"job\": \"manager\"}";
		request.body(user).post("api/users").then().log().status();
	}
	
	//Sending Header along with Post
	@Test
	public void header() {
		//Create a Request
		RequestSpecification request = RestAssured.given();
		//Setup and Execute request
		String user = "{\"name\": \"krishna\",\"job\": \"manager\"}";
		//request.header("content-type", "application/json").body(user).log().all().post("api/users").then().log().all();
		int statusCode = request.header("content-type", "application/json").body(user).log().all().post("api/users").getStatusCode();
		assertEquals(201,statusCode);
		System.out.println("Test header Successful");
	}
	
	//Sending Multiple Header along with Post
	@Test
	public void headerMultiple() {
		//Create a Request
		RequestSpecification request = RestAssured.given();
		//Setup and Execute request
		String user = "{\"name\": \"krishna\",\"job\": \"manager\"}";
		String headers = "header1=value1;header2=value2;header3=value3";
		String[] headerArray = headers.split(";");
		
		for(String header:headerArray) 
		{
			String headerKeyValue[]=header.split("=");
			request.header(headerKeyValue[0],headerKeyValue[1]);
		}
		int statusCode = request.body(user).log().all().post("api/users").getStatusCode();
		assertEquals(201,statusCode);
		System.out.println("Test headerMultiple Successful");
	}
	
	//Passing the authentication for pop-up of Username and Password
	@Test
	public void forAuthUrl() {
		RestAssured.baseURI = "https://postman-echo.com/";
		RequestSpecification request = RestAssured.given();
		String username="postman";
		String password="password";
		request
		.auth().preemptive().basic(username, password)
		.when().get("basic-auth")
		.then().log().all();
		
	}

	@Test
	public void certificateValidationWithError() {
		RestAssured.baseURI = "https://www.picturematters.in/";
		RequestSpecification request = RestAssured.given();
		request.get().then().log().all();
	}
	
	@Test
	public void certificateValidationErrorSkipped() {
		
		RequestSpecification request = RestAssured.given();
		request
		.relaxedHTTPSValidation().baseUri("https://www.picturematters.in/").get().then().log().headers();
	}
	
	
}
