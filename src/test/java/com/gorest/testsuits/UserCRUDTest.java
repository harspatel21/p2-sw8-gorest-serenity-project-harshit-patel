package com.gorest.testsuits;
// **** Created By Harshit Patel ****

import com.gorest.gorestinfo.UserSteps;
import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCRUDTest extends TestBase {


    static String name = "Ram Raja" + TestUtils.getRandomValue();
    static String email = "Ram.raja" + TestUtils.getRandomValue() + "@email.com";
    static String gender = "male";
    static String status = "active";

    static int userID;

    @Steps
    UserSteps userSteps;

    @Title("This will create a new user")
    @Test
    public void test001() {

        ValidatableResponse response = userSteps.createUser(name, email, gender, status);
        response.log().all().statusCode(201);
        userID = response.extract().path("id");

    }

    @Title("Verify if the user is added to application")
    @Test
    public void test002() {


        HashMap<String, Object> userMap = userSteps.getUserInfoByUserID(userID);
        Assert.assertThat(userMap, hasValue(userID));

    }

    @Title("This will update user")
    @Test
    public void test003() {
        name = name + "_Updated";

        userSteps.updateUser(userID, name, email, gender, status);

        HashMap<String, Object> userMap = userSteps.getUserInfoByUserID(userID);
        Assert.assertThat(userMap, hasValue(name));
    }

    @Title("Delete the user and verify if the user is deleted!")
    @Test
    public void test004() {

        userSteps.deleteUser(userID).statusCode(204);
        userSteps.getUserById(userID).statusCode(404);

    }
}
