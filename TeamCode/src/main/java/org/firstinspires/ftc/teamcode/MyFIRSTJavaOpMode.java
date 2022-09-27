package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp
public class MyFIRSTJavaOpMode extends LinearOpMode {
    private DcMotor leftFront  = null;
    private DcMotor rightFront  = null;
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;


    @Override
    public void runOpMode() {
        leftFront  = hardwareMap.get(DcMotor.class, "left_front");
        rightFront = hardwareMap.get(DcMotor.class, "right_front");
        leftBack = hardwareMap.get(DcMotor.class, "left_back");
        rightBack = hardwareMap.get(DcMotor.class, "right_back");

        telemetry.addData("Status: " , "Initialized");
        telemetry.update();
        //wait for the game to start(driver presses play)
        waitForStart();

        //run until end of match(driver presses stop)
        while (opModeIsActive()) {
            telemetry.addData("Status: " , "Running");
            telemetry.update();
            //values from -1 to 1
            leftFront.setPower(0.5);
            leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        }
    }
}
