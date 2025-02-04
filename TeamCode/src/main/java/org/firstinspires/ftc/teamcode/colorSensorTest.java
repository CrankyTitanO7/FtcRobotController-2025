/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/*
 *
 * This OpMode that shows how to use
 * a Modern Robotics Color Sensor.
 *
 * The OpMode assumes that the color sensor
 * is configured with a name of "sensor_color".
 *
 * You can use the X button on gamepad1 to toggle the LED on and off.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */
@TeleOp(name = "am sensor test", group = "jaden the great")
//@Disabled
public class colorSensorTest extends LinearOpMode {

  ColorSensor cs1;    // Hardware Device Object
  ColorSensor cs2;
//  ColorSensor cs3;
  Servo servo;
  boolean dir = true;


  @Override
  public void runOpMode() {

    // hsvValues is an array that will hold the hue, saturation, and value information.
    float hsvValues[] = {0F,0F,0F};

    // values is a reference to the hsvValues array.
    final float values[] = hsvValues;

    // get a reference to the RelativeLayout so we can change the background
    // color of the Robot Controller app to match the hue detected by the RGB sensor.
    int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
    final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

    // bPrevState and bCurrState represent the previous and current state of the button.
    boolean bPrevState = false;
    boolean bCurrState = false;

    // bLedOn represents the state of the LED.
    boolean bLedOn = true;

    // get a reference to our ColorSensor object.
    cs1 = hardwareMap.get(ColorSensor.class, "cs1");
    cs2 = hardwareMap.get(ColorSensor.class, "cs2");
//    cs3 = hardwareMap.get(ColorSensor.class, "cs3");

    servo = hardwareMap.get(Servo.class, "servo");

    // Set the LED in the beginning
//    cs1.enableLed(bLedOn);

    // wait for the start button to be pressed.
    waitForStart();

    if (isStopRequested()) return;

    // while the OpMode is active, loop and read the RGB data.
    // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
    while (opModeIsActive()) {

      // check the status of the x button on either gamepad.
      bCurrState = gamepad1.x;
      servo.setPosition(-1);

      // check for button state transitions.
      if (bCurrState && (bCurrState != bPrevState))  {

        // button is transitioning to a pressed state. So Toggle LED
        bLedOn = !bLedOn;
        cs1.enableLed(bLedOn);
      }

      // update previous state variable.
      bPrevState = bCurrState;

      // convert the RGB values to HSV values.
      Color.RGBToHSV(cs1.red() * 8, cs1.green() * 8, cs1.blue() * 8, hsvValues);

      // send the info back to driver station using telemetry function.
      telemetry.addData("LED", bLedOn ? "On" : "Off");
      telemetry.addData("Clear", cs1.alpha());
      telemetry.addData("Red  ", cs1.red());
      telemetry.addData("Green", cs1.green());
      telemetry.addData("Blue ", cs1.blue());
      telemetry.addData("Hue", hsvValues[0]);

      if (cs1 instanceof DistanceSensor) {
        telemetry.addData("Distance (cm)", "%.3f", ((DistanceSensor) cs1).getDistance(DistanceUnit.CM));
      }

      dir = checkDir((float)servo.getPosition(), dir);

      double colorpos = check(servo, cs1, cs2, (float)100000000, (float)servo.getPosition(), dir);
      servo.setPosition(colorpos);

      // change the background color to match the color detected by the RGB sensor.
      // pass a reference to the hue, saturation, and value array as an argument
      // to the HSVToColor method.
      relativeLayout.post(new Runnable() {
        public void run() {
          relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
        }
      });

      telemetry.update();
    }

    // Set the panel back to the default color
    relativeLayout.post(new Runnable() {
      public void run() {
        relativeLayout.setBackgroundColor(Color.WHITE);
      }
    });
    telemetry.update();


  }

  public double check(Servo servo, ColorSensor cs1, ColorSensor cs2, float min, float pos, boolean dir) {
    boolean scanning = true;

    if (scanning) {
      if (dir) {
        pos += .01;
      } else {
        pos -= .01;
      }

      servo.setPosition(pos);
      telemetry.addData("pos", servo.getPosition());
      telemetry.update();
    }
    return pos;
  }

  public boolean checkDir(float pos, boolean dir){
    if (pos < .1) {
      dir = false;
    } else if (pos > .9) {
      dir = true;
    }
    return dir;
  }

}
