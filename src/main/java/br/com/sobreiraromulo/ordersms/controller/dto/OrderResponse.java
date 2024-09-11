package br.com.sobreiraromulo.ordersms.controller.dto;

import java.math.BigDecimal;

import br.com.sobreiraromulo.ordersms.entity.OrderEntity;

public record OrderResponse(Long orderId,
                            Long customerId,
                            BigDecimal total) {
 

 public static OrderResponse fromEntity(OrderEntity orderEntity){
  return new OrderResponse(orderEntity.getOrderId(), orderEntity.getCustomerId(), orderEntity.getTotal());
 }
}
