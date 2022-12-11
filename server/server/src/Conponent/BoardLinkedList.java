package Conponent;

public class BoardLinkedList {

    static class Node{
        private ChatRoom data;
        public Node link;

        public Node() {
            this.data = null;
            this.link = null;
        }

        public Node(ChatRoom data) {
            this.data = data;
            this.link = null;
        }

        public Node(ChatRoom data, Node front) {
            this.data = data;
            this.link = front;
        }

        public ChatRoom getData() {
            return this.data;
        }
        public void setData(ChatRoom data) {
            this.data = data;
        }
    }

    public static class LinkedList {
        private Node head;

        public LinkedList() {
            head = null;
        }

        public void insertNode (Node preNode, ChatRoom data) {
            Node newNode = new Node(data);
            newNode.link = preNode.link;
            preNode.link = newNode;
        }

        public boolean insertNode (ChatRoom data) {
            if (head == null) {
                this.head = new Node(data);
            } else {
                Node tempNode = head;
                while (tempNode.link != null) {
                    tempNode = tempNode.link;
                }
                tempNode.link = new Node(data);
            }
            return true;
        }

        public void deleteNode(ChatRoom data) {
            Node preNode = head;
            Node tempNode = head.link;

            if (data.equals(preNode.getData())) {
                head = preNode.link;
                preNode.link = null;
            } else {
                while (tempNode != null) {
                    if (data.equals(tempNode.getData())) {
                        if (tempNode.link == null) {
                            preNode.link = null;
                        } else {
                            preNode.link = tempNode.link;
                            tempNode.link = null;
                        }
                        break;
                    } else {
                        preNode = tempNode;
                        tempNode = tempNode.link;
                    }
                }
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

        public ChatRoom searchNode(Option user) {
            Node tempNode = this.head;

            while (tempNode != null) {
                if (tempNode.getData().findUser(user)) {
                    return tempNode.getData();
                } else {
                    tempNode = tempNode.link;
                }
            }
            if (tempNode == null) {
                return new ChatRoom();
            }
            return tempNode.getData();
        }

        public boolean addChat(Option user, String data) {
            Node tempNode = this.head;
            System.out.println("유저 정보: " + user + " 채팅내용: " + data);

            while (tempNode != null) {
                System.out.println("챗팅방 데이터 : " + tempNode.getData().toString());
                if (tempNode.getData().findUser(user)) {
                    tempNode.getData().addChat(user,data);
                    return true;
                } else {
                    tempNode = tempNode.link;
                }
            }
            return false;
        }

        public String[][] toDeepArray() {
            String[][] result = new String[100][];
            Node tempNode = this.head;
            int i = 0;

            while (tempNode != null) {
                result[i] = tempNode.getData().toArrayByDetailInfo();
                tempNode = tempNode.link;
                i++;
            }

            return result;
        }

        public String[][] toDeepArrayByinfo() {
            String[][] result = new String[100][];
            Node tempNode = this.head;
            int i = 0;

            while (tempNode != null) {
                result[i] = tempNode.getData().toArrayByDetailInfo();
                tempNode = tempNode.link;
                i++;
            }

            return result;
        }
    }
}
