/*
 * Copyright (c) 2021 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.autoncamera;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import java.util.ArrayList;

@Autonomous(name = "AprilTagAutonomousInitDetectionExample", group = "Auton")
public class AprilTagAutonomousInitDetectionExample extends LinearOpMode {
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;
    RobotHardware robot = new RobotHardware();
    private ElapsedTime runtime = new ElapsedTime();
    static private int timeoutS = 10000; // APB: not optimized
    private int encoderDist = 70;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    //int ID_TAG_OF_INTEREST = 18; // Tag ID 1,2,3 from the 36h11 family
    int LEFT = 1;
    int MIDDLE = 2;
    int RIGHT = 3;

    AprilTagDetection tagOfInterest = null;

    void tagToTelemetry(AprilTagDetection detection) {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x * FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y * FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z * FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
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
        telemetry.addData("Step: ", "Done");
        telemetry.update();
        sleep(500);
        //robot.zero();
    }


    @Override
    public void runOpMode() {
        int position = 0;
        robot.init(hardwareMap);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        telemetry.setMsTransmissionInterval(50);

        /*
         * The INIT-loop:
         * This REPLACES waitForStart!
         */
        while (!isStarted() && !isStopRequested()) {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if (currentDetections.size() != 0) {
                boolean tagFound = false;

                for (AprilTagDetection tag : currentDetections) {
                    if (tag.id == LEFT || tag.id == RIGHT || tag.id == MIDDLE) {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }

                if (tagFound) {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                } else {
                    telemetry.addLine("Don't see tag of interest :(");

                    if (tagOfInterest == null) {
                        telemetry.addLine("(The tag has never been seen)");
                    } else {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            } else {
                telemetry.addLine("Don't see tag of interest :(");

                if (tagOfInterest == null) {
                    telemetry.addLine("(The tag has never been seen)");
                } else {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }

            telemetry.update();
            sleep(20);
        }

        /*
         * The START command just came in: now work off the latest snapshot acquired
         * during the init loop.
         */

        /* Update the telemetry */
        if (tagOfInterest != null) {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        } else {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();
        }

        /* Actually do something useful */


        // if tagofinterest.id == null
        if (tagOfInterest.id == LEFT) {
            //left trajectory
            telemetry.addData("Direction: ", "left");
            position = 1;
        } else if (tagOfInterest.id == RIGHT) {
            //right trajectory
            telemetry.addData("Direction: ", "right");
            position = 3;
        } else if (tagOfInterest.id == MIDDLE) {
            // middle trajectory
            telemetry.addData("Direction: ", "middle");
            position = 2;
        }

        /* You wouldn't have this in your autonomous, this is just to prevent the sample from ending
        while (opModeIsActive()) {
            sleep(20);
        } */
        waitForStart();
        // left, fwd, right but half, then score on mid pole
        robot.servo1.setPosition(100);
        robot.moveDirectionBlocks(telemetry,1,"left");
        robot.moveDirectionBlocks(telemetry,1,"forward");
        robot.moveDirectionBlocks(telemetry,0.5,"right");
        robot.moveDirectionBlocks(telemetry, 0.25, "forward");
        //robot.viperSlideEncoderMovements(telemetry,25,0.25, "forward");
        robot.ViperSlide.setPower(0.25);
        sleep(5000);
        robot.ViperSlide.setPower(0);
        robot.servo1.setPosition(0);

        robot.ViperSlide.setPower(-0.25);
        sleep(5500);
        robot.ViperSlide.setPower(0);
        robot.moveDirectionBlocks(telemetry,0.25, "backward");
        robot.moveDirectionBlocks(telemetry, 1,"left");
        robot.moveDirectionBlocks(telemetry, 1, "backward", 3);
        robot.moveDirectionBlocks(telemetry,1,"right", 1);


        //robot.encoderMovements(encoderDist, 1);
        if (position == 1) {
            telemetry.addData("Direction: ", "left");
            robot.moveDirectionBlocks(telemetry, 1, "left", 2);
            telemetry.addData("Step: ", "Step #1 w/offset");
            telemetry.update();
            waitForEncoderComplete();
            robot.moveDirectionBlocks(telemetry, 1, "forward");
            telemetry.addData("Step: ", "Step #2");
            telemetry.update();
            waitForEncoderComplete();
            telemetry.addData("Step: ", "Finished");
            telemetry.update();
        } else if (position == 2) {
            telemetry.addData("Direction: ", "middle");
            robot.moveDirectionBlocks(telemetry, 1, "right",2);
            telemetry.addData("Step: ", "Step #1 w/offset");
            telemetry.update();
            waitForEncoderComplete();
            robot.moveDirectionBlocks(telemetry, 1, "forward");
            telemetry.addData("Step: ", "Step #2");
            telemetry.update();
            waitForEncoderComplete();
            robot.moveDirectionBlocks(telemetry, 1, "forward");
            telemetry.addData("Step: ", "Step #3");
            telemetry.update();
            waitForEncoderComplete();
            robot.moveDirectionBlocks(telemetry, 1, "left",3);
            telemetry.addData("Step: ", "Step #4 w/offset");
            telemetry.update();
            waitForEncoderComplete();
            telemetry.addData("Step: ", "Finished");
            telemetry.update();

        } else if (position == 3) {
            telemetry.addData("Direction: ", "right");
            robot.moveDirectionBlocks(telemetry, 1, "right",2);
            telemetry.addData("Step: ", "Step #1 w/offset");
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
}