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
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Autonomous(name = "AprilTagRighAllianceMorePoints", group = "Auton")
public class AprilTagRighAllianceMorePoints extends LinearOpMode {
    OpenCvCamera camera;
    //Extra line
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
        /*
        robot.servo1.setPosition(100);
        robot.moveDirectionBlocks(telemetry,1,"left",4.5, 0.6);
        waitForEncoderComplete();
        robot.moveDirectionBlocks(telemetry,1,"forward",1, 1);
        waitForEncoderComplete();
        robot.moveDirectionBlocks(telemetry,0.5,"right",1.5, 1);
        waitForEncoderComplete();
        //robot.viperSlideEncoderMovements(telemetry,25,0.25, "forward");
        robot.ViperSlide.setPower(0.5);
        sleep(3500);
        robot.ViperSlide.setPower(0);
        sleep(1000);
        robot.moveDirectionBlocks(telemetry, 0, "forward", 4);
        waitForEncoderComplete();
        robot.servo1.setPosition(0);
        sleep(1000);
        sleep(4000);
        robot.ViperSlide.setPower(0);
        robot.moveDirectionBlocks(telemetry,0, "backward",4);
        waitForEncoderComplete();
        robot.moveDirectionBlocks(telemetry, 0.5,"left",0,1);
        waitForEncoderComplete();
        robot.moveDirectionBlocks(telemetry, 1, "backward", 3);
        waitForEncoderComplete();
        robot.moveDirectionBlocks(telemetry,1,"right", 2,0.75);
        waitForEncoderComplete();
        telemetry.addData("Status", "Viper Slide finished");

//
//
         */
        //robot.encoderMovements(encoderDist, 1);
        if (position == 1) {
            robot.servo1.setPosition(100);
            sleep(1000);
            robot.moveDirectionBlocksMAX(telemetry, 1.03, "backward", 0, 0.5);
            telemetry.addData("Direction: ", "left");
            telemetry.addData("Step: ", "Step #1 w/offset");
            telemetry.update();
            waitForEncoderComplete();
            robot.moveDirectionBlocksMAX(telemetry, 1.855, "left", 0, 0.5);
            telemetry.addData("Step: ", "Step #2");
            telemetry.update();
            waitForEncoderComplete();
            telemetry.addData("Step: ", "Finished");
            telemetry.update();
            robot.viperSlideEncoderMovements(telemetry, 29, 0.8, "forward");
            telemetry.addData("Step:", "raised viperslide");
            telemetry.update();
            waitForEncoderComplete();
            robot.moveDirectionBlocksMAX(telemetry, 0, "forward", 3,0.3);
            telemetry.addData("Step: ", "moved forward");
            telemetry.update();
            waitForEncoderComplete();
            robot.viperSlideEncoderMovements(telemetry, 6.5, 0.5, "backward");
            telemetry.addData("Step:", "raised viperslide");
            telemetry.update();
            waitForEncoderComplete();
            sleep(1000);
            robot.servo1.setPosition(0);
            sleep(1000);
            telemetry.addData("opened servo", "dropping");
            telemetry.update();
            sleep(1000);
            robot.moveDirectionBlocksMAX(telemetry, 0, "backward", 3,0.3);
            waitForEncoderComplete();
            robot.viperSlideEncoderMovements(telemetry, 6, 0.5, "backward");
            waitForEncoderComplete();
            robot.moveDirectionBlocksMAX(telemetry, 0.6, "right",0,0.8);
            waitForEncoderComplete();
        }
        else if (position == 2) {
            robot.servo1.setPosition(100);
            sleep(1000);
            robot.viperSlideEncoderMovements(telemetry, 6,0.5, "forward");
            waitForEncoderComplete();
            robot.moveDirectionBlocks(telemetry, 2.3, "left",0.5);
            telemetry.addData("Step: ", "Step #2 and 3");
            telemetry.update();
            waitForEncoderComplete();
            robot.moveDirectionBlocks(telemetry, 1, "right",1);
            telemetry.addData("Step: ", "Step #4 w/offset");
            telemetry.update();
            waitForEncoderComplete();
            robot.moveDirectionBlocks(telemetry, 1, "backward", 1);
            telemetry.addData("Step:", "Completed");
            telemetry.update();
            waitForEncoderComplete();
            robot.viperSlideEncoderMovements(telemetry, 23, 0.5, "forward");
            telemetry.addData("Step:", "brought up");
            telemetry.update();
            waitForEncoderComplete();
            robot.moveDirectionBlocks(telemetry, 0.53, "left", 1);
            telemetry.addData("Step:", "Completed");
            telemetry.update();
            waitForEncoderComplete();
            robot.moveDirectionBlocks(telemetry, 0, "forward" , 2.5);
            telemetry.addData("Step: ", "Finished");
            telemetry.update();
            waitForEncoderComplete();
            sleep(1000);
            robot.servo1.setPosition(0);
            sleep(1000);
            robot.encoderMovements(telemetry, 6.5, 0.5, "backward");
            telemetry.addData("Step:", "Bringing down");
            telemetry.update();
            waitForEncoderComplete();
            robot.servo1.setPosition(95);
            telemetry.addData("Step:", "closed servo");
            telemetry.update();
            robot.moveDirectionBlocks(telemetry, 0.43, "right", 1);
            telemetry.addData("Step:", "completed");
            telemetry.update();
            waitForEncoderComplete();
            robot.moveDirectionBlocks(telemetry, 1, "forward", 1);
            telemetry.addData("Step:", "parked");
            telemetry.update();
            waitForEncoderComplete();
        }
        else if (position == 3) {
            robot.servo1.setPosition(100);
            sleep(1000);
            telemetry.addData("Direction: ", "right");
            robot.moveDirectionBlocks(telemetry, 0.8, "forward",2);
            telemetry.addData("Step: ", "Step #1 w/offset");
            telemetry.update();
            waitForEncoderComplete();
            robot.moveDirectionBlocks(telemetry, 1.3, "left",0);
            telemetry.addData("Step: ", "Step #2");
            telemetry.update();
            waitForEncoderComplete();
            telemetry.addData("Step: ", "Finished");
            telemetry.update();
            robot.moveDirectionBlocksMAX(telemetry, 2, "backward", 1,0.6);
            telemetry.addData("Step:", "moved");
            telemetry.update();
            waitForEncoderComplete();
            robot.viperSlideEncoderMovements(telemetry, 30, 0.5, "forward");
            telemetry.addData("moving up", "completed");
            telemetry.update();
            waitForEncoderComplete();
            robot.moveDirectionBlocks(telemetry, 0.5, "left", 1);
            telemetry.addData("Step:", "moved to pole");
            telemetry.update();
            waitForEncoderComplete();
            robot.moveDirectionBlocks(telemetry, 0, "forward", 3.7);
            telemetry.addData("Step:", "moved forward");
            telemetry.update();
            waitForEncoderComplete();
            robot.viperSlideEncoderMovements(telemetry, 6.5, 0.5, "backward");
            waitForEncoderComplete();
            sleep(500);
            //
            robot.servo1.setPosition(0);
            sleep(500);
            telemetry.addData("Step", "opened servo");
            telemetry.update();
            robot.moveDirectionBlocks(telemetry, 0, "backward", 3.5);
            telemetry.addData("Step:", "moved backward");
            telemetry.update();
            waitForEncoderComplete();
            robot.servo1.setPosition(100);
            telemetry.addData("Step:", "closed servo");
            telemetry.update();
            waitForEncoderComplete();
            robot.moveDirectionBlocks(telemetry, 0.5, "right", 1);
            telemetry.addData("Step", "completed");
            telemetry.update();
            waitForEncoderComplete();
            robot.moveDirectionBlocks(telemetry, 1, "forward", 1.5);
            telemetry.addData("Step:", "parked");
            telemetry.update();
            waitForEncoderComplete();
            robot.viperSlideEncoderMovements(telemetry, 6, 0.5, "backward");
            waitForEncoderComplete();
            robot.moveDirectionBlocks(telemetry,0.9,"forward", 0);
            waitForEncoderComplete();
        }
//otters are bad seals are better

    }
}