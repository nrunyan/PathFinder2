public class Prim {
    public int[] createMST(DLinkedList[][] adjecency, int size,
                          int numCol,
                          int numRow){
        boolean[] inMST=new boolean[size];
        int [] key=new int[size];
        int [] parent=new int[size];
        for (int i=0;i<size;i++) {
            key[i] = Integer.MAX_VALUE;
            inMST[i] = false;
        }
        key[0]=0;
        parent[0] = -1;

        for(int count = 0; count < size; count++){
            int nextMin = extractMin(key,inMST);
            inMST[nextMin] = true;
            int col = nextMin%numCol;
            int row = nextMin/numCol;
            DLinkedList adjecentTOInMST = adjecency[row][col];
            for(int v = 0; v < adjecentTOInMST.size;v++){
                int currV = (int) adjecentTOInMST.get(v);
                if(inMST[currV] && key[currV] == Integer.MAX_VALUE){
                    parent[currV] = nextMin;
                    key[currV] = 1;
                }
            }
        }
        return parent;
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
        char[][] board = ReadConfig.parseFile("testCases/baseTest.txt");
        ParseBoard parseBoard = new ParseBoard(board,35,7,5);
        Prim prim = new Prim();
//        int[] mst = prim.createMST(parseBoard.adjecency,35,7,5);
    }
}
