package ch13;

public class Aufgabe2 {

    /**
     * Methode ermittelt einen fehlenden Wert im Array.
     *
     * @param a Array mit fehlenden Wert
     */
    public static void fehlenderWert(int[] a) {
        if (a == null || a.length < 8)
            throw new IllegalArgumentException("Array has wrong size!");

        int posNull = 0;
        int summeAlle = 45;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 0)
                posNull = i;
            summeAlle -= a[i];
        }
        a[posNull] = summeAlle;
    }

    /**
     * Main methode
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] a = {3, 7, 2, 0, 8, 9, 4, 1, 6};
        fehlenderWert(a);
        System.out.println(a[3]);

        a = new int[]{0, 7, 2, 5, 8, 9, 4, 1, 6};
        fehlenderWert(a);
        System.out.println(a[0]);

        a = new int[]{3, 7, 2, 5, 8, 9, 4, 0, 6};
        fehlenderWert(a);
        System.out.println(a[7]);
    }
}
