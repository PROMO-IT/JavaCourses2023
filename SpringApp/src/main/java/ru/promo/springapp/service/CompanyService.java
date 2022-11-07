package ru.promo.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.promo.springapp.dao.CompanyDAO;
import ru.promo.springapp.dao.CompanyDAOImpl;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;

@Service
public class CompanyService {
    private CompanyDAO companyDAO;

    @Autowired
    public CompanyService(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    @Transactional
    public void createCompany(Company company) {
        //company validation
        company.getVacancies().forEach(vacancy -> vacancy.setCompany(company));
        companyDAO.create(company);
        //...
    }
}
