package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "TeleOptest", group = "test")

public class TeleOpTest {
    //Initialize RobotHardware
    RobotHardware robot = new RobotHardware();
    @Override
    public void init()
    {
        robot.init(hardWareMap);
        telemetry.addData("Status", readyMessage);
        telemtry.update();
    }
}
