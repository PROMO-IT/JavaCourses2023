package ru.promo.javacore.task;

public class KondorNode<T> {
    private KondorNode<T> next;
    private KondorNode<T> prev;
    private T item;

    public KondorNode() {
        this.next = null;
        this.prev = null;
    }

//    public Node(Node<T> prev) {
//        this.next = null;
//        this.prev = prev;
//    }

    public KondorNode(T item) {
        this.item = item;
    }

    public KondorNode<T> getNext() {
        return next;
    }

    public void setNext(KondorNode<T> next) {
        this.next = next;
    }

    public KondorNode<T> getPrev() {
        return prev;
    }

    public void setPrev(KondorNode<T> prev) {
        this.prev = prev;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
