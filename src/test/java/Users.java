import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.get;

public class Users {
    @Test
    public void getUsers(){
        Response response=get("https://reqres.in/api/users?page=1");
        System.out.println(response.getBody().asPrettyString());
    }
}
