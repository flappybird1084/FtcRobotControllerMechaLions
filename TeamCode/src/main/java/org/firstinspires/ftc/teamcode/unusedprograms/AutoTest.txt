package org.firstinspires.ftc.teamcode.unusedprograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotHardware;

//@Autonomous(name = "AutoTest", group = "Auton")

public class AutoTest extends LinearOpMode {
    RobotHardware robot = new RobotHardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        robot.leftFront.setPower(1);
        robot.leftBack.setPower(1);
        robot.rightFront.setPower(1);
        robot.rightBack.setPower(1);
        sleep(1000);

        robot.leftFront.setPower(-.5);
        robot.leftBack.setPower(.5);
        robot.rightFront.setPower(.5);
        robot.rightBack.setPower(-.5);
        sleep(750);

        robot.leftFront.setPower(1);
        robot.leftBack.setPower(1);
        robot.rightFront.setPower(1);
        robot.rightBack.setPower(1);
        sleep(1000);

        robot.zero();

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }

}
