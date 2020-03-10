package uk.ac.reading.agrill;

import java.util.Random;

public class AttackDrone extends Drone {
	private int damage;
	
	/**
	 * Constructor to make an attack drone
	 * @param x
	 * @param y
	 * @param rad
	 * @param angle
	 */
	public AttackDrone(double x, double y, double rad, double angle) {
		super(x, y, rad, angle);
		// overloading constructor such that each drone has different health
		this.health = 500;
		// sets the attack drone's colour to red
		this.col = 'r';
		droneID = droneCounter++;
		this.charge = 500;
		// gives the attack drone a random damage value
		Random r = new Random();
		this.damage = r.nextInt(40);

	}

	@Override
	protected void adjustDrone() {
		// TODO Auto-generated method stub
		double radAngle = this.angle * Math.PI / 180; // put angle in radians
		this.xPos += 2 * Math.cos(radAngle); // new Xpos
		this.yPos += 2 * Math.sin(radAngle); // new Ypos
	}

	@Override
	protected void checkDrone(DroneArena d) {
		if (!(d.checkHit(this))) {
			this.angle = d.CheckDroneAngle(this.xPos, this.yPos, this.rad, this.angle, this.droneID);
			this.adjustDrone();
		}

	}

	@Override
	protected String getStrType() {
		// TODO Auto-generated method stub
		return "Attack drone: " + this.droneID + " at " + Math.round(xPos) + ", " + Math.round(yPos) + ", Health: "
				+ health + " Charge: " + charge;
	}

	/**
	 * Performs an attack on drone d
	 * @param d
	 */
	public void attack(Drone d) {
		// TODO Auto-generated method stub
		d.health -= this.damage;
		this.charge -= 30;

	}
}
