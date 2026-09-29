package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class MerlinLauncher {
    public DcMotorEx motorLaunch, gear, intake;
    public static double launcher_p = 0, launcher_f = 0; // TODO toon ts
    public static double gear_p = 0, gear_f = 0; // TODO toon ts

    private boolean isLaunching = false;
    private boolean isIntaking = false;

    private boolean isRight = true;

    private double currentHeading;

    public boolean autoAlign = false;


    public void init(HardwareMap hwMap) {
        motorLaunch = (DcMotorEx) hwMap.dcMotor.get("flywheel");
        motorLaunch.setDirection(DcMotorSimple.Direction.FORWARD);
        motorLaunch.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorLaunch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLaunch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorLaunch.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(launcher_p, 0, 0, launcher_f));

        gear = (DcMotorEx) hwMap.dcMotor.get("gear");
        gear.setDirection(DcMotorSimple.Direction.FORWARD);
        gear.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        gear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        gear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        gear.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(gear_p, 0, 0, gear_f));

        intake = (DcMotorEx) hwMap.dcMotor.get("intake");
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public double move(double roboX, double roboY, double heading) {
        double targetX = 0.0;
        double targetY = 0.0;
        double turn = 0.0;

        if (roboX > 71) {
            targetX = 57;
            targetY = 57;
        }
        if (roboX < 71) {
            targetX = 57;
            targetY = 86;
        }

        double a = targetX - roboX;
        double b = targetY - roboY;
        double angle = Math.toDegrees(Math.atan2(b, a));

        if (a > 0) {
            turn = heading + angle;
        }
        if (a <= 0) {
            turn = -(heading + angle);
        }

        return angle;
    }

    public void update(double distance) {
        motorLaunch.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(launcher_p, 0, 0, launcher_f));

        gear.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(gear_p, 0, 0, gear_f));


        if (isLaunching) { motorLaunch.setVelocity(distance); /* TODO 3 point equation on Desmos */ }
        else { motorLaunch.setVelocity(0); }

        if (isIntaking) { intake.setPower(1); }
        else { intake.setPower(0); }
    }
}