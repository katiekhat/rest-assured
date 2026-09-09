package tests;

import calls.BookingCalls;
import calls.UserCalls;
import io.restassured.response.Response;
import models.BookingDatesModel;
import models.BookingModel;
import models.UserModel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class BookingTest {
    BookingCalls bookingCalls=new BookingCalls();
    BookingDatesModel dates=new BookingDatesModel();
    BookingModel booking=new BookingModel();

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
        dates.checkin="2026-09-10";
        dates.checkout="2026-09-15";
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
        System.out.println("created booking id : "+bookingId);
        Assert.assertTrue(bookingId>0);
        Response getResponse=bookingCalls.getBooking(bookingId);
        Assert.assertEquals(getResponse.getStatusCode(),200);
        Assert.assertEquals(getResponse.jsonPath().getString("firstname"),"anano");
        Assert.assertEquals(getResponse.jsonPath().getString("lastname"),"anano2");
        Assert.assertEquals(getResponse.jsonPath().getInt("totalprice"),200);
        Assert.assertEquals(getResponse.jsonPath().getString("additionalneeds"),"Breakfast");


    }

    //4 დავალება
    @Test
    public void getBookingModelTest(){
        dates.checkin="2026-09-10";
        dates.checkout="2026-09-15";
        booking.firstname="Mari";
        booking.lastname="mari2";
        booking.totalprice=299;
        booking.depositpaid=true;
        booking.bookingdates=dates;
        booking.additionalneeds="Breakfast";
        Response postResponse=bookingCalls.createBooking(booking);
        Assert.assertEquals(postResponse.getStatusCode(),200);

        int bookingId=postResponse.jsonPath().getInt("bookingid");
        System.out.println("id: "+bookingId);
// Deserialization -> მიღებულ რესპონსს რომელიც არის ჯეისონ ფორმატში გადაიყვანს
// booking model ტიპის ობიექტად
        BookingModel getBookingModelData=bookingCalls.getBookingModel(bookingId);

        Assert.assertEquals(getBookingModelData.firstname,booking.firstname);
        Assert.assertEquals(getBookingModelData.lastname,booking.lastname);
        Assert.assertEquals(getBookingModelData.totalprice,booking.totalprice);
        Assert.assertEquals(getBookingModelData.additionalneeds,booking.additionalneeds);
        Assert.assertEquals(getBookingModelData.bookingdates.checkin,booking.bookingdates.checkin);
        Assert.assertEquals(getBookingModelData.bookingdates.checkout,booking.bookingdates.checkout);
        Assert.assertEquals(getBookingModelData.depositpaid,booking.depositpaid);

    }


    //5 დავალება
    @Test
    public void getAllUsersAndCheckEmails(){
        UserCalls userCalls=new UserCalls();
        List<UserModel> users= userCalls.getUsers();
        Assert.assertFalse(users.isEmpty());
        for(UserModel user: users){
            Assert.assertTrue(user.email.contains("@"));
            Assert.assertFalse(user.first_name.isEmpty());
            Assert.assertFalse(user.last_name.isEmpty());
        }

    }





}
