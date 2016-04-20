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
	//	System.out.flush();
		String fromServer;
		try{
			Socket echoSocket = new Socket(hostname, TicketServer.PORT);
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			while((fromServer=in.readLine())!=null)
			{
				out.println("requesting a ticket");
				if(fromServer.equals("Sorry! Sold out of tickets"))
				{
					break;
				}
				else
				{
					sc.result = fromServer;
				}
				echoSocket.close();
			}	
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		
	}
}

public class TicketClient {
	ThreadedTicketClient tc;
	String result = "dummy";
	String hostName = "";
	String threadName = "";

	TicketClient(String hostname, String threadname) {
		tc = new ThreadedTicketClient(this, hostname, threadname);
		hostName = hostname;
		threadName = threadname;
	}

	TicketClient(String name) {
		this("localhost", name);
	}

	TicketClient() {
		this("localhost", "unnamed client");
	}

	void requestTicket() {
		//TODO::thread.run()
		tc.run();
		System.out.println(result);
	}

	void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
