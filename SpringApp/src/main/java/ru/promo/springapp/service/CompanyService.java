package ru.promo.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.promo.springapp.dao.CompanyDAO;
import ru.promo.springapp.dao.CompanyDAOImpl;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;

@Component
public class CompanyService {
    private CompanyDAO companyDAO;

    @Autowired
    public CompanyService(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public void registerCompany(Company company) {
        //company validation
        companyDAO.create(company);
    }
}
