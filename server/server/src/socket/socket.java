package socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import socket.Saver;

public class socket {
	public static void main(String[] args) {
        int portNumber = 8001;
        Saver process = new Saver();
        Socket socket;

        try {
            System.out.println("서버를 시작합니다...");
            ServerSocket serverSocket = new ServerSocket(portNumber); //포트번호를 매개변수로 전달하면서 서버 소켓 열기
            System.out.println("포트 " + portNumber + "에서 요청 대기중...");

            while(true) {
                socket = serverSocket.accept(); //클라이언트가 접근했을 때 accept() 메소드를 통해 클라이언트 소켓 객체 참조
                InetAddress clientHost = socket.getLocalAddress();
                int clientPort = socket.getPort();
                System.out.println("클라이언트 연결됨. 호스트 : " + clientHost + ", 포트 : " + clientPort);

                //클라이언트로 부터 String[]형식으로 데이터 수신
                ObjectInputStream instream = new ObjectInputStream(socket.getInputStream()); //소켓의 입력 스트림 객체 참조
                String[] res = (String[]) instream.readObject();
                
                //테이터 확인
                System.out.println(Arrays.toString(res)); 
                
                //데이터 처리
                Object req;
                String[][] success = new String[40][40];
                switch (res[0]) {
				case "signin":
					System.out.println("processing sign in");
					success[0][0] = Boolean.toString(process.signin(res));
					break;
					
				case "signup":
					System.out.println("processing sign up");
					success[0][0] = Boolean.toString(process.signup(res));
					break;
					
				case "chat":
					System.out.println("processing chatting");
					success = process.chating(res);
					break;
					
				case "review":
					System.out.println("processing review");
					success = process.review(res);
					break;
					
				case "req_userdata":
					System.out.println("processing requist userdata");
					success[0] = process.reqUserdata(res);
					break;
					
				default:
					System.out.println("wrong request");
					success[0][0] = Boolean.toString(false);
					break;
				}
                req = (Object) success;

                System.out.println(Arrays.deepToString(success));
                //클라이언트로 Object 형태로 데이터 송신 
                ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream()); //소켓의 출력 스트림 객체 참조
                outstream.writeObject(req); //출력 스트림에 응답 넣기
                outstream.writeObject(null); //응답 없을시 객체임을 선언해 줌
                outstream.flush(); // 출력
                
                
                socket.close(); //소켓 해제
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
