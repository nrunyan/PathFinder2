import java.util.List;

public class BBFS {
    private static final int WHITE = 0;
    private static final int GRAY = 1;
    private static final int BLACK = 2;
    public static int[][] singleSourceShortestPaths(List<List<Integer>> adj, int source) {
        int n = adj.size();
        int[] color = new int[n];
        int[] parent = new int[n];
        int[] dist = new int[n];
        for (int i = 0; i < n; i++) {
            color[i] = WHITE;
            parent[i] = -1;
            dist[i] = -1;
        }
        dist[source] = 0;
        color[source] = GRAY;
        BQueue Q = new BQueue();
        Q.enqueue(source);
        while (!Q.isEmpty()) {
            int u = Q.dequeue();
            for (int k = 0; k < adj.get(u).size(); k++) {
                int v = adj.get(u).get(k);
                if (color[v] == WHITE) {
                    color[v] = GRAY;
                    parent[v] = u;
                    dist[v] = dist[u] + 1;
                    Q.enqueue(v);
                }
            }
            color[u] = BLACK;
        }

        return new int[][]{ parent, dist };
    }

}
