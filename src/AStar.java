public class AStar {
    private static double calculateHValue(int row, int col,
                                          int[] dest){
        return Math.sqrt((row - dest[0]) * (row - dest[0])
                + (col - dest[1])
                * (col - dest[1]));
    }
}
