import java.util.Scanner;
public class main{
  public static void main(String args[]){


     //Who is going first, AI or player?
	  boolean playerTurn = false;
	  //Time in Seconds
	  int time = 0;
	  //Create an empty board
	  int c = 1;
     //input, and player move variables
	  Scanner input = new Scanner(System.in);
	  String player;
	  String _move;
      //Ask who is going first, computer or player
	  System.out.print("Would you like to be player one?(y/n)");
	  player = input.next();
	  if(player.toLowerCase().charAt(0) == 'y'){
		  playerTurn = true;
	  }
	  else{
		  playerTurn = false;
	  }

    //testing
    int [][] v = {{ 0, 0, 0, 0, 0, 0, 0, 0},
                  { 0, 0, 0, 0, 0, 0, 0, 0},
                  { 0, 0, 0, 0, 0, 0, 0, 0},
                  { 0, 0, 0, 0, 0, 0, 0, 0},
                  { 0, 0, 0, 0, 0, 0, 0, 0},
                  { 0, 0, 0, 0, 0, 0, 0, 0},
                  { 0, 0, 0, 0, 0, 0, 0, 0},
                  { 0, 0, 0, 0, 0, 0, 0, 0}};
    Board b = new Board(v);
   //How much time should AI have per move?
   System.out.println("\nHow much time should AI have?(s)");
   time = input.nextInt();

   b.printBoard();

   while(!b.isFinishedState()){
		  //If player is first
		  if(playerTurn){
			  System.out.print("\nPlease enter a move: ");
			  _move = input.next();
        int i = _move.toUpperCase().charAt(0)-65;
        int j = Character.getNumericValue(_move.charAt(1)) -1;
        if(i >= 0 && j >= 0 && i < Board.DIM && j < Board.DIM && b.getBoard()[i][j] == 0){
          b.playerMove(_move);
          System.out.println(b.printMove());
  			  b.printBoard();
          playerTurn = false;
        }
        else{
          System.out.println("Invalid Move. Try Again.");
        }
		  }
      if(!playerTurn){
        ABP a = new ABP(b,5,true);
        Board res = a.initialRun(time);
		    int[] move = res.getMove();
        b = res;
  		  b.printBoard();
  		  playerTurn = true;
      }
	  }



	  b.printBoard();
    System.out.println("Game is done");








  }
}
