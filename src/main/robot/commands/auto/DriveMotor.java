package frc.robot.commands.auto;

import frc.robot.commands.driveCommands.EncoderGo;
// import the SimpleDrive command
import frc.robot.commands.driveCommands.SimpleDrive;

/**
 * DriveMotor class
 * <p>
 * This class creates the inline auto command to drive the motor
 */
public class DriveMotor extends AutoCommand
{
    /**
     * Constructor
     */
    public DriveMotor()
    {
        /**
         * Calls the SimpleDrive command and adds a 5 second timeout
         * When the timeout is complete it will call the end() method in the SimpleDrive command
         */
        super(new EncoderGo(50, 100).withTimeout(5), new SimpleDrive().withTimeout(5));
    }
}