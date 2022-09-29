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
    //What happens when you initalize program
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
        double leftStick = -gamepad1.left_stick_y;
        double rightStick = -gamepad1.right_stick_y;

        if(gamepad1.left_bumper) {
            robot.leftFront.setPower(-1);
            robot.leftBack.setPower(1);
            robot.rightFront.setPower(1);
            robot.rightBack.setPower(-1);
        }
        else if(gamepad1.right_bumper) {
            robot.leftFront.setPower(1);
            robot.leftBack.setPower(-1);
            robot.rightFront.setPower(-1);
            robot.rightBack.setPower(1);
        }
        else {
            robot.leftFront.setPower(leftStick);
            robot.leftBack.setPower(leftStick);
            robot.rightFront.setPower(rightStick);
            robot.rightBack.setPower(rightStick);
        }

        if(gamepad1.a) {
            robot.servo1.setPosition(1);
        }
        else {
            robot.servo1.setPosition(0);
        }

        telemetry.addData("LeftFront Power", robot.leftFront.getPower());
        telemetry.update();
    }

    @Override
    public void stop() {

    }
}
