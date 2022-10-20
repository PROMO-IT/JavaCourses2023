package ru.promo.springapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import ru.promo.springapp.model.ITCompany;
import ru.promo.springapp.service.CompanyService;

import java.util.logging.Logger;

@SpringBootApplication
public class Application {
    private static Logger log = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        ITCompany company = context.getBean("company1", ITCompany.class);

        CompanyService companyService = context.getBean(CompanyService.class);
        companyService.registerCompany(company);
    }
}
