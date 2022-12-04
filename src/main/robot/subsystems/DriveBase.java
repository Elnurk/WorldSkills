package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import com.studica.frc.Servo;
import com.studica.frc.TitanQuad;
import com.studica.frc.TitanQuadEncoder;
import com.studica.frc.Cobra;

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
    //Sensors
    private Cobra cobra;

    private DigitalOutput greenLed;

    private ShuffleboardTab tab = Shuffleboard.getTab("727Gamers");
    private NetworkTableEntry leftEncoderValue = tab.add("Left Encoder", 0.0).getEntry();
    private NetworkTableEntry rightEncoderValue = tab.add("Right Encoder", 0.0).getEntry();
    private NetworkTableEntry backEncoderValue = tab.add("Back Encoder", 0.0).getEntry();
    private NetworkTableEntry leftPowerValue = tab.add("leftPowerValue", 0.0).getEntry();
    private NetworkTableEntry rightPowerValue = tab.add("rightPowerValue", 0.0).getEntry();
    private NetworkTableEntry backPowerValue = tab.add("backPowerValue", 0.0).getEntry();
    private NetworkTableEntry elevatorEncoderValue = tab.add("Elevator Encoder", 0.0).getEntry();
    private NetworkTableEntry cobraValue = tab.add("CobraValue", 0.0).getEntry();



    double a = 0;

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
        //Sensors
        cobra = new Cobra(3.3F);

        greenLed = new DigitalOutput(1);
    }

    //speed range -1 to 1 (0 stop)
    public void setDriverMotorSpeed(double leftSpeed, double rightSpeed, double backSpeed) {
        leftMotor.set(leftSpeed);
        rightMotor.set(rightSpeed);
        backMotor.set(backSpeed);
    }
    
    double lSpeed, rSpeed, bSpeed;
    public void setMovement(double x, double y)
    {
        lSpeed = y / Math.cos(Math.PI / 3.0) * 0.5;
        rSpeed = -y / Math.cos(Math.PI / 3.0) * 0.5;
        bSpeed = -x;
        lSpeed += 0.5 * x;
        rSpeed += 0.5 * x;
        double maxSpeed = Math.max(Math.abs(bSpeed), Math.max(Math.abs(lSpeed), Math.abs(rSpeed)));
        if (maxSpeed > 1.0)
        {
            lSpeed /= maxSpeed;
            rSpeed /= maxSpeed;
            bSpeed /= maxSpeed;
        }
        setDriverMotorSpeed(lSpeed, rSpeed, bSpeed);
    }

    public void setRotation(int dir)
    {
        if (dir == 1)
            setDriverMotorSpeed(1.0, 1.0, 1.0);
        else
           setDriverMotorSpeed(-1.0, -1.0, -1.0);
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
        //return elevatorEncoder.getEncoderDistance();
        return elevatorEncoder.getRaw();
    }

    public void resetLiftEncoder() {
        elevatorEncoder.reset();
    }

    public double getCobraValue(){
        return cobra.getRawValue(I2C.Port.kMXP.value);
    }

    public void ledControl(boolean ON){
        greenLed.set(ON);
    }

    @Override
    public void periodic(){
        a += 1;
        leftEncoderValue.setDouble(getLeftEncoderDistance());
        rightEncoderValue.setDouble(getRightEncoderDistance());
        backEncoderValue.setDouble(getBackEncoderDistance());
        elevatorEncoderValue.setDouble(getElevatorEncoderDistance());
        leftPowerValue.setDouble(lSpeed);
        rightPowerValue.setDouble(rSpeed);
        backPowerValue.setDouble(bSpeed);
        cobraValue.setDouble(getCobraValue());
    }
}