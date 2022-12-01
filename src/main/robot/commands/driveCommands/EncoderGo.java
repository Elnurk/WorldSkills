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
    private double targx;
    //Grab the subsystem instance from RobotContainer
    private static final DriveBase driveBase = RobotContainer.driveBase;
    private boolean finished = false;
    private double xSpeed, ySpeed;

    /**
     * Constructor
     */
    public EncoderGo(double targx, double targy)
    {
        addRequirements(driveBase); // Adds the subsystem to the command
        double maxTarg = Math.max(targx, targy);
        xSpeed = targx / maxTarg;
        ySpeed = targy / maxTarg;
        this.targx = targx + driveBase.getBackEncoderDistance();
        // this.targy = targy + driveBase.getLeftEncoderDistance();
    }

    /**
     * Runs before execute
     */
    @Override
    public void initialize()
    {
        while((driveBase.getBackEncoderDistance() != 0) && (driveBase.getRightEncoderDistance() != 0) && (driveBase.getLeftEncoderDistance() != 0)){
            driveBase.resetEncoder();
        }
    }

    /**
     * Called continously until command is ended
     */
    @Override
    public void execute()
    {
        driveBase.setMovement(xSpeed, ySpeed);
        if (driveBase.getBackEncoderDistance() < targx)
        {
            while (driveBase.getBackEncoderDistance() < targx) {}
        }
        else
        {
            while (driveBase.getBackEncoderDistance() < targx) {}
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