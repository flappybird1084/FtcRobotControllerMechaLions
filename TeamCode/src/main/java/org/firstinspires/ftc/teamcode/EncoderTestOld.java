package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "EncoderTestOld", group = "Auton")

    public class EncoderTestOld extends LinearOpMode {
        RobotHardware robot = new RobotHardware();
        private ElapsedTime runtime = new ElapsedTime();
        static private int timeoutS = 10000; // APB: not optimized
        private int encoderDist = 70;


        //@Override whenever you create a method
        //What happens when you initalize program
        @Override
        public void runOpMode() {
            //Initialize hardwareMap
            robot.init(hardwareMap);
            robot.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //        robot.ViperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            waitForStart();

            //robot.encoderMovements(encoderDist, 1);
            robot.moveDirectionBlocks(1, "right");
            waitForEncoderComplete();
            robot.moveDirectionBlocks(1, "forward");
            waitForEncoderComplete();
        }

        public void waitForEncoderComplete() {
            //sleep(10000);

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() && (runtime.seconds() < timeoutS) && robot.isAnyBusy()) {
                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", encoderDist,  encoderDist);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftFront.getCurrentPosition(),
                        robot.rightFront.getCurrentPosition());
                telemetry.update();
            }

            robot.zero();
        }
    }

