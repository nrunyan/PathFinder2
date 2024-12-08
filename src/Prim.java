

public class Prim implements Pathfinder {

    public String getAlgorithmName() {
        return "prim";
    };
    public int[][] createMST(DLinkedList[] adjecency,int source){
        int size = adjecency.length;
        int[] dist = new int[size];
        boolean[]inMST=new boolean[size];
        int[]key = new int[size];
        int[]parent = new int[size];
        for (int i = 0; i < size; i++) {
            key[i] = Integer.MAX_VALUE;
            inMST[i] = false;
        }
        key[source] = 0;
        parent[source] = -1;
        dist[source] =0;
        for(int count = 0; count < size; count++){
            int nextMin = extractMin(key,inMST);
            if(nextMin != -1) {
                inMST[nextMin] = true;
                int par= parent[nextMin];
                if(par != -1) {
                    dist[nextMin] = dist[par] + 1;
                }
                DLinkedList adjecentTOInMST = adjecency[nextMin];
                for (int v = 0; v < adjecentTOInMST.size(); v++) {
                    int currV = (int) adjecentTOInMST.get(v);
                    if (!inMST[currV] && key[currV] == Integer.MAX_VALUE) {
                        parent[currV] = nextMin;
                        key[currV] = 1;
                    }
                }
            }else{
                break;
            }
        }
        return new int[][]{parent,dist};
    }
    private int extractMin(int[] key,boolean[] inMST){
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < key.length; i++){
            if(inMST[i]==false && key[i]<min){
                min = key[i];
                minIndex = i;
            }
        }
        return minIndex;
    }
    public static void main(String[] args) {
        char[][] board = ReadConfig.parseFile("testCases/caseA.txt");
        ParseBoard parseBoard = new ParseBoard(board);
        char[] characters = new char[parseBoard.amountOfNodes];
        int count =0;
        for(int i = 0; i < 5;i++){
            for (int j = 0; j<7;j++){
                characters[count] = board[i][j];
                count++;
            }
        }
        Prim prim = new Prim();
        int[] mst = prim.createMST(parseBoard.adjecency,0)[0];
        for(int i =0;i< mst.length;i++){
            System.out.print(mst[i]+" ");
        }
    }

    @Override
    public int[][] search(DLinkedList[] adj, int source) {
        return createMST(adj,source);
    }
}
