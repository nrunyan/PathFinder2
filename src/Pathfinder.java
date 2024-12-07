public interface Pathfinder {
    int[][] search(DLinkedList[] adj, int source);

    String getAlgorithmName();
}