public class Node {
    private Object value;
    private Node next;
    private Node prev;
    private int index;
    public Node(){
        this.value = null;
        this.next = null;
        this.prev = null;

    }
    public Node(Object value,Node prev){
        this.value = value;
        this.prev = prev;
        this.next = null;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
    public Object getValue(){
        return this.value;
    }
    public Node getNext(){
        return this.next;
    }
}
