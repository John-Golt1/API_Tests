package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rs.qa.courier.*;

public class LoginCourierTest {
    private CourierGenerator generator = new CourierGenerator();
    private CourierAssertions check;
    private CourierSteps step;
    private Courier courier;
    private Credentials credentials;
    int id;

    @Before
    @Step("create objects for tests")
    public void setUp() {
        step = new CourierSteps();
        courier = generator.random();
        step.create(courier);
        credentials = Credentials.from(courier);
        check = new CourierAssertions();

    }
    @Test
    @Step("login with correct values")
    public void loginSuccessFullyTest() {
        ValidatableResponse responseLogin = step.login(credentials);
        check.loginSuccess(responseLogin);
        id = responseLogin.extract().path("id");
    }
    @Test
    @Step("login without login and check message error")
    public void loginWithotLoginTest() {
        Credentials credentialsWithoutLogin = new Credentials("", courier.getPassword()); //c null всё виснет.
        ValidatableResponse responseMessageError = step.login(credentialsWithoutLogin);
        check.loginCourierWithoutLogin(responseMessageError);

    }
    @Test
    @Step("login without password and check message error")
    public void loginWithoutPasswordTest() {
        Credentials credentialsWithoutPassword = new Credentials(courier.getLogin(), "");
        ValidatableResponse responseMessageError = step.login(credentialsWithoutPassword);
        check.loginCourierWithoutLogin(responseMessageError);
    }
    @Test
    @Step ("Check massage after input incorrect login")
    public void loginWithNotCorrectLogin() {
        Credentials credentialsWithNotCorrectLogin = new Credentials("qwerty", credentials.getPassword());
        ValidatableResponse responseMessageError = step.login(credentialsWithNotCorrectLogin);
        check.loginCourierWithFalseValues(responseMessageError); //тут баг
        id = responseMessageError.extract().path("id");
    }
    @Test
    @Step ("Check message after login courier with incorrect password")
    public void loginWithNotCorrectPassword() {
       Credentials credentialsHere = new Credentials(credentials.getLogin(), "0000");
        ValidatableResponse responseMessageError = step.login(credentialsHere);
        check.loginCourierWithFalseValues(responseMessageError);
    }
    @After
    @Step ("delete test couriers")
    public void deleteCourier(){
        if (id != 0) {
            step.deleteCourier(id);
        }
    }
}
