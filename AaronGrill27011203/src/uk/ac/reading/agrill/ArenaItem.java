package uk.ac.reading.agrill;

public abstract class ArenaItem {
	protected double xPos;
	protected double yPos;
	protected double rad;
	protected char col;

	/**
	 * Constructor to create an arena item
	 * @param x
	 * @param y
	 * @param r
	 */
	public ArenaItem(double x, double y, double r) {
		this.xPos = x;
		this.yPos = y;
		this.rad = r;
	}
	
	/**
	 * 
	 * @return xPosition of arena item
	 */
	public double getXPosition() {
		return xPos;
	};

	/**
	 * 
	 * @return yPosition of arena item
	 */
	public double getYPosition() {
		return yPos;
	};
	
	/**
	 * 
	 * @return radians of arena item
	 */
	public double getRadians() {
		return rad;
	};
	
	/**
	 * 
	 * @return colour of arena item
	 */
	public char getCol() {
		return col;
	}

}
