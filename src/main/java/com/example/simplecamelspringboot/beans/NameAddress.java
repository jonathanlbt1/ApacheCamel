package com.example.simplecamelspringboot.beans;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name="fetchAllRows", query="Select x from NameAddress x")
@Entity
@Table(name = "name_address")
public class NameAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "house_number")
    private String houseNumber;

    private String city;
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    public NameAddress(Long id, String name, String houseNumber, String city, String state, String postalCode) {
        this.id = id;
        this.name = name;
        this.houseNumber = houseNumber;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public NameAddress() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
