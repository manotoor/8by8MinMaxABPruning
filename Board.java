import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Board extends State<Board>{

  public static final double WIN = Double.MAX_VALUE;
  public static final double LOSS = -Double.MAX_VALUE;
   Map rowCol = new HashMap();

  private static final int[][] evalTable = {
    { 0, 0, 0, 0, 0, 0, 0, 0},
    { 0,10,10,10,10,10,10, 0},
    { 0,10,18,18,18,18,10, 0},
    { 0,10,18,20,20,18,10, 0},
    { 0,10,18,20,20,18,10, 0},
    { 0,10,18,18,18,18,10, 0},
    { 0,10,10,10,10,10,10, 0},
    { 0, 0, 0, 0, 0, 0, 0, 0}
  };

  final int DIM = 8;
  //0 == nothing, 1 == player, -1 == computer
  int[][] values;

  public Board(){
  char[] row = {'A','B','C','D','E','F','G','H'};
  for(int i = 0; i < DIM; i++){
    	rowCol.put(row[i],i);
    }
    values = new int[DIM][DIM];
    for(int i = 0; i < DIM; i++){
      for(int j = 0; j < DIM; i++){
        values[i][j] = 0;
      }
    }
    evaluate();
  }

  public String toString(){
    String r = "";
    for(int i = 0; i < DIM; i++){
      for(int j = 0; j < DIM; j++){
        r+= String.valueOf(values[i][j]);
      }
    }
    return r;
  }

  public Board(int[][] v){
   char[] row = {'A','B','C','D','E','F','G','H'};
  for(int i = 0; i < DIM; i++){
    	rowCol.put(row[i],i);
    }
    values = v;
    evaluate();
  }

  public int[][] getBoard(){
    return values;
  }

  //override
  protected void evaluate(){

    if(!endState()){
      heuristic();
    }
  }

  //evaluation sub method to give +- infinity score to sate that have four in a row
  private boolean endState(){
    boolean good = false;
    //looks for 4 of one type horizontally
    for(int i = 0; i < (DIM); i++){
      for(int j = 0; j <= (DIM)/2; j++){
        if(values[i][j] != 0 && !good){
          if(values[i][j] == values[i][j+1] && values[i][j] == values[i][j+2] && values[i][j] == values[i][j+3]){
            if(values[i][j] == 1){
              good = true;
              evaluationValue =  WIN;
              utilityValue = WIN;
            }
            else if(values[i][j] == -1){
              good = true;
              evaluationValue =  LOSS;
              utilityValue = LOSS;
            }
          }
        }
      }
    }
    //looks for 4 of one type vertically
    for(int j = 0; j < (DIM); j++){
      for(int i = 0; i <= (DIM)/2; i++){
        if(values[i][j] != 0 && !good){
          if(values[i][j] == values[i+1][j] && values[i][j] == values[i+2][j] && values[i][j] == values[i+3][j]){
            if(values[i][j] == 1){
              good = true;
              evaluationValue =  WIN;
              utilityValue = WIN;
            }
            else if(values[i][j] == -1){
              good = true;
              evaluationValue =  LOSS;
              utilityValue = LOSS;
            }
          }
        }
      }
    }
    return good;
  }

  //We put the heuiristic function here, and set the value to 'evaluationValue'
  private void heuristic(){
    double sum = 0;
    double type ;

    for(int i = 0; i < (DIM); i++){
      for(int j = 0; j <= (DIM)/2; j++){
        //System.out.println(i + ","+j + " | " + (DIM/4)/(i+1)+ " | " + (DIM/4)/(j+1));
        if(values[i][j] != 0){
          if(values[i][j] == -1){
            type = 8;
          }
          else{
            type = 10;
          }

          sum += (evalTable[i][j])*Math.pow(type,3);

          if(values[i][j] == values[i][j+1]){
            sum += (values[i][j] * Math.pow(type,2));
          }
          if(values[i][j] == values[i][j+1] && values[i][j] == values[i][j+2]){
            sum += (values[i][j] * Math.pow(type,4));
          }
          /*
          if(values[i][j] == values[i][j+1] || values[i][j] == values[i][j+2] || values[i][j] == values[i][j+3]){
            for(int k = 0; k < 4; k++){
              sum += (values[i][j+k] * 1001);
            }
          }
          */
        }
      }
    }
    //looks for 4 of one type vertically
    for(int j = 0; j < (DIM); j++){
      for(int i = 0; i <= (DIM)/2; i++){
        if(values[i][j] != 0){
          if(values[i][j] == -1){
            type = 8;
          }
          else{
            type = 10;
          }
          if(values[i][j] == values[i+1][j]){
            sum += (values[i][j] * Math.pow(type,2));
          }
          if(values[i][j] == values[i+1][j] && values[i][j] == values[i+2][j]){
            sum += (values[i][j] * Math.pow(type,4));
          }
          /*
          if(values[i][j] == values[i+1][j] || values[i][j] == values[i+2][j] ){
            for(int k = 0; k < 4; k++){
              sum += (values[i+k][j] * 1000);
            }
          }
          */
        }
      }
    }
    evaluationValue = sum;
  }


  //override
  //indicates when the state is a finished State
  protected boolean isFinishedState(){
    return (utilityValue == WIN || utilityValue == LOSS);
  }

  /*gets all children states
  @param turn : indicates whose turn it is; computer or player
  */
  protected ArrayList<Board> getChildren(boolean turn){
    int cv;
    if(turn){
      cv = 1;
    }else{
      cv = -1;
    }
    ArrayList<Board> t = new ArrayList<>();

    int[][] temp = copy();

    for(int i =0; i < DIM; i++){
      for(int j = 0; j < DIM; j++){
        if(temp[i][j] == 0){
          int[][] a = copy();
          a[i][j] = cv;
          Board b = new Board(a);
          int[] move = {i,j};
          b.setMove(move);
          t.add(b);
          //b.printBoard();

          //System.out.println(b.getEstimateValue()+ "\n");
        }
      }
    }
    return t;
  }

  private int[][] copy() {
    int[][] child = new int[values.length][];
    for (int i = 0; i < values.length; i++)
    child[i] = Arrays.copyOf(values[i], values.length);
    return child;
  }

  // public void printBoard(){
//     for(int i =0; i < DIM; i++){
//       for(int j = 0; j < DIM; j++){
//         System.out.printf("%2d ",values[i][j]);
//       }
//       System.out.println();
//     }
//   }

   public void printBoard(){
	  String[] rows = {"A","B","C","D","E","F","G","H"};
	  for(int i =0; i <= DIM; i++){
		  if(i == 0)
			  System.out.print("  ");
		  else
			  System.out.print(i + " ");
	  }
	  System.out.println("");
    for(int i =0; i < DIM; i++){
    	System.out.print(rows[i]+" ");
      for(int j = 0; j < DIM; j++){
    	  if(values[i][j] == 0)
    		  System.out.print("- ");
    	  if(values[i][j] == 1)
    		  System.out.print("X ");
    	  if(values[i][j] == -1)
    		  System.out.print("O ");
      }
      System.out.println();
    }
  }
   public void playerMove(String _move) {
	int i = (int) rowCol.get(_move.toUpperCase().charAt(0));
	int j = Character.getNumericValue(_move.charAt(1)) -1;
	values[i][j] = -1;
  evaluate();
}
public void updateBoard(int[] move){
	values[move[0]][move[1]] = 1;
  evaluate();
}
  public String printMove(){
    return move[0] + ","+move[1];
  }


}
