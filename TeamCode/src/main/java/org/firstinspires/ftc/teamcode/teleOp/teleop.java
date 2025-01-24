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

        // Button States
        boolean aButtonState = false;
        // Pretend the B button started pressed so we can reset the servo position every time
        boolean bButtonState = true;
        boolean xButtonState = false;
        boolean rightBumperButtonState = false;


//        // Retrieve the IMU from the hardware map
//        IMU imu = hardwareMap.get(IMU.class, "imu");
//        // Adjust the orientation parameters to match the robot
//        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
//                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
//                RevHubOrientationOnRobot.UsbFacingDirection.UP));
//        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            //put code here
        }
    }
}
