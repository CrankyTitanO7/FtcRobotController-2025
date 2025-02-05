package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOp3-auto", group="jaden the great")
public class teleop3 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

//        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();

        Robot bot = new Robot(hardwareMap);

        double[] drivePower = {0, 0, 0, 0};

        waitForStart();

        if (isStopRequested()) return;


        automated.move_to_angle(bot.arm, 180, .5,  false);
        automated.move_to_angle(bot.linearSlide, 0, .5,  false);


        while (opModeIsActive()) {
            //put code here
            automated.dosidosido(bot, gamepad2);
            // player 1 does the driving, so here is the driver output
            drivePower = twocntrl.dualcntrl(gamepad1, .25);
            bot.leftFrontMotor.setPower(drivePower[0]);
            bot.rightFrontMotor.setPower(drivePower[1]);
            bot.leftRearMotor.setPower(drivePower[2]);
            bot.rightRearMotor.setPower(drivePower[3]);
        }

        telemetry.update();
    }
}
