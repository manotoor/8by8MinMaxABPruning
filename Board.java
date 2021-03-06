import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import java.util.*;

public class Board extends State<Board>{

  public static final double WIN = Double.MAX_VALUE;
  public static final double LOSS = -Double.MAX_VALUE;
  private int depth = 1;
  Map rowCol = new HashMap();

  public static final int DIM = 8;
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

  public void setDepth(int i){
    depth = i;
  }

  public int getDepth(){
    return depth;
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
            type = 9;
          }
          else{
            type = 10;
          }

          if(values[i][j] == values[i][j+1]){
            for(int k=0; k< 1;k++){
              sum+=(values[i][j+k] *100 *type);
            }
            //sum += (values[i][j] * Math.pow(type,2));
          }
          if(values[i][j] == values[i][j+1] && values[i][j] == values[i][j+2]){
            for(int k=0; k< 2;k++){
              sum+=(values[i][j+k] *500*type);
            }
            // sum += (values[i][j] * Math.pow(type,3));
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
        type = 9;
      }
      else{
        type = 10;
      }

      if(values[i][j] == values[i+1][j]){
        for(int k=0; k< 1;k++){
          sum+=(values[i+k][j] *100*type);
        }
        //sum += (values[i][j] * Math.pow(type,2));
      }
      if(values[i][j] == values[i+1][j] && values[i][j] == values[i+2][j]){
        for(int k=0; k< 2;k++){
          sum+=(values[i+k][j] *500*type);
        }
        //sum += (values[i][j] * Math.pow(type,3));
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
if(depth > 0){
  sum *= depth;
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

  for(int i =0;i < DIM; i++){
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

  Collections.shuffle(t);
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
  int[] m = {i,j};
  setMove(m);
  evaluate();
}

public void updateMove(String _move){
  int i = (int) rowCol.get(_move.toUpperCase().charAt(0));
  int j = Character.getNumericValue(_move.charAt(1)) -1;
  values[i][j] = 1;
  int[] m = {i,j};
  setMove(m);
  evaluate();
}


public boolean validMove(String move){
   int i,j;
   if(rowCol.containsKey(move.toUpperCase().charAt(0))){
      i = (int) rowCol.get(move.toUpperCase().charAt(0));
      j = Character.getNumericValue(move.charAt(1)) -1;
      if(j > 7 || j < 0)
         return false;
      return values[i][j] ==0 ? true:false;
   }else
      return false;
}
public String printMove(){
    char[] row = {'A','B','C','D','E','F','G','H'};
    int j = move[1] +1;
    return row[move[0]] + ","+j;
}

public Board getInitialMove(){
  Board fin = null;
  String[] pm = {"d4","d5","d6","e4","e5","e6"};
  for(String s : pm){
    if(validMove(s)){
      Board t = new Board(copy());
      t.updateMove(s);
      if(fin == null || t.getEstimateValue() > fin.getEstimateValue()){
        fin = t;
      }
    }
  }

  return fin;
}

class Com implements Comparator<State> {


	//compare object needed for the PriorityQueue
	@Override
	public int compare(State p0, State p1) {
		if(p0.getEstimateValue() < p1.getEstimateValue()){
			return -1;
		}
    else if(p0.getEstimateValue() > p1.getEstimateValue()){
		    return 1;
    }
    return 0;
	}

}




}
