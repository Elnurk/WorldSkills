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
    double a = 0;

    boolean balls = true;

    // private ShuffleboardTab tab = Shuffleboard.getTab("727Gamers");
    // private NetworkTableEntry leftEncoderValue = tab.add("Left Encoder", 0.0).getEntry();
    // private NetworkTableEntry rightEncoderValue = tab.add("Right Encoder", 0.0).getEntry();
    // private NetworkTableEntry backEncoderValue = tab.add("Back Encoder", 0.0).getEntry();
    // private NetworkTableEntry elevatorEncoderValue = tab.add("Elevator Encoder", 0.0).getEntry();

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
    private double curAngUpClaw = 200.0;

    @Override
    public void execute() {

        if (oi.getDriveAButton()) {
            if (Math.abs(oi.getLeftDriveX()) <= 0.3) {
                driveBase.setDriverMotorSpeed(oi.getLeftDriveY() * 0.5, -oi.getLeftDriveY() * 0.5, 0);
            } else {
                driveBase.setDriverMotorSpeed(oi.getLeftDriveX() * 0.25, oi.getLeftDriveX() * 0.25, -oi.getLeftDriveX() * 0.5);
            }
        }
        else if(Math.abs(oi.getRightDriveX()) >= 0.1){
            driveBase.setDriverMotorSpeed(oi.getRightDriveX(), oi.getRightDriveX(), oi.getRightDriveX());
        }
        else{
            driveBase.setMovement(oi.getLeftDriveX(), oi.getLeftDriveY());
        }


        lErr = lC - lT;
        lC += lErr * lP;
        if (oi.getDriveR1()) {// up
            // lT = -0.4;
            driveBase.setElevatorMotorSpeed(0.66);
            // driveBase.setElevatorMotorSpeed(1.0);
        } else if (oi.getDriveR2() >= 0.3) {// up
            // lT = 0.7;
            driveBase.setElevatorMotorSpeed(-0.43);
            // driveBase.setElevatorMotorSpeed(-1.0);
        } else {
            // lT = 0;
            // lC = 0;
            driveBase.setElevatorMotorSpeed(0);
        }
        // driveBase.setElevatorMotorSpeed(lC);

        if (oi.getDriveL1()) {
            driveBase.setClawServoPosition(235);// close
        } else if (oi.getDriveL2() >= 0.3) {
            driveBase.setClawServoPosition(220);// open
        }

        if (oi.getDriveDPad() == 0) {
            curAngUpClaw += 2.0;
        } else if (oi.getDriveDPad() == 180) {
            curAngUpClaw -= 2.0;
        }
        curAngUpClaw = Math.max(curAngUpClaw, 100.0);
        curAngUpClaw = Math.min(curAngUpClaw, 280.0);
        driveBase.setUpClawServoPosition(curAngUpClaw);

        if (oi.getDriveDPad() == 90) {
            driveBase.setRotateClawServoPosition(148);// Start
        } else if (oi.getDriveDPad() == 270) {
            driveBase.setRotateClawServoPosition(58);// 90 degree
        }
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