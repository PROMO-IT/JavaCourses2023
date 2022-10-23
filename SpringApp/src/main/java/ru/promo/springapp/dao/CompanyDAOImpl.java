package ru.promo.springapp.dao;

import org.springframework.stereotype.Component;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;

import java.util.logging.Logger;

@Component("companyDAOImpl")
public class CompanyDAOImpl implements CompanyDAO {
    private static Logger log = Logger.getLogger(CompanyDAOImpl.class.getName());

    @Override
    public void create(Company<CompanyType> company) {
        log.info(getClass().getName() + ": creating company");
    }
}
