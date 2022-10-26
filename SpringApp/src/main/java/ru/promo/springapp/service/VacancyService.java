package ru.promo.springapp.service;

import org.springframework.stereotype.Component;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;
import ru.promo.springapp.model.ITCompany;

@Component
public class VacancyService {
    private Company company;
    public void placeVacancy(String vacancy, Company company) {
        System.out.println("Company with name " + company.getName() + " placed vacancy: " + vacancy);
    }

    public void placeVacancy(String vacancy) {
        this.placeVacancy(vacancy, this.company);
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
