package ru.promo.springapp.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;
import ru.promo.springapp.model.ITCompany;

@Slf4j
@Configuration
public class AppConfiguration {
    private static int cnt = 30;
    @Bean
    public String companyName() {
        return "PROMO";
    }

    @Bean
//    @Scope("prototype")
//    @RequestScope
    @SessionScope
    public Company company1() {
        log.info("init bean company");
        return new ITCompany("Promo IT", cnt++);
    }

    @Bean
    public Company company2(String companyName) {
        return new ITCompany(companyName, 100);
    }
}
