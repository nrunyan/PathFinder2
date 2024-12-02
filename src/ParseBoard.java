import java.util.*;

public class ParseBoard {
    public List<List<Integer>> adjecency=new ArrayList<>();
    public int[] itemVertices;
    public int itemCount = 0;
    public int amountOfNodes;
    private int colunmSize;
    private int rowSize;
    private final char emptySpace='.';
    private final char items='x';
    private final char obsticles='#';
    public ParseBoard(char[][] board, int amountOfNodes, int colunmSize, int rowSize){
        this.rowSize = board.length;
        this.colunmSize = board.length > 0 ? board[0].length : 0;
        this.amountOfNodes = this.rowSize * this.colunmSize;
//        this.amountOfNodes=amountOfNodes;
//        this.colunmSize=colunmSize;
//        this.rowSize=rowSize;
        // Store the locations of items so we know what we're looking for.
        this.itemVertices = new int[this.amountOfNodes];
        // Initialize the array entries to -1 which signifies no item.
        for (int i = 0; i < this.amountOfNodes; i++) {
            itemVertices[i] = -1;
        }
        createAdjecencyList(board);
    }
    private void createAdjecencyList(char [][] board){
        for(int i=0;i<amountOfNodes;i++){
            adjecency.add(new ArrayList<>());
        }
        for(int row=0;row<rowSize;row++){
            for(int colm=0;colm<colunmSize;colm++){
                int n=row*colunmSize+colm;
                if(!(board[row][colm]=='#')){
                    List<Integer> neighbors=findNeighbors(board,n,row,colm);
                    adjecency.get(n).addAll(neighbors);
                }
                if (board[row][colm] == items) {
                    itemVertices[itemCount] = n;
                    itemCount++;
                }


            }
        }

    }
    private List<Integer> findNeighbors(char [][] board, int n, int row, int colm){
        List<Integer> results=new LinkedList<>();
        int[][] directionsArray={{1,0},{-1,0},{0,1},{0,-1}};
        List<Integer> directionsToUse=new LinkedList<>();
        if(row>0){
            directionsToUse.add(1);
        }
        if(row<rowSize-1){
            directionsToUse.add(0);
        }
        if(colm>0){
            directionsToUse.add(3);
        }
        if(colm<colunmSize-1){
            directionsToUse.add(2);
        }
        for(int i:directionsToUse){
            int rowDiff=directionsArray[i][0];
            int colmDiff=directionsArray[i][1];
            if(board[row+rowDiff][colm+colmDiff]!='#'){
                results.add((row+rowDiff)*colunmSize+(colm+colmDiff));
            }
        }

        return results;
    }


    public static void main (String [] args){
        char [][] board={{'.','.','.','x','.'}, {'.','.','.','.','.'},
                {'.','x','.','.','.'}, {'.','.','.','.','.'},{'.','.','.','.','.'}};
        ParseBoard parseBoard =new ParseBoard(board,25,5,5);
        List<List<Integer>> adjecency=new ArrayList<>();
        for(int i=0;i<4;i++){
            adjecency.add(new ArrayList<>());
        }
        int start = 0, dst = 3;
        System.out.println("path from " + start +
                " to " + dst + " are ");

        // Function for finding the paths
        BreadFirst.findPaths(parseBoard.adjecency, start, dst);
    }
}
