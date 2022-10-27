package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

    @Autonomous(name = "EncoderTestOld", group = "Auton")

    public class EncoderTestOld extends OpMode {
        RobotHardware robot = new RobotHardware();

        //@Override whenever you create a method
        @Override
        //What happens when you initalize program
        public void init(){
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
        }

        @Override
        public void init_loop(){

        }

        @Override
        public void start(){

        }

        @Override
        public void loop() {
            robot.encoderMovements(70, 1);
        }

        @Override
        public void stop() {

        }
    }

