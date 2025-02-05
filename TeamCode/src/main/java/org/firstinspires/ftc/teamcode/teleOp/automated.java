package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

public class automated{

    static final double     COUNTS_PER_MOTOR_REV    = 288; // correct
    static final double     COUNTS_PER_RAD         = (COUNTS_PER_MOTOR_REV) / ( 2 * (Math.PI));


    public static void dosidosido (Robot bot, Gamepad gamepad2) {
        if (gamepad2.right_bumper) {
            auto1(bot);
            auto2(bot);
        }
    }



    public static void auto1 (Robot bot){
        DcMotor arm = bot.arm;
        CRServo frontWrist = bot.frontWrist;
        Servo claw2 = bot.claw2;


    }

    public static void auto2 (Robot bot) {
        DcMotor ls = bot.linearSlide;
        // servos
        Servo claw = bot.claw;
        CRServo wrist = bot.wrist;
        CRServo wrist2 = bot.wrist2;
    }

    public static void servo_scan () {

    }

    public static void move_to_angle (DcMotor motor, double angle, double speed, boolean reset) {
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
