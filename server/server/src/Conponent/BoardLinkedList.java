package Conponent;

public class BoardLinkedList {
    class Node{
        private String data;
        public Node link;

        public Node() {
            this.data = null;
            this.link = null;
        }

        public Node(String data) {
            this.data = data;
            this.link = null;
        }

        public Node(String data, Node front) {
            this.data = data;
            this.link = front;
        }

        public String getData() {
            return this.data;
        }
    }

    public class LinkedList {
        private Node head;

        public LinkedList() {
            head = null;
        }

        public void insertNode (Node preNode, String data) {
            Node newNode = new Node(data);
            newNode.link = preNode.link;
            preNode.link = newNode;
        }

        public void insertNode (String data) {
            if (head == null) {
                this.head = new Node(data);
            } else {
                Node tempNode = head;
                while (tempNode.link != null) {
                    tempNode = tempNode.link;
                }
                tempNode.link = new Node(data);
            }
        }

        public void deleteNode(String data) {
            Node preNode = head;
            Node tempNode = head.link;

            if (data.equals(preNode.getData())) {
                head = preNode.link;
                preNode.link = null;
            } else {
                
            }
        }

        public void deleteLastNode() {
            Node preNode;
            Node tempNode;
            if (head == null) {
            } else if (head.link == null) {
                head = null;
            } else {
                preNode = head;
                tempNode = head.link;

                while(tempNode.link != null) {
                    preNode = tempNode;
                    tempNode = tempNode.link;
                }
                preNode.link = null;
            }
        }
    }
}
