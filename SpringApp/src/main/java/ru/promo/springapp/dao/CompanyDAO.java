package ru.promo.springapp.dao;

import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;

public interface CompanyDAO {
    void create(Company<CompanyType> company);
}
