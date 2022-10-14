package ru.promo.javacore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.promo.javacore.model.Company;
import ru.promo.javacore.model.ITCompany;
import ru.promo.javacore.task.*;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class CustomListTest {
    private List<List<ITCompany>> lists = List.of(
//            new AbakumovaList<>(),
//            new Alexyz1103List<>(),
//            new AntoshkeenList<>(),
//            new DonnaQuixoteList<>(),
//            new DSList<>(),
//            new DzList<>(),
//            new EgorovVSList<>(),
            new KirillList<>(),
//            new KondorList<>(),
//            new KresiaKuList<>(),
//            new MadScorpion25List<>(),
//            new MyListik<>(),
            new Nail73List<>(),
//            new NatalyaList<>(),
//            new ProdigyGirlList<>(),
            new RailShabayevList<>(),
            new RamproxList<>(),
            new ReznikovList<>()
//            new SimpleArray<>(),
//            new SuppaPuppaZiList<>()
//            new VarozhList<>()
//            new VladisList<>()
    );

    private static List<ITCompany> companies = null;
    private static Comparator<ITCompany> comparator = Comparator.comparing(ITCompany::getEmployeeCount).thenComparing(Company::getName);

    @BeforeAll
    public static void setUp() {
        System.out.println("setUp");
        companies = createCompanies();
    }

    @Test
    public void testAddElementByIndex() {
        lists.forEach(l -> {
            l.addAll(companies.subList(0,5));
            Assertions.assertEquals(5, l.size(), l.getClass().getName() + " is incorrect");
        });

        ITCompany company = companies.get(6);

        lists.forEach(l -> {
            l.add(3, company);
            Assertions.assertEquals(company, l.get(3), l.getClass().getName() + " is incorrect");
        });
    }

    @Test
    public void testRemoveElement() {
        lists.forEach(l -> {
            l.addAll(companies.subList(0,5));
            Assertions.assertEquals(5, l.size(), l.getClass().getName() + " is incorrect");

            int sizeBefore = l.size();
            ITCompany removedItem = l.remove(3);
            Assertions.assertEquals(sizeBefore - 1, l.size(), l.getClass().getName() + " is incorrect");
            Assertions.assertFalse(l.contains(removedItem), l.getClass().getName() + " is incorrect");
        });
    }

    @Test
    public void testToArray() {
        List<ITCompany> itCompanies = companies.subList(0, 5);
        Object[] array = itCompanies.toArray();

        lists.forEach(l -> {
            l.addAll(itCompanies);
            Object[] objects = l.toArray();
            Assertions.assertEquals(array.length, objects.length, l.getClass().getName() + " is incorrect");
            for (int i = 0; i < array.length; i++) {
                Assertions.assertEquals(array[i], objects[i], l.getClass().getName() + " is incorrect");
            }
        });
    }

    @Test
    public void testListIterator() {
        lists.forEach(l -> {
            l.addAll(companies.subList(0, 10));
            int size = l.size();
            ListIterator<ITCompany> iterator = l.listIterator();
            Assertions.assertNotNull(iterator);

            while (iterator.hasNext()) {
                ITCompany company = iterator.next();
                Assertions.assertEquals(company, iterator.previous());
                iterator.next();
            }

            iterator.remove();
            Assertions.assertEquals(size - 1, l.size(), l.getClass().getName() + " is incorrect");
        });
    }

    @Test
    public void testSortList() {
        List<ITCompany> list = new ReznikovList<>();
        list.addAll(companies);
        Assertions.assertEquals(companies.size(), list.size());

        list.sort(comparator);
        companies.sort(comparator);

        Assertions.assertEquals(companies.size(), list.size());
        for (int i = 0; i < companies.size(); i++) {
            Assertions.assertEquals(companies.get(i), list.get(i));
        }
    }

    private static List<ITCompany> createCompanies() {
        List<ITCompany> list = new ArrayList<>();
        int size = 10000;
        for (int i = 0; i < size/2; i++) {
            ITCompany company = new ITCompany("Company" + Integer.toString(2*i ), size - i);
            list.add(company);
            company = new ITCompany("Company" + Integer.toString(2*i + 1 ), size - i);
            list.add(company);
        }
        return list;
    }
}
