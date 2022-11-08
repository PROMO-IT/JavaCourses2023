package ru.promo.springapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.promo.springapp.dao.CompanyDAO;
import ru.promo.springapp.dao.CompanyDAOImpl;
import ru.promo.springapp.dao.CompanyRepository;
import ru.promo.springapp.exception.CompanyNotFoundException;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class CompanyService {
//    private final CompanyDAO companyDAO;
    private final CompanyRepository companyRepository;

    @Transactional
    public void createCompany(Company company) {
        //company validation
        company.getVacancies().forEach(vacancy -> vacancy.setCompany(company));
//        companyDAO.create(company);
        Company savedCompany = companyRepository.save(company);
        log.info("Saved company: {}", savedCompany);
        //...
    }

    public Company findById(long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(id));
    }

    public List<Company> findByType(CompanyType type) {
//        return companyRepository.findByCompanyType(type);
//        return companyRepository.getByCompanyType(type);
        return companyRepository.selectByCompanyType(type.name());
    }
}
