package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
@Autonomous(name = "rotation test", group = "Auton")
public class IMUTest extends LinearOpMode {
    RobotHardware robot = new RobotHardware();
    DcMotor                 leftMotor, rightMotor;
    BNO055IMU               imu;
    Orientation             lastAngles = new Orientation();
    double                  globalAngle, power = .30, correction;
    boolean                 aButton, bButton, touched;

    public void runOpMode() throws InterruptedException{
        robot.init(hardwareMap);

    }
}
