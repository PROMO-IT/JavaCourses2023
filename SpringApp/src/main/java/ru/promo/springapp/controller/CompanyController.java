package ru.promo.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.promo.springapp.dao.CompanyDAO;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;

import java.util.List;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {
    @Autowired
    private CompanyDAO companyDAO;

    @PostMapping
    public void create(@RequestBody Company company) {
        companyDAO.create(company);
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Long id) {
        return companyDAO.findById(id);
    }

    @GetMapping
    public List<Company> findCompaniesByType(@RequestParam("type") CompanyType type) {
        return companyDAO.findByType(type);
    }
}
