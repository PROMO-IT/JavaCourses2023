package ru.promo.javacore.lections;

public class Lection1 {
    public static void task() {
        int a = 2;
        System.out.println("a = " + a);

        {
            long b = 1L;
            System.out.println("b = " + b);
        }

        double d = 1.1d;
        System.out.println("d = " + d);

        char c = 'a';
        System.out.println("c = " + c + ", code(c) = " + (int) c);

        boolean f = true;
        System.out.println("f = " + f);

        if (a > 5) {
            System.out.println("Condition1");
        } else if (a <= 0) {
            System.out.println("Condition2");
        } else {
            System.out.println("Condition3");
        }

        if (d > 2) {
            System.out.println("Condition4");
        }

        int s = (a > 3) ? 1 : 2;
        System.out.println("s = " + s);

        switch (a) {
            case 0:
                System.out.println("switch1");
                break;
            case 1:
                System.out.println("switch2");
                break;
            default:
                System.out.println("default");
        }

        s = switch (a) {
            case 0, 2, 4 -> 1;
            case 1, 3, 5 -> 10;
            default -> 0;
        };
        System.out.println("s = " + s);

        int[] arr = new int[10];
        arr[5] = a;
        System.out.println("arr[5] = " + arr[5]);

        while (a < 5) {
            System.out.println("while: a = " + a++);
        }

        a = 0;
        do {
            System.out.println("do: a = " + a++);
        } while (a < 2);

        int i = 0;
        for (i = 0; ; i++) {
            arr[i] = i;
            if (i == arr.length - 1) break;
        }

        System.out.println(i);

        System.out.print("arr = ");
        for (int r : arr) {
            System.out.print(r + " ");
        }
    }
}
