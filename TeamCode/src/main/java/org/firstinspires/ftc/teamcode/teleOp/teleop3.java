package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOp3 - auto + manual", group="jaden the great")

public class teleop3 extends handoff {
    @Override
    public void runOpMode() throws InterruptedException {

//        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();

        Robot bot = new Robot(hardwareMap);

        double[] drivePower = {0, 0, 0, 0};

        // manual variables
        double[] dosaction = {0, 0, 0, 0};
        boolean[] clawOpen = {false, false};
        double armspeed = 1;

        waitForStart();

        if (isStopRequested()) return;

        // reset all motor positions
        motor_move_to_angle(bot.arm, 0, .5,  false);
        motor_move_to_angle(bot.elbow, -180, .5,  false);
        motor_move_to_angle(bot.linearSlide, 0, .5,  false);

        // open claws
        bot.claw.setPosition(.25);
        bot.claw2.setPosition(.25);

        while (opModeIsActive()) {
            //put code here
            if (gamepad1.right_trigger > .5) {
                handoffSequence(bot, gamepad2);

            } else {
                // player two operates claw, secondary claw, arm, and various wrist joints.
                manual(bot, gamepad2,
                        .3, .3,
                        .3, .3,
                        .5, .5, .5);

            }

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
