package uk.ac.reading.agrill;

import java.util.ArrayList;
import java.util.Random;

public class DroneArena {
	double xSize, ySize; // size of arena
	private ArrayList<Drone> allDrones; // array list of all drones in arena
	private ArrayList<ChargePoint> allChargers;
	private ArrayList<Obstacle> allObstacles;

	/**
	 * construct an arena
	 */
	DroneArena() {
		this(500, 400);
	}

	/**
	 * construct arena of size xS by yS
	 * add default starter drones
	 * 
	 * @param xS
	 * @param yS
	 */
	DroneArena(double xS, double yS) {
		xSize = xS;
		ySize = yS;
		allDrones = new ArrayList<Drone>();
		allChargers = new ArrayList<ChargePoint>();
		allObstacles = new ArrayList<Obstacle>();

		
		//clearing all array lists so that no items exist from before
		allDrones.clear();
		allChargers.clear();
		allObstacles.clear();
		
		
		//adding starter items to arena
		allDrones.add(new MedicalDrone(xS / 2, 30, 20, 1));
		allDrones.add(new AttackDrone(40, yS / 2, 20, 250));
		allDrones.add(new CivilianDrone(xS / 3, yS / 3, 20, 90));
		allChargers.add(new ChargePoint(xS, 0, 30));
		allChargers.add(new ChargePoint(xS, yS, 30));
		allChargers.add(new ChargePoint(0, yS, 30));
		allChargers.add(new ChargePoint(0, 0, 30));
	
		
		Random r = new Random();
		int obsX = r.nextInt((int)xS);
		int obsY = r.nextInt((int)yS);
		
		//create new obstacle in random location
		allObstacles.add(new Obstacle(obsX, obsY, 30));

	}

	/**
	 * return arena size in x direction
	 * 
	 * @return xSize
	 */
	public double getXSize() {
		return xSize;
	}

	/**
	 * return arena size in y direction
	 * 
	 * @return ySize
	 */
	public double getYSize() {
		return ySize;
	}

	/**
	 * draw all drones in the arena 
	 * draw all charge points in the arena
	 * 
	 * @param mc
	 */
	public void drawArena(MyCanvas mc) {
		for (Drone d : allDrones) {
			d.drawDrone(mc); // draw all drones
		}

		for (ChargePoint cp : allChargers) { 
			cp.drawCharge(mc); // draw all chargers
		}
		
		for (Obstacle o : allObstacles) {
			o.drawObstacle(mc);
		}
	}

	/**
	 * Checks to see if a drone has 0 health or charge
	 * Removes from arena if so
	 */
	public void checkDrones() {
		//loop through all Drones
		for (int i = 0; i < allDrones.size(); i++) {
			//check to see if Drone needs adjusting
			allDrones.get(i).checkDrone(this);
			
			//check to see if drone has 0 health or charge
			if (allDrones.get(i).health <= 0 || allDrones.get(i).charge <= 0) {
				removeDrone(i);
			}
		}
	}

	/**
	 * removes a drone from the array list
	 */
	public void removeDrone(int indexOfDrone) {
		allDrones.remove(indexOfDrone);
	}

	/**
	 * adjust all drones .. move any moving ones
	 */
	public void adjustDrones() {
		for (Drone d : allDrones)
			d.adjustDrone();
	}

	/**
	 * return list of strings defining each ball
	 * 
	 * @return
	 */
	public ArrayList<String> describeAll() {
		ArrayList<String> ans = new ArrayList<String>(); // set up empty arraylist
		for (Drone d : allDrones) {
			ans.add(d.toString()); // add string defining each ball
		}
		for (ChargePoint c : allChargers) {
			ans.add(c.toString());
		}

		return ans; // return string list

	}

	// NEED TO ADD CHECK DRONE ANGLE

	/**
	 * Check angle of ball ... if hitting wall, rebound; if hitting ball, change
	 * angle
	 * 
	 * @param x     ball x position
	 * @param y     y
	 * @param rad   radius
	 * @param ang   current angle
	 * @param notID identity of ball not to be checked
	 * @return new angle
	 */
	public double CheckDroneAngle(double x, double y, double rad, double ang, int notID) {
		double ans = ang;
		if (x < rad || x > xSize - rad)
			ans = 180 - ans;
		// if ball hit (tried to go through) left or right walls, set mirror angle,
		// being 180-angle
		if (y < rad || y > ySize - rad)
			ans = -ans;
		// if try to go off top or bottom, set mirror angle

		for (Drone d : allDrones) {
			if (d.getID() != notID && d.hitting(x, y, rad)) {
				ans = 180 * Math.atan2(y - d.getY(), x - d.getX()) / Math.PI;
			}
		}
		// check all balls except one with given id
		// if hitting, return angle between the other ball and this one.

		return ans; // return the angle
	}

	/**
	 * check to see if a MedicalDrone has been hit and heals the drone if it has
	 * check to see if touching a charge point, charge if it has
	 * @param target
	 * @return
	 */
	public boolean checkHit(MedicalDrone target) {
		boolean ans = false;

		for (Drone d : allDrones) {
			if (d.hitting(target) && d instanceof CivilianDrone) {
				target.heal(d);
				ans = true;
			}

			for (ChargePoint c : allChargers) {
				if (d.hitting(c.xPos, c.yPos, c.rad)) {
					c.chargeDrone(d);
					ans = true;
				}
			}
			
			for (Obstacle o : allObstacles) {
				if (d.hitting(o.getXPosition(), o.getXPosition(), o.rad)) {
					ans = true;
				}
			}
		}
		return ans;
	}
	
	/**
	 * Check to see if a civilian drone has hit a charger, another drone or obstacle
	 * Change direction if so
	 * @param target
	 * @return
	 */

	public boolean checkHit(CivilianDrone target) {
		boolean ans = false;

		for (Drone d : allDrones) {
			if (d instanceof AttackDrone && d.hitting(target)) {
				ans = true;
			} 
			
			for (ChargePoint c : allChargers) {
				if (d.hitting(c.xPos, c.yPos, c.rad)) {
					c.chargeDrone(d);
					ans = true;
				}
			}
			
			for (Obstacle o : allObstacles) {
				if (d.hitting(o.getXPosition(), o.getXPosition(), o.rad)) {
					ans = true;
				}
			}
		}

		return ans;

	}
	
	/**
	 * Check to see if an attack drone has hit a civilian drone 
	 * And attacks it if it has
	 * @param target
	 * @return
	 */
	public boolean checkHit(AttackDrone target) {
		boolean ans = false;

		for (Drone d : allDrones) {
			// if it's touching another attack drone, just change direction
			if (d instanceof AttackDrone && d.hitting(target)) {
				ans = false;
			}
			// if touching a civilian drone, attack it 
			if (d instanceof CivilianDrone && d.hitting(target)) {
				target.attack(d);
				ans = true;
			}
		}
		return ans;
	}

	// ADD DRONE

	public void addCivilianDrone() {
		allDrones.add(new CivilianDrone(xSize / 2, ySize / 2, 20, 55.00));
	}

	public void addAttackDrone() {
		// TODO Auto-generated method stub
		allDrones.add(new AttackDrone(xSize / 2, ySize / 2, 20, 105));
	}

	public void addMedicalDrone() {
		// TODO Auto-generated method stub
		allDrones.add(new MedicalDrone(xSize / 2, ySize / 2, 20, 200));
	}

}
