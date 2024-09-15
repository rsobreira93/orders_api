package br.com.sobreiraromulo.ordersms.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;

import br.com.sobreiraromulo.ordersms.factory.OrderResponseFactory;
import br.com.sobreiraromulo.ordersms.service.OrderService;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

 @Mock
 OrderService orderService;

 @InjectMocks
 OrderController orderController;

 @Captor
 ArgumentCaptor<Long> customerIdCaptor;

 @Captor
 ArgumentCaptor<PageRequest> pageRequestCaptor;

 @Nested
 class ListOrders{

  @Test
  void shouldReturnHttpOk() {
   var customerId = 1L;
   var page = 0;
   var pageSize = 10;

   doReturn(OrderResponseFactory.buildWithOnItem()).when(orderService).findAllByCustomerId(anyLong(), any());
   doReturn(BigDecimal.valueOf(20.50)).when(orderService).findTotalOnOrdersByCustomerId(anyLong());
   var response = orderController.listOrders(page, pageSize, customerId);

   assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
   
  }

  @Test
  void shouldPassCorrectParametersToService() {
   var customerId = 1L;
   var page = 0;
   var pageSize = 10;

   doReturn(OrderResponseFactory.buildWithOnItem()).when(orderService)
   .findAllByCustomerId(customerIdCaptor.capture(), pageRequestCaptor.capture());
   doReturn(BigDecimal.valueOf(20.50)).when(orderService).findTotalOnOrdersByCustomerId(customerIdCaptor.capture());
   
   orderController.listOrders(page, pageSize, customerId);

   assertEquals(2, customerIdCaptor.getAllValues().size());
   assertEquals(customerId, customerIdCaptor.getAllValues().get(0));
   assertEquals(customerId, customerIdCaptor.getAllValues().get(1));

   assertEquals(page, pageRequestCaptor.getValue().getPageNumber());
   assertEquals(pageSize, pageRequestCaptor.getValue().getPageSize());
  }

  @SuppressWarnings("null")
  @Test
  void shouldReturnResponseBodyCorrectly() {
   var customerId = 1L;
   var page = 0;
   var pageSize = 10;
   var totalOnOrders = BigDecimal.valueOf(20.50);
   var pagination = OrderResponseFactory.buildWithOnItem();
   
   doReturn(pagination)
           .when(orderService).findAllByCustomerId(anyLong(), any());
   doReturn(totalOnOrders)
           .when(orderService).findTotalOnOrdersByCustomerId(anyLong());

   var response = orderController.listOrders(page, pageSize, customerId);

   assertNotNull(response);
   assertNotNull(response.getBody());
   assertNotNull(response.getBody().data());
   assertNotNull(response.getBody().pagination());
   assertNotNull(response.getBody().summary());

   assertEquals(totalOnOrders, response.getBody().summary().get("totalOnOrders"));

   assertEquals(pagination.getTotalElements(), response.getBody().pagination().totalElements());
   assertEquals(pagination.getTotalPages(), response.getBody().pagination().totalPages());
   assertEquals(pagination.getNumber(), response.getBody().pagination().page());
   assertEquals(pagination.getSize(), response.getBody().pagination().pageSize());

   assertEquals(pagination.getContent(), response.getBody().data());
  }

 }

}
