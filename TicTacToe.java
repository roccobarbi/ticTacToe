package ticTacToe;

import java.util.Scanner;

/**
 * Implements a program that plays tic tac toe against a human player.
 * The program represents the board as a single array, row-wise from the top left.
 * 0 means that the slot is empty, 1 that the first player moved there, 2 that the second player moved there.
 * 
 * Accepts a --verbose argument to show more information about how the computer calculated a move.
 * 
 * @author Rocco Barbini
 *
 */
public class TicTacToe {
	// Constants
	private static final int BOARD_SIZE = 3; // Lato del tabellone
	
	// Private fields
	private int board[];
	private int computer, human; // Each holds the number that identifies each player (1 or 2)
	private int currentMove, winner; // Identify who should move now and who won the game
	private long timeStart, timeEnd, duration, iterations;
	
	
	// Constructors
	public TicTacToe(){
		int boardLength = (int) Math.pow(BOARD_SIZE, 2);
		board = new int[boardLength];
		for(int i = 0; i < boardLength; i++){
			board[i] = 0;
		}
		// Choose randomly who plays first.
		computer = (int) Math.round(Math.random()) + 1;
		human = computer == 1 ? 2 : 1;
		winner = 0;
		// Zeroes the counters
		timeStart = timeEnd = duration = iterations = 0;
		currentMove = 1;
	}
	
	// Accessors
	
	public int[] getBoard(){
		int[] tempBoard = new int[board.length];
		for(int i = 0; i < board.length; i++){
			tempBoard[i] = board[i];
		}
		return tempBoard;
	}
	
	public long getTimeStart(){
		return timeStart;
	}
	
	public long getTimeEnd(){
		return timeEnd;
	}
	
	public long getDuration(){
		return duration;
	}
	
	public long getIterations(){
		return iterations;
	}
	
	public int getComputer(){
		return computer;
	}
	
	public int getHuman(){
		return human;
	}
	
	public int getWinner(){
		return winner;
	}
	
	private void setTimeStart(long timeNew){
		timeStart = timeNew;
	}
	
	private void setTimeEnd(long timeNew){
		timeEnd = timeNew;
	}
	
	private void setDuration(){
		if(timeEnd > timeStart) duration = timeEnd - timeStart;
		else duration = 0;
	}
	
	// Private methods
	
	private int chooseMove(){
		int move = board.length / 2; // Default move
		float currentValue = 0, maxValue = 0; // the current and maximum tree evaluation up to now
		int tempBoard[] = new int[board.length];
		setTimeStart(System.currentTimeMillis()); // Log the start time of the evaluation phase
		for(int i = 0; i < board.length; i++){ // This will be used by evaluateTree
			tempBoard[i] = board[i];
		}
		for(int i = 0; i < board.length; i++){
			if(validateMove(i)){
				currentValue = evaluateTree(i, 0, tempBoard);
				if(currentValue > maxValue){
					move = i;
					maxValue = currentValue;
				}
				else if(maxValue == 0 && !validateMove(move)){
					move = i;
				}
			}
		}
		setTimeEnd(System.currentTimeMillis()); // Log the end time of the evaluation phase
		setDuration();
		return move;
	}
	
	/* 
	 * Evaluate tree: takes a move and returns the score for the subsequent decision tree
	 * At each step:
	 * - an invalid move returns 0;
	 * - a move that puts the opponent in a winning position returns 0;
	 * - an opponent's winning move returns -1;
	 * - a winning move returns 1;
	 * - anything else returns the sum of what the donwstream moves returned;
	 * - exception: the opponent's move returns that divided by 2 to account for distance.
	 */
	private float evaluateTree(int move, int depth, int[] innerBoard){
		// depth 0 = computer's first move move, 1 = opponent's first move, 2...
		float value = 0; // tree starting value is always 0
		float solution = 0;
		if(depth % 2 == 0){ // The computer is playing
			if(checkVictory(innerBoard) == computer){
				return 1;
			}
			for(int i = 0; i < innerBoard.length; i++){
				if(innerBoard[i] == 0){ // the move is playable
					innerBoard[i] = computer;
					solution = evaluateTree(i, depth + 1, innerBoard);
					if(solution < 0){
						innerBoard[i] = 0; // Reset
						return 0; // The opponent wins, this counts as an invalid move to me
					}
					else{
						innerBoard[i] = 0; // Reset for the next iterations
						value += solution;
					}
				}
			}
		}
		else{ // The opponent is playing
			if(checkVictory(innerBoard) == human){
				return -1;
			}
			for(int i = 0; i < innerBoard.length; i++){
				if(innerBoard[i] == 0){ // the move is playable
					innerBoard[i] = human;
					solution = evaluateTree(i, depth + 1, innerBoard);
					if(solution < 0){
						innerBoard[i] = 0; // Reset
						return 0; // ERROR
					}
					else{
						innerBoard[i] = 0; // Reset for the next iterations
						value += solution / 2; // To account for distance
					}
				}
			}
		}
		return value;
	}
	
	private int askMove(){ // Asks the move to the player and validates it
		int move = 0;
		boolean isValidMove = false;
		Scanner keyboard = new Scanner(System.in);
		while(!isValidMove){
			System.out.println();
			System.out.println("Inserisci la tua mossa e premi invio:");
			System.out.print(">: ");
			if(keyboard.hasNextInt()){
				move = keyboard.nextInt() - 1;
				isValidMove = validateMove(move);
				if(!isValidMove) System.out.println("Mossa non valida.");
			}
			else{
				System.out.println("ERRORE: input non valido. Devi inserire il numero di una casella da 1 a 9");
			}
		}
		return move;
	}
	
	private boolean validateMove(int move){ // Returns true if the move is legal, false otherwise
		boolean isLegal = false;
		if(move >= 0 && move < board.length && board[move] == 0)
			isLegal = true;
		return isLegal;
	}
	
	private int checkVictory(int[] innerBoard){ // 0 = nobody, or 1, or 2.
		int victor = 0;
		if((innerBoard[0] != 0) && // check first diagonal
				innerBoard[0] == innerBoard[4] &&
				innerBoard[0] == innerBoard[8]){
			victor = innerBoard[0];
		}
		if(victor == 0 && (innerBoard[2] != 0) && // check second diagonal
				innerBoard[0] == innerBoard[4] &&
				innerBoard[0] == innerBoard[6]){
			victor = innerBoard[0];
		}
		if(victor == 0){ // Check rows
			for(int i = 0; i < 7; i += 3){
				if((innerBoard[i] != 0) &&
						innerBoard[i] == innerBoard[i + 1] &&
						innerBoard[i] == innerBoard[i + 2]){
					victor = innerBoard[i];
				}
			}
		}
		if(victor == 0){ // check columns
			for(int i = 0; i < 3; i++){
				if((innerBoard[i] != 0) &&
						innerBoard[i] == innerBoard[i + 3] &&
						innerBoard[i] == innerBoard[i + 6]){
					victor = innerBoard[i];
				}
			}
		}
		return victor;
	}
	
	// Public methods
	/**
	 * Resets the game.
	 */
	public void reset(){
		int boardLength = (int) Math.pow(BOARD_SIZE, 2);
		board = new int[boardLength];
		for(int i = 0; i < boardLength; i++){
			board[i] = 0;
		}
		// Choose randomly who plays first.
		computer = (int) Math.round(Math.random()) + 1;
		human = computer == 1 ? 2 : 1;
		winner = 0;
		// Zeroes the counters
		timeStart = timeEnd = duration = iterations = 0;
		currentMove = 1;
	}
	
	/**
	 * Plays the game.
	 */
	public void play(){
		if(computer < human){
			System.out.println("Il computer gioca per primo con il numero " + computer);
			System.out.println("Tu giochi per secondo con il numero " + human);
		}
		else{
			System.out.println("Tu giochi per primo con il numero " + human);
			System.out.println("Il computer gioca per secondo con il numero " + computer);
		}
		while(winner == 0){ // Continua a giocare finché non emerge un vincitore
			if(currentMove == human){
				// ask move
			}
			else{
				// choose move
			}
			// validate move
			winner = checkVictory(board);
			// show current board
			currentMove = currentMove == 1 ? 2 : 1; // New turn, new player.
		}
	}

	// main method
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		boolean play = true, repeatInput = true;
		String input;
		TicTacToe game = new TicTacToe();
		
		System.out.println("This program is going to play tic tac toe with you.");
		System.out.println("Each slot on the board is represented by a number, like this:");
		System.out.println("1 | 2 | 3");
		System.out.println("---------");
		System.out.println("4 | 5 | 6");
		System.out.println("---------");
		System.out.println("7 | 8 | 9");
		System.out.println("When your turn is called, you will have to enter your move and press ENTER.");
		
		while(play){
			game.play();
			// Check if the player wants to play again. Default: yes.
			repeatInput = true;
			while(repeatInput){
				System.out.println("Do you want to play again? [y|n]");
				System.out.print(">:");
				input = keyboard.nextLine();
				if(input.length() > 0){
					switch(input.toLowerCase().charAt(0)){
					case 'n':
						play = false; // No need to break, case 'y' has only one condition and it's good for 'n' too
					case 'y':
						repeatInput = false;
						break;
					default:
						System.out.println("ERROR: invalid input.");
						repeatInput = true;
					}
				}
				else{
					System.out.println("ERROR: invalid input.");
					repeatInput = true;
				}
			}
			if(play) game.reset(); // Only if the user plays again, reset the board for the next iteration.
		}
	}

}
