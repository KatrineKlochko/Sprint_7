package ru.yandex.practicum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.model.Courier;
import ru.yandex.practicum.steps.CouriersSteps;

import static org.hamcrest.CoreMatchers.is;

public class CourierCreateTests extends BaseTest {

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
    @DisplayName("Проверка на создание курьера")
    @Description("Тест на успешное создание курьера для /api/v1/courier эндпоинт")
    public void shouldCreateCourierTest() {
        couriersSteps
                .createCourier(courier)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Проверка на создание двух одинаковых курьеров")
    @Description("Тест на невозможность создания двух одинаковых курьеров для /api/v1/courier. Упал, фактический текст сообщения не соответствует документации")
    public void shouldNotCreateSameCourierTest() {
        couriersSteps
                .createCourier(courier);
        couriersSteps
                .createCourier(courier)
                .statusCode(409)
                .body("message", is("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Проверка на создание курьера без логина")
    @Description("Тест на невозможность создания курьера с незаполненным полем логин для /api/v1/courier эндпоинт")
    public void shouldNotCreateCourierWithoutLoginTest() {
        courier.setLogin("");
        couriersSteps
                .createCourier(courier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка на создание курьера без пароля")
    @Description("Тест на невозможность создания курьера с незаполненным полем пароль для /api/v1/courier эндпоинт")
    public void shouldNotCreateCourierWithoutPasswordTest() {
        courier.setPassword("");
        couriersSteps
                .createCourier(courier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
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
