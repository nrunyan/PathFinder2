import java.util.LinkedList;

public class BMain {
    public static void main(String[] args) {

        // Load all the boards
        char[][] parsedA = ReadConfig.parseFile("./boards/case-a.txt");
        ParseBoard boardA = new ParseBoard(parsedA, 30, 6, 5);

        // Test BFS
        DLinkedList path = runBFS(boardA, 0);

        // Test DFS

        // Test Dijkstra

        // Test AStar

        // Test other
    }

    public static DLinkedList runBFS(ParseBoard board, int source) {
        // When item i is found, itemVertices[i] is set to -1.
        int[] remainingItems = board.itemVertices.clone();
        // Remove 0 from remaining items, in case we started on an item.
        arraySetKey(remainingItems, 0, -1);
        DLinkedList path = new DLinkedList();
        path.push(source);

        while (!arrayOnlyHas(remainingItems, -1)) {
            DLinkedList localPath = bfsToNext(board, source, remainingItems);
            localPath.pop();
            path.addAll(localPath);
            source = (int)path.getLast();
        }

        return path;
    }
    public static DLinkedList bfsToNext(ParseBoard board, int source,
                              int[] remainingItems) {
        int[][] bfsResult = BBFS.singleSourceShortestPaths(board.adjecency, source);
        int[] bfsParent = bfsResult[0];
        int[] bfsDist = bfsResult[1];

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
        return pathFromParentArray(bfsParent, shortestIx);
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
