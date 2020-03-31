import java.net.*;
import java.nio.channels.SocketChannel;

public class Udp {
    public static void main(String[] args) {
        try {
            Socket connect = new Socket("www.google.com",67);


            DatagramSocket datagramSocket = new DatagramSocket();
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            String test = "test";
            sendData = test.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(sendData,sendData.length,connect.getInetAddress(),connect.getPort());
            DatagramPacket receive = new DatagramPacket(receiveData,receiveData.length);
            datagramSocket.send(datagramPacket);
            datagramSocket.receive(receive);




        } catch(Exception e){
            e.printStackTrace();
        }


    }


}
