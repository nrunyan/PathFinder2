import java.util.List;

public class BDijkstra {
    public static int[] shortestPath(List<List<Integer>> adj,
                                     int source, int destination) {
        // Initialization
        Integer[] distances = new Integer[adj.size()];
        Integer[] parents = new Integer[adj.size()];
        for (int i = 0; i < adj.size(); i++) {
            distances[i] = Integer.MAX_VALUE;
            parents[i] = null;
        }
        distances[source] = 0;
        MinHeap Q = new MinHeap();
        for (int i = 0; i < adj.size(); i++) {
            Q.insert(i, distances[i]);
        }
        while (Q.size > 0) {
            int u = (int)Q.extractMin();
            for (int i = 0; i < adj.get(u).size(); i++) {
                int v = adj.get(u).get(i);
                if (distances[v] > distances[u] + 1) {
                    distances[v] = distances[u] + 1;
                    parents[v] = u;
                    Q.decreaseKey(v, distances[v]);
                }
            }
        }

        int[] path = new int[adj.size()];
        Integer j = destination;
        Integer i = parents[destination];
        int step = 0;
        path[step] = j;
        step++;
        while (i != null) {
            path[step] = i;
            j = i;
            i = parents[j];
            step++;
        }

        // TODO: path is in reverse order, need to reverse it
        return path;
    }

    public static void main(String[] args) {
        char[][] board = {
                {'.', '#', '.', 'x', '.'},
                {'.', '.', '.', '.', '.'},
                {'.', 'x', '.', '.', '.'},
                {'.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.'}
        };

        ParseBoard parsedBoard = new ParseBoard(board, 25, 5, 5);
        BDijkstra.shortestPath(parsedBoard.adjecency, 0, 25);
    }
}
