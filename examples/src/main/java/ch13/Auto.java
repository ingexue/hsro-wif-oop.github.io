package ch13;

public class Auto extends Fahrzeug {

    Auto() {
        super(4);
    }

    @Override
    public double getKosten() {
        return 20;
    }
}
