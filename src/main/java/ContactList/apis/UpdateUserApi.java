package ContactList.apis;

import com.shaft.driver.SHAFT;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

public class UpdateUserApi {
    SHAFT.API api;
    public UpdateUserApi(){
        api = new SHAFT.API(System.getProperty("ContactList.BaseUrl"));
    }

    @Step("Update user with token: {token}, with data Email: {email}, FirstName: {firstName}, LastName: {lastName}, Password: {password}")
    public Response updateUser(String token , String email, String firstName, String lastName, String password, int expectedStatusCode){

        HashMap<String,String> reqBody = new HashMap<>();
        reqBody.put("firstName", firstName);
        reqBody.put("lastName", lastName);
        reqBody.put("email", email);
        reqBody.put("password", password);

       return api.patch("/users/me")
               .setContentType(ContentType.JSON)
               .addHeader("Authorization","Bearer "+token)
               .setRequestBody(reqBody)
               .setTargetStatusCode(expectedStatusCode)
               .perform();

    }
}
