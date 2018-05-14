package base_core.socket;

import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * created by ewang on 2018/5/12.
 */
@Component
public class StartSocketListener implements ServletContextListener {

    public static Map<Long, Socket> socketMap = new HashMap<>();

    private ServerMainThread serverMainThread;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        serverMainThread = new ServerMainThread(socketMap);
        serverMainThread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            for (Socket client : socketMap.values()) {
                client.close();
            }
            serverMainThread.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
