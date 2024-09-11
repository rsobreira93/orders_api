package br.com.sobreiraromulo.ordersms.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import br.com.sobreiraromulo.ordersms.controller.dto.OrderResponse;
import br.com.sobreiraromulo.ordersms.entity.OrderEntity;
import br.com.sobreiraromulo.ordersms.entity.OrderItem;
import br.com.sobreiraromulo.ordersms.listener.dto.OrderCreatedEvent;
import br.com.sobreiraromulo.ordersms.repository.OrderRepository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class OrderService {

 @Autowired
 private OrderRepository orderRepository;

 @Autowired
 private MongoTemplate mongoTemplate;

 public void save(OrderCreatedEvent orderCreatedEvent){

  var entity = new OrderEntity();

  entity.setOrderId(orderCreatedEvent.codigoPedido());
  entity.setCustomerId(orderCreatedEvent.codigoCliente());
  entity.setItems(getOrderItems(orderCreatedEvent));
  entity.setTotal(getTotal(orderCreatedEvent));

  orderRepository.save(entity);
 }

 public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest){
        var orders =  orderRepository.findAllByCustomerId(customerId, pageRequest);

        return orders.map(OrderResponse::fromEntity);
 }

 public BigDecimal findTotalOnOrdersByCustomerId(Long customerId){
        var aggregations = newAggregation(
                match(Criteria.where("customerId").is(customerId)),
                group().sum("total").as("total")
        );

        var response = mongoTemplate.aggregate(aggregations, "orders", Document.class);

        return Optional.ofNullable(response.getUniqueMappedResult())
        .map(result -> result.get("total"))
        .map(Object::toString)
        .map(BigDecimal::new)
        .orElse(BigDecimal.ZERO);//usado stream para caso venha nulo ele retornar zero
 }


   private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.itens()
                .stream()
                .map(i -> i.preco().multiply(BigDecimal.valueOf(i.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }


 private static List<OrderItem> getOrderItems(OrderCreatedEvent event) {
  return event.itens().stream()
          .map(i -> new OrderItem(i.produto(), i.quantidade(), i.preco()))
          .toList();
}
 
}
