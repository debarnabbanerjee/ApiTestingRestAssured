package com.debarnab.restAssured.Testing;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.sessionId;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
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

public class folderTests {
    private final String client_ID = "3ccf93b534acfd0dc428";
    //private final String clientSecret="9b32bcf236865123cc1bff130fbde38c2641d807fb942e733f942a79dfe1";
    private final String access_Token = "524f85fa3d80b5de4ae35f0b3e160ce004a9ae23c2c6581fa7837e310d14";

    //https://developer.wunderlist.com/documentation
    private String APIUrl = "https://a.wunderlist.com/api/v1";
    private String json;
    private JsonPath jsonPath;
    private Integer id;
    private String title;
    private String owner_type;
    private String owner_id;
    private String list_type;
    private String publicc;
    private String revision;
    private String created_at;
    private String type;
    private RequestSpecBuilder requestBuilder;
    private RequestSpecification requestSpec;
    private Integer folderCreationid;
    final private String newFolderName = "Debarnab's Poland Folder";

    @BeforeClass
    public void makeRequest() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("X-Access-Token", access_Token);
        headers.put("X-Client-ID", client_ID);

        requestBuilder = new RequestSpecBuilder();
        requestBuilder.addHeaders(headers);
        requestBuilder.setContentType(ContentType.JSON);
        requestSpec = requestBuilder.build();
    }

    @Test
    public void authTestCase() {
        json = given()
                .headers("X-Access-Token", access_Token, "X-Client-ID", client_ID)
                .auth()
                .oauth2(access_Token)
                .when()
                .get(APIUrl + "/user")
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(200).extract().response().asString();

        jsonPath = JsonPath.from(json);
        Assert.assertTrue(jsonPath.getString("name").equalsIgnoreCase("Debarnab Banerjee"));
        Assert.assertTrue(jsonPath.getString("email").equalsIgnoreCase("debarnab.banerjee@gmail.com"));
    }

    @Test(dependsOnMethods = "authTestCase")
    public void getAllFoldersCreatedbyTheCurrentUser() {
        json = given()
                .spec(requestSpec)
                .when().get(APIUrl + "/folders")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response().asString();

       // System.out.println("Get all folders response \n" + json);
    }

    public String generateStringFromJson(String pathOfTheFile) {
        try {
            return new String(Files.readAllBytes(Paths.get(pathOfTheFile)));
        } catch (Exception e) {
            return "Unable to extract string from teh json file" + e.getMessage().toString();
        }
    }

    @Test(dependsOnMethods = {"authTestCase", "getAllFoldersCreatedbyTheCurrentUser"})
    public void CreateAFolder() {
        HashMap<String, Object> dataPayload = new HashMap<String, Object>();
        dataPayload.put("title", newFolderName);
        dataPayload.put("list_ids", new int[]{290279288, 290338194, 290338226, 290338281});

        json = given()
                .spec(requestSpec)
                .body(dataPayload)
                .contentType(ContentType.JSON)
                .post(APIUrl + "/folders")
                .then()
                .assertThat()
                .statusCode(201)
                .extract().response().asString();
        //System.out.println("Create Folder Response is \n" + json);

        jsonPath = JsonPath.from(json);
        folderCreationid = Integer.valueOf(jsonPath.getString("id"));
        revision = jsonPath.getString("revision");
        String title = jsonPath.get("title");
        Assert.assertTrue(folderCreationid != null);
        Assert.assertEquals(newFolderName, title);
        Assert.assertTrue(Integer.valueOf(revision) != 0);
    }

    @Test(dependsOnMethods = {"authTestCase", "getAllFoldersCreatedbyTheCurrentUser", "CreateAFolder"})
    public void deleteCreatedFolder() {
        json = given()
                .spec(requestSpec)
                .param("revision", revision)
                .delete(APIUrl + "/folders/" + folderCreationid)
                .then()
                .assertThat()
                .statusCode(204)
                .extract().response().asString();

//        System.out.println("folderCreationid" + folderCreationid);
    }

    @Test(dependsOnMethods = {"authTestCase", "getAllFoldersCreatedbyTheCurrentUser", "CreateAFolder", "deleteCreatedFolder"})
    public void searchWithTheDeletedFolder() {
        json = given()
                .spec(requestSpec)
                .when().get(APIUrl + "/folders")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response().asString();

        jsonPath = JsonPath.from(json);
        List<Integer> lists = jsonPath.get("id");
        for (int i = 0; i < lists.size(); i++) {
           // System.out.println(lists.get(i));
            if (lists.get(i) == folderCreationid) {
                Assert.fail("Deleted Folder is still present " + folderCreationid);
            }
        }
    }
}

