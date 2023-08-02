package ContactList.apis;


import com.shaft.driver.SHAFT;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class GetUserProfileApi {
    SHAFT.API api;

    public GetUserProfileApi(){
        api = new SHAFT.API(System.getProperty("ContactList.BaseUrl"));
    }

    @Step("Get user's profile using user token: {token}")
    public Response getUserProfile(String token, int expectedStatusCode){

        return api.get("/users/me")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization","Bearer "+token)
                .setTargetStatusCode(expectedStatusCode)
                .perform();

    }
}
