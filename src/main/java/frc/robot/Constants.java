package frc.robot;

public final class Constants {
    /* CAN ID */
    public static final int TITAN_ID = 42;

    /*
     * DriveBase
     */
    // Motors
    public static final int M2 = 1; // Right Motor
    public static final int M0 = 2; // Back Motor
    public static final int M1 = 0; // Left Motor
    public static final int M3 = 3; // Elevator Motor

    // Servos
    public static final int claw = 7;// 19
    public static final int upClaw = 9;// 21
    public static final int rotateClaw = 8;// 20

    public static final double wheelDistPerTick = (Math.PI * 2 * 51) / 1464;
    public static final double robotRadius = 190.0;
}
