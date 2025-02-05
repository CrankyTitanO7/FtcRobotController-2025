package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOp2", group="jaden the great")
public class teleop2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

//        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();

        Robot bot = new Robot(hardwareMap);

        double[] drivePower = {0, 0, 0, 0};
        double [] dosaction = {0, 0, 0, 0};
        boolean clawOpen = false;
        boolean clawOpen2 = false;
        double armspeed;

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            //put code here
            if (gamepad2.right_bumper){
                armspeed = 1;
            } else {
                armspeed = .5;
            }

            // player 1 does the driving, so here is the driver output
            drivePower = twocntrl.dualcntrl(gamepad1, .25);
            bot.leftFrontMotor.setPower(drivePower[0]);
            bot.rightFrontMotor.setPower(drivePower[1]);
            bot.leftRearMotor.setPower(drivePower[2]);
            bot.rightRearMotor.setPower(drivePower[3]);

            // player two operates claw, secondary claw, arm, and various wrist joints.
            dosaction = twocntrl.dosido(gamepad2.left_trigger, gamepad2.right_trigger, clawOpen, clawOpen2, gamepad2.left_stick_x, gamepad2.right_stick_y, 0.5, 0.1, armspeed, gamepad2.right_stick_x, gamepad2.left_stick_y);

            bot.linearSlide.setPower(dosaction[0]);
            bot.claw.setPosition(dosaction[1]);
            bot.claw2.setPosition(dosaction[2]);
            bot.arm.setPower(dosaction[3]);
//            bot.wrist.setPosition(bot.wrist.getPosition() + dosaction[4]);
//            bot.wrist2.setPosition(bot.wrist2.getPosition() + dosaction[5]);
            if (gamepad2.right_stick_x < .1 || gamepad2.right_stick_x > .1){
                bot.wrist.setPower(dosaction[4]);
//                bot.wrist2.setPosition(dosaction[5]);
            } else {
                bot.wrist.setPower(0);
//                bot.wrist2.setPosition(0);
            }

            if (gamepad2.right_stick_y < .1 || gamepad2.right_stick_y > .1){
                bot.wrist2.setPower(dosaction[5]);
//                bot.wrist2.setPosition(dosaction[5]);
            } else {
                bot.wrist2.setPower(0);
//                bot.wrist2.setPosition(0);
            }

            bot.frontWrist.setPower(dosaction[6]);

            if (gamepad2.y) {
                clawOpen = !clawOpen;
            }
            if (gamepad2.b) {
                clawOpen2 = !clawOpen2;
            }
            sleep(250);
        }

        telemetry.update();
    }
}
