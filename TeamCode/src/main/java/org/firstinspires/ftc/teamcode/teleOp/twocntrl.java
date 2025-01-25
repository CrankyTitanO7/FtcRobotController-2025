package org.firstinspires.ftc.teamcode.teleOp;

public class twocntrl {
    public static double[] dualcntrl(double leftY, double leftX, double rightY, double rightX, double scalara, double sens) {

//        double scalar = 1 - 0.5 * scalara;
//
//        double leftScalar = scalar / Math.max(Math.abs(leftY) + Math.abs(leftX), 1);
//        double rightScalar = scalar / Math.max(Math.abs(rightY) + Math.abs(rightX), 1);

        double max;

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double neglefty   = leftY; // Note: pushing stick forward gives negative value

        leftX = -leftX;

        // inverse scalera
        scalara = 1 - (.1) * scalara;

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double leftFrontPower  = (neglefty + leftX + rightX) * scalara * sens;
        double rightFrontPower = (neglefty - leftX - rightX) * scalara * sens;
        double leftBackPower   = (neglefty - leftX + rightX) * scalara * sens;
        double rightBackPower  = (neglefty + leftX - rightX) * scalara * sens;

        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower  /= max;
            rightFrontPower /= max;
            leftBackPower   /= max;
            rightBackPower  /= max;
        }


        double[] returnable = {leftFrontPower, rightFrontPower, leftBackPower, rightBackPower};
        return returnable;

//        telemetry.addData("left y value", leftY);
    }

    public static double[] dosido (double ltrig, double rtrig, boolean y, boolean b, double leftx, double righty, double val, double val3, double armspeed, double rightx, double lefty) {
        double lpow = ltrig * val;
        double rpow = rtrig * val;

        double lspow = rpow - lpow;

        double clwpos = .25;

        if (y) {
            clwpos = .1;
        }

        double clw2pos = .25;

        if (b) {
            clw2pos = .5;
        }

        double armspd = leftx * armspeed;

//        if (leftx > .1) {
//            armspd = armspeed;
//        } else if (leftx < -.1) {
//            armspd = -armspeed;
//        } else {
//            armspd = 0;
//        }



        double deltwrist = -righty * .3;
        double deltwrist2 = rightx * .3;
        double frontdeltwrist = lefty * .3;

        double[] returnable = {lspow, clwpos, clw2pos, armspd, deltwrist, deltwrist2, frontdeltwrist};

        return returnable;
    }


}
