package br.com.sobreiraromulo.ordersms.factory;

import br.com.sobreiraromulo.ordersms.listener.dto.OrderCreatedEvent;
import br.com.sobreiraromulo.ordersms.listener.dto.OrderItemEvent;


import java.math.BigDecimal;
import java.util.List;

public class OrderCreatedEventFactory {

 public static OrderCreatedEvent buildWithOneItem() {

     var items = new OrderItemEvent("notebook", 1, BigDecimal.valueOf(20.50));
     var event = new OrderCreatedEvent(1L, 2L, List.of(items));

     return event;
 }

 public static OrderCreatedEvent buildWithTwoItens() {

     var item1 = new OrderItemEvent("notebook", 1, BigDecimal.valueOf(20.50));
     var item2 = new OrderItemEvent("mouse", 1, BigDecimal.valueOf(35.25));

     var event = new OrderCreatedEvent(1L, 2L, List.of(item1, item2));

     return event;
 }
}