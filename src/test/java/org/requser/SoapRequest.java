package org.requser;

import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.CoreMatchers.equalTo;

public class SoapRequest {

	@Before
	public void before() {
		// Set Base path
		RestAssured.baseURI = "https://www.dataaccess.com";
	}
	
	
	@Test
	public void numberTest1() {
		String PayLoad = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
				+ "  <soap:Body>\n"
				+ "    <NumberToWords xmlns=\"http://www.dataaccess.com/webservicesserver/\">\n"
				+ "      <ubiNum>10</ubiNum>\n"
				+ "    </NumberToWords>\n"
				+ "  </soap:Body>\n"
				+ "</soap:Envelope>";
		
		
		RequestSpecification request = RestAssured.given();
		Response response = (Response) request.header("Content-Type","text/xml; charset=utf-8").and().body(PayLoad).when().post("/webservicesserver/numberconversion.wso");
		response.then().assertThat().body("Envelope.Body.NumberToWordsResponse.NumberToWordsResult",equalTo("ten "));
		System.out.println("Test Passed");
		}
}
