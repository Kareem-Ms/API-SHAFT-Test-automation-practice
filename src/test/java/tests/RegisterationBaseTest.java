package tests;

import ContactList.PojoClasses.User;
import ContactList.apis.AddUserApi;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterationBaseTest {
    AddUserApi addUserApi = new AddUserApi();
    SHAFT.TestData.JSON testData = new SHAFT.TestData.JSON("AddUserTestData.json");
    String currentTime = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());

    @BeforeTest(description = "Valid registration with unregistered email and password")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Registering a user using an email that hasn't been registered before")
    public void VerifyAddingUserWithUnregisteredEmail(ITestContext context) {
        String email = testData.getTestData("UserInfo.email") + "_" + currentTime + testData.getTestData("UserInfo.domain");
        String password = testData.getTestData("UserInfo.password");

        User user = addUserApi.AddUser(email, testData.getTestData("UserInfo.firstName")
                , testData.getTestData("UserInfo.lastName")
                , password, 201).as(User.class);

        Validations.assertThat().object(user.getUserInfo().getEmail()).isEqualTo(email).perform();
        Validations.assertThat().object(user.getToken()).isNotNull().perform();

        user.getUserInfo().setPassword(password);
        context.setAttribute("RegisteredUser", user);
    }

}
