public class Prim {
    public void createMST(DLinkedList[][] adjecency, int size,
                          int numCol,
                          int numRow){
        boolean[] inMST=new boolean[size];
        int [] key=new int[size];
        int [] parent=new int[size];
        int [] path = new int[size];
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

            }
        }


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
}
