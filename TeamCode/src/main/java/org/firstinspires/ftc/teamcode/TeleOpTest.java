package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

//Type of Program (Auto or TeleOp)
@TeleOp (name = "TeleOpTest", group = "test")

public class TeleOpTest  extends OpMode {
    //Create HardwareMap object
    RobotHardware robot = new RobotHardware();

    private String readyMessage = "READY!";
    private double servo0pos;
    //got the min position
    private double servo100pos;
    private double speedScaling = 0.4;

    //@Override whenever you create a method
    @Override
    //What happens when you initialize program
    public void init(){
        //Initialize hardwareMap
        robot.init(hardwareMap);
        telemetry.addData("Status", readyMessage);
        telemetry.update();
        servo0pos =  robot.servo1.getPosition();
        // min position, hopefully putting it in the loop helps.
        robot.servo1.setPosition(servo0pos);
        robot.servo1.setPosition(servo0pos+100);
        servo100pos = robot.servo1.getPosition();
        // got the max position
        robot.servo1.setPosition(servo0pos);
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

        robot.ViperSlide.setPower(-gamepad2.left_stick_y);
        robot.ViperSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        speedScaling = (Math.abs(gamepad2.right_stick_y)*3/5) + 0.4;

        if(gamepad1.right_bumper) {
            // Strafe Right
            robot.leftFront.setPower(-speedScaling);
            robot.leftBack.setPower(speedScaling);
            robot.rightFront.setPower(speedScaling);
            robot.rightBack.setPower(-speedScaling);
        }
        else if(gamepad1.left_bumper) {
            // Strafe Left
            robot.leftFront.setPower(speedScaling);
            robot.leftBack.setPower(-speedScaling);
            robot.rightFront.setPower(-speedScaling);
            robot.rightBack.setPower(speedScaling);
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
            robot.ViperSlide.setPower(0.5);
        }

        else if(gamepad2.dpad_down) {
            robot.ViperSlide.setPower(-0.5);
        }

        else if (gamepad1.dpad_left) {
            robot.ViperSlide.setPower(0); // not in use anymore, rebind if you want
        }
        else {
            // move according to the stick values, will allow the robot to move forward, backward, or turn
            robot.leftFront.setPower(leftStick * speedScaling);
            robot.leftBack.setPower(leftStick * speedScaling);
            robot.rightFront.setPower(rightStick * speedScaling);
            robot.rightBack.setPower(rightStick * speedScaling);
        }
        double servoPos = robot.servo1.getPosition();

        if(gamepad2.a) {
            robot.servo1.setPosition(servo100pos);
            //retracted
        }

        else if (gamepad2.b) {
            robot.servo1.setPosition(servo0pos);
            //extended
        }

        telemetry.addData("LeftFront Power", robot.leftFront.getPower());
        telemetry.addData("LeftBack Power", robot.leftBack.getPower());
        telemetry.update();
    }

    @Override
    public void stop() {
    }
}
