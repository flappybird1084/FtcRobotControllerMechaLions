package org.firstinspires.ftc.teamcode.unusedprograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotHardware;

//@Autonomous(name = "EncoderTestLinearOpMode", group = "Auton")

public class EncoderTestLinearOpMode extends LinearOpMode {
    RobotHardware robot = new RobotHardware();

    //@Override whenever you create a method
    @Override
    //What happens when you initialize program
    public void runOpMode(){
        //Initialize hardwareMap
        robot.init(hardwareMap);
        robot.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.ViperSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.ViperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        //robot.encoderMovements(11.8692, 0.5);
   //     this.sleep(5000);
    }

}
