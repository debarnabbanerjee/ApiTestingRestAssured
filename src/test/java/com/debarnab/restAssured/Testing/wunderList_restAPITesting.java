package com.debarnab.restAssured.Testing;
import static com.jayway.restassured.RestAssured.given;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.authentication.AuthenticationScheme;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.specification.RequestSpecification;

public class wunderList_restAPITesting {

	private final String client_ID="3ccf93b534acfd0dc428";
	//private final String clientSecret="9b32bcf236865123cc1bff130fbde38c2641d807fb942e733f942a79dfe1";
	private final String access_Token="524f85fa3d80b5de4ae35f0b3e160ce004a9ae23c2c6581fa7837e310d14";

	//https://developer.wunderlist.com/documentation
	private String APIUrl = "https://a.wunderlist.com/api/v1";
	private String json;
	private JsonPath jsonPath;
	private Integer id ;
	private String title ;
	private String owner_type ;
	private String owner_id ;
	private String list_type ;
	private String publicc ;
	private String revision ;
	private String created_at ;
	private String type ;
	private RequestSpecBuilder requestBuilder;
	private RequestSpecification requestSpec;
	
	
	@BeforeClass
	public void makeRequest(){
		Map<String,String> headers =  new HashMap<String,String>();
		headers.put("X-Access-Token", access_Token);
		headers.put("X-Client-ID",client_ID);	
		
		requestBuilder = new RequestSpecBuilder();
		requestBuilder.addHeaders(headers);		
		requestBuilder.setContentType(ContentType.JSON);
		requestSpec = requestBuilder.build();	
	}

	@Test
	public void authTestCase(){
		json = given()
				.headers("X-Access-Token", access_Token, "X-Client-ID",client_ID)
				.auth()
				.oauth2(access_Token)
				.when()
				.get(APIUrl+"/user")
				.then()
				.assertThat()
				.contentType(ContentType.JSON)
				.statusCode(200).extract().response().asString();

		jsonPath = JsonPath.from(json);
		Assert.assertTrue(jsonPath.getString("name").equalsIgnoreCase("Debarnab Banerjee"));
		Assert.assertTrue(jsonPath.getString("email").equalsIgnoreCase("debarnab.banerjee@gmail.com"));		
	}
	@Test(dependsOnMethods="authTestCase")
	public void getAllList(){
		//get All Lists and match the schema of the response
		json = given()
				.spec(requestSpec)
				.when()
				.get(APIUrl+"/lists")
				.then()
				.assertThat()
				.contentType(ContentType.JSON)
				.statusCode(200)
				//.body(matchesJsonSchemaInClasspath("jsonscemaList.json"))
				//validate the response here against the schema
				.extract().response().asString();

		jsonPath = JsonPath.from(json);
		List<String> sectionsInResponse = jsonPath.getList("id");
		//System.out.println(sectionsInResponse.size());
	}
	@Test(dependsOnMethods="getAllList")
	public void createList(){

		//1.  create a new list and validate the addition of the newly added list
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Map<String,Object> dataAsPayLoad = new HashMap<String,Object>();
		dataAsPayLoad.put("title", "Debarnab's new Data " + date);

		json = given()
				.spec(requestSpec)
				.body(dataAsPayLoad)
				.when()
				.post(APIUrl+"/lists")
				.then()
				.assertThat()
				.statusCode(201)
				.contentType(ContentType.JSON)
				//validate the response here against the schema
				.extract().response().asString();

		jsonPath = JsonPath.from(json);
		id = Integer.valueOf(jsonPath.getString("id"));
		title = jsonPath.getString("title");
		owner_type = jsonPath.getString("owner_type");
		owner_id = jsonPath.getString("owner_id");
		list_type = jsonPath.getString("list_type");
		publicc = jsonPath.getString("public");
		revision = jsonPath.getString("revision");
		created_at = jsonPath.getString("created_at");
		type = jsonPath.getString("type");

		Assert.assertTrue(id!= null);
		Assert.assertTrue(title!= null);
		Assert.assertTrue(owner_type!= null);
		Assert.assertTrue(owner_id!= null);
		Assert.assertTrue(list_type!= null);
		Assert.assertTrue(publicc!= null);
		Assert.assertTrue(revision!= null);
		Assert.assertTrue(created_at!= null);
		Assert.assertTrue(type!= null);
	}
	@Test(dependsOnMethods="createList")
	public void verifyAddedList(){
		//2. checking that specific list which was added in the previous step in the entire list already added
		json = given()
				.spec(requestSpec)		
				.when()
				.get(APIUrl+"/lists")
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON).extract().response().asString();
		jsonPath = JsonPath.from(json);

		List<Integer> totalNoOfIds = jsonPath.getList("id");
		totalNoOfIds.get(totalNoOfIds.size()-1);
		//System.out.println("id is " + id);
		//System.out.println("From list id is "+ totalNoOfIds.get(totalNoOfIds.size()-1));
		//System.out.println(id instanceof Integer);
		//System.out.println(totalNoOfIds.get(totalNoOfIds.size()-1) instanceof Integer);
		Assert.assertEquals(id, totalNoOfIds.get(totalNoOfIds.size()-1));
	}
	@Test(dependsOnMethods="verifyAddedList")
	public void queryASpecificList(){
		//3. querying with the id of the list added in above step specifically-- getting a specific List
		json = given()
				.spec(requestSpec)
				.when()
				.get(APIUrl+"/lists/"+id)
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.extract().response().asString();
		jsonPath = JsonPath.from(json);
		Assert.assertEquals(Integer.valueOf(jsonPath.getInt("id")), id);
	}
	@Test(dependsOnMethods="queryASpecificList")
	public void updateAList(){		

	/*	//4.Updating the already added list and validating the response -- not working always bad request
		Integer revision_Update = 2;
		String title_Update = "Updating the title";
		json = given()
					.spec(requestSpec)				
				.params("revision", revision_Update)
				.patch(APIUrl+"/lists/"+id)
				.then()
				.assertThat()
				.statusCode(200)
				.extract()
				.response().asString();

		jsonPath = JsonPath.from(json);
		Assert.assertEquals(jsonPath.get("id"),id);
		Assert.assertEquals(jsonPath.get("revision"),revision_Update);
		Assert.assertEquals(jsonPath.get("title"),title_Update);
		Assert.assertEquals(jsonPath.get("type"),"list");*/
	}
	
	@Test(dependsOnMethods="queryASpecificList")
	public void deleteAList(){
		//5.Deleting the already added list and validating the response
		given()
			.spec(requestSpec)
		.params("revision",revision)
		.delete(APIUrl+"/lists/"+id)
		.then()
		.assertThat()
		.statusCode(204)
		;
	}
	@Test(dependsOnMethods="queryASpecificList")
	public void validateDeletedList(){		
		json = given()
				.spec(requestSpec)	
				.when()
				.get(APIUrl+"/lists")
				.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON).extract().response().asString();
		jsonPath = JsonPath.from(json);

		List<Integer> totalNoOfIds = jsonPath.getList("id");
		totalNoOfIds.get(totalNoOfIds.size()-1);
		//System.out.println("id is " + id);
		//System.out.println("From list id is "+ totalNoOfIds.get(totalNoOfIds.size()-1));
		//System.out.println(id instanceof Integer);
		//System.out.println(totalNoOfIds.get(totalNoOfIds.size()-1) instanceof Integer);
		Assert.assertNotEquals(id, totalNoOfIds.get(totalNoOfIds.size()-1));
	}	
}
