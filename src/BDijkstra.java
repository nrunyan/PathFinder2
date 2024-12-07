import java.util.List;

public class BDijkstra implements Pathfinder {

    public String getAlgorithmName() {
        return "dijkstra";
    }

    public int[][] search(DLinkedList[] adj, int source) {
        return shortestPath(adj, source);
    }

    public static int[][] shortestPath(DLinkedList[] adj,
                                     int source) {
        // Initialization
        int[] distances = new int[adj.length];
        int[] parents = new int[adj.length];
        for (int i = 0; i < adj.length; i++) {
            distances[i] = Integer.MAX_VALUE;
            parents[i] = -1;
        }
        distances[source] = 0;
        MinHeap Q = new MinHeap();
        for (int i = 0; i < adj.length; i++) {
            Q.insert(i, distances[i]);
        }
        while (Q.size > 0) {
            int u = (int)Q.extractMin();
            for (int i = 0; i < adj[u].size(); i++) {
                int v = (int)adj[u].get(i);
                if (distances[v] > distances[u] + 1) {
                    distances[v] = distances[u] + 1;
                    parents[v] = u;
                    Q.decreaseKeyById(v, distances[v]);
                }
            }
        }

        return new int[][]{ parents, distances };

//        int[] path = new int[adj.length];
//        Integer j = destination;
//        Integer i = parents[destination];
//        int step = 0;
//        path[step] = j;
//        step++;
//        while (i != null) {
//            path[step] = i;
//            j = i;
//            i = parents[j];
//            step++;
//        }
//
//        // TODO: path is in reverse order, need to reverse it
//        return path;
    }
}
