package base_core.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * created by ewang on 2018/5/11.
 */
public class ServerMainThread extends Thread {

    private Map<Long, Socket> socketMap;

    protected ServerSocket serverSocket;

    public ServerMainThread(Map<Long, Socket> socketMap) {
        this.socketMap = socketMap;
    }

    @Override
    public void run() {
        try {

            serverSocket = new ServerSocket(7777);
            System.out.println("server is here!");
            while (true) {
                System.out.println("waiting...");
                Socket socket = serverSocket.accept();
                Long socketId = getSocketId(socket);
                System.out.println("here comes a client whose id is " + socketId);
                if (socketId != null) {
                    socketMap.put(socketId, socket);
                    ServerSocketThread serverSocketThread = new ServerSocketThread(socketId, socketMap);
                    serverSocketThread.start();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Long getSocketId(Socket socket) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
            String idStr = bufferedReader.readLine();
            return Long.parseLong(idStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
