package com.practice.order_management.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "'name' field can't be blank")
    @NotNull(message = "'name' field can't be null")
    private String name;

    @NotBlank(message = "'email' field can't be blank")
    @NotNull(message = "'email' field can't be null")
    @Email
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "customer", cascade = { CascadeType.ALL })
    @JsonManagedReference
    private List<Order> orders = new ArrayList<>();

}
