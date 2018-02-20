import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Random;



/*
class StateAction {
	boolean action;
	int birdY;
	double birdGravity;
	int tube1X;
	int tube1Y;
	int tube2Y;

	public StateAction(Model m, boolean setAction) {

		this.action = setAction; // Consider that the default action is no flap.
		this.birdY = (m.bird.y > 450 || m.bird.y <10 ? 0: m.bird.y/10);
		this.birdGravity = m.bird.vert_vel;
		Tube t;
		int init = m.tubes.size();
	//	System.out.println("ArrayList Size is :"+ init );
		int count1 = 0;
		while (count1 < init) {
			t = m.tubes.get(count1);
			if (t.x >= 100) {
				tube1X = t.x/5;
			//	tube1Y = t.y - m.bird.y;
				if(t.up ){
					if(m.bird.y+50 >= t.y )
						tube1Y = -500;
					else
						tube1Y = (t.y - m.bird.y)/10;
				} else{
					if(m.bird.y+20 <= t.y)
						tube1Y = 500;
					else 
						tube1Y = (t.y - m.bird.y)/10;
				}
				
				count1++;
			 if(count1 <init){	
					Tube t1 = m.tubes.get(count1);
				//	tube2Y = (t1.y > 450 || t1.y < 150 ? 0 : t1.y/10);
					if(t1.up ){
						if(m.bird.y+50 >= t1.y )
							tube2Y = -500;
						else
							tube2Y = (t1.y - m.bird.y)/10;
					} else{
						if(m.bird.y+20 <= t1.y)
							tube2Y = 500;
						else 
							tube2Y = (t1.y - m.bird.y)/10;
					}
				}
				
				count1 = m.tubes.size();
			}
			count1++;
		}

	}

	@Override
	public String toString() {
	//	return this.action + "," + this.birdY + "," + this.birdGravity + ","
	//			+ this.tube1X + "," + this.tube1Y + "," + this.tube2Y;
		return this.action + "," + this.birdY + "," 
		+ this.tube1X + "," + this.tube1Y + "," + this.tube2Y;
	}

}
*/

class Controller implements MouseListener, KeyListener {
	Model model;
	
	
	// HashMap<String, Double> qtable = new HashMap<String, Double>();

	Controller(Model m) throws Exception {
		this.model = m;
		
	}
/*
	public void updateqtable(Model m){
		
		StateAction myStateact1 = new StateAction(m, true);
	 	StateAction myStateact2 = new StateAction(m, false);
	 	
	 	if(!qtable.containsKey(myStateact1.toString())){
	 		qtable.put(myStateact1.toString(), 0.0);
	 		qtable.put(myStateact2.toString(), 0.0);
	 		System.out.println("entries updated into Qtable");
	 	}
	 	else
	 		System.out.println("keyrepeat"+ myStateact2.toString() );
	 		System.out.println("qtable size is : " + qtable.size());
	 	
	} 
	
	*/
	
	public void mousePressed(MouseEvent e) {
		// System.out.println("Bird coordinates before flap :" +
		// this.model.bird.x +" "+this.model.bird.y);
		this.model.flap();
		// System.out.println("Bird coordinates inside mousePressed: " +
		// this.model.bird.x+" "+this.model.bird.y);
	//	for (int i = 0; i < this.model.tubes.size(); i++) {
	//		Tube t = this.model.tubes.get(i);
	//		System.out.println("tube coordinates :  " + t.x + " " + t.y);
	//	}
	}

	public void keyPressed(KeyEvent e) {

		System.out.println("Key Event occurred in Controller");

		

		this.model.flap();
		
	
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}
}
