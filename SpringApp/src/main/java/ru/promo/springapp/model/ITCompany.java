package ru.promo.springapp.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

@JsonIncludeProperties(value = {"name", "about", "address", "phone", "employeeCount", "companyType", "technologies"})
public class ITCompany extends Company {
    private String[] technologies;
    public ITCompany(String name, int employeeCount) {
        super(name, employeeCount, CompanyType.IT);
//        System.out.println("constructor");
        technologies = new String[10];
    }

    private void develop() {
        System.out.println("ITCompany with name " + this.name + " is developing");
    }

    public static void someMethod() {
        System.out.println("static method of ITCompany");
    }

    public String[] getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String[] technologies) {
        this.technologies = technologies;
    }
}
