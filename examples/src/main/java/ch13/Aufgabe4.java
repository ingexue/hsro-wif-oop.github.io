package ch13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Aufgabe4 {

    /**
     * Ermittelt das Maximum in einem Array.
     * @param a Array
     * @return Maximum
     */
    static int max(int[] a) {
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < a.length; i++) {
            if(a[i] > max)
                max = a[i];
        }
        return max;
    }

    /**
     * Ermittelt das Maximum in einem Array.
     * @param a Array
     * @return Maximum
     */
    static int max2(int[] a) {
        if (a.length == 0)
            return Integer.MIN_VALUE;
        Arrays.sort(a);
        return a[a.length-1];
    }

    /**
     * Ermittelt das Maximum in einer Liste.
     * @param list Liste
     * @return Maximum
     */
    static int max3(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            int i = it.next();
            if (i > max)
                max = i;
        }
        return max;
    }

    /**
     * Klasse zum ermitteln des Maximums.
     * @param <T>
     */
    static class MyList<T extends Comparable> {

        public T max(List<T> list) {
            T max = list.get(0);
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                T i = it.next();
                if (i.compareTo(max) > 0)
                    max = i;
            }
            return max;
        }
    }

    public static void main( String [] args ) {

        int[] a = new int[]{3,2,1,4,1,456,74,21,21};

        int m = max(a);
        System.out.println(m);


        List<Integer> l = new ArrayList<>();
        l.add(29);
        l.add(19);
        l.add(20);
        l.add(21);
        l.add(25);
        l.add(22);
        l.add(23);

        Integer m2 = max3(l);
        System.out.println(m2);


        java.util.List<Integer> l2 = new ArrayList<Integer>();
        l2.add(29);
        l2.add(19);
        l2.add(20);
        l2.add(21);
        l2.add(25);
        l2.add(22);
        l2.add(23);

        MyList<Integer> ml = new MyList<>();
        Integer mi = ml.max(l2);
        System.out.println(mi);

        java.util.List<String> l3 = new ArrayList<String>();
        l3.add("Das");
        l3.add("ist");
        l3.add("ein");
        l3.add("Test");

        MyList<String> sl = new MyList<>();
        String ms = sl.max(l3);
        System.out.println(ms);
    }
}
