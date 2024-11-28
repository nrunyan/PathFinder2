import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dijkstra {
    //create ssp
    private void createSSP(List<List<Integer>> adjecency, int size){
        boolean[] inSSP=new boolean[size];
        int [] distance=new int[size];
        int [] parent=new int[size];
        for (int i=0;i<size;i++) {
            distance[i] = Integer.MAX_VALUE;
            inSSP[i] = false;
        }
        //fill with infinits?
        //provided we are starting at zero
        distance[0]=0;
        //size -1?
        for(int i=0; i<size;i++){
            int smallestIndex=Integer.MAX_VALUE;
            int min=Integer.MAX_VALUE;
            //find the smallest should find zero on the frist run
            for(int n=0; n<size;n++){
                if(distance[n]<min&&!inSSP[n]){
                    min=distance[n];
                    smallestIndex=n;
                }

            }
            inSSP[smallestIndex]=true;
            for(int neighbor:adjecency.get(smallestIndex)){
                if(!inSSP[neighbor]&&distance[smallestIndex]!=Integer.MAX_VALUE
                        &&distance[smallestIndex]+1<distance[neighbor]){
                    distance[neighbor]=distance[smallestIndex]+1;
                    parent[neighbor]=smallestIndex;
                }

            }
            printSolution(distance,size);


        }

    }
    void printSolution(int dist[],int size)
    {
        System.out.println("Node     distance from 0");
        for (int i = 0; i < size; i++)
            System.out.println(i + " \t\t " + dist[i]);
    }
    public static void main (String [] args) {
        char[][] board = {{'.', '.', '.', 'x', '.'}, {'.', '.', '.', '.', '.'},
                {'.', 'x', '.', '.', '.'}, {'.', '.', '.', '.', '.'}, {'.', '.', '.', '.', '.'}};
        ParseBoard parseBoard = new ParseBoard(board, 25, 5, 5);
        Dijkstra dijkstra=new Dijkstra();
        dijkstra.createSSP(parseBoard.adjecency,25);
    }
}
