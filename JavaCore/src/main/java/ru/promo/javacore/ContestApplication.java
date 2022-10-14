package ru.promo.javacore;

import ru.promo.javacore.model.Company;
import ru.promo.javacore.model.ITCompany;
import ru.promo.javacore.task.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.LongStream;

public class ContestApplication {

    private static List<List<ITCompany>> contestLists = List.of(
            new AbakumovaList<>(),
            new KirillList<>(),
            new MadScorpion25List<>(),
            new NatalyaList<>(),
//            new ProdigyGirlList<>(),
//            new RailShabayevList<>(),
            new RamproxList<>(),
            new ReznikovList<>(),
//            new VarozhList<>(),
            new VladisList<>()
    );

    private static Comparator<ITCompany> comparator = Comparator.comparing(ITCompany::getEmployeeCount).thenComparing(Company::getName);

    public static void main(String[] args) {
        contestLists.forEach(l -> {
            System.out.println(((AuthorHolder) l).getAuthor() + " - " + calcAverageSorting(l));
        });
    }

    private static List<ITCompany> createCompanies() {
        List<ITCompany> list = new ArrayList<>();
        int size = 200000;
        for (int i = 0; i < size/2; i++) {
            ITCompany company = new ITCompany("Company" + Integer.toString(2*i ), size - i);
            list.add(company);
            company = new ITCompany("Company" + Integer.toString(2*i + 1 ), size - i);
            list.add(company);
        }
        return list;
    }

    private static double calcAverageSorting(List<ITCompany> list) {
        List<ITCompany> data = createCompanies();
        return LongStream.range(0, 100).map(i -> {
            list.clear();
            list.addAll(data);
            long l = System.currentTimeMillis();
            list.sort(comparator);
            return System.currentTimeMillis() - l;
        }).average().getAsDouble();
    }
}
