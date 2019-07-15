package ch13;

import java.util.ArrayList;
import java.util.List;

public abstract class Fahrzeug {

    private List<Rad> raeder;

    private Fahrer fahrer;
    private int kilometer;

    Fahrzeug(int anzahlRaeder) {
        raeder = new ArrayList<>();
        for (int i = 0; i < anzahlRaeder; i++) {
            raeder.add(new Rad());
        }
    }

    public void setFahrer(Fahrer fahrer) {
        this.fahrer = fahrer;
    }

    public abstract double getKosten();

    public int getKilometer() {
        return kilometer;
    }
}
