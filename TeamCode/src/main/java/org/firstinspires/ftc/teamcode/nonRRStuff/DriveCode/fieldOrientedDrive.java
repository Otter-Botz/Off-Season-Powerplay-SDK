package org.firstinspires.ftc.teamcode.nonRRStuff.DriveCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "driveBaseCodeWorking")
public class fieldOrientedDrive extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("leftBack");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("rightFront");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("rightBack");
        // skibidi toilet will be mine yeah
        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //Imu is the combination of multiple sensors to get info like pitch, yaw roll and the speed the robot is turning
        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            double power = gamepad1.right_trigger;

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepad1.a) {
                imu.resetYaw();
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            //get plane coordinates of robot from yaw
            double a = Math.cos(-botHeading);
            double b = Math.sin(-botHeading);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator * power;
            double backLeftPower = (rotY - rotX + rx) / denominator * power;
            double frontRightPower = (rotY - rotX - rx) / denominator * power;
            double backRightPower = (rotY + rotX - rx) / denominator * power;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
            telemetry.addData("rotx", rotX);
            telemetry.addData("roty", rotY);
            telemetry.addData("frontleftpower", frontLeftPower);
            telemetry.addData("frontrightpower", frontRightPower);
            telemetry.addData("backleftpower", backLeftPower);
            telemetry.addData("backrightpower", backRightPower);
            telemetry.addData("Denominator", denominator);
            telemetry.addData("Parameterforimu", parameters);
            telemetry.addData("leftStickY", gamepad1.left_stick_y);
            telemetry.addData("leftStickX", gamepad1.left_stick_x);
            telemetry.addData("rightStickY", gamepad1.right_stick_y);
            telemetry.addData("rightstickx", gamepad1.right_stick_x);
            telemetry.addData("imuyawpitchroll", imu.getRobotYawPitchRollAngles());
            telemetry.addData("cos of radian", Math.cos(-botHeading));
            telemetry.addData("sin of radian", Math.sin(-botHeading));
            telemetry.addData("radiens yaw", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
            telemetry.addData("a", a);
            telemetry.addData("b", b);
            telemetry.update();
        }
    }
}