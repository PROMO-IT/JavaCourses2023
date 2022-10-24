package ru.promo.springapp.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;

import java.util.logging.Logger;

@Slf4j
@Component("companyDAOImpl")
public class CompanyDAOImpl implements CompanyDAO {
    @Override
    public void create(Company<CompanyType> company) {
        log.info(getClass().getName() + ": creating company");
    }
}
