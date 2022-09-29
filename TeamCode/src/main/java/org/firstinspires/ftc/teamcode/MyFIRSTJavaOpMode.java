package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

//Stating whether Autonomous or TeleOp
@TeleOp
public class MyFIRSTJavaOpMode extends LinearOpMode {
    //Declaring Motors
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    @Override
    public void runOpMode() {
        //Mapping Motors
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        //Data sent to the Driver Station
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
            //Values from -1 to 1
            frontLeft.setPower(0.5);
            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

}
