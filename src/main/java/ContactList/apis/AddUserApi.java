package ContactList.apis;

import com.shaft.driver.SHAFT;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

public class AddUserApi {

    SHAFT.API api;

    public AddUserApi(){
        api = new SHAFT.API(System.getProperty("ContactList.BaseUrl"));
    }

    @Step("Register new user with Email: {email}, FirstName: {firstName}, LastName: {lastName}, Password: {password}")
    public Response AddUser(String email, String firstName, String lastName, String password, int expectedStatusCode){

        HashMap<String,String> reqBody = new HashMap<>();
        reqBody.put("firstName", firstName);
        reqBody.put("lastName", lastName);
        reqBody.put("email", email);
        reqBody.put("password", password);

        return api.post("/users").setRequestBody(reqBody).setContentType(ContentType.JSON).setTargetStatusCode(expectedStatusCode).perform();

    }

}
