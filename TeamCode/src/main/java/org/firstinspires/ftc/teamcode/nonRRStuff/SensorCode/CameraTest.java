package org.firstinspires.ftc.teamcode.nonRRStuff.SensorCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.vision.VisionPortal;


public class CameraTest extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    private VisionPortal visionPortal;
    @Override
    public void runOpMode() {
        telemetry.addData("Controls", "dpad_down to stop stream, dpad_up to resume");
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {


                // Push telemetry to the Driver Station.
                telemetry.update();

                // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }

                // Share the CPU.
                sleep(20);
            }
        }





    }
}
