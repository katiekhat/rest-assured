package calls;

import io.restassured.response.Response;
import models.BookingModel;


import static io.restassured.RestAssured.given;

public class BookingCalls {

    //RequestBody json სახით -> ბუქინგის დამატება
    public Response createBooking(String firstname,String lastname, int totalprice) {
        String requestBody = """
                {
                    "firstname":"%s",
                    "lastname":"%s",
                    "totalprice":%d,
                    "depositpaid":true,
                    "bookingdates":{
                     "checkin":"2026-09-10",
                     "checkout":"2026-09-15"
                    },
                    "additionalneeds":"Breakfast"
                }
                """.formatted(firstname, lastname, totalprice);
        return given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("https://restful-booker.herokuapp.com/booking")
                .then()
                .extract()
                .response();
    }

    //ბუქინგის დამატება Model საშუალებით

    public Response createBooking(BookingModel booking){
        return given()
                .contentType("application/json")
                //serialization ხდება აქ ,
                // BookingModel გარდაიქმნება json-ად და იგზავნება post
                // მეთოდის Request body-ში
                .body(booking)
                .when()
                .post("https://restful-booker.herokuapp.com/booking")
                .then()
                .extract()
                .response();
    }

    public Response getBooking(int bookingId){
        return given()
                .when()
                .get("https://restful-booker.herokuapp.com/booking"+bookingId)
                .then()
                .extract()
                .response();


    }
}
