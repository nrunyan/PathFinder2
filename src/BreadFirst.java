import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class BreadFirst {

    private static void printPath(List<Integer> path)
    {
        int size = path.size();
        for(Integer v : path)
        {
            System.out.print(v + " ");
        }
        System.out.println();
    }

    private static boolean Legal(int x,
                                 List<Integer> path)
    {
        int size = path.size();
        for(int i = 0; i < size; i++)
            if (path.get(i) == x)
                return false;

        return true;
    }

    public static void findPaths(List<List<Integer> > adjecency, int start, int end) {

        Queue<List<Integer> > queue = new LinkedList<>();

        List<Integer> path = new ArrayList<>();
        path.add(start);
        queue.offer(path);

        while (!queue.isEmpty())
        {
            path = queue.poll();
            int ending = path.get(path.size() - 1);

            if (ending == end)
            {
                printPath(path);
            }


            List<Integer> finalNode = adjecency.get(ending);
            for(int i = 0; i < finalNode.size(); i++)
            {
                if (Legal(finalNode.get(i), path))
                {
                    List<Integer> newpath = new ArrayList<>(path);
                    newpath.add(finalNode.get(i));
                    queue.offer(newpath);
                }
            }
        }
    }

}


