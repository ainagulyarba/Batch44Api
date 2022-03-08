package get_http_request.day01;

import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class GetRequest01 {



@Test
    public void test01(){
    String url="https://restful-booker.herokuapp.com/booking/";
    Response response =given().when().get(url);
    //response.prettyPrint();
    System.out.println("Status code: " + response.statusCode());
    System.out.println("Content Type: "+response.contentType());
    System.out.println("Status Line: "+ response.statusLine());
    System.out.println("Time: " + response.time());

    Assert.assertEquals(200,response.statusCode());
    Assert.assertEquals("application/json; charset=utf-8",response.contentType());

    response.
            then().
            assertThat().
            statusCode(200).
            contentType("application/json; charset=utf-8").
            statusLine("HTTP/1.1 200 OK");

}




}
