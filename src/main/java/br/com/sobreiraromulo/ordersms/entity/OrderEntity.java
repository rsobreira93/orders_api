package br.com.sobreiraromulo.ordersms.entity;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "orders")
public class OrderEntity {
 
 @MongoId
 private Long orderId;

 private Long customerId;

 private BigDecimal total;

 private List<OrderItem> Items;

 public OrderEntity() {
 }

 public Long getOrderId() {
  return orderId;
 }

 public void setOrderId(Long orderId) {
  this.orderId = orderId;
 }

 public Long getCustomerId() {
  return customerId;
 }

 public void setCustomerId(Long customerId) {
  this.customerId = customerId;
 }

 public BigDecimal getTotal() {
  return total;
 }

 public void setTotal(BigDecimal total) {
  this.total = total;
 }

 public List<OrderItem> getItems() {
  return Items;
 }

 public void setItems(List<OrderItem> items) {
  Items = items;
 }

 
}
