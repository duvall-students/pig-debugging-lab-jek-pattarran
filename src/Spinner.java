import java.util.Random;

public class Spinner {
	private String[] sections = {"Oink", "Squeal", "Snort", "GRUNT"};
	// This means that "oink" should happen 20% of the time, "Squeal" 50%, etc...
	// I am assuming the probabilities add to 1.
	private double[] probabilities = {.2, .5, .27, .03};
	private Random spinRandom;
	
	public Spinner(){
		spinRandom = new Random();
	}
	
	public String spin(){
		double spinNumber = spinRandom.nextDouble();
		return numToWord(spinNumber);
	}			
	
	/*
	 * Turn the random number into one of the spinner words 
	 * based on the given probabilities.
	 */
	public String numToWord(double spinNumber){	
		int index = 1;
		double low = 0;
		boolean done = false;
		String result = "";
		while(!done && index < 4){
//		while(!done){
//			^^ (Bug 2) makes sure index is less than 4, to prevent arrayOutOfBoundsException
			double high = probabilities[index] + low;
			if(spinNumber > low && spinNumber <= high){
//			if(spinNumber >= low && spinNumber <= high){
//				^^ (Bug 3) makes sure spin number is greater than 0 (low), instead of greater than or 
//						   equal to. Prevents spinNumber from being zero
				result = sections[index];
				done = true;
			}
			else{
				low = high;
				index++;
			}
		}
		return result;
	}

}
