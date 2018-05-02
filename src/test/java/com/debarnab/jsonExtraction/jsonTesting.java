package com.debarnab.jsonExtraction;

import org.json.JSONArray;
import org.json.JSONObject;

import org.testng.annotations.Test;

public class jsonTesting {

    public static void main(String[] args) {
        String response = "{\n" +
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
        try {
            JSONObject jsnobject = new JSONObject(response);
            JSONArray jsonArray = jsnobject.getJSONArray("groups");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject authorObject = jsonArray.getJSONObject(i);
                System.out.println(authorObject);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
