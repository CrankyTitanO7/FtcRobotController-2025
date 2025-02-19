

package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "am motor test", group = "jaden the great")
//@Disabled
public class motors_test extends LinearOpMode {
  Robot bot = new Robot(hardwareMap);

  DcMotor arm = bot.arm;
  DcMotor elbow = bot.elbow;
  DcMotor linearSlide = bot.linearSlide;
  Servo claw = bot.claw;
  Servo claw2 = bot.claw2;
  Servo wrist = bot.wrist;
  Servo wrist2 = bot.wrist2;

  DcMotor[] motors = {arm, elbow, linearSlide};
  Servo[] servos = {claw, claw2, wrist, wrist2};

  public int mot = 0;
  public int serv = 0;
  public double motorang = 0;
  public double servoang = 0;

  @Override
  public void runOpMode() throws InterruptedException {
    waitForStart();

    if (isStopRequested()) return;

    while (opModeIsActive()) {
      try {
        telemetry.addData("mot", mot);
      } catch (Exception e) {
        telemetry.addData("error (mot)", e);
      }

      try {
        telemetry.addData("serv", serv);
      } catch (Exception e) {
        telemetry.addData("error (serv)", e);
      }

      try {
        telemetry.addData("motorang", motorang);
      } catch (Exception e) {
        telemetry.addData("error (motorang)", e);
      }

      try {
        telemetry.addData("servoang", servoang);
      } catch (Exception e) {
        telemetry.addData("error (servoang)", e);
      }

      telemetry.update();

      if (gamepad1.dpad_up) {
        mot++;
      }
      if (gamepad1.dpad_down) {
        mot--;
      }
      if (gamepad1.dpad_right) {
        serv++;
      }
      if (gamepad1.dpad_left) {
        serv--;
      }

      servoang = trig(gamepad1.left_stick_x, gamepad1.left_stick_y);
      servos[serv].setPosition(servoang);
      motorang = trig(gamepad1.right_stick_x, gamepad1.right_stick_y);

      handoff.motor_move_to_angle(motors[mot], 0, .5, false);
    }
  }

  public double trig (double x, double y) {
    try{

      return Math.atan(x/y);

    } catch (Exception e) {

      telemetry.addData("error", e);
      telemetry.update();
      return 0;

    }
  }

}
