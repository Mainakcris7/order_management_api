package com.practice.order_management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.order_management.controller.CustomerController;
import com.practice.order_management.models.Customer;
import com.practice.order_management.service.CustomerServiceImpl;

@WebMvcTest(CustomerController.class)
public class CustomerRestControllerTest {
        @Autowired
        private MockMvc mockMvc;

        // @MockBean is deprecated, we have to use @MockitoBean from v3.4.0
        // https://docs.spring.io/spring-framework/docs/6.2.x/javadoc-api/org/springframework/test/context/bean/override/mockito/MockitoBean.html
        @MockitoBean
        private CustomerServiceImpl service;

        @Test
        public void findCustomerByIdTest() throws Exception {
                Customer customer = new Customer(1, "mainak", "m@gmail.com", null);

                when(service.findCustomerById(1))
                                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(customer));

                mockMvc.perform(get("/customers/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("mainak"))
                                .andDo(print());
        }

        @Test
        public void saveCustomerTest() throws Exception {
                Customer customer = new Customer(1, "mainak", "m@gmail.com", null);

                when(service.saveCustomer(customer))
                                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(customer));

                mockMvc.perform(post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(customer)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.name").value("mainak"))
                                .andDo(print());
        }

        // Saved the API from a BUG, earlier I was invoking service.saveCustomer()
        // instead of service.updateCustomer() from the updateCustomer() method of the
        // controller layer.
        @Test
        public void updateCustomerTest() throws Exception {
                Customer customer = new Customer(1, "mainak", "m@gmail.com", null);

                when(service.updateCustomer(customer))
                                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(customer));

                mockMvc.perform(put("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(customer)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("mainak"))
                                .andDo(print());
        }

        @Test
        public void deleteCustomerTest() throws Exception {
                Customer customer = new Customer(1, "mainak", "m@gmail.com", null);

                when(service.deleteCustomerById(customer.getId()))
                                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(customer));

                mockMvc.perform(delete("/customers/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(customer)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("mainak"))
                                .andDo(print());
        }

}
