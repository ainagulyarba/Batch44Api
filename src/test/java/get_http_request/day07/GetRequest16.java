package get_http_request.day07;

import base_url.JsonPlaceHolderBaseUrl;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetRequest16 extends JsonPlaceHolderBaseUrl {
    
   
    
    @Test
    public void test16(){
        
         
     /*
   https://jsonplaceholder.typicode.com/todos/7

   {
   "userId": 1,
   "id": 7,
   "title": "illo expedita consequatur quia in",
   "completed": false
}
    */
        
        //1- Url olusturma
        spec04.pathParams("bir", "todos", "iki", 7);
        
        //2-Expected (beklenen) Data olustur
        Map<String, Object> expectedData= new HashMap<>();
        expectedData.put("userId", 1); // put ile olusturduk
        expectedData.put("id", 7);
        expectedData.put("title", "illo expedita consequatur quia in");
        expectedData.put("completed", false);

        System.out.println("expectedData = " + expectedData); // expectedData = {id=7, completed=false, title=illo expedita consequatur quia i, userId=1}

        //3-Request ve Response

        // spec04 -> https://jsonplaceholder.typicode.com    /{bir}/{iki} -> todos/7
       Response response = given().spec(spec04).when().get("/{bir}/{iki}");

       // response.prettyPrint();

        // Data'yi Json'dan -> Java'ya =  De-Serialization
        // Data'yi Java'dan -> Json'a  =  Serialization =serilestirme

        Map<String,Object> actualData= response.as(HashMap.class);  // De-Serialization

        System.out.println("actualData = " + actualData); //actualData = {id=7, completed=false, title=illo expedita consequatur quia in, userId=1}

        // actualData DataBase 'deki olan bilgiler yani Json formatinda
        // expectedData burdan DataBase'e gonderecegimiz bilgiler yani Java formatinda

        Assert.assertEquals(expectedData.get("userId"), actualData.get("userId"));
        Assert.assertEquals(expectedData.get("id"), actualData.get("id"));
        Assert.assertEquals(expectedData.get("title"), actualData.get("title"));
        Assert.assertEquals(expectedData.get("completed"), actualData.get("completed"));













    }
    
    
    
    
    
    
    
    
}
