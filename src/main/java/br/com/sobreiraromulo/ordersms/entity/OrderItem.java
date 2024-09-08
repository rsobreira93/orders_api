package br.com.sobreiraromulo.ordersms.entity;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


public class OrderItem {

 private String product;

 private Integer quantity;

 @Field(targetType = FieldType.DECIMAL128)
 private BigDecimal price;

 public String getProduct() {
  return product;
 }

 public OrderItem() {
 }

 public void setProduct(String product) {
  this.product = product;
 }

 public Integer getQuantity() {
  return quantity;
 }

 public void setQuantity(Integer quantity) {
  this.quantity = quantity;
 }

 public BigDecimal getPrice() {
  return price;
 }

 public void setPrice(BigDecimal price) {
  this.price = price;
 }

}
