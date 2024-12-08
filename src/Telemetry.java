public class Telemetry {
    public String algorithmName;
    public String boardName;
    public DLinkedList path;
    public long runtimeNanos;
    public int nodesExplored;

    public Telemetry(String algorithmName, String boardName, DLinkedList path, long runtimeNanos,int nodesExplored) {
        this.algorithmName = algorithmName;
        this.boardName = boardName;
        this.path = path;
        this.runtimeNanos = runtimeNanos;
        this.nodesExplored=nodesExplored;
    }



    public String summarize() {
        String summary = String.format("Result for algorithm '%s' on '%s':\n", algorithmName, boardName)
                + String.format(" - Shortest Path Length: %d\n", path.size() - 1)
                + String.format(" - Path Taken: %s\n", pathToString(path))
                + String.format(" - Execution Time (nanos): %s\n", runtimeNanos)
                + String.format(" - Memory Usage (nodes explored): %d\n",nodesExplored);
        return summary;
    }

    public static String pathToString(DLinkedList path) {
        int[] pathArray = path.toArray();
        StringBuilder result = new StringBuilder(Integer.toString(pathArray[0]));
        for (int i = 1; i < pathArray.length; i++) {
            result.append(String.format(" -> %d", pathArray[i]));
        }
        return result.toString();
    }

    public String toCSVRow() {
        // NOTE: this is used only to generate data for reporting.
        StringBuilder row = new StringBuilder();
        row.append(algorithmName + ", ");
        row.append(boardName + ", ");
        row.append(String.valueOf(path.size()) + ", ");
        row.append(runtimeNanos + ", ");
        row.append(nodesExplored + "\n");
        return row.toString();
    }
}
