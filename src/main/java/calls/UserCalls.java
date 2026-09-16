package calls;

import models.UserModel;

import java.util.List;

import static io.restassured.RestAssured.given;

public class UserCalls {
    public List<UserModel> getUsers(){
        return given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("data",UserModel.class);


    }
 }
