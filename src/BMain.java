public class BMain {
    public static void main(String[] args) {

        // TODO Print boards with node labels

        // Load all the boards
        char[][] parsedA = ReadConfig.parseFile("./boards/case-a.txt");
        char[][] parsedB = ReadConfig.parseFile("./testCases/objectTest.txt");
        ParseBoard boardA = new ParseBoard(parsedA);
        ParseBoard boardB = new ParseBoard(parsedB);

        // Run each algorithm on each board and print performance summary
        runAlgorithmsOnBoard(boardA, "case-a", false);
        runAlgorithmsOnBoard(boardB, "objectTest", false);
        runAlgorithmsOnBoard(boardB, "objectTest + return to source", true);
//        BDijkstra dijkstra = new BDijkstra();
//        Telemetry teleDijk = runPathfinder(dijkstra, boardB, 0, "objectTest", false);
//        System.out.print(teleDijk.summarize());
        int test = 3 * 13;
    }

    public static void runAlgorithmsOnBoard(ParseBoard board, String boardName, boolean shouldReturnToSource) {
        Pathfinder dfs = new BDFS();
        Pathfinder bfs = new BBFS();
        Pathfinder prims = new Prim();
        Pathfinder dijkstra = new BDijkstra();

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
    }
    public static Telemetry runPathfinder(Pathfinder algorithm, ParseBoard board, int source, String boardName,
                                          boolean shouldReturnToSource) {
        long startNanos = System.nanoTime();
        // When item i is found, itemVertices[i] is set to -1. Clone so we don't mutate the board's state.
        int[] remainingItems = board.itemVertices.clone();

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
        }

        long endNanos = System.nanoTime();
        long elapsedNanos = endNanos - startNanos;
        Telemetry telemetry = new Telemetry(algorithm.getAlgorithmName(), boardName, path, elapsedNanos);
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
