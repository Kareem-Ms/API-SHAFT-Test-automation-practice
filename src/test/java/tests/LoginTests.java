package tests;

import ContactList.PojoClasses.User;
import ContactList.apis.AddUserApi;
import ContactList.apis.LoginApii;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

@Epic("ContactList tests")
@Feature("Login tests")
public class LoginTests extends RegisterationBaseTest {

    // Variables Section
    AddUserApi addUserApi;
    LoginApii loginApi;
    SHAFT.TestData.JSON testData;
    String currentTime;


    // Tests Section
    @Test( description = "Valid login with correct email and password")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Login to user account using a registered email and password")
    public void VerifyLoginWithCorrectEmailAndPw(ITestContext context) {
        User registeredUser = (User) context.getAttribute("RegisteredUser");
        User LoggedInUser = loginApi.Login(registeredUser.getUserInfo().getEmail(), registeredUser.getUserInfo().getPassword(), 200).as(User.class);

        Validations.assertThat().object(LoggedInUser.getUserInfo().getEmail()).isEqualTo(registeredUser.getUserInfo().getEmail()).perform();
        Validations.assertThat().object(LoggedInUser.getToken()).isNotNull().perform();
    }

    @Test(description = "Invalid login with incorrect email and password")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Login to user account using a non registered email and password")
    public void VerifyLoginWithInCorrectEmailAndPw() {
        loginApi.Login(testData.getTestData("UserInfo.UnregisteredEmail"), testData.getTestData("UserInfo.password"), 401);
    }


    // Configuration Section
    @BeforeClass
    public void setUp() {
        testData = new SHAFT.TestData.JSON("LoginTestData.json");
        addUserApi = new AddUserApi();
        loginApi = new LoginApii();
        currentTime = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());
    }

}
