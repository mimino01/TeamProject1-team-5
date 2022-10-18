package com.example.myapplication.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Objects;

public class ServerComponent extends Thread{
    static public String serverIp = "192.168.0.6";
    String host;
    ArrayList data;

    public ServerComponent(String host, ArrayList data) {
        this.host = host;
        this.data = data;
    }

    public ServerComponent() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerComponent that = (ServerComponent) o;
        return host.equals(that.host) && data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, data);
    }

    //Getter Setter
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ArrayList getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }

    public static String getServerIp() {
        return serverIp;
    }

    public static void setServerIp(String serverIp) {
        ServerComponent.serverIp = serverIp;
    }

    @Override
    public void run() {
        String response = "data not include";
        try {
            int port = 5555;
            Socket socket = new Socket(host, port);
            ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
            outstream.writeObject(data);
            outstream.flush();

            ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
            response = (String) instream.readObject();

            socket.close();
            data.clear();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
