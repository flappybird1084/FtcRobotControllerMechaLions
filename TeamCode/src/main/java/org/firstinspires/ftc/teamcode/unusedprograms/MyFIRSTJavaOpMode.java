//autonomous mode, not op mode
//
package org.firstinspires.ftc.teamcode.unusedprograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotHardware;

//@Autonomous(name = "EncoderTest2", group = "Auton")
public class MyFIRSTJavaOpMode extends LinearOpMode {
    RobotHardware robot;
    HardwareMap hwMap;
    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack;
    ElapsedTime runtime;
    //DcMotor crane;
    //Servo claw;
    //BNO055IMU imu;
    //ElapsedTime runtime;
    static final double COUNTS_PER_MOTOR_REV  = 28.0;
    static final double DRIVE_GEAR_REDUCTION = 30.24;
    static final double WHEEL_CIRCUMFERENCE_IN = 3.78 * 3.14;
    static final double COUNTS_PER_WHEEL_REV = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;
    static final double COUNTS_PER_IN = COUNTS_PER_WHEEL_REV/WHEEL_CIRCUMFERENCE_IN;

    //robot.init(hwMap);
    @Override
    public void runOpMode() {
        robot.leftFront = hardwareMap.get(DcMotor.class, "robot.leftFront");
        robot.rightFront = hardwareMap.get(DcMotor.class, "robot.rightFront");
        robot.leftBack = hardwareMap.get(DcMotor.class, "robot.leftBack");
        robot.rightBack = hardwareMap.get(DcMotor.class, "robot.rightBack");
        //crane = hardwareMap.get(DcMotor.class, "crane");
        //claw = hardwareMap.get(Servo.class, "claw");
        //imu = hardwareMap.get(BNO055IMU.class, "imu");
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection((DcMotorSimple.Direction.REVERSE));
        int lefttarget = (int)(610 * COUNTS_PER_IN);
        int righttarget = (int)(610 * COUNTS_PER_IN);
        double ltps = (175.0/60) * COUNTS_PER_WHEEL_REV;
        double rtps = (175.0/60) * COUNTS_PER_WHEEL_REV;
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

        int leftTarget = (int)(300 * COUNTS_PER_IN);
        int rightTarget = (int)( -300 * COUNTS_PER_IN);
        double LTPS = (100.0/ 60) * COUNTS_PER_WHEEL_REV;
        double RTPS = (70.0/ 60) * COUNTS_PER_WHEEL_REV;

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

    }
    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftTarget1;
        int newLeftTarget2;
        int newRightTarget1;
        int newRightTarget2;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget1 = leftFront.getCurrentPosition() + (int)(leftInches * COUNTS_PER_IN);
            newLeftTarget2 = leftBack.getCurrentPosition() + (int)(leftInches * COUNTS_PER_IN);
            newRightTarget1 = rightFront.getCurrentPosition() + (int)(rightInches * COUNTS_PER_IN);
            newRightTarget2 = rightBack.getCurrentPosition() + (int)(rightInches * COUNTS_PER_IN);
            leftBack.setTargetPosition(newLeftTarget2);
            rightFront.setTargetPosition(newRightTarget1);
            leftFront.setTargetPosition(newLeftTarget1);
            rightBack.setTargetPosition(newRightTarget2);

            // Turn On RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            leftFront.setPower(Math.abs(speed));
            leftBack.setPower(Math.abs(speed));
            rightFront.setPower(Math.abs(speed));
            rightBack.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    ((leftFront.isBusy() && leftBack.isBusy())&& (rightFront.isBusy() && rightBack.isBusy()))) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget1, newLeftTarget2, newRightTarget1, newRightTarget2);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        leftFront.getCurrentPosition(),
                        leftBack.getCurrentPosition(),
                        rightFront.getCurrentPosition(),
                        rightBack.getCurrentPosition(),
                        telemetry.update());
            }

            // Stop all motion;
            leftFront.setPower(0);
            leftBack.setPower(0);
            rightFront.setPower(0);
            rightBack.setPower(0);

            // Turn off RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //  sleep(250);   // optional pause after each move
        }


    }

    // actual code running part
    //robot.encoderDrive(5.0, 3.0, 3.0, 1.0);
    }

