package uk.ac.reading.agrill;

public class ChargePoint extends ArenaItem {
	protected boolean inUse;
	protected int chargeRemaining;
	static int ChargePointCounter;
	protected int ChargeID;

	/**
	 * Constructor to create a charge point in the arena
	 * @param x
	 * @param y
	 * @param rad
	 */
	public ChargePoint(double x, double y, double rad) {
		super(x, y, rad);
		this.col = 'c';
		setInUse(false);
		chargeRemaining = 2000;
		ChargeID = ChargePointCounter++;
	}

	/**
	 * Will increase the charge of drone d
	 * @param d
	 */
	public void chargeDrone(Drone d) {
		// chargeToGive is the charge required by drone d
		int chargeToGive;

		// check that the drone needs to be healed, the chargepoint has enough charge
		// and is not in use
		if (d.charge < 500 && !(this.chargeRemaining <= 0) && this.inUse == false) {
			
			// set charge point to being in use so that another drone can't use simultaneously
			setInUse(true);

			//assign value to chargeToGive
			chargeToGive = 500 - d.charge;
			
			//charge drone d 
			d.charge += chargeToGive;

			// deduct the charge given to charged drone
			chargeRemaining -= chargeToGive;

		}
		setInUse(false);
	}
	
	/**
	 * Used to draw chargePoint in the canvas
	 * @param mc
	 */
	public void drawCharge(MyCanvas mc) {
		// TODO Auto-generated method stub
		mc.showCircle(this.xPos, this.yPos, this.rad, this.col);
	}

	/**
	 * Method to describe all charging stations in the arena.
	 * 
	 * 
	 */
	public String toString() {
		return "Charging point " + this.ChargeID + " at " + Math.round(xPos) + ", " + Math.round(yPos)
				+ ", Remaining charge: " + this.chargeRemaining;
	}
	
	
	/**
	 * Returns whether or not the charge point in use
	 * @return
	 */
	public boolean isInUse() {
		return inUse;
	}
	
	/**
	 * Used to change whether or not a charge point is in use
	 * @param inUse
	 */
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}

//	public void checkCharge() {
//		// TODO Auto-generated method stub
//		
//	}

}
