import java.util.ArrayList;
import java.util.List;

public class AStar {
    //estimate distance of each node from the cheese
    //manhattan distance
    //TODO ask TA about G-Score
    private int colmSize=5;
    private int rowSize=5;
    private int NUMBEROFNODES=25;
    private int calculateHValue(int start, int end){
        int x=start%colmSize;
        int y=start/colmSize;
        int endX=end%colmSize;
        int endY=end/colmSize;
        return Math.abs(x-endX)+Math.abs(y-endY);
    }

//    boolean isValid(int[][] grid, int rows, int cols,
//                    int current) {
//        int x=current%colmSize;
//        int y=current/colmSize;
//        if (rows > 0 && cols > 0)
//            return (x>= 0) && (x < rows)
//                    && (y >= 0)
//                    && (y < cols);
//
//        return false;
//    }
//
//
//
//    boolean isUnBlocked(int[][] grid, int rows, int cols,
//                        int current) {
//        int x=current%colmSize;
//        int y=current/colmSize;
//        return isValid(grid, rows, cols, current)
//                && grid[x][y] == 1;
//    }


    private ArrayList<Integer>aStar(int start, int end,List<List<Integer>> adjecency){

        ArrayList<Integer> openList=new ArrayList<>(); //nodes to be evaluated
        openList.add(start);
        boolean[] alreadyChecked=new boolean[NUMBEROFNODES];
        int [] heristics =new int[NUMBEROFNODES];
        int [] gScore=new int [NUMBEROFNODES];
        int [] parent=new int[NUMBEROFNODES];
        int currentGScore=0;
        for(int i=0;i<heristics.length;i++){
            heristics[i]=calculateHValue(i,end);
        }
        while(!openList.isEmpty()){
            //find smallest in openList
            int currentIndex=smallestIn(openList,heristics);
            int current=openList.get(currentIndex);
            openList.remove(currentIndex);
            alreadyChecked[current]=true;
            if(current==end){
                return reconstruct_path(current,start,parent);
            }
            for(int neighbor:adjecency.get(current)){
                if(!alreadyChecked[neighbor]){
                    if(!openList.contains(neighbor)){
                        openList.add(neighbor);
                        parent[neighbor]=current;

                    }else{
                        //discard path
                        //something something about g score
                    }

                }

            }

        }
        System.out.println("failure");
        return null;
    }
    private ArrayList<Integer>  reconstruct_path(int current,int start,int [] parent){
        ArrayList<Integer> path =new ArrayList<>();
        while(!(current ==start)){
            //replace with push
            path.addFirst(current);
            current=parent[current];
        }
        //replace with push
        path.addFirst(start);
        return path;
    }

    //TODO: change to be about the heristics
    private int smallestIn(ArrayList<Integer> list,int[] heristics){
        int min =Integer.MAX_VALUE;
        int indexmin=-1;
        for(int i=0;i<list.size();i++){
            if(heristics[list.get(i)]<min){
                indexmin=i;
            }
        }
        return indexmin;
    }

    public static void main(String [] args){
        char [][] board={{'.','.','.','x','.'}, {'.','.','.','.','.'},
                {'.','x','.','.','.'}, {'.','.','.','.','.'},{'.','.','.','.','.'}};
        ParseBoard parseBoard =new ParseBoard(board,25,5,5);
        AStar a=new AStar();
        ArrayList<Integer> path=a.aStar(0,3,parseBoard.adjecency);
        for(int i: parseBoard.stars){
            System.out.println("Star at: "+i);
        }
        for(int i:path){
            System.out.println(i);
        }
    }

}
