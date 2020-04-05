import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class JavaTcpPing {
    private Socket socket;
    private BufferedReader fromServer;
    private DataOutputStream toServer;
    private final String CRLF = "\r\n";
    boolean isConnected;
    private long totalTime;

    public JavaTcpPing(String address) throws IOException {
        socket = new Socket(address,80);
        if (!socket.isConnected()){
            throw new IOException();
        }
        else
            isConnected=true;
        System.out.println("connected");
    }
    public void pingAddress(String address) throws IOException{

        if(isConnected){
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toServer = new DataOutputStream(socket.getOutputStream());

            sendCommand("HEAD / HTTP/1.1 "+ CRLF);
            sendCommand(CRLF);
            sendCommand("Host: " + address + CRLF +CRLF);
            long start = System.currentTimeMillis();
            String fromServer = getFromServer();
            long end = System.currentTimeMillis();
            totalTime = end-start;

            if (hasResponded(5000)){
                readFromServer(fromServer);
                System.out.println("Responce time: "+totalTime+"msec from: "+socket.getInetAddress());
                socket.close();
            }
            else
                System.out.println("no response from server");

        }

    }

    private boolean hasResponded(long delaytime){
        if (totalTime<delaytime){
            return true;
        }
        else
            return false;
    }
    private void sendCommand(String command) throws IOException{
        toServer.writeBytes(command);
    }
    private String getFromServer() throws IOException{
        return fromServer.readLine();
    }
    private void readFromServer(String fromServer)throws IOException{
//        while (fromServer.readLine()!=null){
//            }
        System.out.println(fromServer);
    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Provide the address,you want to ping to.");
        String addr = input.nextLine();
        try {
            JavaTcpPing tcpPing = new JavaTcpPing(addr);
            tcpPing.pingAddress(addr);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
