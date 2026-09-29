package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Configurable
@TeleOp (name = "Comp TeleOp")
public class PeregrinesTeleOp extends OpMode {
    Follower follower;

    Boolean isFieldCentric;
    DrivePowers powers;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);

    }

    @Override
    public void loop() {
        // DRIVING
        if (isFieldCentric) {
            DrivePowers powers = ManualDrive.fieldCentric(
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x,
                    follower.pose().heading()
            );
        }
        if (!isFieldCentric) {
            ManualDrive.driveOrHold(
                    follower,
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x
            );
        }

        // Other Controls

        if (gamepad2.backWasPressed() || gamepad1.backWasPressed()) {
            isFieldCentric = !isFieldCentric;
        }


        Pose robotPose = follower.pose();

        // TELEMETRY
        telemetry.addData("X", robotPose.x());
        telemetry.addData("Y", robotPose.y());
        telemetry.addData("Field Centric", isFieldCentric);
        telemetry.addData("Heading", Math.toDegrees(robotPose.heading()));

        // UPDATE
        if (isFieldCentric) {
            ManualDrive.driveOrHold(follower, powers);
        }
        follower.update();
        telemetry.update();

    }

    // Any additional methods go here

}