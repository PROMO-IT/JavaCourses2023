package ru.promo.springapp.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(long id) {
        super("Company with id = " + id + "  not found");
    }
}
