import java.util.Random;
import javax.swing.JOptionPane;
import java.util.ArrayList; // import the ArrayList class
import java.util.Scanner;
import edu.cmu.ri.createlab.terk.robot.finch.Finch;

public class SimonFinch {
	static Finch finch = new Finch();
	
	static public void main(String args[]) {
		String s = "";
		//This will used throughout the program to control your Finch and report it's status 
		do {
			//Run the menu until quit or cancel is selected
			s = FinchMenu();
			if (s.equals("Start")) start(s);
		} while (!(s.equals("Quit"))); 
		System.out.println("Exiting");
		finch.quit();
	}
	
	private static String FinchMenu() {
		Object[] possibilities = {"Start", "Quit"};
		String s = (String)JOptionPane.showInputDialog(null,"Simon Game\n++++++++++++++++++++++++\nChoose an option:\n\n","Assignment 1",JOptionPane.PLAIN_MESSAGE, null,possibilities,"Start Game");
		if (s == null || s.length() == 0) s = "Quit";
		return(s);
	}
	
	private static void start(String s) {
		boolean run = true, nextround = false;
		int round = 1, score, time = 3000;
		Scanner inputscan = new Scanner(System.in);
		ArrayList<Integer> seq = new ArrayList<Integer>();
		
		seq.add(randnum());
		
		while(run) {
			System.out.println("\r\n++++++++++Round "+round+"++++++++++");
			//System.out.println(seq.get(round));
			replayseq(seq, time);
			System.out.println("Please repeat the sequence played.");
			for(int i=0;i<seq.size();i++) {
				String num = "";
				int e = i+1;
				int j = e % 10, k = e % 100;
				
				if (j == 1 && k != 11) {
					num = e + "st";
				}else if (j == 2 && k != 12) {
					num = e + "nd";
				}else if (j == 3 && k != 13) {
					num = e + "rd";
				}else {
					num = e + "th";
				}
				
				System.out.println("Enter the " + num + " colour: ");
				int input = inputscan.nextInt();
				    
				if(input==seq.get(i)) {
					System.out.println("Nice!");
					nextround = true;
				}else {
					score = round-1;
					System.out.println("Highest score: " + score);
					System.out.println("Game Over!");
					nextround = false;
					run = false;
					break;
				}
			}
			if (nextround) {
				round++;
				seq.add(randnum());
				if(time>=1000) {
					time = time-100;
				}
			}
		}
		//if((seq.get(round)==1) && finch.isTapped()) {
	} 
	
	public static int randnum() {
		Random rand = new Random();
		int randcol = rand.nextInt(4)+1;
		//System.out.println("Random number: " + a);
		return randcol;
	}
	
	public static void replayseq(ArrayList<Integer> a, int duration) {
		for(int i=0;i<a.size();i++) {
			if(a.get(i)==1) {
				finch.setLED(255,0,0,duration);
				finch.buzz(i,10);
			}else if(a.get(i)==2){
				finch.setLED(0,255,0,duration);
				finch.buzz(i,15);
			}else if(a.get(i)==3){
				finch.setLED(0,0,255,duration);
				finch.buzz(i,20);
			}else {
				finch.setLED(255,255,0,duration);
				finch.buzz(i,25);
			}
		}
	}
}
