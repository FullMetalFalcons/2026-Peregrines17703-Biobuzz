package org.firstinspires.ftc.teamcode.purple;

public class Zone {
    Point A;
    Point B;

    public Zone(Point A, Point B) {
        this.A = A;
        this.B = B;
    }

    public boolean inZone(Point A) {
        double largerX = 0;
        double smallerX = 0;
        double largerY = 0;
        double smallerY = 0;

        if (this.A.getX() > this.B.getX()) {
            largerX = this.A.getX();
            smallerX = this.B.getX();
        }
        else if (this.B.getX() > this.A.getX()) {
            largerX = this.B.getX();
            smallerX = this.A.getX();
        }

        if (this.A.getY() > this.B.getY()) {
            largerY = this.A.getY();
            smallerY = this.B.getY();
        }
        if (this.B.getY() > this.A.getY()) {
            largerY = this.B.getY();
            smallerY = this.A.getY();
        }

        if ((A.getX() >= smallerX) &&
                (A.getX() <= largerX) &&
                (A.getY() >= smallerY) &&
                (A.getY() <= largerY)) {
            return true;
        }
        return false;
    }
}
