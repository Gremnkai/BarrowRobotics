package org.usfirst.frc5132.Nick.commands.autonomous;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class AutoBlueRightPos extends Command {
	RobotDrive robotDrive;
	VictorSP kFrontLeftChannel;
	VictorSP kRearLeftChannel;
	VictorSP kFrontRightChannel;
	VictorSP kRearRightChannel;
    NetworkTable table;
    AHRS gyro;
    Timer timer = new Timer();
    
    public AutoBlueRightPos(RobotDrive robotDrive, AHRS gyro) {
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
/*    	if(timer.get() > 0 && timer.get() <= .4 ){
            robotDrive.mecanumDrive_Polar(1, 0, 0);
    	}
    	else if (timer.get() > .4 && timer.get() < 15){
            robotDrive.mecanumDrive_Polar(0, 0, 0);
    	}
*/
    	if(timer.get() > 0 && timer.get() <= 1.0 ){
			double angle = gyro.getAngle();
			robotDrive.mecanumDrive_Cartesian(0, -0.375, 0, angle);
    	}
    	else if (timer.get() > 1.0 && timer.get() <= 15){
			robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
    	}
    	/* DONE
    	 * go fwrd 7 ft 9 in
    	 * trn r 45 dgr
    	 * gear vision procs
    	 * trn l 45 dgr
    	 * go frd 4 ft
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