import java.util.LinkedList;

public class BMain {
    public static void main(String[] args) {

        // Load all the boards
        char[][] parsedA = ReadConfig.parseFile("./boards/case-a.txt");
        char[][] parsedB = ReadConfig.parseFile("./testCases/objectTest.txt");
        ParseBoard boardA = new ParseBoard(parsedA);
        ParseBoard boardB = new ParseBoard(parsedB);

        // Test Pathfinder
        Pathfinder dfs = new BDFS();
        Pathfinder bfs = new BBFS();
        Pathfinder prims = new Prim();

        int[] bfsPath = runPathfinder(bfs, boardB, 0).toArray();
        int[] dfsPath = runPathfinder(dfs, boardB, 0).toArray();
        int[] primPath = runPathfinder(prims, boardB, 0).toArray();
        int test = 3 * 13;
        // old Test BFS
//        int[] bfsPath = runBFS(boardA, 0).toArray();

        // old Test DFS
//        DLinkedList localDfsPath = dfsToNext(boardA, 6, new int[]{14});
//        int[] dfsPath = runDFS(boardA, 0).toArray();

        // Test Dijkstra

        // Test AStar

        // Test other

    }

    public static DLinkedList runPathfinder(Pathfinder algorithm, ParseBoard board, int source) {
        // When item i is found, itemVertices[i] is set to -1. Clone so we don't mutate the board's state.
        int[] remainingItems = board.itemVertices.clone();
        // Remove 0 from remaining items, in case we started on an item.
        arraySetKey(remainingItems, 0, -1);
        DLinkedList path = new DLinkedList();
        path.push(source);

        while (!arrayOnlyHas(remainingItems, -1)) {
            DLinkedList localPath = pathfinderToNext(algorithm, board, source, remainingItems);
            localPath.pop();
            path.addAll(localPath);
            source = (int) path.getLast();
        }

        return path;
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
    public static boolean arrayOnlyHas(int[] array, int key) {
        for (int i : array) {
            if (i != key) {
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
