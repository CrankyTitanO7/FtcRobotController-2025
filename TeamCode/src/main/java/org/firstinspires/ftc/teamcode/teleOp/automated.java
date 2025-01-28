package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class automated extends LinearOpMode {

    static final double     PPR                     = 384.5; // replace with core hex motor
    static final double     COUNTS_PER_MOTOR_REV    = PPR * 4;
    static final double     COUNTS_PER_RAD         = (COUNTS_PER_MOTOR_REV) / (Math.PI);


    public static void dosidosido (Robot bot, Gamepad gamepad2) {
        DcMotor ls = bot.linearSlide;
        DcMotor arm = bot.arm;

        // servos
        Servo claw = bot.claw;
        Servo claw2 = bot.claw2;
        CRServo wrist = bot.wrist;
        CRServo wrist2 = bot.wrist2;
        CRServo frontWrist = bot.frontWrist;

        if (gamepad2.right_bumper) {

        }
    }

    public void move_to_angle (DcMotor motor, double angle, double speed, long timeout, boolean reset) {
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
