package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class handoff {

    static final double     COUNTS_PER_MOTOR_REV_COREHEX= 288;                                      // the counts per motor rev of a core hex motor
    static final double     PPR                         = 384.5;                                    // 5202 Series Yellow Jacket Planetary Gear Motor (13.7:1 Ratio, 435 RPM, 3.3 - 5V Encoder)


    static final double     COUNTS_PER_MOTOR_REV_GOBILDA= PPR * 4;                                  // the counts per motor rev of a goBILDA motor (for linear slide)
    static final double     COUNTS_PER_RAD              = (COUNTS_PER_MOTOR_REV_COREHEX) / ( 2 * (Math.PI));
    static final double     WHEEL_RADIUS_INCHES         = 1;
    static final double     WHEEL_DIAMETER_INCHES       = 2 * WHEEL_RADIUS_INCHES ;                 // For figuring circumference
    static final double     COUNTS_PER_INCH             = (COUNTS_PER_MOTOR_REV_GOBILDA) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    static final double     tierOneHeight               = 13.5;                                     // short bucket height in inches
    static final double     tierTwoHeight               = 31.5;                                     // medium bucket height in inches

    static final double     red_lim                     = 0;                                        // color limits
    static final double     blue_lim                    = 0;                                        // color limits
    static final double     green_lim                   = 0;                                        // color limits
    static final double     ground_dist                 = 5;                                        // ground distance IN CENTIMETERS from the front arm in scanning position

    static double           minPosition                 = 0.0;                                      // Minimum position
    static double           maxPosition                 = 1.0;                                      // Maximum position
    static double           step                        = 0.05;                                     // Step size for movement

    static double position;                                                                         // servo position. needed in bigger scope

    static double pos                                  = minPosition;                               // servo position (iterative variable. stored in bigger scope)
    static boolean servo_move                          = true;                                      // determines the direction of servo scan


    public static void handoffSequence(Robot bot, Gamepad gamepad2) {
        if (gamepad2.left_bumper) {
            auto1(bot, gamepad2); // automated handoff, part 1
        }
        if (gamepad2.right_bumper) {
            auto2(bot, gamepad2); // automated handoff, part 2
        }
    }



    public static void auto1 (Robot bot, Gamepad gamepad){
        DcMotor arm = bot.arm;

        Servo frontWrist = bot.frontWrist;
        Servo frontWristRoll = bot.frontWristRoll;

        Servo claw2 = bot.claw2;

        ColorSensor cs1 = bot.cs1;
        ColorSensor cs2 = bot.cs2;

        motor_move_to_angle(arm, 120, .5, false);                                // move the arm to the scanning angle

        servo_scan(red_lim, blue_lim, green_lim,                                                    // rgb limits
                ground_dist, true,                                                         // distance limits
                frontWristRoll, cs1, cs2,                                                           // hardware
                gamepad);                                                                           // game pad

        claw2.setPosition(0);                                                                       // close claw
        frontWrist.setPosition(0);                                                                  // set front wrist in handoff position
        motor_move_to_angle(arm, -120, .5, false);                              // set arm to handoff position
        frontWrist.setPosition(0.5);                                                                // set front wrist PITCH to handoff position
        frontWristRoll.setPosition(0.5);                                                            // set front wrist ROLL to handoff position
    }

    public static void auto2 (Robot bot, Gamepad gamepad) {
        int level = 1;                                                                              // instantly sets the target bucket to tier 1 (lower bucket(

        DcMotor ls = bot.linearSlide;
        // servos
        Servo claw = bot.claw;
        Servo claw2 = bot.claw2;
        Servo wrist = bot.wrist;
        Servo wrist2 = bot.wrist2;
        DcMotor elbow = bot.elbow;

        claw.setPosition(1);                                                                        // open claw

        // move to angle
        wrist.setPosition(0.5);                                                                     // set top wrist PITCH to handoff position
        wrist2.setPosition(0.5);                                                                    // set top wrist ROLL to handoff position
        motor_move_to_angle(elbow, 330, .5, false);                              // set elbow to handoff position

        claw.setPosition(0);                                                                        // close top claw
        claw2.setPosition(1);                                                                       // open front claw

        if (gamepad.a){
            claw.setPosition(1);                                                                    // open top claw (emergency)
        }

        // reset arm position
        wrist.setPosition(0.5);                                                                     // set top wrist PITCH to bucket drop position
        wrist2.setPosition(0.5);                                                                    // set top wrist ROLL to bucket drop position
        motor_move_to_angle(elbow, 90, .5, false);                               // set elbow to bucket drop position

        boolean dpadUpPressed = false;
        boolean dpadDownPressed = false;
        int lastLevel = 0;

        while (!gamepad.a) {
            // tier select:

            /*
            while the a button is not pressed:
                select a tier:
                - press dpad up to increase tier
                - press dpad down to decrease it
                press b or a to confirm

                the gamepad rumble should indicate which level is currently selected
            */

            // Dpad Up
            if (gamepad.dpad_up) {
                if (!dpadUpPressed) { // If it was not pressed before, now it is
                    level++;
                    dpadUpPressed = true;
                }
            } else {
                dpadUpPressed = false; // Reset when the button is released
            }

            // Dpad Down
            if (gamepad.dpad_down) {
                if (!dpadDownPressed) { // If it was not pressed before, now it is
                    level--;
                    dpadDownPressed = true;
                }
            } else {
                dpadDownPressed = false; // Reset when the button is released
            }

            if (level < 1) {
                level = 1;
            } else if (level > 2) {
                level = 2;
            }

            if (level != lastLevel){
                gamepad.rumbleBlips(level);
                lastLevel = level;
            }

            if (gamepad.b) {
                break;
            }
        }

        if (level == 2) {                                                                           // determined on what the "level" variable is, move to that tier
            ls_move_dist(ls, tierTwoHeight, .5);
        } else {
            ls_move_dist(ls, tierOneHeight, .5);
        }


    }

    public static void servo (Servo servo) {

        servo.setPosition(pos); // move to pos

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


public static void servo_scan (                                                                     // scans using color/distance sensor to orient the claw servo (claw roll) into position to grab
        double rlim, double blim, double glim,                                                      // limits for color sensor
        double dist, boolean distmode,                                                              // limits for distance sensor
        Servo servo, ColorSensor cs1, ColorSensor cs2,                                              // hardware
        Gamepad gamepad)                                                                            // gamepad
{

    if (distmode) {                                                                                 // if we are using distance sensor
        double dist1 = -1;
        double dist2 = -1;

        while (dist1 <= dist && dist2 <= dist){
            servo(servo);                                                                           // move one unit

            if (cs1 instanceof DistanceSensor) {
                dist1 = ((DistanceSensor) cs1).getDistance(DistanceUnit.CM);                        // update distance 1
            }
            if (cs2 instanceof DistanceSensor) {
                dist2 = ((DistanceSensor) cs2).getDistance(DistanceUnit.CM);                        // update distance 2
            }

            if (dist1 == -1 || dist2 == -1){
                break; // in the case that the distance sensors do not initialize or work, the loop breaketh
            }
            if (gamepad.right_bumper){
                for (int i = 0; i < 3; i++) {gamepad.rumble(1000);}
                break;                                                                              // emergency breakout using right bumper
            }
        }

    } else {                                                                                        // if we are using color sensor
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
            position = servo.getPosition();
            double newpos = position + .75;
            if (newpos > 1) {
                newpos = newpos - 1;
            }

            servo.setPosition(newpos);

            if (gamepad.right_bumper) {
                for (int i = 0; i < 3; i++) {gamepad.rumble(1000);}
                break;
            }
        }

    }


}

public static void ls_move_dist (DcMotor ls, double dist, double speed) {                           // function to move the linear slide to a certain distance
    ls.setTargetPosition((int) (dist * COUNTS_PER_INCH));
    ls.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    ls.setPower(speed);
    while (ls.isBusy()) { // Wait for the motor to reach the target
        // You can add telemetry here to monitor progress if needed
        // For example:
        // telemetry.addData("Motor Position", ls.getCurrentPosition());
        // telemetry.update();
    }
    ls.setPower(0); // Stop the motor
}
public static void motor_move_to_angle (DcMotor motor, double angle, double speed, boolean reset) { // function to move the arm to a certain angle. it is used many times in other files
    angle = Math.toRadians(angle);
    motor.setTargetPosition((int) (angle * COUNTS_PER_RAD));
    motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    motor.setPower(speed);
    while (motor.isBusy()) {
        // You can add telemetry here to monitor progress if needed
        // For example:
        // telemetry.addData("Motor Position", motor.getCurrentPosition());
        // telemetry.update();
    }
    motor.setPower(0);


    if (reset) {                                                                                    // optional resetting of the angle
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
