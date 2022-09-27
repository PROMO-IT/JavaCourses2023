package ru.promo.javacore;

import ru.promo.javacore.model.*;

public class JavaCoreApplication {
    public static void main(String[] args) {
//        System.out.println("Hello world!" + Arrays.toString(args));
//        Lection1.task();
        ITCompany company1 = new ITCompany("Company1", 10);
        System.out.println(company1);
        ITCompany company2 = new ITCompany("Company1", 10);
        System.out.println(company2);

        System.out.println(company1.getName());
        company2.placeVacancy("Java Developer");
        company1.work();
        ITCompany.someMethod();
        Company.someMethod();

        BuilderCompany builderCompany = new BuilderCompany("OOO Builder", 100);

//        System.out.println(Factorial.calcFactorial(5));
//        Factorial.factorial.test();
        Company[] companies = new Company[3];
        companies[0] = company1;
        companies[1] = company2;
        companies[2] = builderCompany;

        startWorkDay(companies);
        System.out.println(company1.hashCode());
        System.out.println(company2.hashCode());
        System.out.println(company1.equals(company2));
        System.out.println(company1.getAttr());
        System.out.println(builderCompany.getAttr());

        reAccreditation(builderCompany);

        Notification<Double> balanceNotification = new Notification<>("balance", 100.0);

        Notification<SomeInformation> informationNotification = new Notification<>("info", new SomeInformation("123", 123));
    }

    public static void startWorkDay(Organization[] companies) {
        for (var company : companies) {
            company.work();
        }
    }

    public static <T extends Company<CompanyType>> void reAccreditation(T company) {
        CompanyType type = company.getAttr();
        switch (type) {
            case IT -> System.out.println("Reaccreditation of IT Company with name " + company.getName());
            case BUILDER -> System.out.println("Reaccreditation of Builder Company with name " + company.getName());
        }
    }
}
