package ru.promo.springapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import ru.promo.springapp.model.Company;
import ru.promo.springapp.model.CompanyType;
import ru.promo.springapp.model.ITCompany;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class DemoController {
    @Autowired
    private ApplicationContext context;

    @Autowired
    @Qualifier("company1")
    private Company company;

    @GetMapping("/hello")
    public String hello() {
        log.info("got hello request");
        return "hello";
    }

    @GetMapping("/company")
    public Company getCompany(@RequestParam("key1") String key1,
                              @RequestParam(value = "key2", required = false) String key2) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(company);
        log.info("GET company with params: {}, {}", key1, key2);
//        return company;
        log.info("bean: {}", context.getBean("company1", Company.class));
        log.info("bean: {}", context.getBean("company1", Company.class));
        log.info("bean: {}", company);
        return company;
    }

    @PostMapping("/create/{variable}")
    public ResponseEntity<Company> createCompany(@PathVariable("variable") String variable,
                                 @RequestBody Company company) {
        log.info("POST company with params: {}", variable);
        log.info("POST company with body: {}", company);
        return ResponseEntity.ok(company);
    }
}
