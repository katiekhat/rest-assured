package calls;

import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

public class BookingCalls {
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
}
