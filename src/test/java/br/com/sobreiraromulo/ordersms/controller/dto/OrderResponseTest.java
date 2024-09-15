package br.com.sobreiraromulo.ordersms.controller.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import br.com.sobreiraromulo.ordersms.factory.OrderEntityFactory;

class OrderResponseTest {

    @Nested
    class FromEntity {

        @Test
        void shouldMapCorrectly() {
            var input = OrderEntityFactory.build();

            var output = OrderResponse.fromEntity(input);

            assertEquals(input.getOrderId(), output.orderId());
            assertEquals(input.getCustomerId(), output.customerId());
            assertEquals(input.getTotal(), output.total());
        }
    }

}