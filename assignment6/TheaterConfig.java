package assignment6;

public class TheaterConfig 
{
	int [][]left; 
	int [][]right;
	int [][] middle;
	public TheaterConfig()
	{
		left= new int[27][7];
		right= new int[27][7];
		middle= new int [27][14];
		for(int i =0;i<left.length;i++)
		{
			for(int j=0; j<left[i].length;j++)
			{
				left[i][j]=0;
				right[i][j]=0;
			}
			for(int j=0;j<middle[i].length;j++)
			{
				middle[i][j]=0; //instantiates the entire theater to all available seats
			}
		}
	}
	public synchronized String bestAvailableSeat() //combining bestavailableseat and markAvailableseatTaken
	{
		String seat;
		//priority of seats is middle front, fronts on the sides, then back middle, and back sides
		//back is cut off at 13 and above
		for(int i =0;i<14;i++) //this is for first half of theater config
		{
			for(int j=0; j<middle[i].length;j++)
			{
				if(middle[i][j]!=1)
				{
					seat= categorizeSeats(i);
					middle[i][j]=1;
					return seat +" Seat: " +(j+108);
				}
			}
			for(int j=0;j<left[i].length;j++)
			{
				if(left[i][j]!=1)
				{
					seat=categorizeSeats(i);
					left[i][j]=1;
					return seat +" Seat: " +(j+101);
				}
				if(right[i][j]!=1)
				{
					seat= categorizeSeats(i);
					right[i][j]=1;
					return seat +" Seat: " +(j+122);
				}
			}
			
		}
		for(int i =14;i<middle.length;i++) //this is for the second half of theater config
		{
			for(int j=0; j<middle[i].length;j++)
			{
				if(middle[i][j]!=1)
				{
					seat= categorizeSeats(i);
					middle[i][j]=1;
					return seat +" Seat: " +(j+7);
				}
			}
			for(int j=0;j<left[i].length;j++)
			{
				if(left[i][j]!=1)
				{
					seat= categorizeSeats(i);
					left[i][j]=1;
					return seat +" Seat: " +j;
				}
				if(right[i][j]!=1)
				{
					seat= categorizeSeats(i);
					right[i][j]=1;
					return seat +" Seat: " +(j+21);
				}
			}
			
		}
		return "-1"; //reaching here means theres no available seats left
	}
	public String categorizeSeats(int row)
	{
		String [] alpha = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","AA"};
		String raw =alpha[row];
		return "Row: " +raw;
	}
	
	//need three different arrays, left middle right 
}
