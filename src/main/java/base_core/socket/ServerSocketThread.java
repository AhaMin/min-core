package base_core.socket;

import codec.JSONCodec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * created by ewang on 2018/4/19.
 */
public class ServerSocketThread extends Thread {

    private final Long id;
    private final BufferedReader br;
    private Map<Long, Socket> socketMap;

    public ServerSocketThread(Long id, Map<Long, Socket> socketMap) throws IOException {
        this.id = id;
        this.socketMap = socketMap;
        Socket socket = socketMap.get(id);
        this.br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
    }

    @Override
    public void run() {
        String data;
        while ((data = readFromClient()) != null) {
            SocketMessage message = JSONCodec.decode(data, SocketMessage.class);
            long toUserId = message.getToUserId();
            Socket toSocket = socketMap.get(toUserId);
            if (toSocket != null) {
                try {
                    OutputStream outputStream = toSocket.getOutputStream();
                    outputStream.write((JSONCodec.encode(message) + "\n").getBytes("utf-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("server写出消息内容错误");
                }
            }

        }
    }

    public String readFromClient() {
        try {
            String data = br.readLine();
            if (data == null) {
                socketMap.remove(id);
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            socketMap.remove(id);
            return null;
        }
    }
}
