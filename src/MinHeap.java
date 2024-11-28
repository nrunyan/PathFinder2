public class MinHeap {
    public Keyed[] arr = new Keyed[Short.MAX_VALUE];
    public int size = 0;

    public static void main(String[] args) {
        MinHeap heap = new MinHeap();
        heap.insert(new Object(), 7);
        heap.insert(new Object(), 2);
        heap.insert(new Object(), 3);
        heap.insert(new Object(), 5);
        heap.insert(new Object(), 6);
        heap.insert(new Object(), 1);
        heap.insert(new Object(), 4);

        heap.print();
        System.out.println(heap.extractMin());
        System.out.println(heap.extractMin());
        System.out.println(heap.extractMin());
        System.out.println(heap.extractMin());
        System.out.println(heap.extractMin());
        System.out.println(heap.extractMin());
        System.out.println(heap.extractMin());
        System.out.println(heap.extractMin());
    }


    public void insert(Object datum, int key) {
        // The Keyed class just holds whatever object is inserted while
        // attaching a key property we can use for heap ordering.
        // The key is initially set to MAX_VALUE so we can use decreaseKey to
        // bubble the inserted datum into the correct location. decreaseKey
        // will set the provided key.
        Keyed keyedDatum = new Keyed(datum, Integer.MAX_VALUE);
        int i = this.size;
        arr[i] = keyedDatum;
        size++;
        decreaseKey(i, key);
    }

    /**
     * Bubble an element UP the heap.
     */
    public void decreaseKey(int index, int key) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (this.arr[index].key < key) {
            System.out.println("Error: key provided to decreaseKey is greater" +
                    " than the existing key of that element.");
        }
        arr[index].key = key;
        int i = index;
        while (i > 0 && arr[this.parent(i)].key > arr[i].key) {
            swap(i, this.parent(i));
            // i hasn't been updated yet despite the swap, so looking up the
            // parent based on i still works
            i = this.parent(i);
        }
    }

    /**
     * Bubble an element DOWN in the heap.
     */
    public void minHeapify(int index) {
        int smallest;

        if (this.left(index) <= this.size && arr[this.left(index)].key < arr[index].key) {
            smallest = this.left(index);
        } else {
            smallest = index;
        }
        if (this.right(index) <= this.size && arr[this.right(index)].key < arr[smallest].key) {
            smallest = this.right(index);
        }

        if (smallest != index) {
            this.swap(index, smallest);
            this.minHeapify(smallest);
        }

    }

    public Object extractMin() {
        if (size == 0) {
            return null;
        }
        Keyed removed = arr[0];
        arr[0] = arr[this.size - 1];
        this.size--;
        minHeapify(0);
        return removed.key;
    }

    public void swap(int i, int j) {
        Keyed temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public int parent(int index) {
        return (index - 1) / 2;
    }

    public int left(int index) {
        return 2 * index + 1;
    }

    public int right(int index) {
        return 2 * index + 2;
    }

    public void print() {
        int[] keys = new int[this.size];
        for (int i = 0; i < this.size; i++) {
            keys[i] = this.arr[i].key;
        }

        for (int key : keys) {
            System.out.println(key);
        }
    }


    private static class Keyed {
        public Object data;
        public int key;

        public Keyed(Object data, int key) {
            this.data = data;
            this.key = key;
        }
    }
}
