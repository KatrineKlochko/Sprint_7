package ru.yandex.practicum.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.steps.OrderSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTests extends BaseTest {

    private Order order;
    private OrderSteps orderSteps;
    private final String[] color;

    public CreateOrderTests(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Color: {0}")
    public static Object[][] getColors() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}},
                {null},
        };
    }

    @Before
    public void setUp() {

        orderSteps = new OrderSteps();
        order = new Order()
                .setFirstName("Naruto")
                .setLastName("Uchiha")
                .setAddress("Konoha, 142 apt.")
                .setMetroStation("4")
                .setPhone("+7 800 355 35 35")
                .setRentTime(5)
                .setDeliveryDate("2020-06-06")
                .setComment("Saske, come back to Konoha")
                .setColor(color);
    }

    @Test
    @DisplayName("Проверка создания заказа с разными вариантами цвета")
    public void shouldCreateOrderWithDifferentColors() {

        orderSteps
                .createOrder(order)
                .statusCode(201)
                .body("track", notNullValue());
    }

}
