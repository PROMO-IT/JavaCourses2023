package ru.promo.javacore.model;

public class ITCompany extends Company<CompanyType> {
    private String[] technologies;
    public ITCompany(String name, int employeeCount) {
        super(name, employeeCount);
        technologies = new String[10];
        setAttr(CompanyType.IT);
    }

    private void develop() {
        System.out.println("ITCompany with name " + this.name + " is developing");
    }

    public static void someMethod() {
        System.out.println("static method of ITCompany");
    }

    @Override
    public void work() {
        develop();
    }

    public String[] getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String[] technologies) {
        this.technologies = technologies;
    }
}
