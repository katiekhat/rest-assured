package tests;

import calls.BookingCalls;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BookingTest {
    @Test
    public void createBookingTest(){
        BookingCalls bookingCalls=new BookingCalls();
        Response response=bookingCalls.createBooking("Keti",
                "Khatiashvili",500);
        Assert.assertEquals(response.getStatusCode(),200);
        int bookingId=response.jsonPath().getInt("bookingid");
        Assert.assertTrue(bookingId>0);
    }
}
