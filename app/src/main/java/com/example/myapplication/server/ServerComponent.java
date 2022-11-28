package com.example.myapplication.server;

import static android.content.ContentValues.TAG;

import android.nfc.Tag;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ServerComponent extends Thread{
<<<<<<< HEAD
    static public String serverIp = "192.168.0.10";
    public String[] getRes;
=======
    static public String serverIp = "192.168.45.107";
>>>>>>> origin/new_version
    String host;
    String[] data;
    Object res;
    Socket socket = new Socket();

    public ServerComponent(String host, String[] data) {
        this.host = host;
        this.data = data;
    }

    public ServerComponent(String host) {
        this.host = host;
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

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public static String getServerIp() {
        return serverIp;
    }

    public static void setServerIp(String serverIp) {
        ServerComponent.serverIp = serverIp;
    }

    public Object getRes() {
        return res;
    }

    @Override
    public void run() {
        try {
            int port = 8001;
            socket = new Socket(host, port);
            ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
            outstream.writeObject(data);
            outstream.flush();

            ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
            res = instream.readObject();
            sleep(100);
            Log.i(TAG, "run: " + Arrays.deepToString((String[][]) res));

            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
