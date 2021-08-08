package Maven.APIProject;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class JiraTest {

	public static void main(String[] args) {
		
		RestAssured.baseURI="http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
		//by declaring the session object we can add that object in given statement with filter method
		// filter method will capture the generated response for post api
		//*IMP which each given method add relaxedHTTPSValidation() when dealing with websites
	// login jira	
	String response = given().header("Content-Type", "application/json").body("{ \"username\": \"ashishsapehia17\", \"password\": \"@sapehia12\" }")
		.log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
		
	// similary above responsed will be given to treat this as logged in session
	//add comment in jira bug
	
	String expectedMessage="HI how are you";
	String addCommentResponse =	given().pathParam("key", "10201").log().all().header("Content-Type", "application/json").body("{\r\n"
				+ "    \"body\": \""+expectedMessage+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{key}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
	JsonPath jsid = new JsonPath(addCommentResponse);
	String commentid = jsid.get("id"); //this will fetch id of recent added comments
	
		//add attachment
		// we can use multipart method to add path of file
		//in header add contetnt type for multipart
		
		given().header("X-Atlassian-Token", "no-check").filter(session).pathParam("key", "10201")
		.queryParam("fields", "comment")
		.header("Content-Type", "multipart/form-data").multiPart("file", new File("jira.txt"))
		.when().post("rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
		
		//get issue
		
		String issueDetails = given().filter(session).pathParam("key", "10201")
				.queryParam("fields", "comment")
				.when().get("/rest/api/2/issue/{key}")
		.then().log().all().extract().response().asString();
		
		System.out.println(issueDetails);
		
		JsonPath js = new JsonPath(issueDetails);
		int commentsCount = js.getInt("fields.comment.comments.size()"); // this will fetch size of array Comment/no of comments added
	for(int i=0;i<commentsCount; i++) {
		String commentIDIssue = js.get("fields.comment.comments["+i+"].id").toString(); // this will get id of all comments
	if(commentIDIssue.equalsIgnoreCase(commentid)) {
		String message = js.get("fields.comment.comments["+i+"].body").toString();
		System.out.println(message);
	Assert.assertEquals(message, expectedMessage);
	}
	}
	}
}
