package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "Concept:SeroTest", group = "Concept")
public class ServoCode extends LinearOpMode {

    static final double FORWARD_SPEED = 1.0;
    static final double IDLE_SPEED = 0.0;

    private ElapsedTime runtime = new ElapsedTime();

    CRServo rightScoringServo, leftScoringServo;

    @Override
    public void runOpMode() throws InterruptedException {

        rightScoringServo = hardwareMap.crservo.get("servo1");

        //leftScoringServo = hardwareMap.crservo.get("leftScoringServo");

        rightScoringServo.setDirection(CRServo.Direction.FORWARD);

        //leftScoringServo.setDirection(CRServo.Direction.FORWARD);

        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            // separate delivery servos
            //if (gamepad1.x) {
              //  leftScoringServo.setPower(-FORWARD_SPEED);
            } if (gamepad1.b) {
                rightScoringServo.setPower(FORWARD_SPEED);
            } else {
                rightScoringServo.setPower(IDLE_SPEED);
                //leftScoringServo.setPower(IDLE_SPEED);
            }



        } }