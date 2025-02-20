package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

//@TeleOp(name="manual control", group="jaden the great")

public abstract class manual extends LinearOpMode {


    /**
     * Controls the robot's various mechanisms based on gamepad input.
     *
     * @param bot              The robot object.
     * @param gamepad2         The gamepad object for input.
     * @param frontPitchSens   Sensitivity for front wrist pitch.
     * @param frontRollSens    Sensitivity for front wrist roll.
     * @param backPitchSens    Sensitivity for back wrist pitch.
     * @param backRollSens     Sensitivity for back wrist roll.
     * @param armSens          Sensitivity for arm movement.
     * @param linearSlideSens  Sensitivity for linear slide movement.
     * @param elbowSens        Sensitivity for elbow movement.
     *
     */


    public void manual (Robot bot, Gamepad gamepad2,
                        double frontPitchSens, double frontRollSens,
                        double backPitchSens, double backRollSens,
                        double armSens, double linearSlideSens, double elbowSens) {

        Servo fwroll = bot.frontWristRoll; //pitch
        Servo fwpitch = bot.frontWrist; // roll
        Servo bwpitch = bot.wrist; // pitch
        Servo bwroll = bot.wrist2; //roll

        Servo frontclaw = bot.claw;
        Servo backclaw = bot.claw2;

        DcMotor arm = bot.arm;
        DcMotor linear = bot.linearSlide;
        DcMotor elbow = bot.elbow;


        // open and close the claws
        if (gamepad2.a) {
            if (frontclaw.getPosition() < .175) {
                frontclaw.setPosition(.25);
            } else {
                frontclaw.setPosition(.1);
            }
        }

        if (gamepad2.b) {
            if (backclaw.getPosition() < .375) {
                backclaw.setPosition(.5);
            } else {
                backclaw.setPosition(.25);
            }
        }


        // control the linear slides OR arm depending on if y is pressed

        double pow = gamepad2.right_trigger - gamepad2.left_trigger;

        if (gamepad2.y) {
            linear.setPower(linearSlideSens * pow);
        } else {
            arm.setPower(armSens * pow);
        }


        // control the elbow

        if (gamepad2.dpad_up) {
            elbow.setPower(elbowSens);
        } else if (gamepad2.dpad_down) {
            elbow.setPower(-elbowSens);
        } else {
            elbow.setPower(0);
        }


        // control the front wrist pitch
        fwpitch.setPosition(fwpitch.getPosition() + gamepad2.left_stick_y * frontPitchSens);

        // control the front wrist roll
        fwroll.setPosition(fwroll.getPosition() + gamepad2.left_stick_x * frontRollSens);


        // control the back wrist pitch
        bwpitch.setPosition(bwpitch.getPosition() + gamepad2.right_stick_y * backPitchSens);

        // control the back wrist roll
        bwroll.setPosition(bwroll.getPosition() + gamepad2.right_stick_x * backRollSens);


        sleep(20);
    }
}
