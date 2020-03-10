

public class Obstacle extends ArenaItem {
	
	 Obstacle(double xS, double yS, int rad) {

		super(xS,yS,rad);
		this.col = 'o';
		
	}
	 
	 /**
	  * method to draw an obstacle in the arena
	  * @param mc
	  */

	public void drawObstacle(MyCanvas mc) {
		// TODO Auto-generated method stub
		mc.showCircle(this.xPos, this.yPos, this.rad, this.col);
	}
	
	

}
