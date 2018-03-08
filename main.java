import java.util.Scanner;
public class main{
  public static void main(String args[]){


    //Who is going , AI or player?
    boolean playerTurn = false;
    boolean firstTurn = true;
    //Time in Seconds
    int time = 0;
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
    //System.out.println(b.getEstimateValue());
    //How much time should AI have per move?


    System.out.println("\nHow much time should AI have?(s)");
    time = input.nextInt();


    while(!b.isFinishedState()){
		  //If player is first
		  if( playerTurn){
			  System.out.print("\nPlease enter a move: ");
			  _move = input.next();
           if(b.validMove(_move)){
			   b.playerMove(_move);
           }else{
            while(!b.validMove(_move)){
               System.out.println("That was not a valid move, please enter a new move: ");
               _move = input.next();
            }
            b.playerMove(_move);
           }
			  b.printBoard();
		  }
      if(firstTurn){
        b = b.getInitialMove();
        b.printBoard();
        System.out.println("My move is:  " + b.printMove());
        firstTurn = false;
        playerTurn = true;
      }else{
        ABP a = new ABP(b,10,true);
        Board res = a.initialRun(time);
		    int[] move = res.getMove();
        b = res;
  		  b.printBoard();
        System.out.println("My move is:  " + b.printMove());

      }
	  }
	  b.printBoard();
    System.out.println("Game is done");



  }
}
