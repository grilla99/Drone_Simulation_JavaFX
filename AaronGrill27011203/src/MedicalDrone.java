

import java.util.Random;

public class MedicalDrone extends Drone {
	private int lifeToGive;

	public MedicalDrone(double x, double y, double rad, double angle) {
		super(x, y, rad, angle);
		// sets the medical drone's colour to green
		this.col = 'g';
		// overloading constructor such that each drone has different health
		this.health = 500;
		droneID = droneCounter++;
		this.charge = 500;
		// gives the healing drone a random life value
		Random r = new Random();
		this.lifeToGive = r.nextInt(600);

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
	protected String getStrType() {
		// TODO Auto-generated method stub
		return "Medical drone: " + this.droneID + " at " + Math.round(xPos) + ", " + Math.round(yPos) + ", Health: "
				+ health + " Charge: " + charge + " Life: " + lifeToGive;
	}

	public void heal(Drone d) {
		// TODO Auto-generated method stub
		int lifeRequired = 500 - d.health;
		if (lifeRequired > 0) {
			if (lifeRequired < lifeToGive) {
				d.health += lifeRequired;
				lifeToGive -= lifeRequired;
				this.charge -= 30;
			} else if (lifeRequired > lifeToGive) {
				d.health += lifeToGive;
				lifeToGive = 0;
				this.charge -= 30;
			}
		}
	}
}
