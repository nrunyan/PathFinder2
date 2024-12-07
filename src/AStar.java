import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AStar implements Pathfinder {
    //estimate distance of each node from the cheese
    //manhattan distance
    //TODO ask TA about G-Score
    private int colmSize;
    private int rowSize;
    private int NUMBEROFNODES;
    private int calculateHValue(int start, int end){
        int x=start%colmSize;
        int y=start/colmSize;
        int endX=end%colmSize;
        int endY=end/colmSize;
        return Math.abs(x-endX)+Math.abs(y-endY);
    }
    public AStar(int colmSize, int rowSize,ParseBoard parseBoard){
        this.colmSize=colmSize;
        this.rowSize=rowSize;
        NUMBEROFNODES=colmSize*rowSize;
        DLinkedList path=aStar(0,4,parseBoard.adjecency);
//        for(int i: parseBoard.stars){
        for (int j = 0; j < parseBoard.stars.size(); j++){
            int i = (int)parseBoard.stars.get(j);
            System.out.println("Star at: "+i);
        }
//        for(int i:path){
        for (int j = 0; j< path.size();j++){
            int i = (int)path.get(j);
            System.out.println(i);
        }

    }

    private DLinkedList aStar(int start, int end,
                              DLinkedList[] adjecency){

        DLinkedList openList=new DLinkedList(); //nodes to be evaluated
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
            int current=(int)openList.get(currentIndex);
            openList.remove(currentIndex);
            alreadyChecked[current]=true;
            if(current==end){
                return reconstruct_path(current,start,parent);
            }
//            for(int neighbor:adjecency.get(current)){
            for (int i =0; i < adjecency[current].size();i++){
                int neighbor = (int) adjecency[current].get(i);
                if(!alreadyChecked[neighbor]){
                    if(!openList.contains(neighbor)){
                        openList.add(neighbor);
                        parent[neighbor]=current;

                    }

                }

            }

        }
        System.out.println("failure");
        return null;
    }
    private DLinkedList reconstruct_path(int current,int start,int [] parent){
        DLinkedList path = new DLinkedList();
        while(!(current ==start)){
            path.push(current);
            current=parent[current];
        }
        path.push(start);
        return path;
    }

    //TODO: change to be about the heristics
    private int smallestIn(DLinkedList list,int[] heristics){
        int min =Integer.MAX_VALUE;
        int indexmin=-1;
        for(int i=0;i<list.size();i++){
            if(heristics[(int)list.get(i)]<min){
                min=heristics[(int)list.get(i)];
                indexmin=i;
            }
        }
        return indexmin;
    }

    public static void main(String [] args){
        char [][] board= ReadConfig.parseFile("testCases/objectTest.txt");
        ParseBoard parseBoard =new ParseBoard(board);
        AStar a=new AStar(board[0].length, board.length,parseBoard);

    }

    @Override
    public int[][] search(DLinkedList[] adj, int source) {
        return new int[0][];
    }

    @Override
    public String getAlgorithmName() {
        return "A*";
    }
}
