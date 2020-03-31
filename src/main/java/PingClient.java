
import javafx.scene.chart.PieChart;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

public class PingClient {
    public static void main(String[] args) {
        InetAddress conn = null;
        DatagramSocket socket = null;
        Scanner scan = new Scanner(System.in);
        System.out.println("Indtast server: ");
        String server = scan.nextLine();
        System.out.println("Indtast port nummer: ");
        int port = scan.nextInt();



        try {
           conn = InetAddress.getByName(server);
           socket = new DatagramSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10 ; i++) {
            long time = System.currentTimeMillis();
            String message = "PING "+i+" "+time+" \n";
            DatagramPacket request = new DatagramPacket(message.getBytes(),message.length(),conn,port);
            try {
                socket.send(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.setSoTimeout(1000);
                DatagramPacket reply = new DatagramPacket(new byte[1024],1024);
                socket.receive(reply);
                long time2 = System.currentTimeMillis();
                printData(reply,time2-time);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static void printData(DatagramPacket reply, long l) {
        byte[] bytes = reply.getData();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        InputStreamReader inputStreamReader = new InputStreamReader(byteArrayInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            String line = bufferedReader.readLine();
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
