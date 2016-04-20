package assignment6;//ticket office

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TicketServer {
	static int PORT = 2222;
	static boolean done;
	// EE422C: no matter how many concurrent requests you get,
	// do not have more than three servers running concurrently
	final static int MAXPARALLELTHREADS = 3;
	public static TheaterConfig theater;
	//public static ServerSocket serverSocket;
	public static void start(int portNumber, TheaterConfig theaters ) throws IOException {
		PORT = portNumber;
		theater = theaters;
		done =false;
		try{
			ServerSocket serverSocket = new ServerSocket(TicketServer.PORT);
			while(true)
			{
				Socket clientSock = serverSocket.accept();
				Runnable serverThr = new ThreadedTicketServer(clientSock);
				Thread star = new Thread(serverThr);
				star.start();
			}
		}
		catch(IOException e){
			//e.printStackTrace();
		}
		
		
	}
}
//array of clients are created up to 100, once the clients list is below 10, we should add 50 more. 
//108-121 are the best seats
//Thread execution and the client vs server classes
//Create those three functions, our servers 
//List of clients, in those clients run we will be calling the server classes. These server classes are going to be accesssing 
//the same theater configuration, and it goes prioritising, both ticket servers are called at the same time, if both are called, 
//then we just give it one and send the other, after both are called, call a checking function to see if the tickets are the same, 
// call best available ticket, if sold out then give one client ticket, famliarize with threading and slides and servers 
class ThreadedTicketServer extends Thread implements Runnable {

	final String hostname = "127.0.0.1";
	String threadname = "X";
	String testcase;
	TicketClient sc;
	Socket socket;
	public ThreadedTicketServer(Socket sock)
	{
		this.socket = sock;
	}

	public void run() {
		//TODO:422C
		String fromClient;
		ServerSocket serverSocket;
	//	boolean over= false;
		try {
			//serverSocket=new ServerSocket(TicketServer.PORT)
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			if(in.readLine().equals("requesting a ticket"))
			{
			String result = TicketServer.theater.bestAvailableSeat();
			if(result==null)
				{
					out.println("Sorry! Sold out of tickets");
					socket.close();
					
				}
			else
				{
					out.println(result);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Whats going on");

	}
}