package org.usfirst.frc5132.Nick;

import org.usfirst.frc5132.Nick.commands.autonomous.AutoBlueLeftPos;
import org.usfirst.frc5132.Nick.commands.autonomous.AutoBlueMiddlePos;
import org.usfirst.frc5132.Nick.commands.autonomous.AutoBlueRightPos;
import org.usfirst.frc5132.Nick.commands.autonomous.AutoNone;
import org.usfirst.frc5132.Nick.commands.autonomous.AutoRedLeftPos;
import org.usfirst.frc5132.Nick.commands.autonomous.AutoRedMiddlePos;
import org.usfirst.frc5132.Nick.commands.autonomous.AutoRedRightPos;
import org.usfirst.frc5132.Nick.commands.AutonomousCommand;
import org.usfirst.frc5132.Nick.commands.TeleopCommand;
import org.usfirst.frc5132.Nick.OI;
import org.usfirst.frc5132.Nick.Gamepad;
import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
0 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot implements PIDOutput {
	
	
	
	public static OI oi;
	// Define all physical objects and commands on the robot here
	Gamepad gamepad = new Gamepad(0);
	VictorSP kFrontLeftChannel = new VictorSP(0);
	VictorSP kRearLeftChannel = new VictorSP(1);
	VictorSP kRearRightChannel = new VictorSP(2);
	VictorSP kFrontRightChannel = new VictorSP(3);
	VictorSP agitator = new VictorSP(4);
	CANTalon shooter = new CANTalon(0);
	CANTalon winch1 = new CANTalon(1);
	CANTalon winch2 = new CANTalon(2);
	CANTalon intakeMotor = new CANTalon(3);
	AHRS gyro = new AHRS(SPI.Port.kMXP);
    double rotateToAngleRate;
    static final double kP = 0.03;
    static final double kI = 0.00;
    static final double kD = 0.00;
    static final double kF = 0.00;
    static final double kToleranceDegrees = 2.0f;
	PowerDistributionPanel pdp = new PowerDistributionPanel(0);
	RobotDrive robotDrive = new RobotDrive(kFrontLeftChannel, kRearLeftChannel, kFrontRightChannel, kRearRightChannel);
	TeleopCommand teleopCommand = new TeleopCommand(gamepad, robotDrive, intakeMotor, agitator, winch1, winch2, shooter, gyro);
	int autoLoopCounter;
    Command autonomousCommand = new AutonomousCommand(robotDrive, intakeMotor);
    SendableChooser<Command> autoChooser;
    NetworkTable table;
		public void robotInit() {
		robotDrive.setInvertedMotor(MotorType.kFrontLeft, true); 
		robotDrive.setInvertedMotor(MotorType.kRearLeft, true);
		robotDrive.setExpiration(0.03);
    	table = NetworkTable.getTable("table");
    	SmartDashboard.putString("Drive base", "Move left stick foward/back to drive foward/back. Move left stick left/right to strafe. Move right stick left/right to turn left/right");
    	SmartDashboard.putString("Intake", "A - Press once and it toggles. Do not press again unless you want to turn it off. Do not hold.");
    	SmartDashboard.putString("Winch", "Left Trigger - Hold it down. ");
    	SmartDashboard.putString("Shooter", "X - Hold it down. Wait for it to rev up then start agitator. Keep holding it down until all the balls launch.");
    	SmartDashboard.putString("Agitator", "Right Bumper - Hold down after shooter revs up to feed balls.");
    	CameraServer server = CameraServer.getInstance();
    	server.startAutomaticCapture();
        /*   	
 		ultrasonic.setAutomaticMode(true);	 
    	int heightInPixels = table.getNumber("heightInPixels", -1);
    	int distToCenter = table.getNumber("heightInPixels", 0);
    	table.putBool("killPs", TRUE);
    	// NOTE the autonomous scripts are under Nick.commands.autonomous
    	*/ 
    	 autoChooser = new SendableChooser();
    	 autoChooser.addObject("Red Left Pos", new AutoRedLeftPos(robotDrive, gyro)); //add this option to smartdashboard
    	 autoChooser.addObject("Red Middle Pos", new AutoRedMiddlePos(robotDrive, gyro));
    	 autoChooser.addObject("Red Right Pos", new AutoRedRightPos(robotDrive));
    	 autoChooser.addDefault("None", new AutoNone(robotDrive)); //add this option to smartdashboard and make default
    	 autoChooser.addObject("Blue Left Pos", new AutoBlueLeftPos(robotDrive, gyro));
    	 autoChooser.addObject("Blue Middle Pos", new AutoBlueMiddlePos(robotDrive, gyro));
    	 autoChooser.addObject("Blue Right Pos", new AutoBlueRightPos(robotDrive, gyro));
    	 SmartDashboard.putData("start position", autoChooser); // show options on smartdashboard
		}
    
    public void autonomousPerodic() {
    	Scheduler.getInstance().run();
    }
    
    public void disabledPeriodic() {
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
    	robotDrive.setSafetyEnabled(false);
    	autonomousCommand = (Command) autoChooser.getSelected(); // returns what the driverstation selected
        if (autonomousCommand != null) autonomousCommand.start(); // put the autonomous that the driver selected into scheduler
    }

    public void autonomousPeriodic() {
    	/* 
    	0= wait, 1= high goal, 2= gear, other= kill
    	table.putNumber("state", 1);  
        gear code
        table.putNumber("state", 2);
        double GpixelsToCenter = table.getNumber("GpixelsToCenter", 0.0);        
        double diffOfArea = table.getNumber("diffOfArea", 0.0);
        double averageArea = table.getNumber("averageArea", 0.0);
        high goal
        table.putNumber("state", 1);
        double distInInches = table.getNumber("distInInches", 0.0);
        double HpixelsToCenter = table.getNumber("hPixelsToCenter", 0.0);
        */      
        
        Scheduler.getInstance().run();// run autonomous
        autonomousCommand.start();
    }

    public void teleopInit() {
        table.putNumber("state", 1);
        if (autonomousCommand != null) autonomousCommand.cancel(); //stop autonomous
    	robotDrive.setSafetyEnabled(true);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        teleopCommand.start();
    }

    /**000
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
  	  
        LiveWindow.run();
    }

	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		
	}
}