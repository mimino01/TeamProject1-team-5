package com.example.myapplication.server;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activity.Other.MainActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ServerComponent extends Thread {
    static public String serverIp = "192.168.0.10";

    public String[] getRes;
    String host;
    String[] data;
//    Object res = "not yet";
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
        String[][] temp = new String[1][1];
        temp[0][0] = "not yet";
        res = (Object) temp;
        Object original = res;
        int count = 0;
        while (original == res) {
            Log.i(TAG, "waiting");
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count += 1;
            if (count > 20) {
                temp[0][0] = "null";
                res = (Object) temp;
                return res;
            }
        }
        Log.i(TAG,"delay time is " + (count * 0.050) + " second");
        Log.i(TAG,"data is : " + Arrays.deepToString((String[][]) res));
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
