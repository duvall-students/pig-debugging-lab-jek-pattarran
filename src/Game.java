import java.util.Random;

public class Game {
	private Player player1;
	private Player player2;
	private Random die;
	private Spinner spinner;
	private final String LOSER_SPIN = "grunt";
	
	private final int LOSER_ROLL = 1;
	
	public Game(){
		player1 = new GUIPlayer();
		player2 = new ComputerPlayer();
		
//		Player player1 = new GUIPlayer();
//		Player player2 = new ComputerPlayer();
//		^^ (Bug 1) change player1 and player2 to references instead of declaring new variables; 
//		   allows Winner method to access player privately. 
		
		die = new Random();
		spinner = new Spinner();
	}
	
	/*
	 * Method will play one game between the players.
	 */
	public void playGame(){
		printStartGameMessage();
		Player whoseTurn = player1;
		while(!winner()){
			int roundScore = takeATurn(whoseTurn);
			whoseTurn.addToScore(roundScore);
			
			// Switch whose turn it is.
			if(whoseTurn == player1){
				whoseTurn = player2;
			}
			else{
				whoseTurn = player1;
			}
		}
		printEndGameMessage();
	}
	
	/*
	 * One player's turn.  Ends because
	 * - roll a 1
	 * - spin a "GRUNT"
	 * - or Player decides to stop
	 * 
	 * Return the number of points to add to the player
	 */
	public int takeATurn(Player whoseTurn){
		int roundScore = 0;
		boolean keepGoing = true;
		printStartRoundMessage(whoseTurn);
		while(keepGoing){
			int roll = 1 + die.nextInt(6);
//			int roll = die.nextInt(7);
//			(Bug 3) Makes sure roll is above 0 and below 7
			String spin = spinner.spin();
			System.out.println(roll+ " "+ spin);
			
			
			if(roll == LOSER_ROLL && spin.equals(LOSER_SPIN.toUpperCase())){
					System.out.println("Too bad!  Lose all your points.");
					whoseTurn.resetScore();
					return 0; 
				}
//			^^ (Bug 7) Add another if statement handling both cases happening at the same time
			
			if(roll == LOSER_ROLL){
				System.out.println("Lose a turn.");
				return 0;
			}
			if(spin.equals(LOSER_SPIN.toUpperCase())){
//			else if(spin == LOSER_SPIN.toUpperCase()){
//				^^ (Bug 6) .equals is a better way to compare strings than '=='. removing Else if also allows both if statements to be valid
				
				System.out.println("Too bad!  Lose all your points.");
				whoseTurn.resetScore();
				return 0; 
			}
			else{
				roundScore = roundScore + roll;
				System.out.println("Round total is currently: "+roundScore);
				keepGoing = whoseTurn.rollAgain(roundScore);
			}
		}
		return roundScore;
	}
	
	// True if one of the players has won the game.
	public boolean winner(){
		return player1.hasWon() || player2.hasWon();
//		return player1.hasWon() && player2.hasWon();
//		(Bug 4) Allows game to end if either player1 or player2 has won, instead 
//		of both. 
	}
	
	/* 
	 * These methods are for printing messages to the console to follow the game.
	 */
	public void printStartRoundMessage(Player whoseTurn){
		System.out.println("New Round!  "+ whoseTurn.getName()+"'s turn."); 
		System.out.println(player1);
		System.out.println(player2);
	}
	
	public void printEndGameMessage(){
		if(player1.hasWon()){
			System.out.println("Winner is "+player1.getName());
		}
		else{
			System.out.println("Winner is "+player2.getName());
		}
	}
	
	public void printStartGameMessage(){
		System.out.println("Let's Play Pig!");
	}
}
