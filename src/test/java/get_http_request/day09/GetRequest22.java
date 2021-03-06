package get_http_request.day09;

import base_url.HerOkuAppBaseUrl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import test_data.HepOkuAppTestData;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetRequest22 extends HerOkuAppBaseUrl {


    @Test
    public void test22(){

    /*
    https://restful-booker.herokuapp.com/booking/47
       {
           "firstname": "Ali",
           "lastname": "Can",
           "totalprice": 500,
           "depositpaid": true,
           "bookingdates": {
               "checkin": "2022-02-01",
               "checkout": "2022-02-11"
          }
       }
    1) JsonPhat
    2) De-Serialization
    */

        // 1-Yol olustur
        spec05.pathParams("1", "booking", "2", 17);

        // 2-Expected Data olustur
        HepOkuAppTestData expectedObje= new HepOkuAppTestData();

        HashMap<String , Object> expectedTestDataMap = expectedObje.setUpTestData();

        System.out.println("Expected Data icindeki Expected Data" + expectedTestDataMap);
        //{firstname=Ali,
        // bookingdates={
        //                 checkin=2022-02-01,
        //                 checkout=2022-02-11},
        // totalprice=500,
        // depositpaid=true,
        // lastname=Can}

        // 3-Ruequest ve Response
        Response response = given().spec(spec05).when().get("/{1}/{2}");
        response.prettyPrint(); // Json formatinda yazdirdik
        

        // Dogrulama
        // 1.YOL De-Serialization
        HashMap<String , Object> actualData = response.as(HashMap.class);  // Json formatindan Java formatina cevirdik

        System.out.println("actualData = " + actualData); //Java formatinda yazdirdik
        //actualData = {firstname=Ali, bookingdates={checkin=2022-02-01, checkout=2022-02-11}, totalprice=500, depositpaid=true, lastname=Can}

        Assert.assertEquals(expectedTestDataMap.get("firstname"), actualData.get("firstname"));
        Assert.assertEquals(expectedTestDataMap.get("lastname"), actualData.get("lastname"));
        Assert.assertEquals(expectedTestDataMap.get("totalprice"), actualData.get("totalprice"));
        Assert.assertEquals(expectedTestDataMap.get("depositpaid"), actualData.get("depositpaid"));
        Assert.assertEquals(((Map)expectedTestDataMap.get("bookingdates")).get("checkin"), ((Map) actualData.get("bookingdates")).get("checkin"));
        Assert.assertEquals(((Map) expectedTestDataMap.get("bookingdates")).get("checkout"),((Map) actualData.get("bookingdates")).get("checkout"));

        // 2.YOL Json Path
        JsonPath json = response.jsonPath();
        Assert.assertEquals(expectedTestDataMap.get("firstname"), json.getString("firstname"));
        Assert.assertEquals(expectedTestDataMap.get("lastname"), json.getString("lastname"));
        Assert.assertEquals(expectedTestDataMap.get("totalprice"), json.getInt("totalprice"));
        Assert.assertEquals(expectedTestDataMap.get("depositpaid"), json.getBoolean("depositpaid"));

        Assert.assertEquals(((Map<?, ?>) expectedTestDataMap.get("bookingdates")).get("checkin"), json.getString("bookingdates.checkin"));
        Assert.assertEquals(((Map<?, ?>) expectedTestDataMap.get("bookingdates")).get("checkout"), json.getString("bookingdates.checkout"));

        // 3) Matchers Class ile
        response.then().assertThat().body("firstname", equalTo(expectedTestDataMap.get("firstname"))
                ,"lastname",equalTo(expectedTestDataMap.get("lastname"))
                ,"otalprice",equalTo(expectedTestDataMap.get("otalprice"))
                ,"depositpaid",equalTo(expectedTestDataMap.get("depositpaid"))
                ,"bookingdates[0]",equalTo(expectedTestDataMap.get(".checkin"))
                ,"bookingdates[1]",equalTo(expectedTestDataMap.get("checkout")));



    }





}
