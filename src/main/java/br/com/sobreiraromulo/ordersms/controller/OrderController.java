package br.com.sobreiraromulo.ordersms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.sobreiraromulo.ordersms.controller.dto.ApiResponse;
import br.com.sobreiraromulo.ordersms.controller.dto.OrderResponse;
import br.com.sobreiraromulo.ordersms.controller.dto.PaginationResponse;
import br.com.sobreiraromulo.ordersms.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class OrderController {
 

 @Autowired
 private OrderService orderService;

 @GetMapping("/customers/{customerId}/orders")
 public ResponseEntity<ApiResponse<OrderResponse>> listOrders(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, 
                                    @PathVariable Long customerId) {

     var pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));
     var totalOnOrders = orderService.findTotalOnOrdersByCustomerId(customerId);

     return ResponseEntity.ok(new ApiResponse<>(
      Map.of("totalOnOrders", totalOnOrders),
      pageResponse.getContent(),
      PaginationResponse.fromPage(pageResponse)));
 }
 
}
