
package On_Console;

import java.io.IOException;
import java.net.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Mhaske
 */
public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket=serverSocket;
    }
    
    public void startServer(){
        try {
            JOptionPane.showMessageDialog(null,"Server is ready for connection...");
            while(!serverSocket.isClosed()){
                Socket socket=serverSocket.accept();
                JOptionPane.showMessageDialog(null,"A new client is connected...");
//                System.out.println("A new client is connected!");
                ClientHandler clienthandler=new ClientHandler(socket);
                Thread thread=new Thread(clienthandler);
                thread.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void closeServer(){
        try {
            if(serverSocket!=null){
                serverSocket.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(1234);
        Server server=new Server(serverSocket);
        server.startServer();
        
    }
}
