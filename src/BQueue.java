import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

public class BQueue {
    private int head = 0;
    private int tail = 0;
    private final int size = 10000;
    private final int[] array = new int[size];

    public void enqueue(int x) {
        if (tail + 1 == head || (head == 0 && tail == size - 1)) {
            System.out.println("Enqueue failed, queue is full.");
            throw new BufferOverflowException();
        }
        array[tail] = x;
        if (tail == size - 1) {
            tail = 0;
        } else {
            tail++;
        }
    }

    public int dequeue() {
        if (tail == head) {
            System.out.println("Dequeue failed, queue is empty. Check Queue.isEmpty() before calling.");
            throw new BufferUnderflowException();
        }
        int x = array[head];
        if (head == size - 1) {
            head = 0;
        } else {
            head++;
        }

        return x;
    }

    public boolean isEmpty() {
        return head == tail;
    }
}
