


package org.usfirst.frc5132.Nick;


import edu.wpi.first.wpilibj.DriverStation;

public class Gamepad {
	public enum GamepadButton {
		A(1),
		B(2),
		X(3),
		Y(4),
		LB(5),
		RB(6),
		Back(7),
		Start(8),
		LStick(9),
		RStick(10);
		
		private byte value;
		GamepadButton(int value) {
			this.value = (byte) value;
		}
		
		public byte value() {
			return this.value;
		}
	public enum GamepadAxis {
		LeftX(0),
		LeftY(1),
		LeftTrigger(2),
		RightTrigger(3),
		RightX(4),
		RightY(5);
		private byte value;
		GamepadAxis(int value) {
			this.value = (byte) value;
		}
		public byte value() {
			return this.value;
			
		}
		public static double getRightX() {
			return driverStation.getStickAxis(port, RightX.value());
		}
		public static boolean getRightTrigger() {
			return driverStation.getStickButton(port, RightTrigger.value());
		}
		public static double getLeftTrigger(){
			return driverStation.getStickAxis(port, LeftTrigger.value());
		}
		public static double getRightY() {
			return driverStation.getStickAxis(port, RightY.value());
		}
		public static double getLeftX() {
			return driverStation.getStickAxis(port, LeftX.value());
		}
		public static double getLeftY() {
			return driverStation.getStickAxis(port, LeftY.value());
		}
}

	public static boolean getA() {
		return driverStation.getStickButton(port, A.value());
	}
	public static boolean getB() {
		return driverStation.getStickButton(port, B.value());
	}
	public static boolean getY() {
		return driverStation.getStickButton(port, Y.value());
	}

	public static boolean getX() {
		// TODO Auto-generated method stub
		return driverStation.getStickButton(port, X.value());
	}

	public static boolean getRB() {
		// TODO Auto-generated method stub
		return driverStation.getStickButton(port, RB.value());
	}

	public static boolean getLB() {
		// TODO Auto-generated method stub
		return driverStation.getStickButton(port, LB.value());
	}
}
	private static int port;
	private static DriverStation driverStation;
	
	Gamepad(int port) {
		Gamepad.port = port;
		driverStation = DriverStation.getInstance();
	}
	

}
