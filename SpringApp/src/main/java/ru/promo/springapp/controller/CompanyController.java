package ru.promo.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.promo.springapp.dao.CompanyDAO;
import ru.promo.springapp.exception.CompanyNotFoundException;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;
import ru.promo.springapp.service.CompanyService;
import ru.promo.springapp.service.VacancyService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping
    public void create(@RequestBody Company company) {
        companyService.createCompany(company);
//        companyDAO.create(company);
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Long id) {
        return companyService.findById(id);
    }

    @GetMapping
    public List<Company> findCompaniesByType(@RequestParam("type") CompanyType type) {
        return companyService.findByType(type);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CompanyNotFoundException.class)
    public String companyNotFound(CompanyNotFoundException e) {
        return e.getMessage();
    }
}
