import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	
	public enum state {hungry, eating, thinking}; //different states that a philosopher can be in
	public state[] states; // for storing the states of each philosophers
	PriorityQueue<Integer> eatPriority = new PriorityQueue<Integer>();
	public int numPhilosophers;	
	public boolean isTalking;

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		this.states = new state[piNumberOfPhilosophers];
		this.numPhilosophers = piNumberOfPhilosophers;
		this.isTalking = false;
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		// ...
		// change the state of philosopher piTID
		int index = piTID-1;
		states[index] = state.hungry;

		testEat(index);

		//if the user couldn't secure the chopsticks - you need to set them in a waiting state
		while(states[index] != state.eating){
			try {
				System.out.println("I am waiting - Philosopher "+piTID);
				wait();
				testEat(index);
				
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		// ...
		int index = piTID-1;
		//change from eating to thinking state
		states[index] = state.thinking;
		
		//check if either neighbor needs the chopsticks and possibly signal them
		if(states[(index+(numPhilosophers-1))%numPhilosophers] == state.hungry){
			testEat(((index+(numPhilosophers-1))%numPhilosophers));
		}
		if(states[(index+1)%numPhilosophers] == state.hungry){
			testEat(((index+1)%numPhilosophers));
		}
	}

	/**
	 * helper function to pickUp and putDown methods - used to determine if neighbor chopsticks are used and chnge signal is that is the case
	 */
	public void testEat(final int index){
		// checking whether neighbours are eating - see if the chopsticks are free
		if((states[((index+(numPhilosophers-1))%numPhilosophers)] != state.eating) && (states[((index+1)%numPhilosophers)] != state.eating)){
			// change the state of the philospher to eating
			states[index] = state.eating;

			// signal the philospher that they can eat
			notifyAll();
		}

	}


	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	Object o = new Object();
	public void requestTalk()
	{
		// ...
		//condition when someone is already talking
		synchronized(o) {
			while(isTalking) {
				try {
					o.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			isTalking = true;
		}
	}


	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public void endTalk()
	{
		// ...
		//philosopher is no longer talking
		synchronized(o) {
			isTalking = false;
			o.notifyAll();
		}

	}
}

// EOF
