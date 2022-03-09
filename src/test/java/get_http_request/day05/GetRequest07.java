package get_http_request.day05;

import base_url.ReqresinBaseUrl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetRequest07 extends ReqresinBaseUrl {


    /*
https://reqres.in/api/users URL request olustur.
body icerisindeki id'si 5 olan datayi
1) Matcher CLASS ile
2) JsonPath ile dogrulayin.
*/


@Test
    public  void test07(){

    spec01.pathParams("parametre1","api","parametre2", "users");

    // https://reqres.in

    Response response=given().spec(spec01).when().get("/{parametre1}/{parametre2}");
    // "/{parametre1}/{parametre2}" -> /api/users

    // response.prettyPrint();


    // Matchers Class
    response.then().assertThat().body("data[4].email", equalTo("charles.morris@reqres.in"),
                                     "data[4].first_name", equalTo("Charles"),
                                    "data[4].last_name",equalTo("Morris"));

    // JsonPath
    JsonPath json= response.jsonPath();

    System.out.println("json.getList(\"data.email\") = " + json.getList("data.email"));
    // getString ve getList ayni gorevi yapiyor

    System.out.println("json.getString(\"data.first_name\") = " + json.getString("data.first_name"));

    System.out.println("json.getString(\"data.last_name\") = " + json.getString("data.last_name"));

    Assert.assertEquals("charles.morris@reqres.in",json.getString("data[4].email"));

    Assert.assertEquals("Charles",json.getString("data[4].first_name"));
    Assert.assertEquals("Morris", json.getString("data[4].last_name"));







}








}
