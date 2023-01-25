package org.firstinspires.ftc.teamcode;/* Copyright (c) 2021 FIRST. All rights reserved.
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




import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an example of a Linear "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When a selection is made from the menu, the corresponding OpMode is executed.
 *
 * This particular OpMode illustrates driving a 4-motor Omni-Directional (or Holonomic) robot.
 * This code will work with either a Mecanum-Drive or an X-Drive train.
 * Both of these drives are illustrated at https://gm0.org/en/latest/docs/robot-design/drivetrains/holonomic.html
 * Note that a Mecanum drive must display an X roller-pattern when viewed from above.
 *
 * Also note that it is critical to set the correct rotation direction for each motor.  See details below.
 *
 * Holonomic drives provide the ability for the robot to move in three axes (directions) simultaneously.
 * Each motion axis is controlled by one Joystick axis.
 *
 * 1) Axial:    Driving forward and backward               Left-joystick Forward/Backward
 * 2) Lateral:  Strafing right and left                     Left-joystick Right and Left
 * 3) Yaw:      Rotating Clockwise and counter clockwise    Right-joystick Right and Left
 *
 * This code is written assuming that the right-side motors need to be reversed for the robot to drive forward.
 * When you first test your robot, if it moves backward when you push the left stick forward, then you must flip
 * the direction of all 4 motors (see code below).
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Basic: Omni Linear OpMode", group="Linear Opmode")

public class TeleOpDynamicTest extends LinearOpMode {
    RobotHardware robot = new RobotHardware();
    private ElapsedTime runtime = new ElapsedTime();
    double additionalYaw = 0;
    double leftYawCoolDown = runtime.seconds();
    double rightYawCoolDown = runtime.seconds();
    double ViperSlideEncoderCoolDown = runtime.seconds();

    // Declare OpMode members for each of the 4 motors.


    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.


        // ########################################################################################
        // !!!            IMPORTANT Drive Information. Test your motor directions.            !!!!!
        // ########################################################################################
        // Most robots need the motors on one side to be reversed to drive forward.
        // The motor reversals shown here are for a "direct drive" robot (the wheels turn the same direction as the motor shaft)
        // If your robot has additional gear reductions or uses a right-angled drive, it's important to ensure
        // that your motors are turning in the correct direction.  So, start out with the reversals here, BUT
        // when you first test your robot, push the left joystick forward and observe the direction the wheels turn.
        // Reverse the direction (flip FORWARD <-> REVERSE ) of any wheel that runs backward
        // Keep testing until ALL the wheels move the robot forward when you push the left joystick forward.


        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        robot.init(hardwareMap);
        runtime.reset();
        int target = 0;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double max;
            // robot.ViperSlide.setPower(gamepad2.left_stick_y);
            //robot.ViperSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //andrew wants to decrease this, might make 3/5 to like 1/2.
            double speedScaling = (Math.abs(gamepad2.right_stick_y)*2/5) + 0.6;

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            double lateral =  gamepad1.left_stick_x;
            double yaw     =  gamepad1.right_stick_x;

            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double leftFrontPower  = (axial + lateral + yaw);
            double rightFrontPower = (axial - lateral - yaw);
            double leftBackPower   = (axial - lateral + yaw);
            double rightBackPower  = (axial + lateral - yaw);
            double right_trig = gamepad1.right_trigger;
            double left_trig  = gamepad1.left_trigger;
            double avgMotorPower = (leftBackPower+leftFrontPower+rightBackPower+rightFrontPower)/4;
            String VSPosition = "down";

            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            double servo0pos =  robot.servo1.getPosition();
            // min position, hopefully putting it in the loop helps.
            robot.servo1.setPosition(servo0pos);
            robot.servo1.setPosition(servo0pos+100);
            double servo100pos = robot.servo1.getPosition();
            // got the max position
            robot.servo1.setPosition(servo0pos);

            if (gamepad1.left_bumper && (runtime.seconds()-leftYawCoolDown)>1){
                additionalYaw-=0.01;
                leftYawCoolDown = runtime.seconds();
            }

            if (gamepad1.right_bumper && (runtime.seconds()-rightYawCoolDown)>1){
                additionalYaw+=0.01;
                rightYawCoolDown = runtime.seconds();
            }

            if(gamepad1.right_trigger > 0){
                leftFrontPower = right_trig;
                leftBackPower = -right_trig;
                rightFrontPower = -right_trig;
                rightBackPower = right_trig;
            }

            if(gamepad1.left_trigger > 0){
                leftFrontPower = -left_trig;
                leftBackPower = left_trig;
                rightFrontPower = left_trig;
                rightBackPower = -left_trig;
            }

            if(gamepad2.a) {
                robot.servo1.setPosition(100);
                //retracted

            }
            else if (gamepad2.b) {
                robot.servo1.setPosition(0);
                //extended
            }

            if(gamepad2.dpad_left){
                if(!robot.ViperSlide.isBusy()) {
                    target =robot.viperSlideEncoderMovements(telemetry, 15, 0.9, "forward");
                    VSPosition = "low";
                }
            }

            else if(gamepad2.dpad_right){
                if(!robot.ViperSlide.isBusy()) {
                    target = robot.viperSlideEncoderMovements(telemetry, 25, 0.9, "forward");
                    VSPosition = "med";
                }
            }

            else if(gamepad2.dpad_up){
                if(!robot.ViperSlide.isBusy()) {
                    target = robot.viperSlideEncoderMovements(telemetry, 35, 0.9, "forward");
                    VSPosition = "high";
                }
            }

            else if(gamepad2.dpad_down){
                if(!robot.ViperSlide.isBusy()) {
                    target = robot.viperSlideEncoderMovements(telemetry, 35, 0.9, "backward");
                    VSPosition = "down";
                }
            }

            if(Math.abs(gamepad2.left_stick_y) > 0){
                robot.ViperSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.ViperSlide.setPower(-0.5*gamepad2.left_stick_y);
                ViperSlideEncoderCoolDown = runtime.seconds();
            }

            if((runtime.seconds() - ViperSlideEncoderCoolDown < 0.05)&&(runtime.seconds() - ViperSlideEncoderCoolDown > 0.02)){
                robot.ViperSlide.setPower(0); // cooldown because if you let go
                // of the stick too sharply, the gamepad control would freeze
                // and the slide would keep moving

                // now, a small time after the slide hasn't been triggered, the power
                // is set to zero.
            }

            if (robot.ViperSlide.isBusy()){
                telemetry.addData("ViperSlide",  "Moving to %7d; At %7d", target,
                        robot.ViperSlide.getCurrentPosition());
            }

            if (max > 0.5) {
                leftFrontPower  /= max;
                rightFrontPower /= max;
                leftBackPower   /= max;
                rightBackPower  /= max;
            }

            //adding additional yaw
            leftBackPower += additionalYaw*avgMotorPower;
            leftFrontPower += additionalYaw*avgMotorPower;
            rightBackPower -= additionalYaw*avgMotorPower;
            rightFrontPower -= additionalYaw*avgMotorPower;


            // This is test code:
            //
            // Uncomment the following code to test your motor directions.
            // Each button should make the corresponding motor run FORWARD.
            //   1) First get all the motors to take to correct positions on the robot
            //      by adjusting your Robot Configuration if necessary.
            //   2) Then make sure they run in the correct direction by modifying the
            //      the setDirection() calls above.
            // Once the correct motors move in the correct direction re-comment this code.

/*
            leftFrontPower  = 1; //gamepad1.x ? 1.0 : 0.0;  // X gamepad
            leftBackPower   = 1; //gamepad1.a ? 1.0 : 0.0;  // A gamepad
            rightFrontPower = 1; //gamepad1.y ? 1.0 : 0.0;  // Y gamepad
            rightBackPower  = 1; //gamepad1.b ? 1.0 : 0.0;  // B gamepad

*/
            // Send calculated power to wheels
            robot.leftFront.setPower(leftFrontPower*speedScaling);
            robot.rightFront.setPower(rightFrontPower*speedScaling);
            robot.leftBack.setPower(leftBackPower*speedScaling);
            robot.rightBack.setPower(rightBackPower*speedScaling);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
            telemetry.addData("Additional Yaw: ", additionalYaw);
            telemetry.addData("Average Motor Power: ", avgMotorPower);
            telemetry.update();

        }
    }}
