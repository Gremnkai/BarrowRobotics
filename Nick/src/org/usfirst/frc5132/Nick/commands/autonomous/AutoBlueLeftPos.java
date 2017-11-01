package org.usfirst.frc5132.Nick.commands.autonomous;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class AutoBlueLeftPos extends Command {
	RobotDrive robotDrive;
	VictorSP kFrontLeftChannel;
	VictorSP kRearLeftChannel;
	VictorSP kFrontRightChannel;
	VictorSP kRearRightChannel;
    static final double kP = 0.03;
    static final double kI = 0.00;
    static final double kD = 0.00;
    static final double kF = 0.00;
    static final double kToleranceDegrees = 2.0f;

    NetworkTable table;
    AHRS gyro;
    Timer timer = new Timer();

    public AutoBlueLeftPos(RobotDrive robotDrive, AHRS gyro) {
    	this.robotDrive = robotDrive;
    	this.gyro = gyro;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	table = NetworkTable.getTable("table");
    	robotDrive.setSafetyEnabled(false);
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
        //go forward 7 feet 9 in
    	// 7 ft 9 in = .444976076555
   		//rotate left 135 degrees
        //gear vision processing START
   		//TESTME
/*        Timer.delay(2);
        double GpixelsToCenter = table.getNumber("GpixelsToCenter", 0.0);        
        double diffOfArea = table.getNumber("diffOfArea", 0.0);
        double averageArea = table.getNumber("averageArea", 0.0);
        double inchesToTarget = ((10662) / ((averageArea / 10) + 93.041)) + 10.552; 
*/        //use diffOfArea to check if in front of target
        //use GpixelsToCenter to fine tune rotate
        //use averageArea to find distance
        //104 1/2 Inches
        //16' 10 in
        //8' 5" 
//        if (GpixelsToCenter > 10 || GpixelsToCenter < -10) {
//        	robotDrive.mecanumDrive_Polar(0, GpixelsToCenter / 5, 0);
        	// TODO Gpixels to center divided by factor. 5 is not definite.
//        }
//       	while(inchesToTarget > 1){
//       		robotDrive.mecanumDrive_Polar(1, 0, 0);
//       	}      	
        //gear vision processing END
        
        //high goal vision processing START
        //TESTME
/*        table.putNumber("state", 1);
        double distInInches = table.getNumber("distInInches", 0.0);
        double HpixelsToCenter = table.getNumber("hPixelsToCenter", 0.0);
        double heightOfBoiler = 97;
        double distInMeters = ((distInInches + 24.5)*0.0254); //TODO finish calculation that returns speed
        //double velocity = -0.069944 * Math.pow(distInMeters,2.0) +  1.2604 * distInMeters + 2.6863;
        double velocity = 0.042602 * Math.pow(distInMeters, 2) + 0.3835 * distInMeters + 5.4207;
        //double angle = Math.atan(Math.pow(velocity, 2.0) + Math.sqrt(Math.pow(velocity, 4.0) - 9.81 * (9.81 * Math.pow(0.4315968, 2.0) + 2 * 1.5748 * 5.92)) / 9.81 * 0.4315968);
        double angleDifference = Math.atan(Math.pow(velocity, 2.0) + Math.sqrt(Math.pow(velocity, 4.0) - 9.81 * (9.81 * Math.pow(0.4315968, 2.0) + 2 * 1.5748 * Math.pow(velocity, 2.0)) / (9.81 * 0.4315968)));
        double finalAngle = 90 - angleDifference;
*/        //x\ =\ \arctan \left(y^2+-\sqrt{\frac{y^4-9.81\left(9.81\cdot w^2+2\cdot 1.5748\cdot y^2\	right)}{9.81\cdot w}}\right)
        //y=-0.069944x^2+1.2604x+2.6863
        //y=0.56456x + 5.2849
        //high goal vision processing END
        
        //rotate right 135 degrees
        //FIXME
        //robotDrive.mecanumDrive_Polar(0.1, 135, 0);
        //Timer.delay(1);
        
        //go forward 4 feet
        //FIXME
        //robotDrive.mecanumDrive_Polar(1, 180, 0);
   		//Timer.delay(2);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}