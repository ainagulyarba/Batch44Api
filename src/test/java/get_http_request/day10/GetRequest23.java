package get_http_request.day10;

import base_url.DammyBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import test_data.DummyTestData;

import java.util.*;

import static io.restassured.RestAssured.given;

public class GetRequest23 extends DammyBaseUrl {



@Test
    public void test23(){

 /*
http://dummy.restapiexample.com/api/v1/employees url ine bir istek gönderildiğinde
Status kodun 200 olduğunu,
14. Çalışan isminin "Haley Kennedy" olduğunu,
Çalışan sayısının 24 olduğunu,
Sondan 3. çalışanın maaşının 675000 olduğunu
40,21 ve 19 yaslarında çalışanlar olup olmadığını
10. Çalışan bilgilerinin bilgilerinin aşağıdaki gibi

{
        "id": 10,
        "employee_name": "Sonya Frost",
        "employee_salary": 103600,
        "employee_age": 23,
        "profile_image": ""
 }

  olduğunu test edin.
*/

    // 1-URL olustur
    spec02.pathParams("1","api", "2", "v1", "3", "employees");

    // 2-Expected data olustur
    DummyTestData expectedObje= new DummyTestData();
    HashMap<String, Object> expectedTestData= expectedObje.setUpTestData();
    System.out.println("expectedTestData = " + expectedTestData);
    // expectedTestData = {arananYaslar=[40, 21, 19],
    // sondanUcuncuCalisaninMaasi=675000,
    // calisanSayisi=24,
    // statusCode=200,
    // ondorduncuCalisan=Haley Kennedy,
    // onuncuCalisan={profile_image=,
                    // employee_name=Sonya Frost,
                    // employee_salary=103600,
                    // id=10,
                    // employee_age=23}}

    // 3-Request ve Response olustur
    Response response = given().spec(spec02).contentType(ContentType.JSON).when().get("/{1}/{2}/{3}");
    // response.prettyPrint();

    // 4-Dogrulama

    // 1.yol De-Serilization
    HashMap<String, Object> actualDataMap=response.as(HashMap.class);
   // System.out.println("actualDataMap = " + actualDataMap);

    // Status kodun 200 olduğunu,
    Assert.assertEquals(expectedTestData.get("statusCode"), response.getStatusCode());

    // 14. Çalışan isminin "Haley Kennedy" olduğunu,
    Assert.assertEquals(expectedTestData.get("ondorduncuCalisan"), ((Map)((List)actualDataMap.get("data")).get(13)).get("employee_name"));

    // Çalışan sayısının 24 olduğunu,
    Assert.assertEquals(expectedTestData.get("calisanSayisi"), ((List) actualDataMap.get("data")).size());

    // Sondan 3. çalışanın maaşının 675000 olduğunu
   // 1-yol


    //2-yol
    int dataSize=((List)actualDataMap.get("data")).size();
    Assert.assertEquals(expectedTestData.get("sondanUcuncuCalisaninMaasi"),
            ((Map) ((List)actualDataMap.get("data")).get(dataSize-3)).get("employee_salary"));

    // 40,21 ve 19 yaslarında çalışanlar olup olmadığını



    //1. Yol
    List<Integer> actualYasListesi = new ArrayList<>();

    for(int i =0; i<dataSize; i++){
        actualYasListesi.add(((Integer) ((Map)((List<?>) actualDataMap.get("data")).get(i)).get("employee_age")));
    }
    System.out.println("actualYasListesi = " + actualYasListesi);

    Assert.assertTrue(actualYasListesi.containsAll((Collection<?>) expectedTestData.get("arananYaslar")));

    //2. Yol
    List<Integer> employee_age = new ArrayList<>();
    for(int i=0 ; i < ((List)actualDataMap.get("data")).size() ; i++){
        employee_age.add((Integer) ((Map)((List)actualDataMap.get("data")).get(i)).get("employee_age"));
    }

    // 10. Çalışan bilgilerinin bilgilerinin aşağıdaki gibi




}

// body map'in icinde --> map icinde List(suan ki soru icin bunun ismi=data) var
// Bu List'i de her bir index'e gitmek icin kullaniyorum Orn=((List)actualDataMap.get("data")).get(13))
// her bir index'te bir Map oldugu icin (key-value iliskisi) .get("key") yazip value degere ulasiyorum








}
