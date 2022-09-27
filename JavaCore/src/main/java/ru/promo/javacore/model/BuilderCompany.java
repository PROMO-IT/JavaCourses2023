package ru.promo.javacore.model;

public class BuilderCompany extends Company<CompanyType> {

    public BuilderCompany(String name, int employeeCount) {
        super(name, employeeCount);
        setAttr(CompanyType.BUILDER);
    }

    @Override
    public void work() {
        System.out.println("BuilderCompany with name " + getName() + " is building");
    }
}
