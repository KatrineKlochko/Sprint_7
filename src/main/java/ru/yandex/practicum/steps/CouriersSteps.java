package ru.yandex.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.model.Courier;

import static io.restassured.RestAssured.given;

public class CouriersSteps {

    public static final String COURIER = "/api/v1/courier";
    public static final String LOGIN = "/api/v1/courier/login";

    @Step("Метод создания курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .body(courier)
                .when()
                .post(COURIER)
                .then();
    }

    @Step("Метод логина курьера")
    public ValidatableResponse login(Courier courier) {
        return given()
                .body(courier)
                .when()
                .post(LOGIN)
                .then();
    }

    @Step("Метод удаления курьера")
    public ValidatableResponse deleteCourier(Courier courier) {
        return given()
                .pathParams("id", courier.getId())
                .when()
                .delete("/api/v1/courier/{id}")
                .then();
    }
}
