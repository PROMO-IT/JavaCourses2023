package ru.promo.springapp.dao;

import org.springframework.transaction.annotation.Transactional;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;

import java.util.List;

public interface CompanyDAO {
    void create(Company company);
    Company findById(long id);
    List<Company> findByType(CompanyType type);
}
