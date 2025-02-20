

package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "am motor test", group = "jaden the great")
//@Disabled
public class motors_test extends LinearOpMode {
  @Override
  public void runOpMode() throws InterruptedException {
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

    int mot = 0;
    int serv = 0;
    double motorang = 0;
    double servoang = 0;

    waitForStart();
    // stop if it has been stopped
    if (isStopRequested()) return;
    //main loop

    final double DEADZONE = 0.1;
    while (opModeIsActive()) {


      //prints data


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

      // decide which motor


      if (gamepad1.dpad_right) {
        mot++;
        while (gamepad1.dpad_right){}
      } else if (gamepad1.dpad_left){
        mot--;
        while (gamepad1.dpad_left){}
      }

      if (gamepad1.dpad_up) {
        serv++;
        while (gamepad1.dpad_up){}
      } else if (gamepad1.dpad_down){
        serv--;
        while (gamepad1.dpad_down){}
      }

      sleep(250);

      double leftStickX = gamepad1.left_stick_x;
      double leftStickY = gamepad1.left_stick_y;
      leftStickX = (Math.abs(leftStickX) > DEADZONE) ? leftStickX : 0; // ternary operators
      leftStickY = (Math.abs(leftStickY) > DEADZONE) ? leftStickY : 0; // yet another ternary operator

      servoang = trig(leftStickX, leftStickY) / (2 * Math.PI);

      if (servoang > 1) {
        telemetry.addData("servoang exceeds limit", servoang);
        servoang = 1;
      }
      if (servoang < 0) {
        telemetry.addData("servoang unexceeds limit", servoang);
        servoang = 0;
      }

      try {
        servos[serv].setPosition(servoang);
      } catch (Exception e) {
        telemetry.addData("error (servo)", e);
        telemetry.addData("servoang value: ", servoang);
      }

      telemetry.update();

        double rightStickX = gamepad1.right_stick_x;
        double rightStickY = gamepad1.right_stick_y;
        rightStickX = (Math.abs(rightStickX) > DEADZONE) ? rightStickX : 0;
        rightStickY = (Math.abs(rightStickY) > DEADZONE) ? rightStickY : 0;

      motorang = trig(rightStickX, rightStickY);

      handoff.motor_move_to_angle(motors[mot], motorang, 10, false);
//      sleep(250);
    }
  }

  public double trig (double x, double y) {
    try{

      double theta = Math.atan(x/y);
      if (x == 0 && y == 0) {
        return 0;
      }

      if (x < 0){
        if (y > 0){
          theta = Math.PI - Math.atan(-x/y);
        } else {
          theta = Math.PI + Math.atan(x/y);
        }
      }

      return Math.toDegrees(theta);

    } catch (Exception e) {
      telemetry.addData("error", e);
      telemetry.update();
      return 0;

    }
  }

}
