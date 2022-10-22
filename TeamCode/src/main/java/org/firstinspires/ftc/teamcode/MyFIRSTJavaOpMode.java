package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MyFIRSTJavaOpMode extends LinearOpMode {
    RobotHardware robot = new RobotHardware();
    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack;
    DcMotor crane;
    Servo claw;
    BNO055IMU imu;
    ElapsedTime runtime;
    static final double COUNTS_PER_MOTOR_REV  = 28.0;
    static final double DRIVE_GEAR_REDUCTION = 30.24;
    static final double WHEEL_CIRCUMFERENCE_MM = 3.78 * 3.14;
    static final double COUNTS_PER_WHEEL_REV = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;
    static final double COUNTS_PER_MM = COUNTS_PER_WHEEL_REV/WHEEL_CIRCUMFERENCE_MM;
    //robot.init(HardwareMap)

    @Override
    public void runOpMode() {
        robot.leftFront = hardwareMap.get(DcMotor.class, "robot.leftFront");
        robot.rightFront = hardwareMap.get(DcMotor.class, "robot.rightFront");
        robot.leftBack = hardwareMap.get(DcMotor.class, "robot.leftBack");
        robot.rightBack = hardwareMap.get(DcMotor.class, "robot.rightBack");
        crane = hardwareMap.get(DcMotor.class, "crane");
        claw = hardwareMap.get(Servo.class, "claw");
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection((DcMotorSimple.Direction.REVERSE));
        int lefttarget = (int)(610 * COUNTS_PER_MM);
        int righttarget = (int)(610 * COUNTS_PER_MM);
        double ltps = (175/60) * COUNTS_PER_WHEEL_REV;
        double rtps = (175/60) * COUNTS_PER_WHEEL_REV;
        // Put initialization blocks here
        waitForStart();
        // Put run blocks here
        leftBack.setTargetPosition(lefttarget);
        leftFront.setTargetPosition(lefttarget);
        rightBack.setTargetPosition(righttarget);
        rightFront.setTargetPosition(righttarget);

        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftBack.setPower(ltps);
        leftFront.setPower(ltps);
        rightBack.setPower(rtps);
        rightFront.setPower(rtps);

        while (opModeIsActive() && ((leftFront.isBusy() && rightFront.isBusy()) && (leftBack.isBusy() && rightBack.isBusy()))){
            telemetry.addData("leftfront", leftFront.getCurrentPosition());
            telemetry.addData("leftback", leftBack.getCurrentPosition());
            telemetry.addData("rightfront", rightFront.getCurrentPosition());
            telemetry.addData("rightback", rightBack.getCurrentPosition());
        }

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int leftTarget = (int)(300 * COUNTS_PER_MM);
        int rightTarget = (int)( -300 * COUNTS_PER_MM);
        double LTPS = (100/ 60) * COUNTS_PER_WHEEL_REV;
        double RTPS = (70/ 60) * COUNTS_PER_WHEEL_REV;

        leftFront.setTargetPosition(leftTarget);
        leftBack.setTargetPosition(leftTarget);
        rightFront.setTargetPosition(rightTarget);
        rightBack.setTargetPosition(rightTarget);

        leftFront.setPower(LTPS);
        leftBack.setPower(LTPS);
        rightFront.setPower(RTPS);
        rightBack.setPower(RTPS);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(LTPS);
        rightFront.setPower(RTPS);
        leftBack.setPower(LTPS);
        rightBack.setPower(RTPS);

        //wait for motor to reach position before moving on
        while (opModeIsActive() && ((leftFront.isBusy() && rightFront.isBusy()) && (leftBack.isBusy() && rightBack.isBusy()))){
            telemetry.addData("leftfront", leftFront.getCurrentPosition());
            telemetry.addData("leftback", leftBack.getCurrentPosition());
            telemetry.addData("rightfront", rightFront.getCurrentPosition());
            telemetry.addData("rightback", rightBack.getCurrentPosition());

        }
    }
}