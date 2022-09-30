package ru.promo.javacore.service;

import ru.promo.javacore.model.Company;

public class ITReaccreditationProcess implements ReaccreditationProcess {
    @Override
    public void process(Company company) {
        System.out.println("Reaccreditation of IT Company with name " + company.getName());
    }
}
