package tests;

import ContactList.PojoClasses.User;
import ContactList.apis.AddUserApi;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;

@Epic("ContactList tests")
@Feature("Register user tests")
public class AddUserTests {

    // Variables Section
    AddUserApi addUserApi;
    SHAFT.TestData.JSON testData;
    String email;
    String password;
    User user;
    String currentTime;

    // Tests Section
    @Test(description = "Valid registration with unregistered email and password")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Registering a user using an email that hasn't been registered before")
    public void VerifyAddingUserWithUnregisteredEmail() {
        email = testData.getTestData("UserInfo.email") + "_" + currentTime + testData.getTestData("UserInfo.domain");
        password = testData.getTestData("UserInfo.password");

        user = addUserApi.AddUser(email, testData.getTestData("UserInfo.firstName")
                , testData.getTestData("UserInfo.lastName")
                , password, 201).as(User.class);

        Validations.assertThat().object(user.getUserInfo().getEmail()).isEqualTo(email).perform();
        Validations.assertThat().object(user.getToken()).isNotNull().perform();
    }

    @Test(dependsOnMethods = "VerifyAddingUserWithUnregisteredEmail", description = "Invalid registration with email that have been registered before")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Registering user using an email that have been registered before")
    public void VerifyAddingUserWithRegisteredEmail() {
        Response response = addUserApi.AddUser(email, user.getUserInfo().getFirstName(), user.getUserInfo().getLastName(), password, 400);

        response.then().assertThat().body("message", equalTo(testData.getTestData("messages.EmailTaken")));
    }

    // Configuration Section
    @BeforeClass
    public void setUp() {
        testData = new SHAFT.TestData.JSON("AddUserTestData.json");
        addUserApi = new AddUserApi();
        currentTime = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());
    }

}
