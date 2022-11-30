package frc.robot.subsystems;

import com.studica.frc.Servo;
import com.studica.frc.TitanQuad;
import com.studica.frc.TitanQuadEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveBase extends SubsystemBase {
    //Motors
    private TitanQuad leftMotor;
    private TitanQuad rightMotor;
    private TitanQuad backMotor;
    private TitanQuad elevatorMotor;
    //Encoder
    private TitanQuadEncoder leftEncoder;
    private TitanQuadEncoder rightEncoder;
    private TitanQuadEncoder backEncoder;
    private TitanQuadEncoder elevatorEncoder;
    //Servos
    private Servo claw;
    private Servo upClaw;
    private Servo rotateClaw;

    public DriveBase () {
        //Motor
        leftMotor = new TitanQuad(Constants.TITAN_ID, Constants.M2);
        rightMotor = new TitanQuad(Constants.TITAN_ID, Constants.M0);
        backMotor = new TitanQuad(Constants.TITAN_ID, Constants.M1);
        elevatorMotor = new TitanQuad(Constants.TITAN_ID, Constants.M3);
        //Encoder
        leftEncoder = new TitanQuadEncoder(leftMotor, Constants.M2, Constants.wheelDistPerTick);
        rightEncoder = new TitanQuadEncoder(rightMotor, Constants.M0, Constants.wheelDistPerTick);
        backEncoder = new TitanQuadEncoder(backMotor, Constants.M1, Constants.wheelDistPerTick);
        elevatorEncoder = new TitanQuadEncoder(elevatorMotor, Constants.M3, Constants.wheelDistPerTick);
        //Servos
        claw = new Servo(Constants.claw);
        upClaw = new Servo(Constants.upClaw);
        rotateClaw = new Servo(Constants.rotateClaw);
    }

    //speed range -1 to 1 (0 stop)
    public void setDriverMotorSpeed(double leftSpeed, double rightSpeed, double backSpeed) {
        leftMotor.set(leftSpeed);
        rightMotor.set(rightSpeed);
        backMotor.set(backSpeed);
    }

    //Distance traveled in mm
    public double getLeftEncoderDistance(){
        return leftEncoder.getEncoderDistance();
    }
    public double getRightEncoderDistance(){
        return rightEncoder.getEncoderDistance();
    }
    public double getBackEncoderDistance(){
        return backEncoder.getEncoderDistance();
    }

    public void resetEncoder() {
        leftEncoder.reset();
        rightEncoder.reset();
        backEncoder.reset();
    }

    public void setClawServoPosition(double degrees){
        claw.setAngle(degrees);
    }

    public void setUpClawServoPosition(double degrees){
        upClaw.setAngle(degrees);
    }

    public void setRotateClawServoPosition(double degrees){
        rotateClaw.setAngle(degrees);
    }

    //speed range -1 to 1 (0 stop)
    public void setElevatorMotorSpeed(double Speed) {
        elevatorMotor.set(Speed);
    }

    //Distance traveled in mm
    public double getElevatorEncoderDistance(){
        return elevatorEncoder.getEncoderDistance();
        //return elevatorEncoder.getRaw();
    }

    public void resetLiftEncoder() {
        elevatorEncoder.reset();
    }

    @Override
    public void periodic(){
    }
}