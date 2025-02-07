package org.firstinspires.ftc.teamcode.teleOp;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public abstract class automated extends manual {

    static final double     COUNTS_PER_MOTOR_REV        = 288; // correct
    static final double     COUNTS_PER_RAD              = (COUNTS_PER_MOTOR_REV) / ( 2 * (Math.PI));
    static final double     WHEEL_RADIUS_INCHES         = 1;
    static final double     WHEEL_DIAMETER_INCHES       = 2 * WHEEL_RADIUS_INCHES ;     // For figuring circumference
    static final double     COUNTS_PER_INCH             = (COUNTS_PER_MOTOR_REV) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    static final double     tierOneHeight               = ;   // short bucket height in inches
    static final double     tierTwoHeight               = ;   // medium bucket height in inches
    static final double     tierThreeHeight             = ;   // tall bucket height in inches

    static final double     red_lim                     = ;  // color limits
    static final double     blue_lim                    = ;  // color limits
    static final double     green_lim                   = ;  // color limits
    static final double     ground_dist                 = ;    // ground distance

    double                  minPosition                 = 0.0;  // Minimum position
    double                  maxPosition                 = 1.0;  // Maximum position
    double                  step                        = 0.05;        // Step size for movement
    long                    delay                       = 100;          // Delay in milliseconds between movements

    double pos = minPosition;
    boolean servo_move = true;


    public void dosidosido (Robot bot, Gamepad gamepad2) {
        if (gamepad2.right_bumper) {
            auto1(bot, gamepad2);
        }
        if (gamepad2.left_bumper) {
            auto2(bot, gamepad2);
        }
    }



    public void auto1 (Robot bot, Gamepad gamepad){
        DcMotor arm = bot.arm;

        Servo frontWrist = bot.frontWrist;
        Servo frontWristRoll = bot.frontWristRoll;

        Servo claw2 = bot.claw2;

        ColorSensor cs1 = bot.cs1;
        ColorSensor cs2 = bot.cs2;

        servo_scan(red_lim, blue_lim, green_lim, ground_dist, true, frontWristRoll, cs1, cs2, gamepad);

        claw2.setPosition(0);
        frontWrist.setPosition(0);
        motor_move_to_angle(arm, 120, .5, false);
        frontWrist.setPosition();
        frontWristRoll.setPosition();
    }

    public void auto2 (Robot bot, Gamepad gamepad) {
        int level = 1;

        DcMotor ls = bot.linearSlide;
        // servos
        Servo claw = bot.claw;
        Servo wrist = bot.wrist;
        Servo wrist2 = bot.wrist2;
        DcMotor elbow = bot.elbow;

        claw.setPosition(1); // open claw

        // move to angle
        wrist.setPosition();
        wrist2.setPosition();
        motor_move_to_angle(elbow, 330, .5, false);

        claw.setPosition(0); // close claw

        if (gamepad.a){
            claw.setPosition(1); // open claw (emergency)
        }

        // reset arm position
        wrist.setPosition();
        wrist2.setPosition();
        motor_move_to_angle(elbow, 90, .5, false);

        while (!gamepad.a){

            gamepad.rumbleBlips(level);

            if (gamepad.dpad_up) {
                level++;
            } else if (gamepad.dpad_down) {
                level--;
            }

            if (level < 1) {
                level = 1;
            } else if (level > 3) {
                level = 3;
            }

            if (gamepad.b) {
                break;
            }

            sleep(250);
        }

        if (level == 2) {
            ls_move_dist(ls, tierTwoHeight, .5);
        } else if (level == 3) {
            ls_move_dist(ls, tierThreeHeight, .5);
        } else {
            ls_move_dist(ls, tierOneHeight, .5);
        }


    }

    public void servo (Servo servo) {

        servo.setPosition(pos); // move to pos
        sleep(delay);


        // alternate between scanning left or right

        if (servo_move) {
            pos += step;
        } else {
            pos -= step;
        }


        // if the servo reaches a max or a min, turn around

        if (pos >= maxPosition || pos <= minPosition) {
            servo_move = !servo_move;
        }
    }


public void servo_scan (double rlim, double blim, double glim, double dist, boolean distmode, Servo servo, ColorSensor cs1, ColorSensor cs2, Gamepad gamepad) {

    if (distmode) {
        double dist1 = -1;
        double dist2 = -1;

        if (cs1 instanceof DistanceSensor) {
            dist1 = ((DistanceSensor) cs1).getDistance(DistanceUnit.CM);
        }
        if (cs2 instanceof DistanceSensor) {
            dist2 = ((DistanceSensor) cs2).getDistance(DistanceUnit.CM);
        }


        while (dist1 <= dist && dist2 <= dist){
            servo(servo);
            if (dist1 == -1 || dist2 == -1){
                break; // in the case that the distance sensors do not initialize or work, the loop breaketh
            }
            if (gamepad.right_bumper){
                for (int i = 0; i < 3; i++) {gamepad.rumble(1000);}
                break; // in the case that the distance sensors do not initialize or work, the loop breaketh
            }
        }

    } else {
        double tolerance = 10;

        double rLower = rlim - tolerance;
        double rUpper = rlim + tolerance;
        double bLower = blim - tolerance;
        double bUpper = blim + tolerance;
        double gLower = glim - tolerance;
        double gUpper = glim + tolerance;

        // Modify the while loop condition
        while (!(cs1.red() >= rLower && cs1.red() <= rUpper) &&
                !(cs1.blue() >= bLower && cs1.blue() <= bUpper) &&
                !(cs1.green() >= gLower && cs1.green() <= gUpper)) {

            servo(servo);

            if (gamepad.right_bumper) {
                for (int i = 0; i < 3; i++) {gamepad.rumble(1000);}
                break;
            }
        }

    }


}

public static void ls_move_dist (DcMotor ls, double dist, double speed) {
    ls.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    ls.setTargetPosition((int) Math.round(dist));
    ls.setPower(speed);
    while (ls.isBusy()) { // Wait for the motor to reach the target
        // You can add telemetry here to monitor progress if needed
        // For example:
        // telemetry.addData("Motor Position", ls.getCurrentPosition());
        // telemetry.update();
    }
    ls.setPower(0); // Stop the motor
}
public static void motor_move_to_angle (DcMotor motor, double angle, double speed, boolean reset) {
    angle = Math.toRadians(angle);
    motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    motor.setTargetPosition((int) (angle * COUNTS_PER_RAD));
    motor.setPower(speed);
    while (motor.isBusy()) {
        // You can add telemetry here to monitor progress if needed
        // For example:
        // telemetry.addData("Motor Position", motor.getCurrentPosition());
        // telemetry.update();
    }
    motor.setPower(0);


    if (reset) {
        angle = -angle;
        motor.setTargetPosition((int) (angle * COUNTS_PER_RAD));
        motor.setPower(-speed);
        while (motor.isBusy()) {
            // You can add telemetry here to monitor progress if needed
            // For example:
            // telemetry.addData("Motor Position", motor.getCurrentPosition());
            // telemetry.update();
        }

        motor.setPower(0);
    }
}
}
