package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class MyFIRSTJavaOpMode extends LinearOpMode {
    RobotHardware robot = new RobotHardware();
    //DcMotor robot.leftFront;
    //DcMotor robot.rightFront;
    //DcMotor crane;
    //Servo claw;
    //BNO055IMU imu;
    //ElapsedTime runtime;

    robot.init(HardwareMap);

    @Override
    public void runOpMode() {
        //robot.leftFront = hardwareMap.get(DcMotor.class, "robot.leftFront");
        //robot.rightFront = hardwareMap.get(DcMotor.class, "robot.rightFront");
        //crane = hardwareMap.get(DcMotor.class, "crane");
        //claw = hardwareMap.get(Servo.class, "claw");
        //imu = hardwareMap.get(BNO055IMU.class, "imu");
        // Put initialization blocks here
        waitForStart();
        // Put run blocks here
        sleep(500);
        robot.leftFront.setPower(-1);
        robot.rightFront.setPower(-1);
        sleep(500);
        robot.leftFront.setPower(-0.75);
        robot.rightFront.setPower(-0.75);
        sleep(500);
        robot.leftFront.setPower(-0.55);
        robot.rightFront.setPower(0.55);
        sleep(500);
        robot.leftFront.setPower(0.55);
        robot.rightFront.setPower(-0.55);
        sleep(500);
        robot.leftFront.setPower(1);
        robot.rightFront.setPower(1);
        sleep(500);
        robot.leftFront.setPower(0.75);
        robot.rightFront.setPower(0.75);
        sleep(500);
        robot.leftFront.setPower(-0.5);
        robot.rightFront.setPower(0.5);
        sleep(500);
        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        sleep(500);
        robot.leftFront.setPower(1);
        robot.rightFront.setPower(1);
        sleep(500);
        robot.leftFront.setPower(0.75);
        robot.rightFront.setPower(0.75);
        sleep(500);
        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        sleep(500);
        robot.leftFront.setPower(-0.55);
        robot.rightFront.setPower(0.55);
        sleep(500);
        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        sleep(500);
        robot.leftFront.setPower(-1);
        robot.rightFront.setPower(-1);
        sleep(500);
        robot.leftFront.setPower(-0.75);
        robot.rightFront.setPower(-0.75);
        sleep(500);
        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        sleep(500);
        robot.leftFront.setPower(-1);
        robot.rightFront.setPower(1);
        sleep(500);
        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        sleep(500);
        robot.leftFront.setPower(1);
        robot.rightFront.setPower(1);
        sleep(500);
        robot.leftFront.setPower(0.75);
        robot.rightFront.setPower(0.75);
        sleep(500);
        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        sleep(500);
        robot.leftFront.setPower(-0.5);
        robot.rightFront.setPower(0.5);
        sleep(500);
        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        sleep(500);
        robot.leftFront.setPower(-1);
        robot.rightFront.setPower(-1);
        sleep(500);
        robot.leftFront.setPower(-0.75);
        robot.rightFront.setPower(-0.75);
        sleep(500);
        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        sleep(500);
        robot.leftFront.setPower(-0.85);
        robot.rightFront.setPower(0.85);
        sleep(500);
        robot.leftFront.setPower(1);
        robot.rightFront.setPower(1);
        sleep(500);
        robot.leftFront.setPower(0.75);
        robot.rightFront.setPower(0.75);
        sleep(500);
        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        sleep(500);
        robot.leftFront.setPower(-0.95);
        robot.rightFront.setPower(0.95);
        sleep(500);
        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
/*
        while (opModeIsActive()) {
            // Put loop blocks here
            //sleep(1000);
            //sleep(500);
            //robot.leftFront.setPower(-0.25);
            //robot.rightFront.setPower(0.25);
            //sleep(1000);
            //robot.leftFront.setPower(0);
            //robot.rightFront.setPower(0);
        } */
    }
}