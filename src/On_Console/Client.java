
package On_Console;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Mhaske
 */
public class Client {
    
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    
    public Client(Socket socket,String username){
        try {
            this.socket=socket;
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username=username;
        } catch (Exception e) {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    
    public void sendMessage(){
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner sc=new Scanner(System.in);
            while(socket.isConnected()){
                String messageToSend=sc.nextLine();
                bufferedWriter.write(username+":"+ messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception e) {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    
    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while(socket.isConnected()){
                    try {
                        msgFromGroupChat=bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    } catch (Exception e) {
                        closeEverything(socket,bufferedReader,bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) throws IOException {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter your Username for the group chat: ");
        String username=sc.nextLine();
        Socket socket=new Socket("127.0.0.1",1234);
        Client client=new Client(socket,username);
        client.listenForMessage();
        client.sendMessage();
    }
}
