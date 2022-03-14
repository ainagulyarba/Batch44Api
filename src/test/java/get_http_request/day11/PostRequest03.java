package get_http_request.day11;

import base_url.JsonPlaceHolderBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import test_data.JsonPlaceHolderTestData;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostRequest03 extends JsonPlaceHolderBaseUrl {


/*
   https://jsonplaceholder.typicode.com/todos URL ine aşağıdaki body gönderildiğinde,
    {
    "userId": 55,
    "title": "Tidy your room",
    "completed": false
  }
    Dönen response un Status kodunun 201 ve response body nin aşağıdaki gibi olduğunu test edin
  {
    "userId": 55,
    "title": "Tidy your room",
    "completed": false,
    "id": 201
   }
*/


    @Test
    public void  test03(){

        // 1-URL olustur
        spec04.pathParams("1","todos");

        // 2-Expected Data
        JsonPlaceHolderTestData testObje = new JsonPlaceHolderTestData();
        JSONObject expectedRequest =testObje.setUpPostData();

        // 3-Request ve Response
        Response response =given()
                .contentType(ContentType.JSON)
                .spec(spec04)
                .body(expectedRequest.toString())
                .when()
                .post("/{1}");
        response.prettyPrint();
        // POST isleminde  JSONObject kullandigimizda toString gerek var ( .body(expectedRequest.toString()) ) ,
                    // ama Map kullandigimiza toString'e gerek yok (.body(requestBodyMap))

        // 4-Dogrulama

        // MATCHERS CLASS
        response.then().assertThat().statusCode(201)   // boyle de olur -> .statusCode(expectedRequest.getInt("statusCode"))
                .body("userId", equalTo(expectedRequest.get("userId"))
                        , "title", equalTo(expectedRequest.get("title"))
                        ,"completed", equalTo(expectedRequest.get("completed"))
                        ,"id", equalTo(expectedRequest.get("id")));

        // Json Path
        JsonPath json =response.jsonPath();

        Assert.assertEquals(expectedRequest.getInt("statusCode"), response.getStatusCode());
        Assert.assertEquals(expectedRequest.getInt("userId"), json.getInt("userId"));
        Assert.assertEquals(expectedRequest.getString("title"), json.getString("title"));
        Assert.assertEquals(expectedRequest.getBoolean("completed"), json.getBoolean("completed"));
        Assert.assertEquals(expectedRequest.getInt("id"), json.getInt("id"));

        // De-Serialization
        HashMap<String, Object> actualDataMap= response.as(HashMap.class);

        Assert.assertEquals(expectedRequest.get("statusCode"), response.getStatusCode());
        Assert.assertEquals(expectedRequest.getInt("userId"), actualDataMap.get("userId"));
        Assert.assertEquals(expectedRequest.getString("title"), actualDataMap.get("title"));
        Assert.assertEquals(expectedRequest.getBoolean("completed"), actualDataMap.get("completed"));
        Assert.assertEquals(expectedRequest.getInt("id"), actualDataMap.get("id"));

    }




}
