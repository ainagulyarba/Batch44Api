package get_http_request.day10;

import base_url.HerOkuAppBaseUrl;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import test_data.HepOkuAppTestData;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class PostRequest01 extends HerOkuAppBaseUrl {

/*
   https://restful-booker.herokuapp.com/booking
   { "firstname": "Ali",
              "lastname": "Can",
              "totalprice": 500,
              "depositpaid": true,
              "bookingdates": {
                  "checkin": "2022-03-01",
                  "checkout": "2022-03-11"
               }
}
gönderildiğinde, Status kodun 200 olduğunu ve dönen response body nin ,
}
   "booking": {
       "firstname": "Ali",
       "lastname": "Can",
       "totalprice": 500,
       "depositpaid": true,
       "bookingdates": {
                           "checkin": "2022-03-01",
                            "checkout": "2022-03-11"
       }
   }
}
olduğunu test edin
    */

    @Test
    public void test01(){

        // 1-Url olustur

        spec05.pathParams("1", "booking");

        // 2-Expected Data
        HepOkuAppTestData testData =new HepOkuAppTestData();
        JSONObject expectedRequestData= testData.setUpTestAndRequestData();
        System.out.println("expectedRequestData = " + expectedRequestData);
        // expectedRequestData = {"firstname":"Ali",
        // "bookingdates":
        // {"checkin":"2022-03-01",
        // "checkout":"2022-03-11"},
        // "totalprice":500,
        // "depositpaid":true,
        // "lastname":"Can"}
        //Unsupported Media Type


        // 3-Request ve Response
        Response response = given()
                .contentType(ContentType.JSON)
                .auth()
                .basic("admin", "password123")
                .spec(spec05)
                .body(expectedRequestData.toString())
                .when()
                .post("/{1}");
        // JSONObject' te toString() kullanmaliyiz  ->  .body(expectedRequestData.toString())
        response.prettyPrint();

        // 4- Dogrulama
        // 1.Yol JSON PATH
        JsonPath json = response.jsonPath();

        response.then().assertThat().statusCode(200);

        Assert.assertEquals(expectedRequestData.getString("firstname"), json.getString("booking.firstname"));
        Assert.assertEquals(expectedRequestData.getString("lastname"), json.getString("booking.lastname"));
        Assert.assertEquals(expectedRequestData.getInt("totalprice"), json.getInt("booking.totalprice"));
        Assert.assertEquals(expectedRequestData.getBoolean("depositpaid"), json.getBoolean("booking.depositpaid"));

        Assert.assertEquals(expectedRequestData.getJSONObject("bookingdates").getString("checkin"), json.getString("booking.bookingdates.checkin"));
        Assert.assertEquals(expectedRequestData.getJSONObject("bookingdates").getString("checkout"), json.getString("booking.bookingdates.checkout"));




    }


}
