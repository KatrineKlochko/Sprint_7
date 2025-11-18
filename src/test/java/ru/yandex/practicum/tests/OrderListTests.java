package ru.yandex.practicum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.steps.OrderSteps;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;

public class OrderListTests extends BaseTest{

    private OrderSteps orderSteps;

    @Before
    public void setUp() {
        orderSteps = new OrderSteps();
    }

    @Test
    @DisplayName("Проверка на получение списка заказов")
    @Description("Тест на то, что в тело ответа возвращается список заказов для /api/v1/orders эндпоинт")
    public void shouldReturnOrdersList(){

        orderSteps
                .getOrdersList()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders.size()", greaterThan(0));
    }

}
