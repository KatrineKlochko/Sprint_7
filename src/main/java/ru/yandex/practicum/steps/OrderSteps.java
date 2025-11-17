package ru.yandex.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.model.Order;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    private static final String ORDERS = "/api/v1/orders";
    private static final String ORDER_LIST = "/api/v1/orders";
    private static final String CANCEL_ORDER = "/api/v1/orders/cancel";

    @Step("Метод создания заказа")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .body(order)
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Метод получения списка заказов")
    public ValidatableResponse getOrdersList(){

        return given()
                .when()
                .get(ORDER_LIST)
                .then();
    }

    @Step("Метод отмены заказа")
    public ValidatableResponse cancelOrder(int track) {
        return given()
                .body("{\"track\": " + track + "}")
                .when()
                .put(CANCEL_ORDER)
                .then();
    }

}
