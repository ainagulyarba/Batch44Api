package get_http_request.day13;

import base_url.HerOkuAppBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import pojosYeni.Booking;
import pojosYeni.Bookingdates;
import pojosYeni.HerOkuApp;

import static io.restassured.RestAssured.given;

public class PostRequest03 extends HerOkuAppBaseUrl {

    @Test
    public void test03(){
        spec05.pathParams("bir", "booking");
        Bookingdates bookingdates=new Bookingdates("2022-03-01","2022-03-11");
        Booking booking=new Booking("Ali","Can",500,true,bookingdates);

        System.out.println("booking = " + booking);

        Response response=given().spec(spec05).
                contentType(ContentType.JSON)
                .auth().basic("admin","password123")
                .body(booking).when().post("/{bir}");

        response.prettyPrint();
        HerOkuApp actualHerokuapp=response.as(HerOkuApp.class);
        System.out.println("actualHerokuapp = " + actualHerokuapp);
    }
}
