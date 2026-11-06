package org.firstinspires.ftc.teamcode.purple;
import com.pedropathing.math.Pose;

public class Point {
    private double x;
    private double y;
    private double angle;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.angle = 0.0;
    }

    public Point(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }

    public double getAngle() { return angle; }

    public Pose convertToPose() {
        return new Pose(x, y, Math.toRadians(angle));
    }

    public void update(double x, double y, double angle) {
        this.x = x;
        this.y = y;

        while (angle >= 360) {
            this.angle = angle-360;
        }
        this.angle = angle;
    }
}
