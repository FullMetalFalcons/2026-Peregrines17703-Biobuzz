package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.lang.Math;

import org.firstinspires.ftc.teamcode.purple.Point;

public class Turret {
    public DcMotorEx turret;

    public Point location;
    public Point target;

    public ElapsedTime time_passed;
    public static double turret_p = 0, turret_f = 0; // TODO toon ts

    public Point RightHive = new Point(58, 58);
    public Point LeftHive = new Point(58, 84);
    //TODO gear ratio is 74/24

    public void init(HardwareMap hwMap, Point robot) {
        turret = (DcMotorEx) hwMap.dcMotor.get("turret");
        turret.setDirection(DcMotorSimple.Direction.FORWARD);
        turret.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        turret.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(turret_p, 0, 0, turret_f));

        location = new Point(robot.getX(), robot.getY());
    }

    public void loop(Point robot) {
        location.update(robot.getX(), robot.getY(), location.getAngle());

        double thing = Math.toIntExact(Math.round(Math.toDegrees(Math.atan2(target.getY()-location.getY(), target.getX()- location.getX()))));

        double factor = 1.0;

        if (location.getY() >= 75) {
            target = RightHive;
        }
        if (location.getY() < 75) {
            target = LeftHive;
        }

        double turnDistance = calculateOffset(target);

        // TODO: Make formula to convert degrees to running motor for set time

        while (turret.getCurrentPosition() != thing*factor) {
            if (turnDistance > 0)  {
                turret.setPower(0.25);
            }
            else if (turnDistance < 0) {
                turret.setPower(-0.25);
            }
        }

        turret.setPower(0);

        location.update(robot.getX(), robot.getY(), Math.toDegrees(Math.atan2(target.getY()-location.getY(), target.getX()- location.getX())));
    }

    public double calculateOffset(Point target) {
        double offset = 0.0;

        double wantedAngle = Math.toDegrees(Math.atan2(target.getY()-location.getY(), target.getX()- location.getX()));

        if (wantedAngle >= 200 && location.getAngle() < 200) {
            offset = (wantedAngle - location.getAngle()) - 360;
        }
        else if (wantedAngle < 200 && location.getAngle() >= 200) {
            offset = (wantedAngle - location.getAngle()) + 360;
        }
        else {
            offset = wantedAngle - location.getAngle();
        }

        return offset;
    }
}
