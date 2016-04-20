package assignment6;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class TestTicketOfficeTest {

	public static int score = 0;
	TheaterConfig tick = new TheaterConfig();
	//@Test
	public void basicServerTest() {
		
		final TicketServer bob = new TicketServer();
		
		Thread one = new Thread()
		{
			public void run()
			{
				try
				{
					bob.start(TicketServer.PORT, tick);
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		};
		one.start();
		final TicketClient client = new TicketClient("A");
		Thread two = new Thread()
		{
			public void run()
			{
				client.requestTicket();
			}
			
		};
		two.start();
		try{
			two.join();
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		finally{
			System.exit(0);
		}
	}
	@Test
	public void createdTest()
	{
		final TicketServer bob = new TicketServer();//server one
		final TicketServer tim = new TicketServer();//server two
		tick = new TheaterConfig();
		Thread one = new Thread()
		{
			public void run()
			{
				try
				{
					bob.start(TicketServer.PORT, tick);
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		};
		one.start();
		Thread two = new Thread()
		{
			public void run()
			{
				try{
				tim.start(TicketServer.PORT, tick);
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
			
		};
		two.start();
		Thread [] list = new Thread[750];
		final TicketClient test1 = new TicketClient("A");
		final TicketClient test2 = new TicketClient("B");
		try{
		for(int i = 0;i<750;i++)
		{
			if(i%2==1)
			{
				list[i] = new Thread()
				{
					public void run(){
						test1.requestTicket();
					}
					
				};
				list[i].start();
			}
			else{
				list[i]= new Thread()
				{
					public void run()
					{
						test2.requestTicket();
					}
					
				};
				list[i].start();
			}
		}
		}
		catch(Exception e)
		{
			System.err.println("Error");
		}
		finally
		{
			for(int i =0;i<750;i++)
			{
				try {
					list[i].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			System.exit(0);
		}
		
		
	}
	//@Test
	public void testServerCachedHardInstance() {
		try {
			//TicketServer.start(16790);
		} catch (Exception e) {
			fail();
		}
		TicketClient client1 = new TicketClient("localhost", "c1");
		TicketClient client2 = new TicketClient("localhost", "c2");
		client1.requestTicket();
		client2.requestTicket();
		System.out.println("Done?");
	}

	//@Test
	public void twoNonConcurrentServerTest() {
		try {
		//	TicketServer.start(16791);
		} catch (Exception e) {
			fail();
		}
		//TicketClient c4 = new TicketClient("localhost", "c1");
		TicketClient c1 = new TicketClient("nonconc1");
		TicketClient c2 = new TicketClient("nonconc2");
		TicketClient c3 = new TicketClient("nonconc3");
		c1.requestTicket();
		c2.requestTicket();
		c3.requestTicket();
	}

	//@Test
	public void twoConcurrentServerTest() {
		try {
			//TicketServer.start(16792);
		} catch (Exception e) {
			fail();
		}
		final TicketClient c1 = new TicketClient("conc1");
		final TicketClient c2 = new TicketClient("conc2");
		final TicketClient c3 = new TicketClient("conc3");
		Thread t1 = new Thread() {
			public void run() {
				c1.requestTicket();
			}
		};
		Thread t2 = new Thread() {
			public void run() {
				c2.requestTicket();
			}
		};
		Thread t3 = new Thread() {
			public void run() {
				c3.requestTicket();
			}
		};
		t1.start();
		t2.start();
		t3.start();
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
