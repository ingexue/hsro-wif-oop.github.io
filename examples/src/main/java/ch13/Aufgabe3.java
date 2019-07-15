package ch13;

public class Aufgabe3 {

    /**
     * Ermittelt die Quersumme eienr Zahl iterativ.
     * @param z Zahl dessen Quersumme ermittelt werden soll.
     * @return Quersumme
     */
    private static int quersumme(int z) {
        int resultat = 0;
        while(z > 0) {
            resultat += z % 10;
            z = z /10;
        }
        return resultat;
    }


    /**
     * Ermittelt die Quersumme eienr Zahl rekursiv.
     * @param z Zahl dessen Quersumme ermittelt werden soll.
     * @return Quersumme
     */
    private static int quersummeR(int z) {

        if (z <= 9)
            return z;

        return (z % 10) + quersummeR(z/10);
    }

    /**
     * Ermittelt die Quersumme eienr Zahl tail-rekursiv (end-rekursiv).
     * @param z Zahl dessen Quersumme ermittelt werden soll.
     * @return Quersumme
     */
    private static int quersummeTR(int z) {
        return quersummeH(z, 0);
    }

    /**
     * Hilfunktion zur end-rekurisiven Ermittelung der Quersumme.
     * @param z Zahl dessen Quersumme ermittelt werden soll.
     * @param summe Akkumulator
     * @return Quersumme
     */
    private static int quersummeH(int z, int summe) {

        if (z <= 9)
            return z + summe;

        return quersummeH(z/10, summe + (z % 10) );
    }

    public static void main(String[] args) {

        System.out.println(quersumme(21));
        System.out.println(quersumme(111));
        System.out.println(quersumme(2222));
        System.out.println(quersumme(1));

        System.out.println();

        System.out.println(quersummeR(21));
        System.out.println(quersummeR(111));
        System.out.println(quersummeR(2222));
        System.out.println(quersummeR(1));

        System.out.println();

        System.out.println(quersummeTR(21));
        System.out.println(quersummeTR(111));
        System.out.println(quersummeTR(2222));
        System.out.println(quersummeTR(1));

    }
}
