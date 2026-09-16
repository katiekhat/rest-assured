import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class Users {
    //1 დავალება
    @Test
    public void getUsers() {
        Response response = get("https://reqres.in/api/users?page=1");
        System.out.println(response.getBody().asPrettyString());
    }

    //2 დავალება
    @Test
    public void getUsersBBD() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=1")
                .then()
                .statusCode(200);
    }

    //3 დავალება
    @Test
    public void countPages() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=1")
                .then()
                .statusCode(200)
                .body("page", equalTo(1))
                .body("total_pages", greaterThan(0));
    }

    //4 დავალება
    @Test
    public void callSecondPage() {
        given()
                .queryParam("page", 2)
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200)
                .body("page", equalTo(2));
    }

    //5 დავალება
    @Test
    public void getData() {
        Response response = given()
                .queryParam("page", 1)
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200)
                .extract().response();

        int page = response.jsonPath().getInt("page");
        int totalPages = response.jsonPath().getInt("total_pages");
        int firstId = response.jsonPath().getInt("data[0].id");
        String email = response.jsonPath().getString("data[0].email");
        System.out.println("page: " + page + " total pages: " + totalPages);
        System.out.println("First user id: " + firstId + " User email: " + email);
    }

    //6 დავალება
    @Test
    public void getAllEmails() {
        Response response = given()
                .queryParam("page", 1)
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200)
                .extract().response();

        List<String> emails = response.jsonPath().getList("data.email");
        System.out.println(emails);
        for (String email : emails) {
            Assert.assertNotNull(email, "Email is null");
            if (email != null) {
                Assert.assertFalse(email.isEmpty(), "email is empty");
            }
        }

    }

    //7 დავალება
    @Test
    public void verifyAllEmails() {
        Response response = given()
                .queryParam("page", 1)
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200)
                .extract().response();

        List<String> emails = response.jsonPath().getList("data.email");
        SoftAssert softAssert = new SoftAssert();
        for (String email : emails) {
            Assert.assertTrue(email != null && email.contains("@"),
                    "Email is problematic" + email);
        }
        softAssert.assertAll();
    }

    //8 დავალება
    @Test
    public void printUserData() {
        Response response = given()
                .queryParam("page", 1)
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200)
                .extract().response();
        List<Map<String, Object>> users = response.jsonPath().getList("data");
        for (Map<String, Object> user : users) {
            System.out.println("id: " + user.get("id"));
            System.out.println("first name: " + user.get("first_name"));
            System.out.println("last name: " + user.get("last_name"));
            System.out.println("email: " + user.get("email"));

        }
    }

    //9 დავალება
    @Test
    public void getAllUsers() {
        Response responseFirst = given()
                .queryParam("page", 1)
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200)
                .extract().response();
        int totalPages = responseFirst.jsonPath().getInt("total_pages");
        for (int i = 1; i <= totalPages; i++) {
            Response response = given()
                    .queryParam("page", i)
                    .when()
                    .get("https://reqres.in/api/users")
                    .then()
                    .statusCode(200)
                    .extract().response();

            List<Integer> allId = response.jsonPath().getList("data.id");
            for (int id : allId) {
                Assert.assertTrue(id > 0, "id ნაკლებია 0-ზე");
            }
        }
    }

    //10 დავალება
    @Test
    public void verifyAllUser() {
        Response responseFirst = given()
                .queryParam("page", 1)
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200)
                .extract().response();
        int totalPages = responseFirst.jsonPath().getInt("total_pages");
        SoftAssert softAssert=new SoftAssert();

        for(int i=1;i<totalPages;i++){
            Response response = given()
                    .queryParam("page", 1)
                    .when()
                    .get("https://reqres.in/api/users")
                    .then()
                    .statusCode(200)
                    .extract().response();
            List<Map<String,Object>> users=response.jsonPath().getList("data");
            for (Map<String,Object> user:users){
                int id= (int) user.get("id");
                String email= (String) user.get("email");
                String firstName= (String) user.get("first_name");
                String lastName= (String) user.get("last_name");

                softAssert.assertTrue(id>0,"id ნაკლებია 0-ზე");
                softAssert.assertNotNull(email,"მეილი არის ნალი");
                softAssert.assertFalse(email.isEmpty(),"მეილი ცარიელია");
                softAssert.assertTrue(email.contains("@"),"მეილი არ შეიცავს @-სიმბოლოს");
                softAssert.assertTrue(firstName!=null&&!firstName.isEmpty(),"სახელი არის ნალი ან ცარიელი");
                softAssert.assertTrue(lastName!=null&& !lastName.isEmpty(),"გვარი არის ნალი ან ცარიელი");

            }
        }
        softAssert.assertAll();
    }

}


