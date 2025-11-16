package ru.yandex.practicum.tests;

import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.model.Courier;
import ru.yandex.practicum.steps.CouriersSteps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTests extends BaseTest {

    private CouriersSteps couriersSteps = new CouriersSteps();
    private Courier courier;

    @Before
    public void setUp() {

        courier = new Courier();
        courier
                .setLogin(RandomStringUtils.randomAlphabetic(12))
                .setPassword(RandomStringUtils.randomAlphabetic(12));
    }

    @Test
    @DisplayName("Проверка на логин курьера")
    public void shouldLoginCourierTest() {
        couriersSteps
                .createCourier(courier);
        couriersSteps
                .login(courier)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Проверка на авторизацию без логина")
    public void shouldNotLoginCourierWithoutLoginTest() {
        couriersSteps
                .createCourier(courier);
        Courier badCourier = new Courier(courier);
        badCourier.setLogin("");
        couriersSteps
                .login(badCourier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка на авторизацию без пароля")
    public void shouldNotLoginCourierWithoutPasswordTest() {
        couriersSteps
                .createCourier(courier);
        Courier badCourier = new Courier(courier);
        badCourier.setPassword("");
        couriersSteps
                .login(badCourier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка на авторизацию с несуществующим пользователем")
    public void shouldNotLoginCourierWithNonExistentUserTest() {
        Courier nonExistingCourier = new Courier()
                .setLogin(RandomStringUtils.randomAlphabetic(12))
                .setPassword(RandomStringUtils.randomAlphabetic(12));
        couriersSteps
                .login(nonExistingCourier)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {

        Integer id = couriersSteps
                .login(courier)
                .extract()
                .body()
                .path("id");
        if (id != null) {
            courier.setId(id);
            couriersSteps.deleteCourier(courier);
        }
    }
}
