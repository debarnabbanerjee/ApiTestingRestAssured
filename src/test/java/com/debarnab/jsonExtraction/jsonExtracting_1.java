package com.debarnab.jsonExtraction;




import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class jsonExtracting_1 {

	@Test
	public void try1() throws JSONException{		
		String data = "[{\"userName\": \"sandeep\",\"age\":30},{\"userName\": \"vivan\",\"age\":5}]";
		JSONArray jsonArr = new JSONArray(data);
		for (int i = 0; i < jsonArr.length(); i++)
		{
			JSONObject jsonObj = jsonArr.getJSONObject(i);

			//System.out.println(jsonObj);
		}
	}

	@Test
	public void try2() throws JSONException{
		String json = "{\n" +
				"  \"semester\": \"Fall 2015\",\n" +
				"  \"groups\": [\n" +
				"    {\n" +
				"      \"siteUrl\": \"http://cphbusinessjb.cloudapp.net/CA2/\",\n" +
				"      \"error\": \"NO AUTHOR/CLASS-INFO\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"siteUrl\": \"http://ca2-ebski.rhcloud.com/CA2New/\",\n" +
				"      \"authors\": \"Ebbe, Kasper, Christoffer\",\n" +
				"      \"class\": \"A klassen\",\n" +
				"      \"group\": \"Gruppe: Johns Llama Herders A/S\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"siteUrl\": \"http://ca2-chrislind.rhcloud.com/CA2Final/\",\n" +
				"      \"error\": \"NO AUTHOR/CLASS-INFO\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"siteUrl\": \"http://ca2-pernille.rhcloud.com/NYCA2/\",\n" +
				"      \"authors\": \"Marta, Jeanette, Pernille\",\n" +
				"      \"class\": \"DAT A\",\n" +
				"      \"group\": \"Group: MJP\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"siteUrl\": \"https://ca2-afn.rhcloud.com:8443/company.jsp\",\n" +
				"      \"error\": \"NO AUTHOR/CLASS-INFO\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"siteUrl\": \"http://ca-smcphbusiness.rhcloud.com/ca2/index.jsp\",\n" +
				"      \"authors\": \"Mikkel, Steffen, B Andersen\",\n" +
				"      \"class\": \"A Class Computer Science\",\n" +
				"      \"group\": \"1\"\n" +
				"    }\n" +
				"  ]\n" +
				"}";

		JSONObject jsonObj = new JSONObject(json);
		JSONArray jsonArr = jsonObj.getJSONArray("groups");
		for (int i = 0; i < jsonArr.length(); i++)
		{
			JSONObject jsonObj1 = jsonArr.getJSONObject(i);	
			if(jsonObj1.get("siteUrl").equals("http://ca-smcphbusiness.rhcloud.com/ca2/index.jsp"))
				Assert.assertEquals(jsonObj1.get("authors"),"Mikkel, Steffen, B Andersen");
		}
	}
}
