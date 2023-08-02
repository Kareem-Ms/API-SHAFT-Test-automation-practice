package ContactList.apis;

import com.shaft.driver.SHAFT;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

public class LoginApii {
    SHAFT.API api;
    public LoginApii(){
        api = new SHAFT.API(System.getProperty("ContactList.BaseUrl"));
    }

    @Step("Login to user account using Email: {email}, Password: {password}")
    public Response Login(String email, String password, int expectedStatusCode){

        HashMap<String,String> reqBody = new HashMap<>();
        reqBody.put("email",email);
        reqBody.put("password",password);

        return api.post("/users/login")
                .setContentType(ContentType.JSON)
                .setRequestBody(reqBody)
                .setTargetStatusCode(expectedStatusCode)
                .perform();

    }
}
