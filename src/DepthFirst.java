import java.util.ArrayList;
import java.util.List;
//keep track of parents, make it full of -1
class DepthFirst {
    static void DFSRec(List<List<Integer> > adj,
                       boolean[] visited, int s){
        visited[s] = true;
        System.out.print(s + " ");
        for (int i : adj.get(s)) {
            if (!visited[i]) {
                DFSRec(adj, visited, i);
            }
        }
    }
    static void DFS(List<List<Integer> > adj, int s) {
        boolean[] visited = new boolean[adj.size()];
        // Call the recursive DFS function
        DFSRec(adj, visited, s);
    }
    public static void main(String[] args)
    {
        char[][] board = {{'.', '.', '.', 'x', '.'}, {'.', '.', '.', '.', '.'},
                {'.', 'x', '.', '.', '.'}, {'.', '.', '.', '.', '.'}, {'.', '.', '.', '.', '.'}};
        ParseBoard parseBoard = new ParseBoard(board, 25, 5, 5);

        int source = 1;
        System.out.println("DFS from source: " + source);
        DFS(parseBoard.adjecency, source);
    }
}