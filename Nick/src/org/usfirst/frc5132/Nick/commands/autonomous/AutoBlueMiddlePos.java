package org.usfirst.frc5132.Nick.commands.autonomous;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoBlueMiddlePos extends Command {
	RobotDrive robotDrive;
	VictorSP kFrontLeftChannel;
	VictorSP kRearLeftChannel;
	VictorSP kFrontRightChannel;
	VictorSP kRearRightChannel;
    NetworkTable table;
    Timer timer = new Timer();
    AHRS gyro;

    public AutoBlueMiddlePos(RobotDrive robotDrive, AHRS gyro) {
    	this.robotDrive = robotDrive;
    	this.gyro = gyro;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	table = NetworkTable.getTable("table");
    	robotDrive.setSafetyEnabled(false);
    	timer.start();
    	gyro.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/* 0= wait, 1= high goal, 2= gear, other= kill
        //gear code
        table.putNumber("state", 2);
        double GpixelsToCenter = table.getNumber("GpixelsToCenter", 0.0);        
        double diffOfArea = table.getNumber("diffOfArea", 0.0);
        double averageArea = table.getNumber("averageArea", 0.0);
        //high goal
        table.putNumber("state", 1);
        double distInInches = table.getNumber("distInInches", 0.0);
      double HpixelsToCenter = table.getNumber("hPixelsToCenter", 0.0);
      */
    	
    	if(timer.get() > 0 && timer.get() <= 1.9){
			double angle = gyro.getAngle();
			SmartDashboard.putNumber("Gyro Angle", angle);
			robotDrive.mecanumDrive_Cartesian(0, 0.375, 0, angle);
    	}
    	else if (timer.get() > 1.9 && timer.get() <= 15){
			robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
    	}
    	/* DONE
    	 * go fwd 4 ft 
    	 * rotate 180 degrese l
    	 * gear vision processing
    	 * rotate 65 degrese r
    	 * HG vision processing
    	 * rotate 25 degrese r
    	 * go fwd 3 ft
    	 * rotate 90 degrese r
    	 * go fwd 7 ft
    	 * 
    	 */
    	
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