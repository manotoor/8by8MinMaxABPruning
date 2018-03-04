public class main{
  public static void main(String args[]){


    //testing
    int [][] v = {{0,0,1,0,0,0,0,0},
                  {0,0,1,0,0,0,0,0},
                  {0,0,1,0,0,0,0,0},
                  {0,0,0,0,0,0,0,0},
                  {0,0,0,0,0,0,0,0},
                  {0,0,0,0,0,0,0,0},
                  {0,0,0,0,0,0,0,0},
                  {0,0,0,0,0,0,0,0}};
    Board b = new Board(v);

    System.out.println(b.getEstimateValue());


    //b.getChildren(true);


    ABP a = new ABP(b, 3, true);
    Board res = a.initialRun(20);
    int[] move = res.getMove();
    res.printBoard();
    //System.out.println(move[0] + "-"+ move[1]);




  }
}
