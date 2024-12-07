import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dijkstra implements Pathfinder {
    public String getAlgorithmName() {
        return "dijkstra";
    }

    public int[][] search(DLinkedList[] adj, int source) {
        return createSSP(adj, source);
    }

    private static int[][] createSSP(DLinkedList[] adjecency, int source) {
        int size = adjecency.length;
        boolean[] inSSP = new boolean[size];
        int[] distance = new int[size];
        int[] parent = new int[size];
        for (int i = 0; i < size; i++) {
            distance[i] = Integer.MAX_VALUE;
            inSSP[i] = false;
        }
        distance[0] = 0;
        for (int i = 0; i < size; i++) {
            int smallestIndex = Integer.MAX_VALUE;
            int min = Integer.MAX_VALUE;
            for (int n = 0; n < size; n++) {
                if (distance[n] < min && !inSSP[n]) {
                    min = distance[n];
                    smallestIndex = n;
                }

            }
            if (smallestIndex == Integer.MAX_VALUE) {
                break;
            }
            inSSP[smallestIndex] = true;
            for (int j = 0; j < adjecency[smallestIndex].size(); j++) {
                int neighbor = (int) adjecency[smallestIndex].get(j);
                if (!inSSP[neighbor] && distance[smallestIndex] != Integer.MAX_VALUE
                        && distance[smallestIndex] + 1 < distance[neighbor]) {
                    distance[neighbor] = distance[smallestIndex] + 1;
                    parent[neighbor] = smallestIndex;
                }

            }

        }
        return new int[][]{parent, distance};

    }

//    public static void printSolution(int dist[],int size) {
//        System.out.println("Node     distance from 0");
//        for (int i = 0; i < size; i++)
//            System.out.println(i + " \t\t " + dist[i]);
//    }
}
//    private DLinkedList  reconstruct_path(int current,int start,int [] parent){
//        DLinkedList dLinkedList=new DLinkedList();
//        while(!(current ==start)){
//            //TODO:replace with push
//            dLinkedList.push(current);
//            System.out.println("pushing "+current);
//            current=parent[current];
//        }
//        //TODO:replace with push
//        System.out.println("pushing "+start);
//        dLinkedList.push(start);
//        return dLinkedList;
//    }
//    public void makePath(int current, int start, int [] parent){
//        DLinkedList dLinkedList=reconstruct_path(current,start, parent);
//        System.out.println("Path from "+start+" to "+current);
//        for(int i=0; i< dLinkedList.size(); i++) {
//            System.out.println(dLinkedList.get(i));
//        }
//    }
//    public static void main (String [] args) {
//        char [][] board= ReadConfig.parseFile("testCases/objectTest.txt");
//        ParseBoard parseBoard =new ParseBoard(board);
////        Dijkstra dijkstra=new Dijkstra(parseBoard.adjecency,board[0].length* board.length);
////        dijkstra.makePath(11,0);
//
//    }
//    public void makePath(int current, int start){
//        DLinkedList dLinkedList=reconstruct_path(current,start, parent);
//        System.out.println("Path from 0 to 11");
//        for(int i=0; i< dLinkedList.size(); i++){
//            System.out.println(dLinkedList.get(i));
//        }
//    }
//}
