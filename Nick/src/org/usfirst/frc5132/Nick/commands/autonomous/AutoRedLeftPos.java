package org.usfirst.frc5132.Nick.commands.autonomous;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class AutoRedLeftPos extends Command {
	RobotDrive robotDrive;
	VictorSP kFrontLeftChannel;
	VictorSP kRearLeftChannel;
	VictorSP kFrontRightChannel;
	VictorSP kRearRightChannel;
    NetworkTable table;
    Timer timer = new Timer();
    Timer timer2 = new Timer();
    AHRS gyro;

    public AutoRedLeftPos(RobotDrive robotDrive, AHRS gyro) {
    	this.robotDrive = robotDrive;
    	this.gyro = gyro;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	table = NetworkTable.getTable("table");
    	robotDrive.setSafetyEnabled(false);
    	timer.start();
    	gyro.reset();
    	gyro.resetDisplacement();
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
        
    	/* DONE
    	 * go fwrd 7 ft 7 in = 2.311 meters = 1.9779185 seconds
    	 * trn r 60 dgr
    	 * gear vision procs
    	 * trn l 60 dgr
    	 * go frd 4 ft
    	 * 
    	 */
        table.putNumber("state", 2);
        double GpixelsToCenter = table.getNumber("GpixelsToCenter", 0.0);        
        double diffOfArea = table.getNumber("diffOfArea", 0.0);
        double averageArea = table.getNumber("averageArea", 0.0);
        SmartDashboard.putNumber("Gpixelstocenter", GpixelsToCenter);
        SmartDashboard.putNumber("Angle", gyro.getAngle());
    	if(timer.get() < 1.8379185){
			double angle = gyro.getAngle();
			robotDrive.mecanumDrive_Cartesian(0, 0.4, 0, angle);
    	}
    	else if(timer.get() >= 1.8379185){
			robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
    	// 46 INCHES = 1.1684 METERS	    	
			if (gyro.getAngle() > 63){
	    		robotDrive.mecanumDrive_Polar(0, 0, -.375);
	    	}
			else if(gyro.getAngle() < 57){
	    		robotDrive.mecanumDrive_Polar(0, 0, .375);
	    	}
	    	else{
	    		robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
	    		timer2.start();
	    		if(timer2.get() < 1.3){
	    			double angle = gyro.getAngle();
	    			robotDrive.mecanumDrive_Cartesian(0, 0.4, 0, angle);
	    		}
	    		else if(timer2.get() >= 1.3){
		    		robotDrive.mecanumDrive_Polar(0, 0, 0);
	    		} 
	    	 }
    	}
//    	}
//    	else if(gyro.getAngle() <= -47 && gyro.getAngle() >= -42){
            //gear code
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