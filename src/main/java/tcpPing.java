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
        System.out.println("Set a timeout in milliseconds: ");
        int timeout = scan.nextInt();
        System.out.println("Set number of requests to send: ");
        int request = scan.nextInt();

        for (int i = 0; i < request; i++) {

            try {
                server = new Socket(pingTo, 80);
                fromServer = new BufferedReader((new InputStreamReader(server.getInputStream())));
                toServer = new DataOutputStream(server.getOutputStream());
                server.setSoTimeout(timeout);


               // System.out.println(server.isConnected());
                toServer.writeBytes("HEAD / HTTP/1.1 " + CRLF);
                toServer.writeBytes("Host: " + pingTo + CRLF + CRLF);

                long time1 = System.currentTimeMillis();


                String response=" ";
                response = fromServer.readLine();
                long time2 = System.currentTimeMillis();
                if(!response.equals(" "))
                System.out.println("Responce time: " + (time2 - time1) + " msec from: " + server.getInetAddress());

                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
