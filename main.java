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
		  c = -1;
	  }
	  else{
		  playerTurn = false;
		  c = 1;
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

    System.out.println("ORiginal Val :"+b.getEstimateValue());

   //How much time should AI have per move?
   System.out.println("\nHow much time should AI have?(s)");
   time = input.nextInt()*1000;
    //b.getChildren(true);
   while(!b.isFinishedState()){
		  //If player is first
		  if(player.toLowerCase().charAt(0) == 'y' || playerTurn){
			  System.out.print("\nPlease enter a move: ");
			  _move = input.next();
			  b.playerMove(_move);
			  b.printBoard();
		  }
        ABP a = new ABP(b,3,true);
        Board res = a.initialRun(5);
		  int[] move = res.getMove();
      //b.updateBoard(move);
      b =res;
		  b.printBoard();
		  playerTurn = true;
	  }
	  b.printBoard();

    // ABP a = new ABP(b, 3, true);
//     long start  =System.currentTimeMillis();
//     Board res = a.initialRun(5);
//     System.out.println(System.currentTimeMillis() - start);
//     int[] move = res.getMove();
//     res.printBoard();
//     System.out.println("Res Val :"+res.getEstimateValue());
//     System.out.println(move[0] + "-"+ move[1]);





  }
}
