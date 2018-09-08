package com.azaroff.x3.user.dao.entity;

import com.azaroff.x3.user.UserType;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Size(min = 5, max = 50)
    private String firstName;
    @NotNull
    @Size(min = 5, max = 50)
    private String lastName;
    @Column(length = 32, columnDefinition = "varchar(32) default 'CUSTOMER'")
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @NotNull
    @Size(min = 9, max = 12)
    private String phoneNumber;
    @NotNull
    private String email;
    private String city;
    private String address;
    private String address2;
    private String zipCode;
    private String zipCode2;
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
//    private BusinessAccount businessAccount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCode2() {
        return zipCode2;
    }

    public void setZipCode2(String zipCode2) {
        this.zipCode2 = zipCode2;
    }

//    public BusinessAccount getBusinessAccount() {
//        return businessAccount;
//    }
//
//    public void setBusinessAccount(BusinessAccount businessAccount) {
//        this.businessAccount = businessAccount;
//    }
}
