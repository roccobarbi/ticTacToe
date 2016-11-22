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
	private int computer, human, currentMove;
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
		// Zeroes the counters
		timeStart = timeEnd = duration = iterations = currentMove = 0;
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
	
	// Private methods
	
	private int chooseMove(){ // Returns the move by the computer
		int move = 0;
		// Logic
		return move;
	}
	
	private int askMove(){ // Asks the move to the player and validates it
		int move = 0;
		// Logic
		return move;
	}
	
	private boolean validateMove(int move){ // Returns true if the move is legal, false otherwise
		boolean isLegal = false;
		// Logic
		return isLegal;
	}
	
	private int checkVictory(){ // 0 = nobody, or 1, or 2.
		int victor = 0;
		// Logic
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
		// Zeroes the counters
		timeStart = timeEnd = duration = iterations = currentMove = 0;
	}
	
	/**
	 * Plays the game.
	 */
	public void play(){
		// Game Logic
		// Spiega al giocatore chi è il primo (X) e chi è il secondo (O).
		// Decidi chi deve muovere.
		// Fino alla prima mossa valida:
			// Se è il computer
				// chooseMove();
			// Se è il giocatore
				// askMove();
			// In ogni caso: validateMove()
		// checkVictory();
		// Mostra il tabellone
	}

	// main method
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		boolean play = false, repeatInput = true;
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
		while(repeatInput){
			System.out.println("Are you ready to play? [y|n]");
			System.out.println(">:");
			input = keyboard.nextLine();
			if(input.length() == 1){
				switch(input.charAt(0)){
				case 'y':
					play = true;
					repeatInput = false;
					break;
				case 'n':
					play = false;
					repeatInput = false;
					break;
				default:
					System.out.println("ERROR: invalid input.");
					play = false;
					repeatInput = true;
				}
			}
		}
		while(play){
			game.play();
			
			repeatInput = true;
			while(repeatInput){
				System.out.println("Do you want to play again? [y|n]");
				System.out.println(">:");
				input = keyboard.nextLine();
				if(input.length() == 1){
					switch(input.charAt(0)){
					case 'y':
						play = true;
						repeatInput = false;
						break;
					case 'n':
						play = false;
						repeatInput = false;
						break;
					default:
						System.out.println("ERROR: invalid input.");
						play = false;
						repeatInput = true;
					}
				}
			}
		}
	}

}
