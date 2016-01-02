package util;

import exception.AndroidConnectionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Created by Thomas on 12/19/2015.
 */
public class AndroidConsoleSession {

    private int port;

    private Socket telnetSocket;

    private PrintWriter out;
    private BufferedReader in;

    public AndroidConsoleSession(int port) {
        this.port = port;
    }

    public AndroidConsoleSession() {
        this(5554);
    }

    public void startSession() throws IOException, AndroidConnectionException {
        try {
            telnetSocket = new Socket("localhost", port);
        }catch (ConnectException e){
            e.printStackTrace();
            // todo couldn't connect to emulator
            throw new AndroidConnectionException();
        }

        out = new PrintWriter(telnetSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(telnetSocket.getInputStream()));
//        System.out.println(in.readLine());
    }

    public void sendGpsCoords(double lat, double lon){
        String command = "geo fix " + lon + " " + lat;
        out.println(command);
        System.out.println(command);
//        try {
//            System.out.println(in.readLine());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void endSession(){
        if(telnetSocket != null){
            try {
                telnetSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
