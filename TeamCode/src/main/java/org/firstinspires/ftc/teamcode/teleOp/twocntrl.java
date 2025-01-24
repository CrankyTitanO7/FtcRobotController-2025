package org.firstinspires.ftc.teamcode.teleOp;

public class twocntrl {
    public static double[] dualcntrl(double leftY, double leftX, double rightY, double rightX, double scalara) {

//        double scalar = 1 - 0.5 * scalara;
//
//        double leftScalar = scalar / Math.max(Math.abs(leftY) + Math.abs(leftX), 1);
//        double rightScalar = scalar / Math.max(Math.abs(rightY) + Math.abs(rightX), 1);

        double max;

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double neglefty   = -leftY; // Note: pushing stick forward gives negative value

        leftX = -leftX;

        // inverse scalera
        scalara = 1 - scalara;

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double leftFrontPower  = (neglefty + leftX + rightX) * scalara;
        double rightFrontPower = (neglefty - leftX - rightX) * scalara;
        double leftBackPower   = (neglefty - leftX + rightX) * scalara;
        double rightBackPower  = (neglefty + leftX - rightX) * scalara;

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

    public static double[] dosido (double ltrig, double rtrig, boolean y, boolean b, double leftx, double righty, double val, double val3, double armspeed) {
        double lpow = ltrig * val;
        double rpow = rtrig * val;

        double lspow = rpow - lpow;

        double clwpos = 0;

        if (y) {
            clwpos = 1;
        }

        double clw2pos = 0;

        if (b) {
            clw2pos = 1;
        }

        double armspd = leftx * armspeed;

        double deltwrist = righty * val3;

        double[] returnable = {lspow, clwpos, clw2pos, armspd, deltwrist};

        return returnable;
    }
}
