package com.practice.order_management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.practice.order_management.controller.OrderController;
import com.practice.order_management.service.OrderServiceImpl;
import com.practice.order_management.models.Order;

@WebMvcTest(OrderController.class)
public class OrderRestControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private OrderServiceImpl service;

        @Test
        public void testFindOrderById() throws Exception {
                Order order = new Order(1, "maggi", Short.parseShort("1"), LocalDateTime.now(), 10.0, null);

                when(service.findOrderById(1))
                                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(order));

                mockMvc.perform(get("/orders/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("maggi"));
        }

        @Test
        public void testFindAllOrders() throws Exception {
                Order order1 = new Order(1, "maggi", Short.parseShort("1"), LocalDateTime.now(), 10.0, null);
                Order order2 = new Order(2, "pepsi", Short.parseShort("1"), LocalDateTime.now(), 20.0, null);

                List<Order> orders = List.of(order1, order2);

                when(service.findAllOrders())
                                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(orders));

                mockMvc.perform(get("/orders")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[1].name").value("pepsi"));
        }

        @Test
        public void testSaveOrder() throws JsonProcessingException, Exception {
                Order order = new Order(1, "maggi", Short.parseShort("1"), LocalDateTime.now(), 10.0, null);
                System.out.println((new ObjectMapper().registerModule(new JavaTimeModule())
                                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                                .writeValueAsString(order)));
                when(service.saveOrder(order))
                                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(order));

                mockMvc.perform(post("/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().registerModule(new JavaTimeModule())
                                                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                                                .writeValueAsString(order)))
                                .andDo(print())
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.name").value("maggi"));
        }

        @Test
        public void updateOrder() throws JsonProcessingException, Exception {
                Order order = new Order(1, "football", Short.parseShort("1"), LocalDateTime.now(), 500.0, null);

                when(service.updateOrder(order))
                                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(order));

                mockMvc.perform(put("/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().registerModule(new JavaTimeModule())
                                                .writeValueAsString(order)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.price").value(500.0))
                                .andDo(print());
        }

        @Test
        public void deleteOrder() throws JsonProcessingException, Exception {
                Order order = new Order(1, "computer", Short.parseShort("1"), LocalDateTime.now(), 50000.0, null);

                when(service.deleteOrderById(1))
                                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(order));

                mockMvc.perform(delete("/orders/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.price").value(50000.0))
                                .andDo(print());
        }
}
