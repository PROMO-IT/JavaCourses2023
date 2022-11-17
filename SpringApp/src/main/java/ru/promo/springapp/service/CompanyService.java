package ru.promo.springapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.promo.springapp.annotation.Retry;
import ru.promo.springapp.dao.CompanyRepository;
import ru.promo.springapp.exception.CompanyNotFoundException;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;

import java.util.List;

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

    @Retry(count = 2, delay = 5000L)
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
