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
        couriersSteps
                .createCourier(courier);
    }

    @Test
    @DisplayName("Проверка на логин курьера")
    @Description("Тест на успешный логин для /api/v1/courier/login эндпоинт")
    public void shouldLoginCourierTest() {
        couriersSteps
                .login(courier)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Проверка на авторизацию без логина")
    @Description("Тест на невозможность залогиниться без логина для /api/v1/courier/login эндпоинт")
    public void shouldNotLoginCourierWithoutLoginTest() {
        Courier badCourier = new Courier(courier);
        badCourier.setLogin("");
        couriersSteps
                .login(badCourier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка на авторизацию без пароля")
    @Description("Тест на невозможность залогиниться без пароля для /api/v1/courier/login эндпоинт")
    public void shouldNotLoginCourierWithoutPasswordTest() {
        Courier badCourier = new Courier(courier);
        badCourier.setPassword("");
        couriersSteps
                .login(badCourier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка на авторизацию с несуществующим логином")
    @Description("Тест на невозможность залогиниться с несуществующим логином для /api/v1/courier/login эндпоинт")
    public void shouldNotLoginCourierWithNonExistentLoginTest() {
        Courier nonExistingCourier = new Courier(courier)
                .setLogin(RandomStringUtils.randomAlphabetic(12));
        couriersSteps
                .login(nonExistingCourier)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверка на авторизацию с несуществующим паролем")
    @Description("Тест на невозможность залогиниться с несуществующим паролем для /api/v1/courier/login эндпоинт")
    public void shouldNotLoginCourierWithNonExistentPasswordTest() {
        Courier nonExistingCourier = new Courier(courier)
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
