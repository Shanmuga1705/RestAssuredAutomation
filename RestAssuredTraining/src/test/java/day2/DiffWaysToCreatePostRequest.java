package day2;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

public class DiffWaysToCreatePostRequest {
	
	//1)Post Request body using HashMap
	
	//@Test(priority=1)
	void testPostUsingHashMap() {
		HashMap data = new HashMap();
		
		data.put("name", "Vignesh");
		data.put("location", "Chennai");
		data.put("phone", "123456");
		
		String courseArr[]= {"C","C++"};
		data.put("courses", courseArr);
		
		given()
			.contentType("application/json")
			.body(data)
		.when()
			.post("http://localhost:3000/students")
		.then()
			.statusCode(201)
			.body("name",equalTo("Vignesh"))
			.body("location",equalTo("Chennai"))
			.body("phone",equalTo("123456"))
			.body("courses[0]", equalTo("C"))
			.body("courses[1]", equalTo("C++"))
			.header("Content-Type", "application/json")
			.log().all();			
	}
	
	//@Test(priority=2)
	void testDelete()
	{
		given()
		
		.when()
			.delete("http://localhost:3000/students/96ab")
		.then()
			.statusCode(200);
	}
	
	//2) Post Request body using org.json Library
	
		//@Test(priority=3)
		void testPostUsingJsonLibrary() {
			JSONObject data = new JSONObject();
			
			data.put("name", "Vignesh");
			data.put("location", "Chennai");
			data.put("phone", "123456");
			
			String courseArr[]= {"C","C++"};
			data.put("courses", courseArr);
			
			given()
				.contentType("application/json")
				.body(data.toString())
			.when()
				.post("http://localhost:3000/students")
			.then()
				.statusCode(201)
				.body("name",equalTo("Vignesh"))
				.body("location",equalTo("Chennai"))
				.body("phone",equalTo("123456"))
				.body("courses[0]", equalTo("C"))
				.body("courses[1]", equalTo("C++"))
				.header("Content-Type", "application/json")
				.log().all();			
		}
		
		//3) Post Request body using POJO class
		
			//@Test(priority=4)
			void testPostUsingPOJOClass() {
				Pojo_PostRequest data=new Pojo_PostRequest();
				
				data.setName("Scott");
				data.setLocation("France");
				data.setPhone("123456");
				
				String coursesArr[]= {"C","C++"};
				data.setCourses(coursesArr);
				
				given()
					.contentType("application/json")
					.body(data)
				.when()
					.post("http://localhost:3000/students")
				.then()
					.statusCode(201)
					.body("name",equalTo("Scott"))
					.body("location",equalTo("France"))
					.body("phone",equalTo("123456"))
					.body("courses[0]", equalTo("C"))
					.body("courses[1]", equalTo("C++"))
					.header("Content-Type", "application/json")
					.log().all();			
			}
			
			//4) Post Request body using External JSON File
			
			@Test(priority=5)
			void testPostUsingExternalJsonFile() throws FileNotFoundException {
				
				File f =new File(".\\body.json");
				FileReader fr=new FileReader(f);
				JSONTokener jt=new JSONTokener(fr);
				JSONObject data=new JSONObject(jt);
				
				given()
					.contentType("application/json")
					.body(data.toString())
				.when()
					.post("http://localhost:3000/students")
				.then()
					.statusCode(201)
					.body("name",equalTo("Scott"))
					.body("location",equalTo("France"))
					.body("phone",equalTo("123456"))
					.body("courses[0]", equalTo("C"))
					.body("courses[1]", equalTo("C++"))
					.header("Content-Type", "application/json")
					.log().all();			
			}
}
