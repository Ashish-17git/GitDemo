package Maven.APIProject;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojoPack.Api;
import pojoPack.GetCourse;
import pojoPack.WebAutomation;
public class oAuthTest {

	public static void main(String[] args) throws InterruptedException {
		
		String[] titles = {"abc", "xyz", "dasda"};
		
		/*
		 * System.setProperty("webdriver.chrome.driver",
		 * "C://Users//Shivangi Sapehia//Desktop//Automation//browserDriver//chromedriver.exe"
		 * ); WebDriver driver = new ChromeDriver(); driver.get(
		 * "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php"
		 * );
		 * 
		 * driver.findElement(By.cssSelector("input[type='email']")).sendKeys(
		 * "testashish61");
		 * driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER
		 * ); Thread.sleep(3000);
		 * driver.findElement(By.cssSelector("input[type='password']")).sendKeys(
		 * "@sapehia17");
		 * driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.
		 * ENTER); Thread.sleep(3000);
		 */
		//String url= driver.getCurrentUrl();
		// ****GOOGLE WONT ALLOW AUTOMATION USE url from above enteriusername pass then manually enter url in string url
	String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWi1NcdUtbygHHgskBPrIj3V7D4PQDhuWwkSRa25a92cKC_eZBkI-XgWqogDT0zSfQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none#";
		
		
		String partialcode=url.split("code=")[1];
		String code=partialcode.split("&scope=")[0];
		System.out.println(code);
		
		
		//urlEncodingEnabled(false) method will ignore specialchars
		
	String accessTokenResponse= given().urlEncodingEnabled(false)
		.queryParams("code", code)
		.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
        .queryParams("grant_type", "authorization_code")
       // .queryParams("state", "verifyfjdss")
       // .queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
     // .queryParam("scope", "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")
        .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
        .when().log().all()
        .post("https://www.googleapis.com/oauth2/v4/token").asString();
	
	JsonPath js = new JsonPath(accessTokenResponse);
	String accessToken =js.getString("access_Token");
	
	
	//add expect and default Parse method to define queryparam type
		
	GetCourse gc = given().queryParam("access_Token", accessToken).expect().defaultParser(Parser.JSON)
		.when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);

	System.out.println(gc.getLinkdIn());
	System.out.println(gc.getInstructor());
	
	System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
	
	List<Api> apiCourses = gc.getCourses().getApi();
	
	for(int i=0; i<apiCourses.size(); i++) {
		
		if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
		{
			System.out.println(apiCourses.get(i).getPrice());
		}
		
		// get coursetitle of webautomation
		
		ArrayList<String> a = new ArrayList<String>(); // capture dynamic list of objs in array
	
		 List<WebAutomation> w = gc.getCourses().getWebAutomation();
		
		for(int j =0; j<w.size(); j++) {
			
			a.add(w.get(i).getCourseTitle());
			// System.out.println(w.get(i).getCourseTitle());
		}
		List<String> expetedList= Arrays.asList(titles); // converting array to arraylist
		
	Assert.assertTrue(a.equals(expetedList));
	
	}
	
	//System.out.println(response);
	}

}
