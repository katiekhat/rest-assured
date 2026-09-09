package tests;

import calls.BookingCalls;
import io.restassured.response.Response;
import models.BookingDatesModel;
import models.BookingModel;
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

    @Test
    public void createBookingModelTest(){
        BookingDatesModel dates=new BookingDatesModel();
        dates.checkin="2026-09-10";
        dates.checkout="2026-09-15";
        BookingModel booking=new BookingModel();
        booking.firstname="Nino";
        booking.lastname="Shiukashvili";
        booking.totalprice=600;
        booking.depositpaid=true;
        booking.bookingdates=dates;
        booking.additionalneeds="Breakfast";
        BookingCalls bookingCalls=new BookingCalls();
        Response response=bookingCalls.createBooking(booking);
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getString("booking.firstname"),booking.firstname);
        Assert.assertEquals(response.jsonPath().getString("booking.lastname"),booking.lastname);
        Assert.assertEquals(response.jsonPath().getInt("booking.totalprice"),booking.totalprice);
        Assert.assertEquals(response.jsonPath().getString("booking.bookingdates.checkin"),booking.bookingdates.checkin);
        Assert.assertEquals(response.jsonPath().getString("booking.bookingdates.checkout"),booking.bookingdates.checkout);



    }


}
