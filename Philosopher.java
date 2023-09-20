import java.util.Random;

import common.BaseThread;

/**
 * Class Philosopher.
 * Outlines main subrutines of our virtual philosopher.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Philosopher extends BaseThread
{
	/**
	 * Max time an action can take (in milliseconds)
	 */
	public static final long TIME_TO_WASTE = 1000;

	/**
	 * The act of eating.
	 * - Print the fact that a given phil (their TID) has started eating.
	 * - yield
	 * - Then sleep() for a random interval.
	 * - yield
	 * - The print that they are done eating.
	 */
	public void eat()
	{
		try
		{
			// ...
			System.out.println("Philosopher "+getTID()+" has started eating. NOM NOM NOM");
			Thread.yield();
			sleep((long)(Math.random() * TIME_TO_WASTE));
			Thread.yield();
			System.out.println("Philosopher "+getTID()+" has finished eating. SATISFACTION");
			// ...
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.eat():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * The act of thinking.
	 * - Print the fact that a given phil (their TID) has started thinking.
	 * - yield
	 * - Then sleep() for a random interval.
	 * - yield
	 * - The print that they are done thinking.
	 */
	public void think()
	{
		// ...
		try
		{
			// ...
			System.out.println("Philosopher "+getTID()+" has started thinking...");
			Thread.yield();
			sleep((long)(Math.random() * TIME_TO_WASTE));
			Thread.yield();
			System.out.println("Philosopher "+getTID()+" has finished thinking...");
			// ...
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.eat():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * The act of talking.
	 * - Print the fact that a given phil (their TID) has started talking.
	 * - yield
	 * - Say something brilliant at random
	 * - yield
	 * - The print that they are done talking.
	 */
	public void talk()
	{
		// ...
		System.out.println("Philosopher "+getTID()+" has started talking. EVERYBODY LISTEN");
		Thread.yield();
		saySomething();
		Thread.yield();
		System.out.println("Philosopher "+getTID()+" is done talking. THANK YOU");
		// ...
	}

	/**
	 * No, this is not the act of running, just the overridden Thread.run()
	 */
	public void run()
	{
		for(int i = 0; i < DiningPhilosophers.DINING_STEPS; i++)
		{

			System.out.println("turn "+i+" for Philosopher "+getTID());
			DiningPhilosophers.soMonitor.pickUp(getTID());
			eat();
			DiningPhilosophers.soMonitor.putDown(getTID());
 		
			think();

			/*
			 * TODO:
			 * A decision is made at random whether this particular
			 * philosopher is about to say something terribly useful.
			 */
			
			Random random = new Random();
			int num = random.nextInt(3);
			String[] states = {"eat", "think", "talk"};
			if(states[num].equals("talk"))
			{
				// Some monitor ops down here...
				DiningPhilosophers.soMonitor.requestTalk();
				talk();
				DiningPhilosophers.soMonitor.endTalk();
				// ...
			}

			Thread.yield();
			
		}
	} // run()

	/**
	 * Prints out a phrase from the array of phrases at random.
	 * Feel free to add your own phrases.
	 */
	public void saySomething()
	{
		String[] astrPhrases =
		{
			"Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
			"You know, true is false and false is true if you think of it",
			"2 + 2 = 5 for extremely large values of 2...",
			"If thee cannot speak, thee must be silent",
			"My number is " + getTID() + ""
		};

		System.out.println
		(
			"Philosopher " + getTID() + " says: " +
			astrPhrases[(int)(Math.random() * astrPhrases.length)]
		);
	}
}

// EOF
