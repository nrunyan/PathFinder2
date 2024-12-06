import java.util.List;

public class BDFS {
    private static final int WHITE = 0;
    private static final int GRAY = 1;
    private static final int BLACK = 2;
    public static int[][] search(DLinkedList[] adj, int source) {
        // Initialization
        State state = new State(adj.length);
        for (int u = 0; u < adj.length; u++) {
            state.color[u] = WHITE;
            state.parent[u] = -1;
            state.discovered[u] = -1;
            state.finished[u] = -1;
        }
        state.time = 0;

        dfsVisit(adj, source, state);
        // Problem is here: we want to start from the source!
//        for (int u = 0; u < adj.size(); u++) {
//            if (state.color[u] == WHITE) {
//                dfsVisit(adj, u, state);
//            }
//        }

        return new int[][]{ state.parent, state.discovered };
    }

    private static void dfsVisit(DLinkedList[] adj, int u, State state) {
        state.time++;
        state.discovered[u] = state.time;
        state.color[u] = GRAY;
        for (int k = 0; k < adj[u].size(); k++) {
            int v = (int) adj[u].get(k);
            if (state.color[v] == WHITE) {
                state.parent[v] = u;
                dfsVisit(adj, v, state);
            }
        }
        state.time++;
        state.finished[u] = state.time;
        state.color[u] = BLACK;

    }
    private static class State {
        public int[] color;
        public int[] parent;
        public int[] discovered;
        public int[] finished;
        public int time = 1;

        public State(int n) {
            color = new int[n];
            parent = new int[n];
            discovered = new int[n];
            finished = new int[n];
        }
    }
}
