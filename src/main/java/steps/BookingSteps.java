package steps;

import calls.BookingCalls;
import io.restassured.response.Response;
import models.BookingModel;

public class BookingSteps extends CommonSteps<BookingSteps, BookingModel>{
    private BookingCalls bookingCalls=new BookingCalls();
    private Response response;
    private int bookingId;


    public BookingSteps addBook(){
        response=bookingCalls.createBooking(data);
        bookingId = response.jsonPath().getInt("bookingid");
        return this;
    }
    public BookingSteps getBooking(){
        response= bookingCalls.getBooking(bookingId);
        return this;
    }

    public BookingSteps checkBooking(){
        return this;
    }

}
