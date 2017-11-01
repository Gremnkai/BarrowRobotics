package org.usfirst.frc5132.Nick.commands.autonomous;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
public class AutoRedRightPos extends Command {
	RobotDrive robotDrive;
	VictorSP kFrontLeftChannel;
	VictorSP kRearLeftChannel;
	VictorSP kFrontRightChannel;
	VictorSP kRearRightChannel;
    NetworkTable table;
	Timer timer = new Timer();

    public AutoRedRightPos(RobotDrive robotDrive) {
    	this.robotDrive = robotDrive;
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
    	/* DONE
    	 * go fwd 7 ft 9 in
    	 * rotate 45 degrese left
    	 * rotate l 180 degrese
    	 * gear vision processing
    	 * HG vision processing
    	 * rotate l 135 degrese
    	 * go fwd 4 ft
    	 */
    	// 7ft 9 in = .444976076555
    	if(timer.get() > 0 && timer.get() <= .4 ){
            robotDrive.mecanumDrive_Polar(1, 0, 0);
    	}
    	else if (timer.get() > .4 && timer.get() < 15){
            robotDrive.mecanumDrive_Polar(0, 0, 0);
    	}
        
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