package frc.robot;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.Teleop;
import frc.robot.commands.auto.AutoCommand;
import frc.robot.commands.auto.DriveMotor;
import frc.robot.gamepad.OI;
import frc.robot.subsystems.DriveBase;

public class RobotContainer {
  public static DriveBase driveBase;
  public static OI oi;
  
  public static SendableChooser<String> autoChooser;
  public static Map<String, AutoCommand> autoMode = new HashMap<>();

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    driveBase = new DriveBase();
    oi = new OI();
    driveBase.setDefaultCommand(new Teleop());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    String mode = RobotContainer.autoChooser.getSelected();
    SmartDashboard.putString("Chosen Auto Mode", mode);
    return autoMode.getOrDefault(mode, new DriveMotor());
  }
}
