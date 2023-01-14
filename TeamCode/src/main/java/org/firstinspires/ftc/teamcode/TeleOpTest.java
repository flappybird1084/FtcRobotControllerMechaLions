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
        double rightStick = gamepad1.right_stick_y;
        double leftStick = gamepad1.left_stick_y;

        robot.ViperSlide.setPower(gamepad2.left_stick_y);
        robot.ViperSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //andrew wants to decrease this, might make 3/5 to like 1/2.
        speedScaling = (Math.abs(gamepad2.right_stick_y)*3/5) + 0.4;
        //would be better to increase speedscaling
        //strafe program
        if(gamepad1.right_bumper) {
            // Move Right
            robot.leftFront.setPower(-speedScaling);
            robot.leftBack.setPower(speedScaling);
            robot.rightFront.setPower(speedScaling);
            robot.rightBack.setPower(-speedScaling);
            //apparently teleop test is inverted
            //would probably switch the positive and the negative
        }
        else if(gamepad1.left_bumper) {
            // Move Left
            robot.leftFront.setPower(-speedScaling);
            robot.leftBack.setPower(speedScaling);
            robot.rightFront.setPower(speedScaling);
            robot.rightBack.setPower(-speedScaling);
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
            robot.ViperSlide.setPower(-0.5);
        }

        else if(gamepad2.dpad_down) {
            robot.ViperSlide.setPower(0.5);
        }

        else if (gamepad1.dpad_left) {
            robot.ViperSlide.setPower(0); // not in use anymore, rebind if you want
        }





        // move slide up if no prev. movement
/*
        else if(viperUp) {
            robot.ViperSlide.setPower(0.5);
            robot.leftFront.setPower(leftStick * 0.4);
            robot.leftBack.setPower(leftStick * 0.4);
            robot.rightFront.setPower(rightStick * 0.4);
            robot.rightBack.setPower(rightStick * 0.4);

        }

        else if (viperDown) {
            robot.ViperSlide.setPower(-0.35);

            robot.leftFront.setPower(leftStick * 0.4);
            robot.leftBack.setPower(leftStick * 0.4);
            robot.rightFront.setPower(rightStick * 0.4);
            robot.rightBack.setPower(rightStick * 0.4);

        }
*/


/*
        // gamepad 2 is currently a debug tester!
        else if (gamepad2.a) {
            telemetry.addData("VS: ", "initialized");
            telemetry.update();
            robot.viperSlideEncoderMovements(telemetry, 20,0.5,"forward");
            telemetry.addData("VS: ", "method called");
            telemetry.update();
            while(robot.ViperSlide.isBusy()){
                telemetry.addData("VS: ", "running");
                telemetry.update();
            }
            telemetry.addData("VS: ", "done");
            telemetry.update();

        }

        else if (gamepad2.b) {
            telemetry.addData("VSB: ", "initialized");
            telemetry.update();
            robot.viperSlideEncoderMovements(telemetry, 20,0.5,"backward");
            telemetry.addData("VSB: ", "method called");
            telemetry.update();
            while(robot.ViperSlide.isBusy()){
                telemetry.addData("VSB: ", "running");
                telemetry.update();
            }
            telemetry.addData("VSB: ", "done");
            telemetry.update();

        }


*/




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

            robot.leftFront.setPower(leftStick * speedScaling);
            robot.leftBack.setPower(leftStick * speedScaling);
            robot.rightFront.setPower(-rightStick * speedScaling);
            robot.rightBack.setPower(-rightStick * speedScaling);

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
