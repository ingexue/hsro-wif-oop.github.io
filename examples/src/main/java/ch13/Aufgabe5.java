package ch13;

import java.util.Objects;

public class Aufgabe5 {

    static class Quadrat {
        protected int len;

        Quadrat(int len) {
            this.len = len;
        }

        public int getArea(){
            return len * len;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Quadrat)) return false;
            Quadrat quadrat = (Quadrat) o;
            return len == quadrat.len;
        }

        @Override
        public int hashCode() {
            return Objects.hash(len);
        }
    }

    static class Rechteck extends Quadrat {

        private int height;

        Rechteck(int len, int height) {
            super(len);
            this.height = height;
        }

        @Override
        public int getArea() {
            return len * height;
        }

        public void test() {
            System.out.println("test");
        }
    }

    public static void main(String[] args) {
        Rechteck q = new Rechteck(2, 4);
        System.out.println(q.getArea());
        q.test();
        Quadrat q2 = q;
        System.out.println(q2.getArea());
        ((Rechteck) q2).test();
    }
}
