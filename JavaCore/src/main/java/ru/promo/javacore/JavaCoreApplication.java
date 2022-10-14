package ru.promo.javacore;

import ru.promo.javacore.model.*;
import ru.promo.javacore.service.BuilderReaccreditationProcess;
import ru.promo.javacore.service.ITReaccreditationProcess;
import ru.promo.javacore.service.ReaccreditationProcess;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JavaCoreApplication {

    private static Map<CompanyType, ReaccreditationProcess> processMap = Map.of(
            CompanyType.IT, new ITReaccreditationProcess(),
            CompanyType.BUILDER, new BuilderReaccreditationProcess(),
            CompanyType.CONSULTING, (c) -> System.out.println("Reaccreditation of Consulting Company with name " + c.getName())
    );

    public static void main(String[] args) {
//        System.out.println("Hello world!" + Arrays.toString(args));
//        Lection1.task();
        ITCompany company1 = new ITCompany("Company1", 10);
        System.out.println(company1);
        ITCompany company2 = new ITCompany("Company2", 10);
        System.out.println(company2);
        company2.setAttr(CompanyType.CONSULTING);

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

//        reAccreditation(builderCompany);

        Notification<Double> balanceNotification = new Notification<>("balance", 100.0);

        Notification<SomeInformation> informationNotification = new Notification<>("info", new SomeInformation("123", 123));

        List<Company<CompanyType>> list = new ArrayList<>();
        list.add(company1);
        list.add(company2);
        list.add(builderCompany);
        list.add(builderCompany);

        Object[] objects = new Object[0];
        Object[] objects1 = list.toArray(objects);
        System.out.println("toArray");
        Arrays.stream(objects1).forEach(System.out::println);

        list.forEach(JavaCoreApplication::reAccreditation);

        Set<String> collect = list.stream()
                .map(Company::getName)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());
        System.out.println(collect);

        Optional<String> first = list.stream()
                .filter(company -> CompanyType.IT.equals(company.getAttr()))
                .map(Company::getName)
                .findFirst();

//        System.out.println(list);
//        System.out.println(list.size());

//        reAccreditation(company1);
//        reAccreditation(builderCompany);
//        reAccreditation(company2);


//        int[] ints = IntStream.range(0, 1000000000).toArray();
//        IntStream stream = Arrays.stream(ints);
//
//        long l = System.currentTimeMillis();
//        stream.sum();
//        System.out.println(System.currentTimeMillis() - l);
//
//        int s = 0;
//        l = System.currentTimeMillis();
//        for (int a: ints) {
//            s += a;
//        }
//        System.out.println(System.currentTimeMillis() - l);
    }

    public static void startWorkDay(Organization[] companies) {
        for (var company : companies) {
            company.work();
        }
    }

    public static <T extends Company<CompanyType>> void reAccreditation(T company) {
        CompanyType type = company.getAttr();
        ReaccreditationProcess reaccreditationProcess = processMap.get(type);
        reaccreditationProcess.process(company);
    }
}
