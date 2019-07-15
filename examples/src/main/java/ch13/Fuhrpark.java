package ch13;

import java.util.ArrayList;
import java.util.List;

public class Fuhrpark {

    private List<Fahrzeug> fahrzeuge;

    Fuhrpark() {
        this.fahrzeuge = new ArrayList<>();
    }

    public void addFahrzeug(Fahrzeug f) {
        this.fahrzeuge.add(f);
    }

    public double getGesamtKosten() {
        int gesamt = 0;
        for (Fahrzeug f: fahrzeuge) {
            gesamt += f.getKosten();
        }
        return gesamt;
    }

    public static void main(String[] args) {
        Fuhrpark fp = new Fuhrpark();
        Auto a = new Auto();
        a.setFahrer(new Fahrer());

        Fahrrad f = new Fahrrad();
        f.setFahrer(new Fahrer());

        fp.addFahrzeug(a);
        fp.addFahrzeug(f);
        System.out.println(fp.getGesamtKosten());
    }
}
