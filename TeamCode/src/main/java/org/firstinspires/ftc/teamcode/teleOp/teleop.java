package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOp", group="jaden the great")
public class teleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

//        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();

        Robot bot = new Robot(hardwareMap);

        double[] drivePower = {0, 0, 0, 0};
        double [] dosaction = {0, 0, 0, 0};

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            //put code here

            // player 1 does the driving, so here is the driver output
            drivePower = twocntrl.dualcntrl(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_y, gamepad1.right_stick_x, gamepad1.right_trigger);
            bot.leftFrontMotor.setPower(drivePower[0]);
            bot.rightFrontMotor.setPower(drivePower[1]);
            bot.leftRearMotor.setPower(drivePower[2]);
            bot.rightRearMotor.setPower(drivePower[3]);

            // player two operates claw, secondary claw, and arm.
            dosaction = twocntrl.dosido(gamepad2.left_trigger, gamepad2.right_trigger, gamepad2.y, gamepad2.b, gamepad2.left_stick_x, gamepad2.right_stick_y, 0.5, 0.5, .5);

            bot.linearSlide.setPower(dosaction[0]);
            bot.claw.setPosition(dosaction[1]);
            bot.claw2.setPosition(dosaction[2]);
            bot.arm.setPower(dosaction[3]);
            bot.wrist.setPosition(bot.wrist.getPosition() + dosaction[4]);
        }

        telemetry.update();
    }
}
