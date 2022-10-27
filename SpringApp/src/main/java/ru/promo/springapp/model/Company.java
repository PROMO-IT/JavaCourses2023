package ru.promo.springapp.model;


import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import javax.persistence.*;
import java.util.Objects;

@JsonIncludeProperties(value = {"name", "about", "address", "phone", "employeeCount", "companyType"})
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    protected String name;
    private String about;
    private String address;
    private String phone;

    @Column(name = "employee_count", nullable = false)
    private int employeeCount;
    @Column(name = "company_type")
    @Enumerated(value = EnumType.STRING)
    private CompanyType companyType;

    public Company() {
    }

    public Company(String name, String about, String address, String phone, int employeeCount, CompanyType companyType) {
        this.name = name;
        this.about = about;
        this.address = address;
        this.phone = phone;
        this.employeeCount = employeeCount;
        this.companyType = companyType;
    }

    public Company(String name, int employeeCount, CompanyType companyType) {
        this.name = name;
        this.employeeCount = employeeCount;
        this.companyType = companyType;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", about='" + about + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", employeeCount=" + employeeCount +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public static void someMethod() {
        System.out.println("static!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return employeeCount == company.employeeCount && Objects.equals(name, company.name) && Objects.equals(about, company.about) && Objects.equals(address, company.address) && Objects.equals(phone, company.phone);
    }

    @Override
    public int hashCode() {
         return Objects.hash(name, about, address, phone, employeeCount);
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }
}
