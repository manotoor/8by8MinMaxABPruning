public class main{
  public static void main(String args[]){


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


    //b.getChildren(true);



    ABP a = new ABP(b, 3, true);
    long start  =System.currentTimeMillis();
    Board res = a.initialRun(5);
    System.out.println(System.currentTimeMillis() - start);
    int[] move = res.getMove();
    res.printBoard();
    System.out.println("Res Val :"+res.getEstimateValue());
    System.out.println(move[0] + "-"+ move[1]);





  }
}
