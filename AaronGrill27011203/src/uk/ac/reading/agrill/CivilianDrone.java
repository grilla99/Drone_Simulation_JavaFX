package uk.ac.reading.agrill;

public class CivilianDrone extends Drone {
	private double droneAngle;
	
	/**
	 * Constructor to create a civilian drone to be added to the arena
	 * @param x
	 * @param y
	 * @param rad
	 * @param angle
	 */
	public CivilianDrone(double x, double y, double rad, double angle) {
		super(x, y, rad, angle);
		// sets the medical drone's colour to green
		this.col = 'y';
		// overloading constructor such that each drone has different health
		this.health = 500;
		droneID = droneCounter++;
		this.charge = 100;
	}
	
	/**
	 * See super
	 */
	@Override
	protected void checkDrone(DroneArena d) {
		// TODO Auto-generated method stub
		if (d.checkHit(this)) {
			this.angle = d.CheckDroneAngle(this.xPos, this.yPos, this.rad, this.angle, this.droneID);
			this.adjustDrone();
		} else {
			this.angle = d.CheckDroneAngle(this.xPos, this.yPos, this.rad, this.angle, this.droneID);
			this.adjustDrone();
		}
	}

	@Override
	protected void adjustDrone() {
		// TODO Auto-generated method stub
		double radAngle = this.angle * Math.PI / 180; // put angle in radians
		this.xPos += 2 * Math.cos(radAngle); // new Xpos
		this.yPos += 2 * Math.sin(radAngle); // new Ypos
	}

	@Override
	protected String getStrType() {
		// TODO Auto-generated method stub
		return "Civilian drone: " + this.droneID + " at " + Math.round(xPos) + ", " + Math.round(yPos) + ", Health: "
				+ health + " Charge: " + charge;
	}

}
