package org.firstinspires.ftc.teamcode.unusedprograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotHardware;

//@Autonomous(name = "EncoderTestOld", group = "Auton")

    public class EncoderTestOld extends LinearOpMode {
        RobotHardware robot = new RobotHardware();
        private ElapsedTime runtime = new ElapsedTime();
        static private int timeoutS = 10000; // APB: not optimized
        private int encoderDist = 70;


        //@Override whenever you create a method
        //What happens when you initalize program
        @Override
        public void runOpMode() {
            int position = 3;
            // position can be 1, 2, or 3: 1 is left, 2 is center, and 3 is right.
            // second position makes the robot move in front of the cone instead.
            //Initialize hardwareMap
            robot.init(hardwareMap);
            //        robot.ViperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            waitForStart();

            //robot.encoderMovements(encoderDist, 1);
            if (position == 1) {
                robot.moveDirectionBlocks(telemetry, 1, "left");
                telemetry.addData("Step: ", "Step #1");
                telemetry.update();
                waitForEncoderComplete();
                robot.moveDirectionBlocks(telemetry, 1, "forward");
                telemetry.addData("Step: ", "Step #2");
                telemetry.update();
                waitForEncoderComplete();
                telemetry.addData("Step: ", "Finished");
                telemetry.update();
            }
            else if (position == 2) {
                robot.moveDirectionBlocks(telemetry, 1, "right");
                telemetry.addData("Step: ", "Step #1");
                telemetry.update();
                waitForEncoderComplete();
                robot.moveDirectionBlocks(telemetry, 1, "forward");
                telemetry.addData("Step: ", "Step #2");
                telemetry.update();
                robot.moveDirectionBlocks(telemetry, 1, "forward");
                telemetry.addData("Step: ", "Step #3");
                telemetry.update();
                robot.moveDirectionBlocks(telemetry, 1, "left");
                telemetry.addData("Step: ", "Step #4");
                telemetry.update();
                waitForEncoderComplete();
                telemetry.addData("Step: ", "Finished");
                telemetry.update();
            }
            else if (position == 3) {
                robot.moveDirectionBlocks(telemetry, 1, "right");
                telemetry.addData("Step: ", "Step #1");
                telemetry.update();
                waitForEncoderComplete();
                robot.moveDirectionBlocks(telemetry, 1, "forward");
                telemetry.addData("Step: ", "Step #2");
                telemetry.update();
                waitForEncoderComplete();
                telemetry.addData("Step: ", "Finished");
                telemetry.update();
            }
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
                //telemetry.addData("Path1",  "Running to %7d :%7d", encoderDist,  encoderDist);
                //telemetry.addData("Path2",  "Running at %7d :%7d",
                //        robot.leftFront.getCurrentPosition(),
                //        robot.rightFront.getCurrentPosition());
                //telemetry.update();
            }
            telemetry.addData("Step: ","Done"); telemetry.update();
            sleep(500);
            //robot.zero();
        }

    }

