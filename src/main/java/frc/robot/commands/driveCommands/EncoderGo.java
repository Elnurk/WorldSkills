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
public class EncoderGo extends CommandBase
{
    private double targx, targy;
    //Grab the subsystem instance from RobotContainer
    private static final DriveBase driveBase = RobotContainer.driveBase;
    private boolean finished = false;

    /**
     * Constructor
     */
    public EncoderGo(double targx, double targy)
    {
        addRequirements(driveBase); // Adds the subsystem to the command
        this.targx = targx + driveBase.getBackEncoderDistance();
        this.targy = targy + driveBase.getLeftEncoderDistance() / Math.cos(0.524);
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
        if (targx < driveBase.getBackEncoderDistance())
        {
            // maybe 0.433 instead of 0.5
            driveBase.setDriverMotorSpeed(0.5, 0.5, -1.0);
            while (targx < driveBase.getBackEncoderDistance()) {}
        }
        else
        {
            driveBase.setDriverMotorSpeed(-0.5, -0.5, 1.0);
            while (targx > driveBase.getBackEncoderDistance()) {}
        }
        driveBase.setDriverMotorSpeed(0.0, 0.0, 0.0);
        if (targy < driveBase.getLeftEncoderDistance())
        {
            driveBase.setDriverMotorSpeed(-1.0, 1.0, 0.0);
            while (targy < driveBase.getLeftEncoderDistance()) {}
        }
        else
        {
            driveBase.setDriverMotorSpeed(1.0, -1.0, 0.0);
            while (targy > driveBase.getLeftEncoderDistance()) {}
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