package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveBase;
import frc.robot.gamepad.GamepadConstants;
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
    private double curAngUpClaw = 300.0;
    private boolean clawAutoMode = true;
    private double clawPosition = 220;

    @Override
    public void execute() {

        if (oi.getDriveAButton()) {
            if (Math.pow(Math.abs(oi.getLeftDriveY()), 2) >= 0.6) {
                // driveBase.setDriverMotorSpeed(oi.getLeftDriveY() * 0.5, -oi.getLeftDriveY() * 0.5, 0);
                // driveBase.setMovement(0, Math.pow(Math.abs(oi.getLeftDriveY()), 2) * (oi.getLeftDriveY() < 0.0 ? -1.0 : 1.0));
                driveBase.setDriverMotorSpeed(Math.pow(Math.abs(oi.getLeftDriveY()), 2) * (oi.getLeftDriveY() < 0.0 ? -0.5 : 0.5), Math.pow(Math.abs(oi.getLeftDriveY()), 2) * (oi.getLeftDriveY() < 0.0 ? 0.5 : -0.5), 0);
            } else
            if (Math.pow(Math.abs(oi.getLeftDriveY()), 2) >= 0.3) {
                // driveBase.setDriverMotorSpeed(oi.getLeftDriveY() * 0.5, -oi.getLeftDriveY() * 0.5, 0);
                // driveBase.setMovement(0, Math.pow(Math.abs(oi.getLeftDriveY()), 2) * (oi.getLeftDriveY() < 0.0 ? -1.0 : 1.0));
                driveBase.setDriverMotorSpeed(Math.pow(Math.abs(oi.getLeftDriveY()), 2) * (oi.getLeftDriveY() < 0.0 ? -0.4 : 0.4), Math.pow(Math.abs(oi.getLeftDriveY()), 2) * (oi.getLeftDriveY() < 0.0 ? 0.5 : -0.5), 0);
            } else
            if (Math.pow(Math.abs(oi.getLeftDriveX()), 2) >= 0.4) {
                // driveBase.setMovement(Math.pow(Math.abs(oi.getLeftDriveX()), 2) * (oi.getLeftDriveX() < 0.0 ? -1.0 : 1.0), 0);
                // driveBase.setDriverMotorSpeed(oi.getLeftDriveX() * 0.25, oi.getLeftDriveX() * 0.25, -oi.getLeftDriveX() * 0.5);
                driveBase.setDriverMotorSpeed(Math.pow(Math.abs(oi.getLeftDriveX()), 2) * (oi.getLeftDriveX() < 0.0 ? -0.25 : 0.25), Math.pow(Math.abs(oi.getLeftDriveX()), 2) * (oi.getLeftDriveX() < 0.0 ? -0.25 : 0.25), Math.pow(Math.abs(oi.getLeftDriveX()), 2) * (oi.getLeftDriveX() < 0.0 ? 0.5 : -0.5));
            } else {
                driveBase.setMovement(0, 0);
            }
        }
        else if(oi.drivePad.getRawButton(GamepadConstants.Y_BUTTON))
        {
            driveBase.setDriverMotorSpeed(1.0, 1.0, 1.0);
        }
        else if(Math.abs(oi.getRightDriveX()) >= 0.1){
            driveBase.setDriverMotorSpeed(0.6 * (oi.getRightDriveX() > 0.0 ? 1.0 : 1.0) * Math.pow(oi.getRightDriveX(), 3), 0.6 * (oi.getRightDriveX() > 0.0 ? 1.0 : 1.0) * Math.pow(oi.getRightDriveX(), 3), 0.6 * (oi.getRightDriveX() > 0.0 ? 1.0 : 1.0) * Math.pow(oi.getRightDriveX(), 3));
        }
        else{
            driveBase.setMovement(oi.getLeftDriveX(), oi.getLeftDriveY());
        }

        lErr = lC - lT;
        lC += lErr * lP;
        if (oi.getDriveR1()) {// up
            // lT = -0.4;
            driveBase.setElevatorMotorSpeed(0.75);
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
            curAngUpClaw += 2.0;
        } else if (oi.getDriveL2() >= 0.3) {
            curAngUpClaw -= 2.0;
        }

        curAngUpClaw = Math.max(curAngUpClaw, 120.0);
        curAngUpClaw = Math.min(curAngUpClaw, 300.0);
        driveBase.setUpClawServoPosition(curAngUpClaw);

        if (clawAutoMode)
        {
            if (oi.drivePad.getRawButtonPressed(GamepadConstants.B_BUTTON)) {
                clawPosition = (clawPosition == 235 ? 218 : 235);
            }
            //     clawPosition = 235;
            // } else if (oi.getDriveDPad() == 0) {
            //     clawPosition = 218;
            // }
        } else
        {
            if (oi.getDriveDPad() == 180) {
                clawPosition += 2;
            } else if (oi.getDriveDPad() == 0) {
                clawPosition -= 2;
            }
            clawPosition = Math.max(185, clawPosition);
            clawPosition = Math.min(235, clawPosition);
        }
        driveBase.setClawServoPosition(clawPosition);
        if (oi.drivePad.getRawButtonPressed(GamepadConstants.X_BUTTON))
        {
            clawAutoMode = !clawAutoMode;
            if (clawAutoMode)
            {
                driveBase.setClawServoPosition(235); // open
            }
        }

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