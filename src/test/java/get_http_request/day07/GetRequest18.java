package get_http_request.day07;

import base_url.GMIBankBaseUrl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetRequest18 extends GMIBankBaseUrl {

@Test
    public void test18(){

    /*
http://www.gmibank.com/api/tp-customers/43703

"firstName": "Alda",
"lastName": "Monahan",
"middleInitial": "Nichelle Hermann Kohler",
"email": "com.github.javafaker.Name@7c011174@gmail.com",
"mobilePhoneNumber": "909-162-8114",
"city": "St Louis",
"ssn": "108-53-6655"

1) MATCHERS CLASS
2) JSON PATH
3) De-Serialization
 */

    // 1- URL olustur
    spec03.pathParams("bir", "tp-customers", "iki" , "43703");

    // 2 - Expected Data olustur
    Map<String,Object> expectedData= new TreeMap<>();

    expectedData.put("firstName", "Alda");
    expectedData.put("lastName", "Monahan");
    expectedData.put("middleInitial", "Nichelle Hermann Kohler");
    expectedData.put("email", "com.github.javafaker.Name@7c011174@gmail.com");
    expectedData.put("mobilePhoneNumber", "909-162-8114");
    expectedData.put("city", "St Louis");
    expectedData.put("ssn", "108-53-6655");
    System.out.println("expectedData = " + expectedData);

    // 3 - Request ve Response
    Response response= given()
            .header("Authorization", "Bearer "+ generateToken())
            .spec(spec03)
            .when()
            .get("/{bir}/{iki}");

   response.prettyPrint();

    // 4 - Dogrulama

    // 1. Yol MATCHER CLASS ile
    response.then().body("firstName", equalTo("Alda")
            , "lastName", equalTo("Monahan")
            , "email", equalTo("com.github.javafaker.Name@7c011174@gmail.com")
            , "mobilePhoneNumber", equalTo("909-162-8114")
            , "city", equalTo("St Louis")
            , "ssn", equalTo("108-53-6655"));


    // 2. Yol JSON PATH ile
    JsonPath json=response.jsonPath();

    Assert.assertEquals("Alda", json.getString("firstName"));
    Assert.assertEquals("Monahan", json.getString("lastName"));
    Assert.assertEquals("Nichelle Hermann Kohler", json.getString("middleInitial"));
    Assert.assertEquals("com.github.javafaker.Name@7c011174@gmail.com", json.getString("email"));
    Assert.assertEquals("909-162-8114", json.getString("mobilePhoneNumber"));
    Assert.assertEquals("St Louis", json.getString("city"));
    Assert.assertEquals("108-53-6655", json.getString("ssn"));



    // 3. Yol De-Serialization
    Map<String,Object> actualData =response.as(TreeMap.class);  //  De-Serialization yaptik

    // Data'yi Json formatindan -> Java'ya =  De-Serialization
    // Data'yi Java'dan -> Json'a  =  Serialization =serilestirme

    // De-Serialization asagidaki JSON formatindaki Data'yi Map'e donusturur
/*
{
    "id": 43703,
    "firstName": "Alda",
    "lastName": "Monahan",
    "middleInitial": "Nichelle Hermann Kohler",
    "email": "com.github.javafaker.Name@7c011174@gmail.com",
    "mobilePhoneNumber": "909-162-8114",
    "phoneNumber": "231-501-9849",
    "zipCode": "67321",
    "address": "I live in St louis MO",
    "city": "St Louis",
    "ssn": "108-53-6655",
    "createDate": "0211-09-09T05:50:36Z",
    "zelleEnrolled": false,
    "country": null,
    "state": "",
    "user": null,
    "accounts": [

    ]
}

 */
    // JSON Data'sini Map'e donusmus hali
    /*
    actualData = {accounts=[], address=I live in St louis MO, city=St Louis, country=null,
    createDate=0211-09-09T05:50:36Z, email=com.github.javafaker.Name@7c011174@gmail.com,
    firstName=Alda, id=43703, lastName=Monahan, middleInitial=Nichelle Hermann Kohler,
    mobilePhoneNumber=909-162-8114, phoneNumber=231-501-9849, ssn=108-53-6655, state=,
    user=null, zelleEnrolled=false, zipCode=67321}

     */


    System.out.println("actualData = " + actualData);

    Assert.assertEquals(expectedData.get("firstName"), actualData.get("firstName"));
    Assert.assertEquals(expectedData.get("lastName"), actualData.get("lastName"));
    Assert.assertEquals(expectedData.get("middleInitial"), actualData.get("middleInitial"));
    Assert.assertEquals(expectedData.get("email"), actualData.get("email"));
    Assert.assertEquals(expectedData.get("mobilePhoneNumber"), actualData.get("mobilePhoneNumber"));
    Assert.assertEquals(expectedData.get("city"), actualData.get("city"));
    Assert.assertEquals(expectedData.get("ssn"), actualData.get("ssn"));



}



}
