package Specification.Booking;

import Utils.Configuration;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecificationBooking {
  public static RequestSpecification requestSpecification(){
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(Configuration.BASE_URL)
                .setBasePath(Configuration.BASE_PATH)
                .build();
    }
}
