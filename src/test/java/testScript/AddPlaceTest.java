package testScript;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import POJO.AddPlace;
import POJO.Location;

public class AddPlaceTest {
	
	
	public static void main(String[] args){
		
	RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType("application/json")
			.addQueryParam("key","qaclick123").build();
	
	ResponseSpecification res=new ResponseSpecBuilder().expectContentType("application/json")
			.build();
	SessionFilter s=new SessionFilter();
			
		
	//RestAssured.baseURI="https://rahulshettyacademy.com";
	
	AddPlace addplace=new AddPlace();
	Location location=new Location();
    location.setLng(-38.383494);
    location.setLat(33.427362);
	addplace.setLocation(location);
	addplace.setAccuracy(50);
	addplace.setName("Frontline house");
	addplace.setPhone_number("(+91) 983 893 3937");
	addplace.setAddress("29, side layout, cohen 09");
	List<String> l=new ArrayList<String>();
	l.add("shoe park");
	l.add("shop");
	addplace.setTypes(l);
	addplace.setWebsite("http://google.com");
	addplace.setLanguage("French-IN");
	
	
//	String response=given().log().all().header("Content-Type","application/json").queryParam("key","qaclick123")
//	.body(addplace)
//    .when().post("/maps/api/place/add/json")
//    .then().extract().response().asString();
//	System.out.println(response);
	
	Response request=given().log().all().spec(req).body(addplace).when().post("/maps/api/place/add/json");
	
	String response=request.then().extract().response().asString();
	
	System.out.println(response);
	
	JsonPath js=new JsonPath(response);
	String id=js.getString("place_id");
	System.out.println(id);
	
	Response req1=given().relaxedHTTPSValidation().log().all().spec(req).queryParam("place_id",id).filter(s)
	.when().get("/maps/api/place/get/json").then().log().all().extract().response();
	
	
	
	String response1=req1.asString();
	
	JsonPath js1=new JsonPath(response1);
	
	String name=js1.getString("name");
	String accuracy=js1.getString("accuracy");
	
	System.out.println(name);
	System.out.println(accuracy);
	
	
	
	
	
	
	
	
	
	

}
}
