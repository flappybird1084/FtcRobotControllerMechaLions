package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//Type of Program (Auto or TeleOp)
@TeleOp (name = "TeleOpTest", group = "test")

public class TeleOpTest  extends OpMode {
    //Create HardwareMap object
    RobotHardware robot = new RobotHardware();

    private String readyMessage = "READY!";

    //@Override whenever you create a method
    @Override
    //What happens when you initialize program
    public void init(){
        //Initialize hardwareMap
        robot.init(hardwareMap);
        telemetry.addData("Status", readyMessage);
        telemetry.update();
    }

    @Override
    public void init_loop(){
    }

    @Override
    public void start(){
    }

    @Override
    public void loop() {
        double rightStick = -gamepad1.right_stick_y;
        double leftStick = -gamepad1.left_stick_y;

        if(gamepad1.right_bumper) {
            // Move Right
            robot.leftFront.setPower(1);
            robot.leftBack.setPower(-1);
            robot.rightFront.setPower(-1);
            robot.rightBack.setPower(1);
            robot.ViperSlide.setPower(0);
        }
        else if(gamepad1.left_bumper) {
            // Move Left
            robot.leftFront.setPower(-1);
            robot.leftBack.setPower(1);
            robot.rightFront.setPower(1);
            robot.rightBack.setPower(-1);
            robot.ViperSlide.setPower(0);
        }


        else if(gamepad1.dpad_up) {
            robot.ViperSlide.setPower(0.5);
        }

        else if(gamepad1.dpad_down) {
            robot.ViperSlide.setPower(-0.5);
        }

        /*
        // debug code because half the motors were unaliving
        else if(gamepad1.dpad_left) {
            robot.leftFront.setPower(1);
        }

        else if(gamepad1.dpad_right) {
            robot.rightFront.setPower(1);
        }

        else if(gamepad1.b) {
            robot.leftBack.setPower(1);
        }

        else if(gamepad1.x) {
            robot.rightBack.setPower(1);
        }
        */

        /*
        rians possible future code

        else if(gamepad1.left_stick_x < 0.1 && gamepad1.left_stick_x > 0.1) {
            robot.leftFront.setPower(leftStick);
            robot.leftBack.setPower(leftStick);
            robot.rightFront.setPower(leftStick);
            robot.rightBack.setPower(leftStick);
        }

         */
        else {
            robot.leftFront.setPower(leftStick);
            robot.leftBack.setPower(leftStick);
            robot.rightFront.setPower(rightStick);
            robot.rightBack.setPower(rightStick);
            robot.ViperSlide.setPower(0);
        }
        double servoPos = robot.servo1.getPosition();
        boolean servoTrig = false;
        if(gamepad1.a) {
            servoTrig = !servoTrig;
        }

        if (servoTrig) {
            robot.servo1.setPosition(100);
        }

        else {
            robot.servo1.setPosition(0);
        }


        telemetry.addData("LeftFront Power", robot.leftFront.getPower());
        telemetry.addData("LeftBack Power", robot.leftBack.getPower());
        telemetry.update();
    }

    @Override
    public void stop() {
    }
}
