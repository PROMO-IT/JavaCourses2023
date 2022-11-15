package ru.promo.springapp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;
import ru.promo.springapp.model.ITCompany;

@Service
public class VacancyService {
    public void placeVacancy(String vacancy, Company company) {
        System.out.println("Company with name " + company.getName() + " placed vacancy: " + vacancy);
    }
}
