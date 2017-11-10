package com.debarnab.jsonExtraction;
import junit.framework.Assert;
import org.testng.annotations.Test;

public class jsonTesting {

//	@Test
//	public void testCase() throws JSONException{
//		String json = "{\n" +
//				"  \"semester\": \"Fall 2015\",\n" +
//				"  \"groups\": [\n" +
//				"    {\n" +
//				"      \"siteUrl\": \"http://cphbusinessjb.cloudapp.net/CA2/\",\n" +
//				"      \"error\": \"NO AUTHOR/CLASS-INFO\"\n" +
//				"    },\n" +
//				"    {\n" +
//				"      \"siteUrl\": \"http://ca2-ebski.rhcloud.com/CA2New/\",\n" +
//				"      \"authors\": \"Ebbe, Kasper, Christoffer\",\n" +
//				"      \"class\": \"A klassen\",\n" +
//				"      \"group\": \"Gruppe: Johns Llama Herders A/S\"\n" +
//				"    },\n" +
//				"    {\n" +
//				"      \"siteUrl\": \"http://ca2-chrislind.rhcloud.com/CA2Final/\",\n" +
//				"      \"error\": \"NO AUTHOR/CLASS-INFO\"\n" +
//				"    },\n" +
//				"    {\n" +
//				"      \"siteUrl\": \"http://ca2-pernille.rhcloud.com/NYCA2/\",\n" +
//				"      \"authors\": \"Marta, Jeanette, Pernille\",\n" +
//				"      \"class\": \"DAT A\",\n" +
//				"      \"group\": \"Group: MJP\"\n" +
//				"    },\n" +
//				"    {\n" +
//				"      \"siteUrl\": \"https://ca2-afn.rhcloud.com:8443/company.jsp\",\n" +
//				"      \"error\": \"NO AUTHOR/CLASS-INFO\"\n" +
//				"    },\n" +
//				"    {\n" +
//				"      \"siteUrl\": \"http://ca-smcphbusiness.rhcloud.com/ca2/index.jsp\",\n" +
//				"      \"authors\": \"Mikkel, Steffen, B Andersen\",\n" +
//				"      \"class\": \"A Class Computer Science\",\n" +
//				"      \"group\": \"1\"\n" +
//				"    }\n" +
//				"  ]\n" +
//				"}";
//		JSONObject jsnobject = new JSONObject(json);
//		org.json.JSONArray jsonArray = jsnobject.getJSONArray("groups");
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject explrObject = jsonArray.getJSONObject(i);
//			if(explrObject.get("siteUrl").equals("http://ca-smcphbusiness.rhcloud.com/ca2/index.jsp")){
//				System.out.println(explrObject);
//				Assert.assertEquals(explrObject.getString("authors"), "Mikkel, Steffen, B Andersen");
//			}
//		}
//	}

}
