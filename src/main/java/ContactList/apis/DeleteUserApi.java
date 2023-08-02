package ContactList.apis;

import com.shaft.driver.SHAFT;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class DeleteUserApi {

    SHAFT.API api;

    public DeleteUserApi() {
        api = new SHAFT.API(System.getProperty("ContactList.BaseUrl"));
    }

    @Step("Delete user using user token: {token}")
    public Response deleteUser(String token , int expectedStatusCode){

       return api.delete("/users/me")
               .setContentType(ContentType.JSON)
               .addHeader("Authorization","Bearer "+token)
               .setTargetStatusCode(expectedStatusCode)
               .perform();

    }
}
