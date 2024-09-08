package br.com.sobreiraromulo.ordersms.listener.dto;

import java.math.BigDecimal;

public record OrderItemEvent(String produto,
                             Long quantidade,
                             BigDecimal preco) {
}
