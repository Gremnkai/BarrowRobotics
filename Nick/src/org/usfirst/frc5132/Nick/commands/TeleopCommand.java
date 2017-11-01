package org.usfirst.frc5132.Nick.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.VictorSP;

import org.usfirst.frc5132.Nick.Gamepad;
import org.usfirst.frc5132.Nick.Gamepad.GamepadButton;
import org.usfirst.frc5132.Nick.Gamepad.GamepadButton.GamepadAxis;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

public class TeleopCommand extends Command {
	
	double maxSpeed = 0.5;
	double getLeftX;
	double getRightX;
	double getLeftY;
	double getRightY;
	boolean getA;
	boolean tempA;
	boolean tempA2;
	boolean tempX2;
	boolean tempX;
	double gearTemp;
	Gamepad gamepad;
	RobotDrive robotDrive;
	VictorSP kFrontLeftChannel;
	PowerDistributionPanel pdp = new PowerDistributionPanel(0);
	VictorSP kRearLeftChannel;
	VictorSP kFrontRightChannel;
	VictorSP kRearRightChannel;
	CANTalon intakeMotor;
	VictorSP agitator;
	CANTalon winch1;
	CANTalon winch2;
	CANTalon shooter;
	VictorSP gearMotor;
	NetworkTable table;
	double lastPWM = 0;
	AHRS gyro;
	
    public TeleopCommand(Gamepad gamepad, RobotDrive robotDrive, CANTalon intakeMotor, VictorSP agitator, CANTalon winch1, CANTalon winch2, CANTalon shooter, AHRS gyro) {
        this.gamepad = gamepad;
        this.robotDrive = robotDrive;
        this.intakeMotor = intakeMotor;
        this.agitator = agitator;
        this.winch1 = winch1;
        this.winch2 = winch2;
        this.shooter = shooter;
        this.gyro = gyro;
   }
	

public void initialize() {
	table = NetworkTable.getTable("table");
	tempA = false;
	gyro.reset();
	gyro.resetDisplacement();
	gearTemp = 1;
	tempA2 = false;
	tempX2 = false;
	tempX = false;
}
  public void execute() {
/* 	  SmartDashboard.putNumber("PDP Amperage", pdp.getCurrent(12));
 	  double angle = angleGyro.getAngle();
 	  System.out.println(angle);
      SmartDashboard.putNumber("Angle", angle);
*/
      table.putNumber("state", 1);
      double distInInches = table.getNumber("distInInches", 0.0);
      SmartDashboard.putNumber("Distance In Inches", distInInches);
      SmartDashboard.putNumber("Gyro angle", gyro.getAngle());
      SmartDashboard.putNumber("Displacement", gyro.getDisplacementX());
      double getLeftX;
	  double getLeftY;
	  double getRightX;
	  if (-.2 < GamepadAxis.getRightX() && GamepadAxis.getRightX() < .2) {
		  getRightX = 0;
	  }
	  else {
		 getRightX = GamepadAxis.getRightX();
	  }
	  
	  if (-.2 < GamepadAxis.getLeftY() && GamepadAxis.getLeftY() < .2) {
		  getLeftY = 0;
	  }
	  else {
		  getLeftY = GamepadAxis.getLeftY();
	  }
	  if (-.2 < GamepadAxis.getLeftX() && GamepadAxis.getLeftX() < .2) {
		  getLeftX = 0;
	  }
	  else {
		  getLeftX = GamepadAxis.getLeftX();
	  }
     robotDrive.mecanumDrive_Cartesian(getLeftX, getLeftY, getRightX, 0);
     if (GamepadAxis.getLeftTrigger() >= .3) {
    	 winch1.set(-GamepadAxis.getLeftTrigger());
    	 winch2.set(-GamepadAxis.getLeftTrigger());
    	 double current = pdp.getCurrent(12);
    	 SmartDashboard.putNumber("current", current);
     }
    	 
    	 else if(GamepadAxis.getLeftTrigger() < .3) {
    		 winch1.set(0);
    		 winch2.set(0);
    	 }
   
  

     if (GamepadButton.getA() == true) {
    	 if (tempA2 == false) {
    		 intakeMotor.set(.85);
    		 tempA2 = true;
    	 }
    	 else if (tempA2 == true){
    		 intakeMotor.set(0);
    		 tempA2 = false;
    	 }
     }
      if (GamepadButton.getX() == true) {
         SmartDashboard.putNumber("Distance In Inches", distInInches);
         //double heightOfBoiler = 97;
         double distInMeters = ((distInInches + 24.5)*0.0254); 
         /*
         double velocity = -0.069944 * Math.pow(distInMeters,2.0) +  1.2604 * distInMeters + 2.6863;
         double velocity = 0.042602 * Math.pow(distInMeters, 2) + 0.3835 * distInMeters + 5.4207;
         12.5663706144 = 4 times pi
         double rpm =  ((velocity * 60) / ((0.3048) / 12)) / 12.5663706144;
    	 double speed = ((rpm / 440) * 1/12) + .5;
    	   */
         double a = -1.5748 / distInMeters;
         double velocity = Math.sqrt(((9.81 / 2) / a) / Math.cos(80));
         double rpm =  ((velocity * 60) / ((0.3048) / 12)) / 12.5663706144;
    	 double speed = .75;
    	 // TMP changed speed
    	 //if (speed <= .5 && speed > .1 ){
    	 //	 speed = speed + .5;
    	 //}
    	 //else{
    	 //	 speed = .75;
    	 //}
    	 
		/*
		 rpm / 440 * 1/12
         double difference = rpm - shooterEncoder.getRate();
         double averageDiff = ((lastPWM + difference) / 2) * .0000005;
         double speed = averageDiff;
         lastPWM =  difference; 
         */
         shooter.set(speed);
         SmartDashboard.putNumber("Speed", speed);
     	       }
       else {
    	 shooter.set(0);
     }
         
/*         double fpm = velocity * 12.5663706144;
         //fpm stands for feet per minute
         double mpm = fpm * 0.3048;
         //mpm meters per minute
         double mps = mpm / 60;
         //mps meters per second
         
         double angle = Math.atan(Math.pow(velocity, 2.0) + Math.sqrt(Math.pow(velocity, 4.0) - 9.81 * (9.81 * Math.pow(0.4315968, 2.0) + 2 * 1.5748 * 5.92)) / 9.81 * 0.4315968);
         double angleDifference = Math.atan(Math.pow(velocity, 2.0) + Math.sqrt(Math.pow(velocity, 4.0) - 9.81 * (9.81 * Math.pow(0.4315968, 2.0) + 2 * 1.5748 * Math.pow(velocity, 2.0)) / (9.81 * 0.4315968)));
         double finalAngle = 90 - angleDifference;
*/         
         
     
     if (GamepadButton.getRB() == true) {
    	 agitator.set(1);
     }
     else {
    	 agitator.set(0);
     }
   }
protected boolean isFinished() {
	return false;
	}
protected void end() {
		
	}
protected void interupt() {
	
	}
}