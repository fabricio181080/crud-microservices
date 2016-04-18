package com.matera.crudmicroservices.filter;

import javax.ws.rs.QueryParam;

public class PersonFilter {

    @QueryParam("name")
    private String name;

    @QueryParam("phoneNumber")
    private String phoneNumber;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }
}
