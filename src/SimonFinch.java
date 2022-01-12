import java.util.Random;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import edu.cmu.ri.createlab.terk.robot.finch.Finch; //import package to use finches

public class SimonFinch {
	static Finch rfinch = new Finch(), gfinch = new Finch(), bfinch = new Finch(), yfinch = new Finch(), switchfinch = new Finch(); //declare and initialise the finches
	
	static public void main(String args[]) throws InterruptedException {
		String Status = ""; //This will be used throughout the program to control the program and return its status
		
		do {
			Status = GameMenu(); //Run the menu until quit or cancel is selected
			if (Status.equals("Start")) StartGame(Status); //if the start option is selected, call StartGame method while passing the Status parameter 
		} while (!(Status.equals("Quit"))); //do while loop active if status does not equal Quit
		System.out.println("\nExiting"); //output exiting
		rfinch.quit(); //quit
		gfinch.quit(); 
		bfinch.quit(); 
		yfinch.quit(); 
		switchfinch.quit(); 
	}
	
	private static String GameMenu() { 
		Object[] possibilities = {"Start", "Quit"}; //list of options available for section to the user
		String MenuStatus = (String)JOptionPane.showInputDialog(null,"Simon Game\n++++++++++++++++++++++++\nChoose an option:\n\n","Assignment 1",JOptionPane.PLAIN_MESSAGE, null,possibilities,"Start Game");
		if (MenuStatus == null || MenuStatus.length() == 0) MenuStatus = "Quit"; //if MenuStatus is empty or equal to 0, then quit the program
		return(MenuStatus); //returns the value of MenuStatus 
	}
	
	private static void StartGame(String s) throws InterruptedException {
		int Round = 1, Score = 0, Lives = 3, Time = 3000; //declare and initialise several integer variables, Round set to 1, Score set to 0, Lives set to 3, and Time to 3000 (3 seconds)
		boolean Run = true, NextRound = false; //declare and initialise several booleans, Run to true and NextRound to false
		ArrayList<Integer> Sequence = new ArrayList<Integer>(); //declare and initialise Sequence as an ArrayList to the Integer data type
		
		System.out.println("\nStarting Game..."); //output this
		
		Sequence.add(RandNum()); //call the RandNum method and add the generated value to the Sequence ArrayList
		
		while(Run || Lives >= 1) { //while loop runs if Run is true OR the beak of switch finch is down OR if the value of Lives is greater and or equal to 1
			if(switchfinch.isBeakUp()) { //if the beak of switchfinch is up then...
				System.out.println("\nNevermind, the Finch is flying away..."); //output this
				Run = false; //assign the value of false to Run
				Lives = 0; //assign the value of 0 to Lives
				break; //loop is terminated
			}
			
			System.out.println("\n+++++++++++++++++++++Round " + Round + "+++++++++++++++++++++"); //output this along with the value of Round
			System.out.println("Highest score: " + Score); //output this along with the value of Score
			System.out.println("Lives remaining: " + Lives + "\n"); //output the along with the value of Lives
			ReplaySeq(Sequence, Time); //call the ReplaySeq method while passing the values of Sequence and Time through as arguments 
			System.out.println("\nPlease repeat the sequence played."); //output this
			for(int i=0;i<Sequence.size();i++) { //loop through the Sequence ArrayList
				String NumOrdinal = ""; //declare NumOrdinal as string and initialise its value to nothing
				int TappedFinch = 0, CurCol = i+1; //declare and initialise integers, TappedFinch to 0, and CurCol to increment 1 from i in the for loop
				
				NumOrdinal = Ordinal(CurCol); //call the Ordinal method while passing through CurCol as an argument and assigning its value to NumOrdinal
				TappedFinch = TapFinch(NumOrdinal); //call the TappedFinch method while passing through the NumOrdinal argument and assigning its method to TappedFinch
				    
				if(TappedFinch == Sequence.get(i)) { //if the TappedFinch value matches the value of i in the array then...
					rfinch.setLED(0,255,0); //set finch the LED colour
					gfinch.setLED(0,255,0); 
					bfinch.setLED(0,255,0); 
					yfinch.setLED(0,255,0); 
					switchfinch.setLED(0,255,0); 
					Score = Round-1; //assign the value of Round minus 1 to Score
					System.out.println("\nNice!"); //output this
					NextRound = true; //assign the value of true to NextRound
				}else if(TappedFinch != Sequence.get(i)) { //if the value of TappedFinch does not equal the value of i in the array then...
					rfinch.setLED(255,0,0); 
					gfinch.setLED(255,255,0); 
					bfinch.setLED(255,255,0); 
					yfinch.setLED(255,255,0); 
					switchfinch.setLED(255,0,0); 
					rfinch.buzzBlocking(1000, 100); //play frequency for set duration
					gfinch.buzzBlocking(400, 80); 
					bfinch.buzzBlocking(600, 70); 
					yfinch.buzzBlocking(200, 60); 
					switchfinch.buzzBlocking(300, 50); 
					
					System.out.println("\nWrong colour!"); //output this
					
					TappedFinch = 0; //assign the value 0 to TappedFinch
					Score = Round-1; //assign the value of Round minus 1 to Score
					Lives--; //decrement a value to Lives
					NextRound = false; //assign the value of false to NextRound
					Run = false; //assign the value of false to Run
					break; //loop is terminated
				}
			}
			
			if (NextRound) { //if NextRound is true then...
				Round++; //increment a value to Round
				Score++; //increment a value to Score
				Sequence.add(RandNum());  //call the RandNum method and add the generated value to the Sequence ArrayList
				if(Time>1000) { //if Time is more than the value of 1000 then...
					Time = Time-100; //assign the value of Time minus 100 to Time
				}
			}else if(Lives <= 0) { //else if Lives is less than or equal to 0 then...
				rfinch.setLED(255,0,0, 50); //set finch the LED colourfor set duration
				gfinch.setLED(0,255,0, 50); 
				bfinch.setLED(0,0,255, 50); 
				yfinch.setLED(255,255,0, 50); 
				switchfinch.setLED(255,0,0, 50); 
				
				
				rfinch.buzzBlocking(1000, 100); 
				gfinch.buzzBlocking(400, 80); 
				bfinch.buzzBlocking(600, 70); 
				yfinch.buzzBlocking(200, 60); 
				switchfinch.buzzBlocking(300, 50); 
				
				switchfinch.setLED(255,0,0, 50); 
				yfinch.setLED(255,255,0, 50); 
				bfinch.setLED(0,0,255, 50); 
				gfinch.setLED(0,255,0, 50); 
				rfinch.setLED(255,0,0, 50); 
				
				System.out.println("\nGame Over!"); //output this
				System.out.println("Highest score: " + Score); //output this along with the value of Score
				System.out.println("Ending game..."); //output this
				NextRound = false; //assign the value of false to NextRound
				Run = false; //assign the value of false to Run
				break; //loop is terminated
			}
		}
	}
	
	public static int RandNum() { //method to generate a random number
		Random RandNum = new Random(); //Declare RandNum as Random and set its value to the result of the Random method
		return RandNum.nextInt(4)+1; //returns a random number ranging from 1 to 4
	}
	
	public static void ReplaySeq(ArrayList<Integer> Sequence, int Duration) throws InterruptedException { //method replays the sequence of colours stored in the Sequence array using the parameter for a set Duration
		for(int i=0;i<Sequence.size();i++) { //loop through the array play the sequence of colours
			rfinch.setLED(0,0,0); //turn off LEDs for finch
			gfinch.setLED(0,0,0); 
			bfinch.setLED(0,0,0); 
			yfinch.setLED(0,0,0); 
			
			if(Sequence.get(i) == 1) { //if the value of i in the Sequence array equals to 1 then...
				rfinch.setLED(255, 0, 0, Duration); 
				rfinch.buzz(1000, 50); 
			}else if(Sequence.get(i) == 2){ //if the value of i in the Sequence array equals to 2 then...
				gfinch.setLED(0, 255, 0, Duration); 
				gfinch.buzz(2000, 50); 
			}else if(Sequence.get(i) == 3){ //if the value of i in the Sequence array equals to 3 then...
				bfinch.setLED(0, 0, 255, Duration); 
				bfinch.buzz(3000, 50); 
			}else { //if the other conditions are not met then...
				yfinch.setLED(255, 255, 0, Duration); 
				yfinch.buzz(4000, 50); 
			}
			Thread.sleep(200); //holds the program on idle for 200 milliseconds and throws an interrupted exception
		}
	}
	
	public static String Ordinal(int e) { //determines the ordinal affix to the number of the colour that was played
		int j = e % 10, k = e % 100; //variables are declared and initialised with modulus calculations to determine the ordinal affix for each number
		
		if (j == 1 && k != 11) { //if number ends in 1 then...
			return e + "st"; //return the value of e and add on the ordinal affix to the number
		}else if (j == 2 && k != 12) { //if number ends in 2 then...
			return e + "nd"; //return the value of e and add on the ordinal affix to the number
		}else if (j == 3 && k != 13) { //if number ends in 3 then...
			return e + "rd"; //return the value of e and add on the ordinal affix to the number
		}else { //for all other number endings...
			return e + "th"; //return the value of e and add on the ordinal affix to the number
		}
	}
	
	public static int TapFinch(String Ordinal) { //TapFinch method with a string parameter
		boolean Tap = false; //declare and initialise Tap to boolean and set its value to false
		int FinchVal = 0; //declare FinchVal as integer and initialise its value to 0 
		
		System.out.println("Tap the finch corresponding to the " + Ordinal + " colour: "); //output this along with the value of Ordinal
		
		while(!Tap) { //run while Tap is false
			rfinch.setLED(255,0,0); 
			gfinch.setLED(0,255,0); 
			bfinch.setLED(0,0,255); 
			yfinch.setLED(255,255,0); 
			
			if(rfinch.isTapped()) { //if the red finch is tapped then...
				return FinchVal = 1; //assign and return the value of 1 to FinchVal
			}else if(gfinch.isTapped()) { //if the green finch is tapped then...
				return FinchVal = 2; //assign and return the value of 2 to FinchVal
			}else if(bfinch.isTapped()) { //if the blue finch is tapped then...
				return FinchVal = 3; //assign and return the value of 3 to FinchVal
			}else if(yfinch.isTapped()) { //if the yellow finch is tapped then...
				return FinchVal = 4; //assign and return the value of 4 to FinchVal
			}
		}
		rfinch.setLED(0,0,0); 
		gfinch.setLED(0,0,0); 
		bfinch.setLED(0,0,0); 
		yfinch.setLED(0,0,0); 
		
		return FinchVal; //return the value of FinchVal
	}
}
