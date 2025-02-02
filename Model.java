import java.util.ArrayList;
import java.util.Random;

class Bird
{
	int x;
	int y;
	double vert_vel;
	int time_since_flap;

	Bird() {
		x = 100;
		y = 100;
	}

	boolean update() {
		time_since_flap++;
	//	System.out.println("Time since flap is :" + time_since_flap);
	//	System.out.println("vert_vel is :" + vert_vel);
		y += vert_vel;
		vert_vel += 1.5;
		if(y < -55)
			return false;
		else if(y > 500)
			return false;
		return true;
	}

	void flap() {
		vert_vel = -12.0;
		if(time_since_flap > 4)
			time_since_flap = 0;
		else
			time_since_flap = 5;
		//	System.out.println("When flap executes Time since flap is :" + time_since_flap);
			
			
	}
}


class Tube
{
	int x;
	int y;
	boolean up;

	Tube(int _y, boolean _up) {
		y = _y;
		x = 500;
		up = _up;
	}

	boolean update(Bird bird) {
		x -= 5;
		if(x < bird.x + 60 && x + 45 > bird.x) {
			if(up) {
				if(bird.y + 50 > y)
					return false;
			} else {
				if(bird.y + 20 < y)
					return false;
			}
		}
		return true;
	}
}

class Model
{
	Bird bird;
	ArrayList<Tube> tubes;
	Random rand;
	int frame;
	int score;
	static int count; 

	Model() {
		rand = new Random(0);
		bird = new Bird();
		tubes = new ArrayList<Tube>();
		frame = 38;
		
	}
   
	public boolean update() {
	//	count ++;
	//	System.out.println("Count value is :" + count);
		if(!bird.update())
			return false;
		for(int i = 0; i < tubes.size(); i++) {
			Tube t = tubes.get(i);
			if(!t.update(bird))
				return false;
			if(t.x < -55) {
				tubes.set(i, tubes.get(tubes.size() - 1));
				tubes.remove(tubes.get(tubes.size() - 1));
				++score;
//				System.out.println("Score is :" +Integer.toString(score));
			}
		}
		if(++frame % 35 == 0) {
		//	System.out.println("Frame value is : " + frame);
			boolean up = rand.nextBoolean();
			Tube t = new Tube(rand.nextInt(350) + (up ? 150 : 0), up);
			tubes.add(t);
	//		System.out.println("Added new tube at " + t.x);
		}
		return true;
	}

	public void flap() {
		bird.flap();
	}
}








