public class DLinkedList {
    Node root;
    Node last;
    int size;
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
    public int size(){
        return this.size;
    }
    public void push(Object object){
        Node next = this.root.getNext();
        Node newNode = new Node(object,this.root);
        next.setPrev(newNode);
        this.size++;
        this.root.setNext(newNode);
        newNode.setNext(next);
    }

//    public static void Main(String[] args) {
//        DLinkedList dLinkedList = new DLinkedList();
//        dLinkedList.add(1);
//        dLinkedList.add(2);
//        dLinkedList.add(3);
//        dLinkedList.add(4);
//        System.out.println(dLinkedList.size);
//        System.out.println("");
//        DLinkedList list = new DLinkedList();
//        list.addAll(dLinkedList);
//        System.out.println(list.size);
//        System.out.println("");
//        for(int i = 0; i < list.size(); i++){
//            System.out.println(list.get(i));
//        }
//        System.out.println("");
//        int first = (int) list.pop();
//        System.out.println(first);
//        System.out.println("");
//
//        for(int i = 0; i < list.size(); i++){
//            System.out.println(list.get(i));
//        }
//        System.out.println("");
//        list.push(5);
//        for(int i = 0; i < list.size(); i++){
//            System.out.println(list.get(i));
//        }
//        System.out.println("");
//        System.out.println(list.getFirst());
//        System.out.println("");
//        System.out.println(list.getLast());
//    }
}
