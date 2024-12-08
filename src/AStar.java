public class AStar{
    public static int nodesUnchecked=0;
    public DLinkedList runAStar(int start, int end,
                                DLinkedList[] adjecency,int colmSize){
        return aStar(start,end,adjecency,colmSize);
    }
    private static int calculateHValue(int start, int end,int colmSize){
        int x=start%colmSize;
        int y=start/colmSize;
        int endX=end%colmSize;
        int endY=end/colmSize;
        return Math.abs(x-endX)+Math.abs(y-endY);
    }
    private static DLinkedList aStar(int start, int end,
                              DLinkedList[] adjecency,int colmSize){
        DLinkedList openList=new DLinkedList();
        nodesUnchecked= adjecency.length;//nodes to be evaluated
        openList.add(start);
        int size=adjecency.length;
        boolean[] alreadyChecked=new boolean[size];
        int [] heristics =new int[size];
        int [] parent=new int[size];
        for(int i=0;i<heristics.length;i++){
            heristics[i]=calculateHValue(i,end,colmSize);
        }
        while(!openList.isEmpty()){
            int currentIndex=smallestIn(openList,heristics);
            int current=(int)openList.get(currentIndex);
            openList.remove(currentIndex);
            alreadyChecked[current]=true;
            if(current==end){
                return reconstruct_path(current,start,parent);
            }
            for (int i =0; i < adjecency[current].size();i++){
                int neighbor = (int) adjecency[current].get(i);
                if(!alreadyChecked[neighbor]){
                    if(!openList.contains(neighbor)){
                        nodesUnchecked--;
                        openList.add(neighbor);
                        parent[neighbor]=current;

                    }

                }

            }

        }
        System.out.println("failure");
        return null;
    }
    private static DLinkedList reconstruct_path(int current,int start,int [] parent){
        DLinkedList path = new DLinkedList();
        while(!(current ==start)){
            path.push(current);
            current=parent[current];
        }
        path.push(start);
        return path;
    }

    private static int smallestIn(DLinkedList list,int[] heristics){
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
        char [][] board= ReadConfig.parseFile("testCases/emptyTest.txt");
        DLinkedList stars =new DLinkedList();
        stars.add(3);
        stars.add(7);
        stars.add(11);
        ParseBoard parseBoard =new ParseBoard(board);
        int colmSize=board[0].length;
        DLinkedList path=new DLinkedList();
        int start=0;
        for(int i=0; i<stars.size();i++){
            if(!path.isEmpty()){
                path.dequeue();
            }
            if(path.isEmpty()||!path.contains(stars.get(i))){
                DLinkedList temp=aStar(start,(int)stars.get(i), parseBoard.adjecency, colmSize);
                if(temp!=null){
                    path.addAll(temp);
                }else{
                    System.out.println("null path");
                }

            }
            start= (int) stars.get(i);
        }
        for (int j = 0; j< path.size();j++){
            int i = (int)path.get(j);
            System.out.println(i);
        }

    }

}
