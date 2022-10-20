package ru.promo.springapp.service;

import org.springframework.stereotype.Component;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.ITCompany;

@Component
public class VacancyService {
    private ITCompany company;
    public void placeVacancy(String vacancy, Company company) {
        System.out.println("Company with name " + company.getName() + " placed vacancy: " + vacancy);
    }

    public void placeVacancy(String vacancy) {
        this.placeVacancy(vacancy, this.company);
    }

    public ITCompany getCompany() {
        return company;
    }

    public void setCompany(ITCompany company) {
        this.company = company;
    }
}
