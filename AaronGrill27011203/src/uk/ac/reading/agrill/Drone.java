package uk.ac.reading.agrill;

/*
 * Abstract class to handle a drone arena item
 */

/**
 * @author User
 *
 */
abstract class Drone extends ArenaItem {
	protected int health;
	protected int charge;
	protected double angle;
	static int droneCounter = 0;
	protected int droneID;
	
	
	/**
	 * Constructor to set up a drone, inherited by child classes
	 * @param x
	 * @param y
	 * @param rad
	 * @param angle
	 */
	public Drone(double x, double y, double rad, double angle) {
		super(x, y, rad);
		droneID = droneCounter++;
		this.charge = 1000;
		this.angle = angle;
	}

	/**
	 * return x position of drone
	 * 
	 * @return
	 */
	public double getX() {
		return super.getXPosition();
	}

	/**
	 * return y position of drone
	 * 
	 * @return
	 */
	public double getY() {
		return super.getYPosition();
	}

	/**
	 * return radius of drone
	 * 
	 * @return
	 */
	public double getRad() {
		return super.getRadians();
	}

	/**
	 * return ID of drone
	 * 
	 * @return
	 */
	public int getID() {
		return droneID;
	}

	/**
	 * return charge of drone
	 * 
	 * @return
	 */
	public int getCharge() {
		return charge;
	}

	/**
	 * return health of drone
	 * 
	 * @return
	 */
	public int getHealth() {
		return health;
	}
	
	
 public void setXY(double x, double y) {
		xPos = x;
		yPos = y;
	}

	// abstract method as each Drone will need to return a different drone type
	protected abstract String getStrType();

	/**
	 * Returns a string detailing info about drone
	 */
	public String toString() {
		return getStrType();
	}

	/**
	 * Draws drone into arena
	 * @param mc
	 */
	public void drawDrone(MyCanvas mc) {
		mc.showCircle(this.xPos, this.yPos, rad, col);
	}

	/**
	 * abstract method for checking a Drone in arena d
	 * 
	 * @param d
	 */
	protected abstract void checkDrone(DroneArena d);

	/**
	 * abstract method for moving a drone
	 */
	protected abstract void adjustDrone();

	/**
	 * is drone at ox,oy size or hitting this drone
	 * 
	 * @param ox
	 * @param oy
	 * @param or
	 * @return true if hitting
	 */
	public boolean hitting(double ox, double oy, double or) {
		return (ox - xPos) * (ox - xPos) + (oy - yPos) * (oy - yPos) < (or + rad) * (or + rad);
	} // hitting if dist between ball and ox,oy < ist rad + or

	/**
	 * is ball hitting the other ball
	 * 
	 * @param oDrone - the other ball
	 * @return true if hitting
	 */
	public boolean hitting(Drone oDrone) {
		return hitting(oDrone.getX(), oDrone.getY(), oDrone.getRad());
	}

}
