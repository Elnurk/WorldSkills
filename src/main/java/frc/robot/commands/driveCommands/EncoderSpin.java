package frc.robot.commands.driveCommands;

//WPI imports
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
//RobotContainer import
import frc.robot.RobotContainer;

//Subsystem imports
import frc.robot.subsystems.DriveBase;;

/**
 * SimpleDrive class
 * <p>
 * This class drives a motor at 50% speed until the command is ended
 */
public class EncoderSpin extends CommandBase
{
    private static final DriveBase driveBase = RobotContainer.driveBase;
    private boolean finished = false;
    private double targ;

    /**
     * Constructor
     */
    public EncoderSpin(double degs)
    {
        addRequirements(driveBase); // Adds the subsystem to the command
        double rads = degs / 180 * Math.PI;
        targ = driveBase.getLeftEncoderDistance() + Constants.robotDiameter * rads;
    }

    /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {

    }

    /**
     * Called continously until command is ended
     */
    @Override
    public void execute()
    {
        if (targ < driveBase.getLeftEncoderDistance())
        {
            driveBase.setDriverMotorSpeed(-1.0, -1.0, -1.0);
            while (targ < driveBase.getLeftEncoderDistance()) {}
        }
        else
        {
            driveBase.setDriverMotorSpeed(1.0, 1.0, 1.0);
            while (targ < driveBase.getLeftEncoderDistance()) {}
        }
        driveBase.setDriverMotorSpeed(0.0, 0.0, 0.0);
        finished = true;
    }

    /**
     * Called when the command is told to end or is interrupted
     */
    @Override
    public void end(boolean interrupted)
    {
        driveBase.setDriverMotorSpeed(0.0, 0.0, 0.0); // Stop motor
    }

    /**
     * Creates an isFinished condition if needed
     */
    @Override
    public boolean isFinished()
    {
        return finished;
    }

}