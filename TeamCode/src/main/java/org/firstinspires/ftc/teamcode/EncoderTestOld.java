package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

    @Autonomous(name = "EncoderTestOld", group = "Auton")

    public class EncoderTestOld extends LinearOpMode {
        RobotHardware robot = new RobotHardware();

        //@Override whenever you create a method
        //What happens when you initalize program
        @Override
        public void runOpMode(){
            //Initialize hardwareMap
            robot.init(hardwareMap);
            waitForStart();
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

            robot.encoderMovements(70, 1);
        }

    }

