package tests;

import calls.BookingCalls;
import io.restassured.response.Response;
import models.BookingDatesModel;
import models.BookingModel;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BookingTest {
    BookingCalls bookingCalls=new BookingCalls();
    //1 დავალება
    @Test
    public void createBookingTest(){
        Response response=bookingCalls.createBooking("Keti",
                "Khatiashvili",500);
        Assert.assertEquals(response.getStatusCode(),200);
        int bookingId=response.jsonPath().getInt("bookingid");
        Assert.assertTrue(bookingId>0);
    }

    //2 დავალება
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
        Response response=bookingCalls.createBooking(booking);
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getString("booking.firstname"),booking.firstname);
        Assert.assertEquals(response.jsonPath().getString("booking.lastname"),booking.lastname);
        Assert.assertEquals(response.jsonPath().getInt("booking.totalprice"),booking.totalprice);
        Assert.assertEquals(response.jsonPath().getString("booking.bookingdates.checkin"),booking.bookingdates.checkin);
        Assert.assertEquals(response.jsonPath().getString("booking.bookingdates.checkout"),booking.bookingdates.checkout);



    }

    //3 დავალება
    @Test
    public void getBookingTest(){
        Response postResponse=bookingCalls.createBooking("anano","anano2",200);
        Assert.assertEquals(postResponse.getStatusCode(),200);
        int bookingId=postResponse.jsonPath().getInt("bookingid");
        //პოსტი მუშაობს ,იმ მომენტში -> აქ გამომაქვს შექმნილი აიდი
        System.out.println("created booking id : "+bookingId);
        Assert.assertTrue(bookingId>0);
        //ტესტი მიფეილდება რადგან ბუქინგ აიდის არ ინახავს საბოლოო ჯამში და აბრუნებს 404-ს
        Response getResponse=bookingCalls.getBooking(bookingId);
        Assert.assertEquals(getResponse.getStatusCode(),200);
        Assert.assertEquals(getResponse.jsonPath().getString("firstname"),"anano");
        Assert.assertEquals(getResponse.jsonPath().getString("lastname"),"anano2");
        Assert.assertEquals(getResponse.jsonPath().getInt("totalprice"),200);
        Assert.assertEquals(getResponse.jsonPath().getString("additionalneeds"),"Breakfast");


    }


}
