import java.io.*;
import java.net.*;
import java.util.Scanner;

public class tcpPing {
    public static void main(String args[]) {
        final String CRLF = "\r\n";
        Socket server;
        BufferedReader fromServer;
        DataOutputStream toServer;
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter url/ip number to ping ?");
        String pingTo = scan.nextLine();
        try {
            server = new Socket(pingTo, 80);
            fromServer = new BufferedReader((new InputStreamReader(server.getInputStream())));
            toServer = new DataOutputStream(server.getOutputStream());

            System.out.println(server.isConnected());


            toServer.writeBytes("HEAD / HTTP/1.1 " + CRLF);
            toServer.writeBytes("Host: " + pingTo + CRLF +CRLF);
            long time1 = System.currentTimeMillis();



            System.out.println(fromServer.readLine());
            long time2 = System.currentTimeMillis();
            System.out.println("Responce time: "+(time2-time1)+"msec from: "+server.getInetAddress() );

            server.close();
        }catch (IOException e) {
            e.printStackTrace();}
    }
}
