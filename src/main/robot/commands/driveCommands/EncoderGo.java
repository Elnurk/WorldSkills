package frc.robot.commands.driveCommands;

//WPI imports
import edu.wpi.first.wpilibj2.command.CommandBase;

//RobotContainer import
import frc.robot.RobotContainer;

//Subsystem imports
import frc.robot.subsystems.DriveBase;;

/**
 * EncoderGo class
 * <p>
 * This class drives to the point by encoders
 */
public class EncoderGo extends CommandBase {
    private double targ;
    private boolean straight;
    // Grab the subsystem instance from RobotContainer
    private static final DriveBase driveBase = RobotContainer.driveBase;
    private boolean finished = false;

    /**
     * Constructor
     */
    public EncoderGo(double targ, boolean straight) {
        addRequirements(driveBase); // Adds the subsystem to the command
        if (straight)
            this.targ = targ / Math.cos(Math.PI / 3) + driveBase.getRightEncoderDistance();
        else
            this.targ = targ + driveBase.getBackEncoderDistance();
        this.straight = straight;
    }

    /**
     * Runs before execute
     */
    @Override
    public void initialize() {

    }

    /**
     * Called continously until command is ended
     */
    @Override
    public void execute() {
        if (!straight) {
            if (driveBase.getBackEncoderDistance() < targ) {
                driveBase.setMovement(0.5, 0);
                while (driveBase.getBackEncoderDistance() < targ) {
                }
            } else {
                driveBase.setMovement(-0.5, 0);
                while (driveBase.getBackEncoderDistance() > targ) {
                }
            }
        } else {
            if (driveBase.getLeftEncoderDistance() < targ) {
                driveBase.setMovement(0, 0.5);
                while (driveBase.getRightEncoderDistance() < targ) {
                }
            } else {
                driveBase.setMovement(0, -0.5);
                while (driveBase.getRightEncoderDistance() > targ) {
                }
            }
        }
        driveBase.setDriverMotorSpeed(0.0, 0.0, 0.0);
        finished = true;
    }

    /**
     * Called when the command is told to end or is interrupted
     */
    @Override
    public void end(boolean interrupted) {
        driveBase.setDriverMotorSpeed(0.0, 0.0, 0.0); // Stop motor
    }

    /**
     * Creates an isFinished condition if needed
     */
    @Override
    public boolean isFinished() {
        return finished;
    }
}