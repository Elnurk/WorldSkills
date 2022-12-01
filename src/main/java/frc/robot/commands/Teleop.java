package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveBase;
import frc.robot.gamepad.OI;

public class Teleop extends CommandBase {

    /**
     * Bring in DriveBase and OI
     */
    private static final DriveBase driveBase = RobotContainer.driveBase;
    private static final OI oi = RobotContainer.oi;

    double rightMotor = 0;
    double leftMotor = 0;
    double backMotor = 0;
    double lT, lErr, lP = -0.3, lC = 0;

    boolean balls = true;

    private ShuffleboardTab tab = Shuffleboard.getTab("727Gamers");
    private NetworkTableEntry leftEncoderValue = tab.add("Left Encoder", 0.0).getEntry();
    private NetworkTableEntry rightEncoderValue = tab.add("Right Encoder", 0.0).getEntry();
    private NetworkTableEntry backEncoderValue = tab.add("Back Encoder", 0.0).getEntry();
    private NetworkTableEntry elevatorEncoderValue = tab.add("Elevator Encoder", 0.0).getEntry();

    /**
     * Constructor
     */
    public Teleop() {
        addRequirements(driveBase);
    }

    /**
     * Code here will run once when the command is called for the first time
     */
    @Override
    public void initialize() {
        driveBase.resetEncoder();
        driveBase.resetLiftEncoder();
    }

    /**
     * Code here will run continously every robot loop until the command is stopped
     */
    @Override
    public void execute() {
        /*
         * rErr = rC - rT;
         * rC += rErr * rP;
         * xErr = xC - xT;
         * xC += xErr * xP;
         * if(Math.abs(oi.getRightDriveX()) >= 0.1){//Rotation
         * leftMotor = oi.getRightDriveX();
         * rightMotor = oi.getRightDriveX();
         * backMotor = oi.getRightDriveX();
         * }
         * else if((Math.abs(oi.getLeftDriveY()) >= 0.7) || (Math.abs(xC) >= 0.3)){
         * xT = oi.getLeftDriveY();
         * leftMotor = -xC;
         * rightMotor = xC;
         * backMotor = 0;
         * }
         * else{
         * rT = oi.getLeftDriveX();
         * leftMotor = 0.5 * rC;
         * rightMotor = 0.5 * rC;
         * backMotor = -rC;
         * }
         * driveBase.setDriverMotorSpeed(leftMotor, rightMotor, backMotor);
         */
        if (!balls) {
            if (oi.getDriveAButton()) {
                if (Math.abs(oi.getLeftDriveX()) <= 0.3) {
                    driveBase.setDriverMotorSpeed(-oi.getLeftDriveY(), oi.getLeftDriveY(), 0);
                } else {
                    driveBase.setDriverMotorSpeed(oi.getLeftDriveX() * 0.5, oi.getLeftDriveX() * 0.5,
                            -oi.getLeftDriveX());
                }
            } else if (Math.abs(oi.getRightDriveX()) <= 0.1) {
                double x, y, m;
                x = -oi.getLeftDriveX();
                y = oi.getLeftDriveY();
                m = Math.sqrt(x * x + y * y);
                if (m <= 0.3) {
                    driveBase.setDriverMotorSpeed(0, 0, 0);
                }

                double bM, lM, rM;
                double cAng = Math.atan2(y, x);

                bM = Math.cos(cAng) * m;
                rM = Math.cos(cAng - 2.094) * m;
                lM = Math.cos(cAng - 4.189) * m;
                driveBase.setDriverMotorSpeed(lM, rM, bM);
            } else {
                driveBase.setDriverMotorSpeed(oi.getRightDriveX(), oi.getRightDriveX(), oi.getRightDriveX());
            }
        } else {
            driveBase.setMovement(oi.getLeftDriveX(), oi.getLeftDriveY());
        }

        lErr = lC - lT;
        lC += lErr * lP;
        if (oi.getDriveR1()) {// down
            // lT = -0.4;
            driveBase.setElevatorMotorSpeed(0.6);
        } else if (oi.getDriveR2() >= 0.3) {// up
            // lT = 0.7;
            driveBase.setElevatorMotorSpeed(-0.4);
        } else {
            // lT = 0;
            // lC = 0;
            driveBase.setElevatorMotorSpeed(0);
        }
        // driveBase.setElevatorMotorSpeed(lC);

        if (oi.getDriveL1()) {
            driveBase.setClawServoPosition(230);// close
        } else if (oi.getDriveL2() >= 0.3) {
            driveBase.setClawServoPosition(180);// open
        }

        if (oi.getDriveDPad() == 0) {
            driveBase.setUpClawServoPosition(310);// Up
        } else if (oi.getDriveDPad() == 180) {
            driveBase.setUpClawServoPosition(220);// Down
        }

        if (oi.getDriveDPad() == 90) {
            driveBase.setRotateClawServoPosition(148);// Start
        } else if (oi.getDriveDPad() == 270) {
            driveBase.setRotateClawServoPosition(58);// 90 degree
        }

        leftEncoderValue.setDouble(driveBase.getLeftEncoderDistance());
        rightEncoderValue.setDouble(driveBase.getRightEncoderDistance());
        backEncoderValue.setDouble(driveBase.getBackEncoderDistance());
        elevatorEncoderValue.setDouble(driveBase.getElevatorEncoderDistance());
    }

    @Override
    public void end(boolean interrupted) {
        driveBase.setDriverMotorSpeed(0.0, 0.0, 0.0);
        driveBase.setElevatorMotorSpeed(0.0);
    }

    /**
     * Check to see if command is finished
     */
    @Override
    public boolean isFinished() {
        return false;
    }
}