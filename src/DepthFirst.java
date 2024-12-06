import java.util.ArrayList;
import java.util.List;
//keep track of parents, make it full of -1
class DepthFirst {
    static void DFSRec(DLinkedList[] adj,
                       boolean[] visited, int s){
        visited[s] = true;
        System.out.print(s + " ");
//        for (int i : adj.get(s)) {
        for (int i = 0; i < adj[s].size();i++){
            int j = (int)adj[s].get(i);
            if (!visited[j]) {
                DFSRec(adj, visited, j);
            }
        }
    }
    static void DFS(DLinkedList[] adj, int s) {
        boolean[] visited = new boolean[adj.length];
        // Call the recursive DFS function
        DFSRec(adj, visited, s);
    }
    public static void main(String[] args)
    {
        char[][] board = {{'.', '.', '.', 'x', '.'}, {'.', '.', '.', '.', '.'},
                {'.', 'x', '.', '.', '.'}, {'.', '.', '.', '.', '.'}, {'.', '.', '.', '.', '.'}};
        ParseBoard parseBoard = new ParseBoard(board);

        int source = 1;
        System.out.println("DFS from source: " + source);
//        DFS(parseBoard.adjecency, source);
    }
}