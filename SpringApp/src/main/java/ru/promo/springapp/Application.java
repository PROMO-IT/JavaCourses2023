package ru.promo.springapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import ru.promo.springapp.model.ITCompany;
import ru.promo.springapp.service.CompanyService;

import java.util.logging.Logger;

@Slf4j
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        ITCompany company = context.getBean("company1", ITCompany.class);
        log.info("Company = " + company);

        CompanyService companyService = context.getBean(CompanyService.class);
        companyService.registerCompany(company);
    }
}
