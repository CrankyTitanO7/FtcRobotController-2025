package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

//@TeleOp(name="manual control", group="jaden the great")

public abstract class manual extends LinearOpMode {
    public void manual (Robot bot, Gamepad gamepad2){
        double[] drivePower = {0, 0, 0, 0};
        double[] dosaction = {0, 0, 0, 0};
        boolean clawOpen = false;
        boolean clawOpen2 = false;
        double armspeed;

        if(gamepad2.right_bumper)
        {
            armspeed = 1;
        } else

        {
            armspeed = .5;
        }

        // player two operates claw, secondary claw, arm, and various wrist joints.
        dosaction =twocntrl.dosido(gamepad2.left_trigger,gamepad2.right_trigger,clawOpen,clawOpen2,gamepad2.left_stick_x,gamepad2.right_stick_y,0.5,0.1,armspeed,gamepad2.right_stick_x,gamepad2.left_stick_y);

        bot.linearSlide.setPower(dosaction[0]);
        bot.claw.setPosition(dosaction[1]);
        bot.claw2.setPosition(dosaction[2]);
        bot.arm.setPower(dosaction[3]);
//            bot.wrist.setPosition(bot.wrist.getPosition() + dosaction[4]);
//            bot.wrist2.setPosition(bot.wrist2.getPosition() + dosaction[5]);
        if(gamepad2.right_stick_x< .1||gamepad2.right_stick_x >.1)

        {
            bot.wrist.setPosition(dosaction[4]);
//                bot.wrist2.setPosition(dosaction[5]);
        } else

        {
            bot.wrist.setPosition(0);
//                bot.wrist2.setPosition(0);
        }

        if(gamepad2.right_stick_y< .1||gamepad2.right_stick_y >.1)

        {
            bot.wrist2.setPosition(dosaction[5]);
//                bot.wrist2.setPosition(dosaction[5]);
        } else

        {
            bot.wrist2.setPosition(0);
//                bot.wrist2.setPosition(0);
        }

        bot.frontWrist.setPosition(dosaction[6]);

        if(gamepad2.y)

        {
            clawOpen = !clawOpen;
        }
        if(gamepad2.b)

        {
            clawOpen2 = !clawOpen2;
        }

        if (gamepad2.right_bumper){

            bot.elbow.setPower(.7);
        } else if (gamepad2.left_bumper){
            bot.elbow.setPower(-.7);
        } else {
            bot.elbow.setPower(0);
        }


        sleep(250);
    }
}
