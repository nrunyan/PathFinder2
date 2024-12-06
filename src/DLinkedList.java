public class DLinkedList {
    private Node root;
    private Node last;
    private int size;
    public DLinkedList(){
        this.root = new Node();
        this.last = root;
        this.size = 0;
    }
    private Node getRoot(){
        return this.root;
    }
    public void add(Object value){
        Node addedNode;
        addedNode = new Node(value,this.last);
        this.last.setNext(addedNode);
        this.last = addedNode;
        this.size++;
    }
    public Object get(int index){
        Node curr = this.root;
        for(int i = 0; i < index+1; i++) {
            if(curr.getNext()!= null) {
                curr = curr.getNext();
            }else {
                return null;
            }
        }
        return curr.getValue();
    }
    public Object dequeue(){
        Object result = getLast();
        Node prevLast = this.last.getPrev();
        this.last = prevLast;
        prevLast.setNext(null);
        this.size--;
        return result;
    }
    public Object getFirst() {
        return this.root.getNext().getValue();
    }
    public Object getLast() {
        return this.last.getValue();
    }
    public Object pop() {
        Node poppedNode = this.root.getNext();
        Object poppedObj = this.root.getNext().getValue();
        this.root.setNext(poppedNode.getNext());
        poppedNode.getNext().setPrev(this.root);
        this.size--;
        return poppedObj;
    }
    public void addAll(DLinkedList list){
        Node currInList = list.getRoot();
        while(currInList.getNext() != null){
            currInList = currInList.getNext();
            add(currInList.getValue());
        }
    }
    public boolean contains(Object object){
        Node curr = this.root;
        while(curr.getNext() != null){
            if(curr.getValue() == object){
                return true;
            }
            curr = curr.getNext();
        }
        return false;
    }
    public Object remove(int index){
        Object result = get(index);

        if(index == size-1){
            dequeue();
            return result;
        }
        Node curr = this.root;
        for(int i = 0; i < index+1; i++){
            if(curr.getNext() != null){
                curr = curr.getNext();
            }else {
                return null;
            }
        }
        Node prev = curr.getPrev();
        Node next = curr.getNext();
        if(curr.getNext() != null) {
            next.setPrev(prev);
        }
        this.size--;
        prev.setNext(next);
        return result;
    }

    public int size(){
        return this.size;
    }
    public void push(Object object){
        if(this.root.getNext() == null){
            add(object);
            return;
        }
        Node next = this.root.getNext();
        Node newNode = new Node(object,this.root);
        next.setPrev(newNode);
        this.size++;
        this.root.setNext(newNode);
        newNode.setNext(next);
    }
    public int[] toArray(){
        int[] array = new int[size];
        for(int i = 0; i < size;i++){
            array[i] = (int)this.get(i);
        }
        return array;
    }
    public boolean isEmpty(){
        return this.root.getNext() == null;
    }

    public static void main(String[] args) {
        DLinkedList dLinkedList = new DLinkedList();
        dLinkedList.push(1);
        dLinkedList.add(2);
        dLinkedList.add(3);
        dLinkedList.add(4);
        System.out.println(dLinkedList.size);
        System.out.println("");
        DLinkedList list = new DLinkedList();
        list.addAll(dLinkedList);
        System.out.println(list.size);
        System.out.println("");
        for(int i = 0; i < list.size(); i++){
            System.out.print(list.get(i));
        }
        System.out.println("");
        int first = (int) list.pop();
        System.out.println(first);
        System.out.println("");

        for(int i = 0; i < list.size(); i++){
            System.out.print(list.get(i));
        }
        System.out.println("");
        list.push(5);
        list.push(6);
        int size = list.size;

        for(int i = 0; i < size; i++){
            list.push(i);
        }
        for(int i = 0; i < list.size(); i++){
            System.out.print(list.get(i));
        }
        System.out.println("");
        System.out.println(list.getFirst());
        System.out.println("");
        System.out.println(list.getLast());
        System.out.println("");
        list.remove(3);
        list.remove(0);

        for(int i = 0; i < list.size(); i++){
            System.out.print(list.get(i));
        }
        System.out.println("");
        System.out.println(list.size);
        System.out.println("");
        list.remove(7);
        System.out.println(list.getLast());
        System.out.println("");
        list.dequeue();
        System.out.println(list.getLast());

    }
}
