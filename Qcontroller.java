import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

class StateAction implements Serializable {
	boolean action;
	int birdY;
	double birdGravity;
	int tube1X;
	int tube1Y;
	int tube2Y;

	StateAction(Model m, boolean setAction) {
		this.action = setAction;
		birdGravity = m.bird.vert_vel;
		
		int count1 = 0;
		int tubeSize = m.tubes.size();
		if (tubeSize > 0) {
			while (count1 < tubeSize) {
				Tube t = m.tubes.get(count1);
				if (t.x > 100) {
					tube1X = t.x / 5;
					tube1Y = t.y - m.bird.y;
					if (t.up) {

						if (tube1Y <= 50)
							tube1Y = -700;
					//	else
					//		tube1Y = tube1Y;
					} else {
						if (tube1Y >= 20)
							tube1Y = 700;
					//	else
					//		tube1Y = tube1Y / 10 - 1;
					}
					count1 = tubeSize + 1;
				} else {
					count1++;
				}

			}

		}

	}

	

	@Override
	public String toString() {
		return this.action + "," + this.birdGravity + "," + this.tube1X + ","
				+ this.tube1Y + "," + this.tube2Y;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (action ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(birdGravity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + birdY;
		result = prime * result + tube1X;
		result = prime * result + tube1Y;
		result = prime * result + tube2Y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StateAction other = (StateAction) obj;
		if (action != other.action)
			return false;
		if (Double.doubleToLongBits(birdGravity) != Double
				.doubleToLongBits(other.birdGravity))
			return false;
		if (birdY != other.birdY)
			return false;
		if (tube1X != other.tube1X)
			return false;
		if (tube1Y != other.tube1Y)
			return false;
		if (tube2Y != other.tube2Y)
			return false;
		return true;
	}
	
	

}

public class Qcontroller implements MouseListener, KeyListener,Serializable {
	Model model;
	StateAction stateAction;
	public  HashMap<StateAction, Double> qtable =readMapFileAndFly() /*new HashMap<StateAction,Double>()*/;
	public static Random rnd1 = new Random(1);
	public static int ttl;
	boolean KEY_FLAP = false;
	Boolean trained=false;
	public void mousePressed(MouseEvent e) {
		this.model.flap();
	}
	public void keyPressed(KeyEvent e) {
		StateAction actioni = new StateAction(model, true);
		updateQtable(actioni);
		this.model.flap();
		updateQvalue(model, actioni);
		
	}
	private void updateQtable(StateAction actioni) {
		if(!qtable.containsKey(actioni)){
			qtable.put(actioni, 0.0);
			StateAction actioni2 = new StateAction(model, false);
			qtable.put(actioni2, 0.0);
		}
	}

	public Qcontroller(Model mod) {
		this.model = mod;
	}
		
	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }

	public void mouseClicked(MouseEvent e) {    }
	public void keyTyped(KeyEvent e) {    }
	public void keyReleased(KeyEvent e) {    }
	
	public void exploitAI(Model xploit_mod) {
		/*if(trained){
		}*/
//		readMapFileAndFly();
		
		//	Model xploit_mod_copy = xploit_mod;
	boolean	IS_MODEL_UPDATED_EXPLOIT;
	double REWARD_EXPLOIT;
	
	//	if (updateQtable(xploit_mod))
	//		exploreAI(xploit_mod);
	StateAction exploitStateActionk1 = new StateAction(xploit_mod, true);
	StateAction exploitStateActionk2 = new StateAction(xploit_mod, false);
		  if(!qtable.containsKey(exploitStateActionk1)){
			  qtable.put(exploitStateActionk1, 0.0);
			  qtable.put(exploitStateActionk2, 0.0);
			  exploreAI(xploit_mod);
		  }
		else {
			if (qtable.get(exploitStateActionk1) == qtable.get(exploitStateActionk2)) {
				exploreAI(xploit_mod); } 
			
			else {
				boolean perfAction;
				perfAction = ((qtable.get(exploitStateActionk1) > qtable.get(exploitStateActionk2)) ? exploitStateActionk1.action: exploitStateActionk2.action);
			  StateAction exploitStateActioni = new StateAction(xploit_mod, perfAction);
			if(perfAction)
				xploit_mod.flap();
			IS_MODEL_UPDATED_EXPLOIT = xploit_mod.update();
			
			if(IS_MODEL_UPDATED_EXPLOIT){
				REWARD_EXPLOIT = 10;
				if(xploit_mod.tubes.size() > 1){
				Tube z = xploit_mod.tubes.get(0);
				if(z.x == 55){
					REWARD_EXPLOIT +=10;
				} }
			}else{
				REWARD_EXPLOIT = -100;
				ttl = 50;
			}
					
			StateAction exploitStateActionj1 = new StateAction(this.model, true);
			StateAction exploitStateActionj2 = new StateAction(this.model, false);
			
			if(!qtable.containsKey(exploitStateActionj1)){
		 		qtable.put(exploitStateActionj1, 0.0);
		 		qtable.put(exploitStateActionj2, 0.0);
		 	}
			
			double qvalj1_exploit = qtable.get(exploitStateActionj1);
			double qvalj2_exploit = qtable.get(exploitStateActionj2);
	//		System.out.println("qvals are :"+ qvalj1_exploit + "   "+ qvalj2_exploit);
			
			
			double qval_exploit = ((qtable.get(exploitStateActionj1) >= qtable.get(exploitStateActionj2)) ? qtable.get(exploitStateActionj1) : qtable.get(exploitStateActionj2)) ;
		//	System.out.println("qval value is "+ qval);
			
			
			double updateQval_exploit = REWARD_EXPLOIT + (0.99 * qval_exploit); 
					
//			qtable.put(exploitStateActioni, updateQval_exploit );
		//	System.out.println("updated qvalue is " +exploitStateActioni.toString()+"   "+ updateQval_exploit );
			
			exploitStateActionj1 = null;
			exploitStateActionj2 = null;
			exploitStateActioni = null;
			exploitStateActionk1 = null;
			exploitStateActionk2 = null;
	
			
			}
		}

	}
	public void writeToFile(){
		try{
	    	File file = new File("qtable.txt"); // to view the values
	    	file.createNewFile();
	    	File file2=new File("hashStore.txt");
	    	file2.createNewFile();
	    	FileOutputStream fos=new FileOutputStream(file2);
	    	ObjectOutputStream oos=new ObjectOutputStream(fos);
	    	oos.writeObject(qtable);
	    	oos.flush();
	    	oos.close();
	    	fos.flush();
	    	fos.close();
//	    	System.out.println("created qtable file");
	    	FileWriter writer= new FileWriter(file);
	    	for(StateAction key:qtable.keySet()){
//	    		System.out.println(key.toString()+"\t"+qtable.get(key).toString());	
	    		writer.append(key.toString()+"\t"+qtable.get(key).toString()+"\n");
	    	}
	    	writer.flush();
	    	writer.close();
	    	
	    	}catch(FileNotFoundException fnf){
	    		System.out.println("check permissions, couldn't create file");
	    	}catch(IOException io){
	    		System.out.println("Couldn't write into the file");
	    		io.printStackTrace();
	    	}
		
	}
	public HashMap<StateAction, Double> readMapFileAndFly(){
		try{
		File file=new File("hashStore.txt");
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		qtable=(HashMap<StateAction, Double>)ois.readObject();
//		exploitAI(model);
		}catch(FileNotFoundException fnf){
    		System.out.println("check permissions, couldn't create file");
    	}catch(IOException io){
    		System.out.println("Couldn't write into the file");
    	}catch(ClassNotFoundException cnf){
    		System.out.println("Can't read hashmap object from file");
    		
    	}
		return qtable;
		
	}

	public void exploreAI(Model xplore_mod) {
		
		boolean FLAP;
		boolean IS_MODEL_UPDATED;
		int REWARD =0;
		int mod_sco_plore;
		
		Random rnd2 = rnd1;
		Double numb = rnd2.nextDouble();
	//	mod_sco_plore = this.model.score;
		if (numb < 0.50 || xplore_mod.bird.y < 50){
			FLAP = false;
			if(xplore_mod.bird.y > 400)
				FLAP = true;
			
		}else
			FLAP = true;
		StateAction exploreStateActioni = new StateAction(xplore_mod, FLAP);	
		
		if(FLAP)
			xplore_mod.flap();
		
		updateQvalue(xplore_mod, exploreStateActioni);
		
		exploreStateActioni = null;
		
		
	
		
	}

	private void updateQvalue(Model xplore_mod, StateAction exploreStateActioni) {
		boolean IS_MODEL_UPDATED;
		int REWARD;
		IS_MODEL_UPDATED = xplore_mod.update();
		
		if(IS_MODEL_UPDATED){
			REWARD = 10;
			if(xplore_mod.tubes.size()> 1){
			Tube z = xplore_mod.tubes.get(0);
			if(z.x == 55){
				REWARD += 1000;
	//			System.out.println("Crossed a tube in explore");
			}}
		}else{
			REWARD = -100;
			ttl = 50;
//			System.out.println("bird died at ++++++++++++++++++++ :" + exploreStateActioni.toString() +" "+ qtable.get(exploreStateActioni)  );
			}
		
	//	System.out.println("Reward is  :"+ REWARD);
				
		StateAction exploreStateActionj1 = new StateAction(xplore_mod, true);
		StateAction exploreStateActionj2 = new StateAction(xplore_mod, false);
	//	System.out.println("new states created");
		
		if(!qtable.containsKey(exploreStateActionj1)){
	 		qtable.put(exploreStateActionj1, 0.0);
	 		qtable.put(exploreStateActionj2, 0.0);
	// 		System.out.println("entries updated into Qtable");
	 	}
		
		double qvalj1 = qtable.get(exploreStateActionj1);
		double qvalj2 = qtable.get(exploreStateActionj2);
//		System.out.println("qvals are :"+ qvalj1 + "   "+ qvalj2);
		
		
		double qval = ((qtable.get(exploreStateActionj1) >= qtable.get(exploreStateActionj2)) ? qtable.get(exploreStateActionj1) : qtable.get(exploreStateActionj2)) ;
	//	System.out.println("qval value is "+ qval);
		
		
		double updateQval = REWARD + (0.99 * qval); 
				
		qtable.put(exploreStateActioni, updateQval );
		int scorer=xplore_mod.score;
		scorer++;
		System.out.println("score is"+scorer);
		if(scorer == 25){
			trained=true;
			System.out.println("writing map into file");
			writeToFile();
			System.out.println("Birds ready to fly");
			readMapFileAndFly();
			
		}
	}
	
}
