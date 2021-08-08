package demo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojoPack.AddPlace;
import pojoPack.Location;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;

public class SpecBuilderTest {

	public static void main(String[] args) {
		
	RestAssured.baseURI="https://rahulshettyacademy.com";
	
	AddPlace p =new AddPlace();
	p.setAccuracy(50);
	p.setAddress("29, side layout, cohen 09");
	p.setLanguage("French-IN");
	p.setPhone_number("(+91) 983 893 3937");
	p.setWebsite("http://google.com");
	p.setName("test demo");
	
	//create arraylist obj to add list of items
	ArrayList<String> ab = new ArrayList<String>();
	ab.add("shoe park");
	ab.add("shop");
	// pass that list as types is excepting a list of items
	p.setTypes(ab);
	
	//create obj of subclass location and set values
	Location l = new Location();
	l.setLat(-38.383494);
	l.setLng(33.427362);
	
	// pass obj here
	p.setLocation(l);
	// since we have created class obj p which contains all the value pass obj p in BODY
	
	//RequestSpecBuilder obj can be used for reuseable code
	
	RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
	.setContentType(ContentType.JSON).build();
	
	ResponseSpecification resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	
	RequestSpecification res= given().spec(req)
	.body(p);
	
	Response response=res.when().post("/maps/api/place/add/json")
	.then().spec(resspec).extract().response();

	String responseString =response.asString();
	System.out.println(responseString);
	
	}
}
