package org.firstinspires.ftc.teamcode.purple;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class Light implements Servo {
    private double color;
    
    private int red;
    private int green;
    private int blue;

    public Light(int r, int g, int b) {
        this.color = convertFromRGB(r, g, b);
    }

    public Light() {
        this.color = 0.0;
    }

    public void setColor(double color) {
        this.color = color;
    }

    public void setColor(String color) {
        if (color == "RED") {
            this.color = convertFromRGB(255, 0, 0);
        }
        if (color == "ORANGE") {
            this.color = convertFromRGB(255, 165, 0);
        }
        if (color == "YELLOW") {
            this.color = convertFromRGB(155, 255, 0);
        }
        if (color == "GREEN") {
            this.color = convertFromRGB(0, 255, 0);
        }
        if (color == "BLUE") {
            this.color = convertFromRGB(0, 0, 255);
        }
        if (color == "PURPLE") {
            this.color = convertFromRGB(255, 0, 255);
        }
        else {
            this.color = convertFromRGB(0, 0, 0);
        }
    }

    private double convertFromRGB(int r, int g, int b) {

        double rPrime = r / 255.0;
        double gPrime = g / 255.0;
        double bPrime = b / 255.0;

        double cMax = Math.max(rPrime, Math.max(gPrime, bPrime));
        double cMin = Math.min(rPrime, Math.min(gPrime, bPrime));

        double delta = cMax - cMin;

        if (delta == 0) {
            return 0.0;
        }

        double hue = 0;
        if (cMax == rPrime) {
            hue = 60 * ((gPrime - bPrime) / delta);
        }
        else if (cMax == gPrime) {
            hue = 60 * (((bPrime - rPrime) / delta) + 2);
        }
        else {
            hue = 60 * (((rPrime - gPrime) / delta) + 4);
        }

        if (hue < 0) {
            hue += 360;
        }

        return hue;

    }

    public void update() {
        setPosition(color);
    }
}
