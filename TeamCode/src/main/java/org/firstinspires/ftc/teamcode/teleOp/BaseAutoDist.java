package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class BaseAutoDist extends LinearOpMode {

    /* Declare OpMode members. */

    // Calculate the COUNTS_PER_INCH for your specific drive train.
    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
    // This is gearing DOWN for less speed and more torque.
    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
    static final double     PPR                     = 384.5;
    static final double     COUNTS_PER_MOTOR_REV    = PPR * 4;    // eg: 5202 Series Yellow Jacket Planetary Gear Motor (13.7:1 Ratio, 435 RPM, 3.3 - 5V Encoder)
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
    static final double     WHEEL_RADIUS_INCHES     = 1.75;
    static final double     WHEEL_DIAMETER_INCHES   = 2 * WHEEL_RADIUS_INCHES ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;


    private ElapsedTime runtime = new ElapsedTime();
    protected Robot bot;

    protected void setRobot(Robot robot) {
        this.bot = robot;
    }

    protected void driveForward(double dist, double timeout) {
        encoderDrive(DRIVE_SPEED, dist, dist, timeout);
    }

    protected void spinLeft(double dist, double timeout) {
        encoderDrive(TURN_SPEED, -dist, dist, timeout);
    }

    protected void spinRight(double dist, double timeout) {
        encoderDrive(TURN_SPEED, dist, -dist, timeout);
    }

    protected void strafeLeft (double dist, double timeout) {
        encoderDrive(DRIVE_SPEED, -dist, dist, timeout);
    }

    protected void strafeRight (double dist, double timeout) {
        encoderDrive(DRIVE_SPEED, dist, -dist, timeout);
    }

//    protected void stopRobot() {
//        encoderDrive(0, 0, 0, timeout);
//    }

    protected void funnyStrafeLeft(double dist, double timeout) {
        encoderDrive(DRIVE_SPEED, -dist, dist, timeout);
    }

    protected void funnyStrafeRight(double dist, double timeout) {
        encoderDrive(DRIVE_SPEED, dist, -dist, timeout);
    }


    public void funnyStrafe(double speed,
                            double leftInches, double rightInches,
                            double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {
            // Determine new target position, and pass to motor controller
            newLeftTarget = bot.leftFrontMotor.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newRightTarget = bot.rightFrontMotor.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            bot.leftFrontMotor.setTargetPosition(newLeftTarget);
            bot.rightFrontMotor.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            bot.rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bot.rightRearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            bot.leftRearMotor.setPower(Math.abs(speed));
            bot.rightRearMotor.setPower(Math.abs(speed));
            bot.leftFrontMotor.setPower(Math.abs(speed));
            bot.rightFrontMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (bot.leftFrontMotor.isBusy() && bot.rightFrontMotor.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Running to", " %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Currently at", " at %7d :%7d",
                        bot.leftFrontMotor.getCurrentPosition(), bot.rightFrontMotor.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            bot.leftRearMotor.setPower(0);
            bot.leftFrontMotor.setPower(0);
            bot.rightRearMotor.setPower(0);
            bot.rightFrontMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            bot.leftRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bot.leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bot.rightRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bot.rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move.
        }
    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {
            // Determine new target position, and pass to motor controller
            newLeftTarget = bot.leftRearMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = bot.rightRearMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            bot.leftRearMotor.setTargetPosition(newLeftTarget);
            bot.rightRearMotor.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            bot.leftRearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bot.leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bot.rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bot.rightRearMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            bot.leftRearMotor.setPower(Math.abs(speed));
            bot.leftFrontMotor.setPower(Math.abs(speed));
            bot.rightRearMotor.setPower(Math.abs(speed));
            bot.rightFrontMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (bot.leftRearMotor.isBusy() && bot.rightRearMotor.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Running to",  " %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Currently at",  " at %7d :%7d",
                        bot.leftRearMotor.getCurrentPosition(), bot.rightRearMotor.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            bot.leftRearMotor.setPower(0);
            bot.leftFrontMotor.setPower(0);
            bot.rightRearMotor.setPower(0);
            bot.rightFrontMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            bot.leftRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bot.leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bot.rightRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bot.rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move.
        }
    }
}