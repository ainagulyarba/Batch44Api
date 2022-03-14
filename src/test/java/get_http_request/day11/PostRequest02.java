package get_http_request.day11;

import base_url.DummyBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import test_data.DummyTestData;
import test_data.HepOkuAppTestData;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class PostRequest02 extends DummyBaseUrl {

            /*
            http://dummy.restapiexample.com/api/v1/create url ine, Request Body olarak
        {
            "name":"Ali Can",
            "salary":"2000",
            "age":"40",
        }
        gönderildiğinde,Status kodun 200 olduğunu ve dönen response body nin,

        {
            "status": "success",
            "data": {
            "id":…
        },
            "message": "Successfully! Record has been added."
        }

        olduğunu test edin
         */

    @Test
    public void test02(){

        // 1-Url olustur

        spec02.pathParams("1", "api", "2", "v1", "3", "create");

        // 2-Expected Data
        DummyTestData obje =new DummyTestData();

        // Request data icin
        HashMap<String, Object> requestBodyMap = obje.setUpRequestBody();
        System.out.println("requestBodyMap = " + requestBodyMap);  // requestBodyMap = {name=Ali Can, salary=2000, age=40}

        // Expected data icin
        HashMap<String, Object> expectedDataMap = obje.setUpExpectedData();
        System.out.println("expextedDataMap = " + expectedDataMap);  // expextedDataMap = {message=Successfully! Record has been added., statusCode=200, status=success}


        // 3- Request ve Response
        Response response = given()
                .contentType(ContentType.JSON)
                .spec(spec02)
                .body(requestBodyMap)
                .when()
                .post("/{1}/{2}/{3}");
        // POST isleminde  JSONObject kullandigimizda toString gerek var ( .body(expectedRequest.toString()) ) ,
                        // ama Map kullandigimiza toString'e gerek yok (.body(requestBodyMap))
        
        response.prettyPrint();


        // 4- Dogrulama

        response.then().assertThat().statusCode(200);

        // De-Serialization
        HashMap<String, Object> actualDataMap = response.as(HashMap.class);
        System.out.println("actualDataMap = " + actualDataMap);

        Assert.assertEquals(expectedDataMap.get("statusCode"), response.getStatusCode());
        Assert.assertEquals(expectedDataMap.get("message"), actualDataMap.get("message"));
        Assert.assertEquals(expectedDataMap.get("status"), actualDataMap.get("status"));

        // JSON PATH
        JsonPath json = response.jsonPath();

        Assert.assertEquals(expectedDataMap.get("statusCode"), response.getStatusCode());
        Assert.assertEquals(expectedDataMap.get("message"), json.getString("message"));
        Assert.assertEquals(expectedDataMap.get("status"), json.getString("status"));



    }



}
