package ru.promo.springapp.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;

import java.util.logging.Logger;

@Component("companyRepository")
public class CompanyRepository implements CompanyDAO {
    private static Logger log = Logger.getLogger(CompanyRepository.class.getName());
    @Override
    public void create(Company<CompanyType> company) {
        log.info(getClass().getName() + ": creating company");
    }
}
