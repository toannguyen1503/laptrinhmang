package doanltm;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Scott
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    static final int PORT=1025;
    private ServerSocket server=null;
    
    public TCPServer(){
       try{
           server=new ServerSocket(PORT);
       }catch(IOException e){
       }
    }
    
    public void action_Them(){
        Socket socket = null;
        int i=0;
        System.out.println("Server Dang Cho ... ");
        try{
            while((socket=server.accept()) != null) {
                new ServerThread(socket, "Client#" +i);
                System.out.printf("Thread for client#%d generating ... %n", i++);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        //new TCPServer().action_DN();
           new TCPServer().action_Them();
    }
}
