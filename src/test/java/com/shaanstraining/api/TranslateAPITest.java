package com.shaanstraining.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

public class TranslateAPITest {
	static final String JSON_FILE = "src/test/resources/translate-api.json";
	static final String JSON_FILE_2 = "src/test/resources/translate-api2-negative.json";

	@Test(enabled=false)
	public void traslateTest() throws Exception {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(JSON_FILE));

        JSONObject jsonObject = (JSONObject) obj;
                
		given().
			header("contentType", "application/json").and().
			body(jsonObject.toJSONString()).
        when().
        	post("https://translation.googleapis.com/language/translate/v2?key=AIzaSyD1ktJSjROx50db6zRP-iFPPBjxXcJCHx8").
        then().
        	statusCode(200).
        	log().all();
	}
	
	@Test
	public void traslateTestNegative() throws Exception {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(JSON_FILE_2));

        JSONObject jsonObject = (JSONObject) obj;
                
		given().
			header("contentType", "application/json").and().
			body(jsonObject.toJSONString()).
        when().
        	post("https://translation.googleapis.com/language/translate/v2?key=AIzaSyD1ktJSjROx50db6zRP-iFPPBjxXcJCHx8").
        then().
        	statusCode(400).
        	body("error.message", equalTo("Invalid Value")).
        	log().all();
	}
	
}
