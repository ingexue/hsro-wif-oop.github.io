package ch13;

public class Fahrrad extends Fahrzeug {

    Fahrrad() {
        super(2);
    }

    @Override
    public double getKosten() {
        return 10;
    }
}
