//package org.firstinspires.ftc.teamcode.teleOp;
//
//import com.qualcomm.robotcore.hardware.CRServo;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.Servo;
//
//public class automated {
//    public static double[] autodub(double leftY, double leftX, double rightY, double rightX, double scalara) {
//
////        double scalar = 1 - 0.5 * scalara;
////
////        double leftScalar = scalar / Math.max(Math.abs(leftY) + Math.abs(leftX), 1);
////        double rightScalar = scalar / Math.max(Math.abs(rightY) + Math.abs(rightX), 1);
//
//        double max;
//
//        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
//        double neglefty   = leftY; // Note: pushing stick forward gives negative value
//
//        leftX = -leftX;
//
//        // inverse scalera
//        scalara = 1 - (.1) * scalara;
//
//        // Combine the joystick requests for each axis-motion to determine each wheel's power.
//        // Set up a variable for each drive wheel to save the power level for telemetry.
//        double leftFrontPower  = (neglefty + leftX + rightX) * scalara;
//        double rightFrontPower = (neglefty - leftX - rightX) * scalara;
//        double leftBackPower   = (neglefty - leftX + rightX) * scalara;
//        double rightBackPower  = (neglefty + leftX - rightX) * scalara;
//
//        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
//        max = Math.max(max, Math.abs(leftBackPower));
//        max = Math.max(max, Math.abs(rightBackPower));
//
//        if (max > 1.0) {
//            leftFrontPower  /= max;
//            rightFrontPower /= max;
//            leftBackPower   /= max;
//            rightBackPower  /= max;
//        }
//
//
//        double[] returnable = {leftFrontPower, rightFrontPower, leftBackPower, rightBackPower};
//        return returnable;
//
////        telemetry.addData("left y value", leftY);
//    }
//
//    public static double[] dosidosido (Robot bot) {
//        DcMotor ls = bot.linearSlide;
//        DcMotor arm = bot.arm;
//
//        // servos
//        Servo claw = bot.claw;
//        Servo claw2 = bot.claw2;
//        CRServo wrist = bot.wrist;
//        CRServo wrist2 = bot.wrist2;
//        CRServo frontWrist = bot.frontWrist;
//
//
//
//
//    }
//
//}
