public class Prim {
    public void createMST(DLinkedList adjecency, int size){
        boolean[] inMST=new boolean[size];
        int [] distance=new int[size];
        int [] parent=new int[size];
        for (int i=0;i<size;i++) {
            distance[i] = Integer.MAX_VALUE;
            inMST[i] = false;
        }
        distance[0]=0;

    }
}
