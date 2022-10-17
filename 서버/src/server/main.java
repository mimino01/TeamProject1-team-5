package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class main {

	public static void main(String[] args) {
        int portNumber = 5555;

        try {
            System.out.println("������ �����մϴ�...");
            ServerSocket serverSocket = new ServerSocket(portNumber); //��Ʈ��ȣ�� �Ű������� �����ϸ鼭 ���� ���� ����
            System.out.println("��Ʈ " + portNumber + "���� ��û �����...");

            while(true) {
                Socket socket = serverSocket.accept(); //Ŭ���̾�Ʈ�� �������� �� accept() �޼ҵ带 ���� Ŭ���̾�Ʈ ���� ��ü ����
                InetAddress clientHost = socket.getLocalAddress();
                int clientPort = socket.getPort();
                System.out.println("Ŭ���̾�Ʈ �����. ȣ��Ʈ : " + clientHost + ", ��Ʈ : " + clientPort);

                ObjectInputStream instream = new ObjectInputStream(socket.getInputStream()); //������ �Է� ��Ʈ�� ��ü ����
                Object obj = instream.readObject(); // �Է� ��Ʈ�����κ��� Object ��ü ��������
                System.out.println("Ŭ���̾�Ʈ�κ��� ���� ������ : " + obj); // ������ ��ü ���

                ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream()); //������ ��� ��Ʈ�� ��ü ����
                outstream.writeObject(obj + " from server"); //��� ��Ʈ���� ���� �ֱ�
                outstream.flush(); // ���
                socket.close(); //���� ����
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
