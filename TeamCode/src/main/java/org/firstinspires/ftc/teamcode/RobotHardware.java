/* Copyright (c) 2022 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * This file works in conjunction with the External Hardware Class sample called: ConceptExternalHardwareClass.java
 * Please read the explanations in that Sample about how to use this class definition.
 *
 * This file defines a Java Class that performs all the setup and configuration for a sample robot's hardware (motors and sensors).
 * It assumes three motors (left_drive, right_drive and arm) and two servos (left_hand and right_hand)
 *
 * This one file/class can be used by ALL of your OpModes without having to cut & paste the code each time.
 *
 * Where possible, the actual hardware objects are "abstracted" (or hidden) so the OpMode code just makes calls into the class,
 * rather than accessing the internal hardware directly. This is why the objects are declared "private".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with *exactly the same name*.
 *
 * Or.. In OnBot Java, add a new file named RobotHardware.java, drawing from this Sample; select Not an OpMode.
 * Also add a new OpMode, drawing from the Sample ConceptExternalHardwareClass.java; select TeleOp.
 *
 */


public class RobotHardware {

    /* Declare OpMode members. */
    //private LinearOpMode myOpMode = null;   // gain access to methods in the calling OpMode.

    // Define Motor and Servo objects  (Make them private so they can't be accessed externally)

    public DcMotor leftFront = null;
    public DcMotor rightFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightBack = null;
    public DcMotor ViperSlide = null;

    //RobotHardware.leftFront.setPower(0);
    //RobotHardware.servo1.setposition(RobotHardware.MID_SERVO);

    public Servo servo1 = null;
    public Servo servo2 = null;

    // Define Drive constants.  Make them public so they CAN be used by the calling OpMode
    /*
    // Not used
    public static final double MID_SERVO = 0.5;
    public static final double HAND_SPEED = 0.02;  // sets rate to move servo
    public static final double ARM_UP_POWER = 0.45;
    public static final double ARM_DOWN_POWER = -0.45;
    public static final double ARM_CLOSE = 0.0;
    public static final double ARM_OPEN = 1.0;

     */
    public static final double TICK_COUNT = 537.7; // strafer chassis kit v5, same motor as the viper slide
    public static final double CIRCUMFERENCE = 3.14 * 3.78; // this is in inches
    public static final double VS_DIA = 4.41; // sku: 5203-2402-0019
    public static final double VS_CIRCUMFERENCE = VS_DIA * 3.14;

    private ElapsedTime runtime = new ElapsedTime(); //trying to make the robot execute sleep();

    public void resetMotor(DcMotor motor) {
        motor.setPower(0);
    }


    /* local OpMode members. */

    HardwareMap hwMap;

    // Define a constructor that allows the OpMode to pass a reference to itself.
//    public RobotHardware() {
//
//    }


    /**
     * Initialize all the robot's hardware.
     * This method must be called ONCE when the OpMode is initialized.
     * <p>
     * All of the hardware devices are accessed via the hardware map, and initialized.
     */
    public void init(HardwareMap ahwMap) {
        // Define and Initialize Motors (note: need to use reference to actual OpMode).
        leftFront = ahwMap.get(DcMotor.class, "leftFront");
        rightFront = ahwMap.get(DcMotor.class, "rightFront");
        leftBack = ahwMap.get(DcMotor.class, "leftBack");
        rightBack = ahwMap.get(DcMotor.class, "rightBack");
        ViperSlide = ahwMap.get(DcMotor.class, "ViperSlide");

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        ViperSlide.setDirection(DcMotor.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ViperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
        // leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Define and initialize ALL installed servos.
        servo1 = ahwMap.get(Servo.class, "Servo1");
        servo2 = ahwMap.get(Servo.class, "Servo2");
        servo1.setPosition(0);
        servo2.setPosition(0);


//        myOpMode.telemetry.addData(">", "Hardware Initialized");
//        myOpMode.telemetry.update();
    }

    /**
     * This function sets all of the powers on the motors to 0
     * setPower takes 0 as the value, in order to reset the power.
     * @param
     */
    public void zero() {
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
    }

    /**
     * This function sets all of the powers on the motors to 1, which is max power.
     * setPower takes 1 as the value, in order to max out the power.
     * @param
     */
    public void all_full_power() {
        leftFront.setPower(1);
        leftBack.setPower(1);
        rightFront.setPower(1);
        rightBack.setPower(1);
    }

    /**
     * This function checks whether all of the motors are busy.
     * returns true or false depending on the case: whether the code and the motors are running or not.
     * @return
     */
    public boolean isAllBusy() {
        if (leftBack.isBusy() && leftFront.isBusy() && rightBack.isBusy() && rightFront.isBusy()) {
            return true;
        }
        return false;
    }

    /**
     * Ths function check whether any, multiple or all of the motors, or the ViperSlide is busy
     * returns true or false depending on the case: whether the code and the motors are running or not.
     * @return
     */
    public boolean isAnyBusy() {
        if (leftBack.isBusy() || leftFront.isBusy() || rightBack.isBusy() || rightFront.isBusy() || ViperSlide.isBusy()) {
            return true;
        }
        return false;
    }

    /**
     * This function causes the robot to move forward for a certain distance, at a certain power, in a certain direction
     * The distance is specified as a double
     * The power is also specified as a double with a value from 0-1
     * The direction can be either "forward", "backward", "left", or "right"
     * The program uses encoders to allow the robot to move forward for a certain distance, at a certain speed, based on the user's parameters
     * @param telemetry
     * @param distance
     * @param power
     * @param direction
     */
    public void encoderMovements(Telemetry telemetry, double distance, double power, String direction) {
        // broken because power is only taken as a positive value and we should've made target position negative.
        // fixed it now by replacing leftfrontpower with leftfronttarget and so on
        // distance in inches
        // direction can be forward, backward, left, or right

        double rotationsNeeded = distance / CIRCUMFERENCE;
        int encoderDrivingTarget = (int) (rotationsNeeded * TICK_COUNT);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        power = Math.abs(power);
        int leftFrontTarget = encoderDrivingTarget;
        int rightFrontTarget = encoderDrivingTarget;
        int leftBackTarget = encoderDrivingTarget;
        int rightBackTarget = encoderDrivingTarget;

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        direction = direction.toLowerCase();

        // no forward if statement cause it's already that
        if (direction.equals("backward")) {
            telemetry.addData("Moving ", "Backwards"); telemetry.update();
            leftBackTarget *= -1;
            leftFrontTarget *= -1;
            rightBackTarget *= -1;
            rightFrontTarget *= -1;
        }
        else if (direction.equals("right")) {
            telemetry.addData("Moving ", "Right"); telemetry.update();
            leftBackTarget *= -1;
            rightFrontTarget *= -1;
        }
        else if (direction.equals("left")) {
            telemetry.addData("Moving ", "Left"); telemetry.update();
            leftFrontTarget *= -1;
            rightBackTarget *= -1;
        }
        else{
            telemetry.addData("Moving ", "Forward"); telemetry.update();
        }

        leftFront.setPower(power);
        leftBack.setPower(power);
        rightFront.setPower(power);
        rightBack.setPower(power);

        leftFront.setTargetPosition(leftFrontTarget);
        leftBack.setTargetPosition(leftBackTarget);
        rightFront.setTargetPosition(rightFrontTarget);
        rightBack.setTargetPosition(rightBackTarget);

        telemetry.addData("leftFrontTarget ", leftFrontTarget); telemetry.update();
        telemetry.addData("leftBackTarget ", leftBackTarget); telemetry.update();
        telemetry.addData("rightFrontTarget ", rightFrontTarget); telemetry.update();
        telemetry.addData("rightBackTarget ", rightBackTarget); telemetry.update();

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //zero(); //Don't do this here as it prevents motors from running to completion.
    }

    /**
     * This function causes the Viper Slide to move for a set amount of distance, at a certain power, and at a specific direction.
     * The distance is specified a double
     * The power is also specified as a double with a value from 0-1
     * The direction can either be "forward", or "backward", and moves the Viper Slide up and down, respectively.
     * @param telemetry
     * @param distance
     * @param power
     * @param direction
     */
    public int viperSlideEncoderMovements(Telemetry telemetry, double distance, double power, String direction) {
        // distance in inches
        // direction can be forward or backward
        double rotationsNeeded = distance / VS_CIRCUMFERENCE*3;
        int encoderDrivingTarget = (int) (rotationsNeeded * TICK_COUNT);

        ViperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        power = Math.abs(power);
        int viperSlideTarget = encoderDrivingTarget;
        ViperSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        direction = direction.toLowerCase();

        // no forward if statement cause it's already that
        if (direction.equals("backward")) {
            telemetry.addData("Moving ", "Backwards"); telemetry.update();
            viperSlideTarget *= -1;
            //power *= -1;
        }
        else{
            telemetry.addData("Moving ", "Forward"); telemetry.update();
            //viperSlideTarget *= -1;
            //power*= -1;
        }

        ViperSlide.setPower(power);
        ViperSlide.setTargetPosition(viperSlideTarget);
        ViperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        return viperSlideTarget;
        //zero(); //Don't do this here as it prevents motors from running to completion.
    }

    /**
     * This function programs the controller movements to link up with the motor movements in order to move the robot accordingly.
     * This function has the objects GamePad 1 and GamePad 2 for the controllers
     * This function has servo0pos and servo100pos defined as doubles, in order to open or close an object. In our case it is a claw.
     * @param gamepad1
     * @param gamepad2
     * @param servo0pos
     * @param servo100pos
     * @param telemetry
     */
    public void TeleopLoop(Gamepad gamepad1, Gamepad gamepad2, double servo0pos, double servo100pos, Telemetry telemetry){
        double speedScaling;
        double rightStick = -gamepad1.right_stick_y;
        double leftStick = -gamepad1.left_stick_y;

        ViperSlide.setPower(-gamepad2.left_stick_y);
        ViperSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        speedScaling = (Math.abs(gamepad2.right_stick_y)*3/5) + 0.4;

        if(gamepad1.right_bumper) {
            // Strafe Right
            leftFront.setPower(speedScaling);
            leftBack.setPower(-speedScaling);
            rightFront.setPower(-speedScaling);
            rightBack.setPower(speedScaling);
        }
        else if(gamepad1.left_bumper) {
            // Strafe Left
            leftFront.setPower(-speedScaling);
            leftBack.setPower(speedScaling);
            rightFront.setPower(speedScaling);
            rightBack.setPower(-speedScaling);
        }
/*
        else if(gamepad1.dpad_up) {
            viperUp = true;
            viperDown = false;
        }

        else if(gamepad1.dpad_down) {
            viperDown = true;
            viperUp = false;
        }
*/
        else if(gamepad2.dpad_up) {
            ViperSlide.setPower(0.5);
        }

        else if(gamepad2.dpad_down) {
            ViperSlide.setPower(-0.5);
        }

        else if (gamepad1.dpad_left) {
            ViperSlide.setPower(0); // not in use anymore, rebind if you want
        }
        else {
            // move according to the stick values, will allow the robot to move forward, backward, or turn
            leftFront.setPower(leftStick * speedScaling);
            leftBack.setPower(leftStick * speedScaling);
            rightFront.setPower(rightStick * speedScaling);
            rightBack.setPower(rightStick * speedScaling);
        }
        double servoPos = servo1.getPosition();

        if(gamepad2.a) {
            servo1.setPosition(servo100pos);
            //retracted
        }

        else if (gamepad2.b) {
            servo1.setPosition(servo0pos);
            //extended
        }

        telemetry.addData("LeftFront Power", leftFront.getPower());
        telemetry.addData("LeftBack Power", leftBack.getPower());
        telemetry.update();


    }



    /**
     * This program utilizes the encoderMovements program to allow the robot to move a specified
     * number of mat blocks, or the squares in the mat.
     * Power is default 0.5
     * Direction is specified as the same for encoderMovements, "forward", "backward", "left", and "right"
     * @param telemetry
     * @param blocks
     * @param direction
     */
    public void moveDirectionBlocks (Telemetry telemetry, double blocks, String direction) {
        double inches = blocks * 24;

        encoderMovements(telemetry, inches, 0.5, direction);
    }

    /**
     * Same as previous program, but offset inches cause the robot to move extra, just in case it missed its target
     * @param telemetry
     * @param blocks
     * @param direction
     * @param offsetInches
     */
    public void moveDirectionBlocks (Telemetry telemetry, double blocks, String direction, double offsetInches) {
        // note that offset is in inches
        double inches = blocks * 24;
        inches += offsetInches;

        encoderMovements(telemetry, inches, 0.5, direction);
    }

    /**
     * Same as moveDirectionBlocks, but power isn't defaulted, and takes in a parameter from the user.
     * Direction, offsetInches, telemetry, and blocks are the same as before
     * @param telemetry
     * @param blocks
     * @param direction
     * @param offsetInches
     * @param power
     */
    public void moveDirectionBlocksMAX (Telemetry telemetry, double blocks, String direction, double offsetInches, double power) {
        // note that offset is in inches
        double inches = blocks * 24;
        inches += offsetInches;

        encoderMovements(telemetry, inches, power, direction);

    }








    /**
     * Calculates the left/right motor powers required to achieve the requested
     * robot motions: Drive (Axial motion) and Turn (Yaw motion).
     * Then sends these power levels to the motors.
     *
     * @param Drive     Fwd/Rev driving power (-1.0 to 1.0) +ve is forward
     * @param Turn      Right/Left turning power (-1.0 to 1.0) +ve is CW
     */
//    public void driveRobot(double Drive, double Turn) {
//        // Combine drive and turn for blended motion.
//        double left  = Drive + Turn;
//        double right = Drive - Turn;
//
//        // Scale the values so neither exceed +/- 1.0
//        double max = Math.max(Math.abs(left), Math.abs(right));
//        if (max > 1.0)
//        {
//            left /= max;
//            right /= max;
//        }
//
//        // Use existing function to drive both wheels.
//        setDrivePower(left, right);
//    }

    /**
     * Pass the requested wheel motor powers to the appropriate hardware drive motors.
     *
     * @param leftWheel     Fwd/Rev driving power (-1.0 to 1.0) +ve is forward
     * @param rightWheel    Fwd/Rev driving power (-1.0 to 1.0) +ve is forward
     */
//    public void setDrivePower(double leftWheel, double rightWheel) {
//        // Output the values to the motor drives.
//        leftDrive.setPower(leftWheel);
//        rightDrive.setPower(rightWheel);
//    }

    /**
     * Pass the requested arm power to the appropriate hardware drive motor
     *
     * @param power driving power (-1.0 to 1.0)
     */
//    public void setArmPower(double power) {
//        armMotor.setPower(power);
//    }

    /**
     * Send the two hand-servos to opposing (mirrored) positions, based on the passed offset.
     *
     * @param offset
     */
//    public void setHandPositions(double offset) {
//        offset = Range.clip(offset, -0.5, 0.5);
//        leftHand.setPosition(MID_SERVO + offset);
//        rightHand.setPosition(MID_SERVO - offset);
//    }
}
