package org.firstinspires.ftc.teamcode.unusedprograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotHardware;

//@Autonomous(name = "EncoderTest", group = "Auton")

public class EncoderTest extends OpMode {
    RobotHardware robot = new RobotHardware();

    //@Override whenever you create a method
    @Override
    //What happens when you initialize program
    public void init(){
        //Initialize hardwareMap
        robot.init(hardwareMap);
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();
        robot.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.ViperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("Status", "Running using Encoder");
        telemetry.update();
        robot.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.ViperSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    @Override
    public void init_loop(){

    }

    @Override
    public void start(){
        MyFIRSTJavaOpMode myopmode = new MyFIRSTJavaOpMode();
        telemetry.addData("Status" , "Attempting one rotation of spin");
        myopmode.encoderDrive(1,5,5,5);
        //this.sleep(5000);

        // we have problems with this
        // check between linear op mode and op mode for this
    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {

    }

    public static void main(String[] args) {

    }
}
