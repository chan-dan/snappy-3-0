import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import java.io.IOException;
import java.awt.event.WindowEvent;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;



public class Game extends JFrame implements ActionListener {
	Model model;
	View view;
	Timer timer;
	int ttl;
	Robot robot;
	int frame;
	StateAction myStateact;
	public Qcontroller myqcontroller;
	//	public static HashMap<String, Double> qtable = new HashMap<String, Double>();
	public static int iter;


	public Game() throws IOException, Exception {
		//		iter++;
		//		System.out.println(" Game started iter is "+ iter );


		this.model = new Model();
		Controller controller = new Controller(this.model);
		this.view = new View(this.model);
		this.myqcontroller = new Qcontroller(this.model);
		//	this.myStateact = new StateAction(this.model);
		addMouseListener(myqcontroller);
		addKeyListener(myqcontroller);
		this.setTitle("Snappy Bird");
		this.setSize(500, 500);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.robot = new Robot();
		timer = new Timer(30, this);

		timer.start(); // Indirectly calls actionPerformed at regular intervals
	}

	public void actionPerformed(ActionEvent evt) {
		StateAction myStateact1 = new StateAction(this.model, true);
		StateAction myStateact2 = new StateAction(this.model, false);



		decide(myStateact1, myStateact2);
	}
	/*
	public void exploit()throws Exception{
		boolean IS_MODEL_UPDATED_EXPLOIT;
		double REWARD_EXPLOIT =0;
		int mod_sco;
	//	System.out.println("entered exploit()");
		StateAction exploitStateActioni1 = new StateAction(this.model, true);
		StateAction exploitStateActioni2 = new StateAction(this.model, false);
		if(!qtable.containsKey(exploitStateActioni1.toString())){
	 		qtable.put(exploitStateActioni1.toString(), 0.0);
	 		qtable.put(exploitStateActioni2.toString(), 0.0);
		}

		if(qtable.get(exploitStateActioni1.toString()) ==  qtable.get(exploitStateActioni2.toString())){




		}


		boolean perfAction = ((qtable.get(exploitStateActioni1.toString()) >= qtable.get(exploitStateActioni2.toString())) ? exploitStateActioni1.action : exploitStateActioni2.action) ;
	//	System.out.println(" Selected higher value of  : " + qtable.get(exploitStateActioni1.toString())+"   " + qtable.get(exploitStateActioni2.toString()) );
	//	System.out.println("selected action is "+ perfAction);
		StateAction exploitStateActioni = new StateAction(this.model, perfAction);
		mod_sco = this.model.score;
		IS_MODEL_UPDATED_EXPLOIT = this.model.update();
		//	System.out.println("status of model update :"+ IS_MODEL_UPDATED);

			//REWARD_EXPLOIT = (IS_MODEL_UPDATED_EXPLOIT ? +1 : -100 );

			if(IS_MODEL_UPDATED_EXPLOIT){
				REWARD_EXPLOIT = 1;
				if(this.model.tubes.size() > 1){
				Tube z = this.model.tubes.get(0);
				if(z.x == 55){
					REWARD_EXPLOIT += 1000;
					System.out.println("Crossed a tube in exploit");
				} }
			}else{
				REWARD_EXPLOIT = -100;
				ttl = 50;
			}
		//	System.out.println("Reward is  :"+ REWARD);

			StateAction exploitStateActionj1 = new StateAction(this.model, true);
			StateAction exploitStateActionj2 = new StateAction(this.model, false);
		//	System.out.println("new states created");

			if(!qtable.containsKey(exploitStateActionj1.toString())){
		 		qtable.put(exploitStateActionj1.toString(), 0.0);
		 		qtable.put(exploitStateActionj2.toString(), 0.0);
		// 		System.out.println("entries updated into Qtable");
		 	}

			double qvalj1_exploit = qtable.get(exploitStateActionj1.toString());
			double qvalj2_exploit = qtable.get(exploitStateActionj2.toString());
	//		System.out.println("qvals are :"+ qvalj1_exploit + "   "+ qvalj2_exploit);


			double qval_exploit = ((qtable.get(exploitStateActionj1.toString()) >= qtable.get(exploitStateActionj2.toString())) ? qtable.get(exploitStateActionj1.toString()) : qtable.get(exploitStateActionj2.toString())) ;
		//	System.out.println("qval value is "+ qval);


			double updateQval_exploit = REWARD_EXPLOIT + (0.99 * qval_exploit); 

			qtable.put(exploitStateActioni.toString(), updateQval_exploit );
		//	System.out.println("updated qvalue is " +exploitStateActioni.toString()+"   "+ updateQval_exploit );

			exploitStateActionj1 = null;
			exploitStateActionj2 = null;
			exploitStateActioni = null;
			exploitStateActioni1 = null;
			exploitStateActioni2 = null;

	}
	 */
	/*
	public void explore() throws Exception{
		boolean FLAP;
		boolean IS_MODEL_UPDATED;
		int REWARD =0;
		int mod_sco_plore;

		// pick donot flap 90% times and flap 10% times
	//	System.out.println("entered explore()");
		Random seed = new Random();
		double s = seed.nextDouble();
//		System.out.println("Seed for random generator in explore is : "+s);
		Random rnd = new Random();
		Double numb = rnd.nextDouble();
	//	mod_sco_plore = this.model.score;
		if (numb < 0.90 || this.model.bird.y < 50){
			FLAP = false;
			if(this.model.bird.y > 400)
				FLAP = true;

		}else
			FLAP = true;
		StateAction exploreStateActioni = new StateAction(this.model, FLAP);	

		if(FLAP)
			this.model.flap();

		IS_MODEL_UPDATED = this.model.update();
	//	System.out.println("status of model update :"+ IS_MODEL_UPDATED);

	//	REWARD = (IS_MODEL_UPDATED ? +1 : -100 );

		if(IS_MODEL_UPDATED){
			REWARD = 1;
			if(this.model.tubes.size()> 1){
			Tube z = this.model.tubes.get(0);
			if(z.x == 55){
				REWARD += 1000;
	//			System.out.println("Crossed a tube in explore");
			}}
		}else{
			REWARD = -100;
			ttl = 50;
			}

	//	System.out.println("Reward is  :"+ REWARD);

		StateAction exploreStateActionj1 = new StateAction(this.model, true);
		StateAction exploreStateActionj2 = new StateAction(this.model, false);
	//	System.out.println("new states created");

		if(!myqcontroller.qtable.containsKey(exploreStateActionj1.toString())){
			myqcontroller.qtable.put(exploreStateActionj1.toString(), 0.0);
			myqcontroller.qtable.put(exploreStateActionj2.toString(), 0.0);
	// 		System.out.println("entries updated into Qtable");
	 	}

		double qvalj1 = myqcontroller.qtable.get(exploreStateActionj1.toString());
		double qvalj2 = myqcontroller.qtable.get(exploreStateActionj2.toString());
//		System.out.println("qvals are :"+ qvalj1 + "   "+ qvalj2);


		double qval = ((myqcontroller.qtable.get(exploreStateActionj1.toString()) >= myqcontroller.qtable.get(exploreStateActionj2.toString())) ? myqcontroller.qtable.get(exploreStateActionj1.toString()) : myqcontroller.qtable.get(exploreStateActionj2.toString())) ;
	//	System.out.println("qval value is "+ qval);


		double updateQval = REWARD + (0.9 * qval); 

		qtable.put(exploreStateActioni.toString(), updateQval );
//		System.out.println("updated qvalue is " +exploreStateActioni.toString()+"   "+ updateQval );

		exploreStateActionj1 = null;
		exploreStateActionj2 = null;
		exploreStateActioni = null;



	}

	 */

	private void decide(StateAction myStateact1, StateAction myStateact2) {
		if(myqcontroller.ttl > 0)
		{
			if(--myqcontroller.ttl == 0)
			{
				//				timer.stop();
				//dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

				try {
					//					myStateact2.birdY = 0;
					//					myStateact2.birdGravity = 0.00;
					//					myStateact2.tube1X=0;
					//					myStateact2.tube1Y=400;
					//					myStateact2.tube2Y=;
					//					new Game();
					reset(this.model);
					myStateact1 = null;
					myStateact2 = null;
					this.model.update();
					//					for(Map.Entry<StateAction, Double> entry : myqcontroller.qtable.entrySet()){
					////						System.out.println("Key is : "+ entry.getKey()+" Value is: "+ entry.getValue());
					//					}

					//				    System.out.println("Game started : qtable size is :" +myqcontroller.qtable.size());
					//timer.start();
				} catch (Exception e) {
					System.out.println("Error during new game start");
					e.printStackTrace();
				}
				//	timer.start();
				//	dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			}
			return;
		}


		/*if(!myqcontroller.qtable.containsKey(myStateact1)){
			myqcontroller.qtable.put(myStateact1, 0.0);
			myqcontroller.qtable.put(myStateact2, 0.0);
			//	System.out.println("entries updated into Qtable");


			try {
				myqcontroller.exploitAI(this.model);
			} catch (Exception e) {
				System.out.println("Error when function explore is called");
				e.printStackTrace();
			}

		}
		else{*/
			//	System.out.println("keyrepeat"+ myStateact2.toString() );
			//	System.out.println("qtable size is : " + qtable.size());

			Random seed1 = new Random();
			double s1 = seed1.nextDouble();
			//			System.out.println("The seed for random generator while picking expore Vs exploit: "+ s1);
			Random rnd1 = new Random();
			Double numb1 = rnd1.nextDouble();

			/*if (numb1 < 0.005 && myqcontroller.trained == false){
			    if(numb1 > 0.3 && iter > 5000){
					try{
						myqcontroller.exploreAI(this.model);
				}catch(Exception e){
					System.out.println("Error when function exploit is called");
					e.printStackTrace();
				}


				}
				else{
				try {
					myqcontroller.exploreAI(this.model);
				} catch (Exception e) {
					System.out.println("Error when function explore is called");
					e.printStackTrace();
				}
			}	
			}else*/
//			try{
				myqcontroller.exploitAI(this.model);
			/*}catch(Exception e){
				System.out.println("Error when function exploit is called");
				e.printStackTrace();
			}
*/
//		}


		//	if(!this.model.update())
		//		ttl = 50;

		//		robot.mouseMove(470 + (int)(20 * Math.cos(frame)), 70 + (int)(20 * Math.sin(frame++)));

		view.invalidate();
		repaint(); // Indirectly calls View.paintComponent
		//		
		myStateact1 = null;
		myStateact2 = null;
	}

	public void reset(Model m){
		//		System.out.println("game started");
		m.bird.x = 100;
		m.bird.y = 100;
		m.tubes.clear();
		m.bird.vert_vel =0.0;
		m.bird.time_since_flap =0;
		m.rand = new Random(0);
		m.score = 0;



	}

	public static void main(String[] args) throws IOException, Exception {

		//	HashMap<String, Double> qtable = new HashMap<String, Double>();
		new Game();
	}

	/*@Override
	public void run() {
		try{
			for (int i=1; i<=2; i++){
		new Game();
		Thread.sleep(1000000);
			}
			}catch(Exception e){
				System.out.println("Some exception");
			}
		}catch(InterruptedException ie){
			System.out.println("threads screwed up");
		}catch(Exception e){
			System.out.println("Some exception");
		}

	}*/

}
