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
    Pose stephenPose;

    double headingPower;
    PIDFController headingPIDF;

    MerlinLauncher maxBernard = new MerlinLauncher();

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        maxBernard.init(hardwareMap);

        PIDFController headingPIDF = new PIDFController(0.01, 0, 0.001, 0.05); // TODO toon ts
    }

    @Override
    public void loop() {
        // DRIVING
        if (gamepad2.left_trigger > 0.2) {
            maxBernard.autoAlign = true;
        }
        else {maxBernard.autoAlign = false;}


        if (maxBernard.autoAlign) {
            //Wyatt DO NOT TOUCH THIS!!
            //TODO if there is an error its prob here
            headingPower = headingPIDF.calculate(maxBernard.move(stephenPose.x(), stephenPose.y(), stephenPose.heading()), stephenPose.heading());
        }
        if (isFieldCentric) {
            DrivePowers powers = ManualDrive.fieldCentric(
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    headingPower,
                    follower.pose().heading()
            );
        }
        if (!isFieldCentric) {
            ManualDrive.driveOrHold(
                    follower,
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    headingPower
            );
        }

        // Other Controls

        if (gamepad2.backWasPressed() || gamepad1.backWasPressed()) {
            isFieldCentric = !isFieldCentric;
        }


        stephenPose = follower.pose();

        // TELEMETRY
        telemetry.addData("X", stephenPose.x());
        telemetry.addData("Y", stephenPose.y());
        telemetry.addData("Field Centric", isFieldCentric);
        telemetry.addData("Heading", Math.toDegrees(stephenPose.heading()));

        // UPDATE
        if (isFieldCentric) {
            ManualDrive.driveOrHold(follower, powers);
        }
        follower.update();
        telemetry.update();

    }

    // Any additional methods go here

}