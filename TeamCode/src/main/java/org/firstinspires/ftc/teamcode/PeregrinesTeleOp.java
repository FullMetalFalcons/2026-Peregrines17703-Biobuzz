package org.firstinspires.ftc.teamcode;

//import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;

//@Configurable
@TeleOp (name = "Comp TeleOp")
public class PeregrinesTeleOp extends OpMode {
    Follower follower;

    Boolean isFieldCentric;
    DrivePowers powers;
    Pose stephenPose;

    double headingPower;
    PIDFController headingPIDF;
    boolean autoAlignToggle = true;

    PeregrineSystem maxBernard = new PeregrineSystem();

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        maxBernard.init(hardwareMap);

        isFieldCentric = false;

        //PIDFController headingPIDF = new PIDFController(0.01, 0, 0.001, 0.05); // TODO toon ts
    }

    @Override
    public void loop() {
        // DRIVING
        if (gamepad2.a) {
            // maxBernard.autoAlign = true;
            maxBernard.setLauncher(1.0);
        }
        else if (gamepad2.right_trigger >= 0.2) {
            // maxBernard.autoAlign = false;
            maxBernard.setLauncher(-1.0);
        }
        else {
            maxBernard.setLauncher(0);
        }

        if (gamepad2.left_trigger >= 0.2) {
            maxBernard.setIntake(1);
        }
        else if (gamepad2.right_bumper) {
            maxBernard.setIntake(-1);
        }
        else {
            maxBernard.setIntake(0);
        }

        if (gamepad2.backWasPressed()) {
            autoAlignToggle = !autoAlignToggle;
            isFieldCentric = !isFieldCentric;
        }

        if (gamepad2.a) {
            maxBernard.isMatchLoaderActive = !maxBernard.isMatchLoaderActive;
        }

        headingPower = gamepad1.right_stick_x;

        /* if (isFieldCentric) {
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

        /*
        if (gamepad2.backWasPressed() || gamepad1.backWasPressed()) {
            isFieldCentric = !isFieldCentric;
        }
        */

        follower.manual(
                -gamepad1.right_stick_y,
                gamepad1.right_stick_x,
                gamepad1.left_stick_x
        );


        stephenPose = follower.pose();
        maxBernard.update(100);

        // TELEMETRY
        telemetry.addData("X", stephenPose.x());
        telemetry.addData("Y", stephenPose.y());
        telemetry.addData("Field Centric", isFieldCentric);
        telemetry.addData("Heading", Math.toDegrees(stephenPose.heading()));

        // UPDATE
        //if (isFieldCentric) {
            //ManualDrive.driveOrHold(follower, powers);
            //follower.manual(powers);
        //}
        follower.update();
        telemetry.update();

    }

    // Any additional methods go here

}