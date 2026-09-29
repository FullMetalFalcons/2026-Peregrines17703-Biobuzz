package org.firstinspires.ftc.teamcode;

public class PIDFController {
    // PIDF coefficients
    public double kP, kI, kD, kF;

    // State variables
    private double sumError = 0;
    private double lastError = 0;
    private double lastTime = 0;

    public PIDFController(double kP, double kI, double kD, double kF) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
    }

    public void setPIDF(double kP1, double kI1, double kD1, double kF1) {
        kP = kP1;
        kI = kI1;
        kD = kD1;
        kF = kF1;
    }

    public void reset() {
        sumError = 0;
        lastError = 0;
        lastTime = 0;
    }

    public double calculate(double target, double current) {
        double currentTime = System.nanoTime() / 1e9;
        double deltaTime = (lastTime == 0) ? 0 : (currentTime - lastTime);

        double error = target - current;

        // Proportional
        double P = kP * error;

        // Integral
        if (deltaTime > 0) {
            sumError += error * deltaTime;
        }
        double I = kI * sumError;

        // Derivative
        double derivative = (deltaTime > 0) ? (error - lastError) / deltaTime : 0;
        double D = kD * derivative;

        // Feedforward
        double F = kF * target;

        // Store for next loop
        lastError = error;
        lastTime = currentTime;

        // Total output
        return P + I + D + F;
    }
}
