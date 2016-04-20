package assignment6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ThreadedTicketClient implements Runnable {
	String hostname = "127.0.0.1";
	String threadname = "X";
	TicketClient sc;

	public ThreadedTicketClient(TicketClient sc, String hostname, String threadname) {
		this.sc = sc;
		this.hostname = hostname;
		this.threadname = threadname;
	}

	public void run() {
		System.out.flush();
		String fromServer;
		try{
			Socket echoSocket = new Socket(hostname, TicketServer.PORT);
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			out.println("requesting a ticket");
			fromServer=in.readLine(); //this is where my connection keeps resetting
			if(fromServer.equals("Sorry! Sold out of tickets"))
			{	
				echoSocket.close();
				System.out.println("Sorry! Sold out of tickets");
				sc.done=true;
			//	
				System.exit(1);
			}
			else
			{
				sc.result = fromServer;
				echoSocket.close();
			}
			
				
		}catch (Exception e) {
			e.printStackTrace();
			//System.exit(1);
		}
		
		
	}
}

public class TicketClient {
	ThreadedTicketClient tc;
	String result = "dummy";
	String hostName = "";
	String threadName = "";
	static boolean done;

	TicketClient(String hostname, String threadname) {
		tc = new ThreadedTicketClient(this, hostname, threadname);
		hostName = hostname;
		threadName = threadname;
		done =false;
	}

	TicketClient(String name) {
		this("localhost", name);
		done=false;
	}

	TicketClient() {
		this("localhost", "unnamed client");
		done=false;
	}

	void requestTicket() {
		//TODO::thread.run()
		
		tc.run();
		System.out.println("TicketBooth: " + threadName+ " Reserved Seats " +result);
	}

	void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
