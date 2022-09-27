package ru.promo.javacore;

public class Factorial {

    static {
        System.out.println("static!");
    }
    public static Factorial factorial = new Factorial();

    public Factorial() {
        System.out.println("init");
    }

    public static long calcFactorial(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * calcFactorial(n-1);
    }

    public void test() {
        System.out.println("test");
    }
}
