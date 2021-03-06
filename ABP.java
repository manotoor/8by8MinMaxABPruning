import java.util.ArrayList;

public class ABP{

  Board initial;
  int depth;
  boolean max;

  //gets the initial board state, the max depth requested, and whether or not to perform max or min operation
  public ABP(Board initial, int depth, boolean max){
    this.initial = initial;
    this.depth = depth;
    this.max = max;
  }

  //calls this to start alpha beta pruning
  public Board initialRun(long time){
    return run(-Double.MAX_VALUE,Double.MAX_VALUE,System.currentTimeMillis() + (time*1000));
  }

  //requires alpha, beta, and end time
  public Board run(double alpha, double beta,long endTime){
    //is the initial Board a finished state? if so break and return the state
    //the utility value would have already been set to +- infinity
    //System.out.println("Depth : "+depth);
    if(initial.isFinishedState()){
      //System.out.println("Finished State Reached : " +initial.getUtilityValue());
      //initial.printBoard();
      return initial;
    }
    //the state is a regular state
    else{
      //if he max depth is reached or the time runs out
      if(depth == 0){
        //updates the current state's utility value to it's estimate value
        initial.setUtilityValue(initial.getEstimateValue());
        return initial;
      }

      //if max operation is performed
      if(max){
        //get the current state's children
        ArrayList<Board>children = initial.getChildren(true);
        //the best state that will be found from the children
        Board bestState = null;
        double bestVal = Double.MAX_VALUE;
        for(Board c : children){

          //set up the alpha beta pruning for the current state
          ABP a = new ABP(c,depth - 1, false);
          //get the aplha beta pruning result of the current state
          Board res = a.run(alpha, beta, endTime);
          res.setDepth(depth);
          //get the resulting state's utility value
          Double value = res.getUtilityValue();
          //if the resulting state is the better than the saved one ; update best vars


          if(value > bestVal || bestState == null){
            bestVal = value;
            bestState = c;
          }
          /*
          if(value == Board.WIN){
            System.out.println("WIN DEPTH: "+res.getDepth() + " | "+res.toString());
          }
          */

          if(bestVal == Board.WIN && value == Board.WIN && res.getDepth() > bestState.getDepth()){
            bestVal = value;
            bestState = c;
          }
          // set alpha to whatever variable is larger
          // between alpha max
          alpha = Math.max(alpha, value);
          // pruning, do not continue
           if (beta <= alpha) {
            break;
          }
          if(System.currentTimeMillis() >= endTime ){
            break;
          }

        }
        // set the moves for the best board found
        //bestState.setMove(bestMove);
        return bestState;
      }
      //min level
      else{
        ArrayList<Board>children = initial.getChildren(false);
        //the best state that will be found from the children
        Board bestState = null;
        double bestVal = Double.MAX_VALUE;
        for(Board c : children){

          ABP a = new ABP(c,depth - 1, true);
          Board res = a.run(alpha, beta, endTime);
          res.setDepth(depth);
          Double value = res.getUtilityValue();
          if(value < bestVal || bestState == null){
            bestVal = value;
            bestState = c;
          }
          /*
          if(value == Board.LOSS){
            System.out.println("LOSS DEPTH: "+res.getDepth() + " | "+res.toString());
          }
          */
          if(bestVal == Board.LOSS && value == Board.LOSS && res.getDepth() > bestState.getDepth()){
            bestVal = value;
            bestState = c;
          }

           beta = Math.min(beta, value);

           // Prune branches that do not lead to better choices
           if (beta <= alpha) {
             break;
           }
           if(System.currentTimeMillis() >= endTime ){
             break;
           }

        }
        // set the moves for the best board found
        //bestState.setMove(bestMove);
        return bestState;
      }
    }
  }
}
