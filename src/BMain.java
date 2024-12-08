public class BMain {
    public static void main(String[] args) {

        // TODO Print boards with node labels

        // Load all the boards
        char[][] parsedA = ReadConfig.parseFile("./boards/case-a.txt");
        char[][] parsedB = ReadConfig.parseFile("./testCases/objectTest.txt");
        char[][] parsedGiant = ReadConfig.parseFile("./testCases/giantTest.txt");
        ParseBoard boardA = new ParseBoard(parsedA);
        ParseBoard boardB = new ParseBoard(parsedB);
        ParseBoard boardGiant = new ParseBoard(parsedGiant);

        // Run each algorithm on each board and print performance summary
        printBoard(boardA,parsedA,"case-a");
        runAlgorithmsOnBoard(boardA, "case-a", false);
        printBoard(boardB,parsedB,"objectTest");
        runAlgorithmsOnBoard(boardB, "objectTest", false);
        printBoard(boardB,parsedB,"objectTest + return to source");
        runAlgorithmsOnBoard(boardB, "objectTest + return to source", true);
        printBoard(boardGiant,parsedGiant,"giant board");
        runAlgorithmsOnBoard(boardGiant, "giant board", false);

//        BDijkstra dijkstra = new BDijkstra();
//        Telemetry teleDijk = runPathfinder(dijkstra, boardGiant, 0, "giant", false);
//        System.out.print(teleDijk.summarize());
        int test = 3 * 13;
    }
    public static void printBoard(ParseBoard adj, char[][] board,
                                  String testCase){
        System.out.println(testCase);
       int row = board.length;
       int col = board[0].length;
       int current = 0;
       for (int r = 0;r<row;r++){
           for(int c = 0;c<col;c++){
               System.out.print(board[r][c]+"  ");
           }
           System.out.print(" ");
           for(int c = 0;c<col;c++){
               System.out.printf("%2d ",current);
               current++;
           }
           System.out.println("");
       }
        System.out.println("");
    }

    public static void runAlgorithmsOnBoard(ParseBoard board, String boardName, boolean shouldReturnToSource) {
        Pathfinder dfs = new BDFS();
        Pathfinder bfs = new BBFS();
        Pathfinder prims = new Prim();
        Pathfinder dijkstra = new BDijkstra();
        AStar aStar=new AStar();

        Telemetry bfsResult = runPathfinder(bfs, board, 0, boardName, shouldReturnToSource);
        System.out.print(bfsResult.summarize());
        System.out.print('\n');
        Telemetry dfsResult = runPathfinder(dfs, board, 0, boardName, shouldReturnToSource);
        System.out.print(dfsResult.summarize());
        System.out.print('\n');
        Telemetry primResult = runPathfinder(prims, board, 0, boardName, shouldReturnToSource);
        System.out.print(primResult.summarize());
        System.out.print('\n');
        Telemetry dijkstraResult = runPathfinder(dijkstra, board, 0, boardName, shouldReturnToSource);
        System.out.print(dijkstraResult.summarize());
        System.out.print('\n');
        Telemetry aStarResult = runAStar(aStar, board, 0, boardName, shouldReturnToSource);
        System.out.print(aStarResult.summarize());
        System.out.print('\n');

    }
    public static Telemetry runPathfinder(Pathfinder algorithm, ParseBoard board, int source, String boardName,
                                          boolean shouldReturnToSource) {
        long startNanos = System.nanoTime();
        // When item i is found, itemVertices[i] is set to -1. Clone so we don't mutate the board's state.
        int[] remainingItems = board.itemVertices.clone();
        int nodesExplored= board.itemCount* board.amountOfNodes;

        // Remove 0 from remaining items, in case we started on an item.
        arraySetKey(remainingItems, 0, -1);

        DLinkedList path = new DLinkedList();
        path.push(source);
        int currentPosition = source;

        while (!arrayOnlyContainsNegativeOnes(remainingItems)) {
            DLinkedList localPath = pathfinderToNext(algorithm, board, currentPosition, remainingItems);
            localPath.pop();
            path.addAll(localPath);
            currentPosition = (int) path.getLast();
        }

        if (shouldReturnToSource) {
            DLinkedList localPath = pathfinderToNext(algorithm, board, currentPosition, new int[]{ source });
            localPath.pop();
            path.addAll(localPath);
            currentPosition = (int)path.getLast();
            nodesExplored= (board.itemCount+1)* board.amountOfNodes;
        }

        long endNanos = System.nanoTime();
        long elapsedNanos = endNanos - startNanos;
        Telemetry telemetry = new Telemetry(algorithm.getAlgorithmName(), boardName, path, elapsedNanos,nodesExplored);
        return telemetry;
    }
    public static Telemetry runAStar(AStar aStar,ParseBoard board, int source, String boardName,
                                     boolean shouldReturnToSource){
        long startNanos = System.nanoTime();
        DLinkedList path = new DLinkedList();
        int colmSize=board.colunmSize;
        DLinkedList stars=board.stars;
        for(int i=0; i<stars.size();i++){
            if(!path.isEmpty()){
                path.dequeue();
            }
            if(path.isEmpty()||!path.contains(stars.get(i))){
                DLinkedList temp=aStar.runAStar(source,
                        (int)stars.get(i), board.adjecency,colmSize);
                if(temp!=null){
                    path.addAll(temp);
                }
            }
            source= (int) stars.get(i);
        }
        System.out.println(board.itemCount+" "+ board.amountOfNodes+" "+AStar.nodesUnchecked);
        int nodesExplored= board.itemCount* (AStar.nodesUnchecked);
        if(shouldReturnToSource){
            DLinkedList temp=aStar.runAStar((int)path.get(path.size()-1),
                    0, board.adjecency,colmSize);
//            for(int i= path.size()-2;i>=0;i--){
//                temp.add(path.get(i));
//            }
            temp.pop();
            path.addAll(temp);
        }

        long endNanos = System.nanoTime();
        long elapsedNanos = endNanos - startNanos;
        Telemetry telemetry = new Telemetry("A*", boardName, path, elapsedNanos,nodesExplored);
        return telemetry;
    }
    public static DLinkedList pathfinderToNext(Pathfinder algorithm, ParseBoard board, int source,
                                               int[] remainingItems) {
        int[][] result = algorithm.search(board.adjecency, source);
        int[] bfsParent = result[0];
        int[] bfsDist = result[1];

        int shortestIx = -1;
        int shortestLen = Integer.MAX_VALUE;
        // Find the path to an item with shortest length
        for (int i = 0; i < board.amountOfNodes; i++) {
            if (arrayHasKey(remainingItems, i)) {
                // -1 signifies no path exists
                if (bfsDist[i] != -1 && bfsDist[i] < shortestLen) {
                    shortestIx = i;
                    shortestLen = bfsDist[i];
                }
            }
        }

        // Remove the item we collected from remainingItems, using an array as a ghetto Set
        if (shortestIx != -1) {
            arraySetKey(remainingItems, shortestIx, -1);
        }

        // Reconstruct the path using the parent pointers
        DLinkedList path =  pathFromParentArray(bfsParent, shortestIx);
        int[] pathArray = path.toArray();
        return path;
    }

    public static boolean arrayHasKey(int[] array, int key) {
        for (int i : array) {
            if (i == key) {
                return true;
            }
        }
        return false;
    }
    public static void arraySetKey(int[] array, int key, int newValue) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == key) {
                array[i] = newValue;
            }
        }
    }
    public static boolean arrayOnlyContainsNegativeOnes(int[] array) {
        for (int i : array) {
            if (i != -1) {
                return false;
            }
        }
        return true;
    }
    public static DLinkedList pathFromParentArray(int[] parent, int destination) {
        DLinkedList path = new DLinkedList();
        // Destination == -1 means no path was found, return the empty path.
        if (destination == -1) {
            return path;
        }

        int curr = destination;
        int prev = parent[curr];
        path.push(curr);
        while (prev != -1) {
            path.push(prev);
            curr = prev;
            prev = parent[curr];
        }

        return path;
    }

}
