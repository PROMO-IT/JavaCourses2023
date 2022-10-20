package ru.promo.springapp.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;
import ru.promo.springapp.model.ITCompany;

@Configuration
public class AppConfiguration {
    @Bean
    public String companyName() {
        return "PROMO";
    }

    @Bean
    public Company<CompanyType> company1() {
        return new ITCompany("Promo IT", 30);
    }

    @Bean
    public Company<CompanyType> company2(String companyName) {
        return new ITCompany(companyName, 100);
    }
}
