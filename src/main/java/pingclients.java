// Java Program to Ping an IP address
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.Date;

class NewClass
{
    // Sends ping request to a provided IP address
    public static void sendPingRequest(String ipAddress)
            throws UnknownHostException, IOException
    {
        final String CRLF = "\r\n";
        InetAddress geek = InetAddress.getByName(ipAddress);
        InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(ipAddress),80);
        SocketChannel socketChannel = SocketChannel.open(address);
        System.out.println(socketChannel.isConnected());
        System.out.println(socketChannel.getRemoteAddress());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socketChannel.socket().getInputStream()));
        DataOutputStream dataOutputStream = new DataOutputStream(socketChannel.socket().getOutputStream());
        dataOutputStream.writeBytes("Ping"+"\r\n");
        System.out.println(bufferedReader.readLine());
        String message = "hello"+ CRLF;
        socketChannel.write(ByteBuffer.wrap(message.getBytes()));

        byte[] read = new byte[1024];
        socketChannel.read(ByteBuffer.wrap(read));
        System.out.println(read.toString());



        System.out.println("Sending Ping Request to " + ipAddress);
        if (geek.isReachable(5000))
            System.out.println("Host is reachable");
        else
            System.out.println("Sorry ! We can't reach to this host");
    }

    // Driver code
    public static void main(String[] args)
            throws UnknownHostException, IOException
    {
        String ipAddress = "www.dtu.dk";
        long sek1 = System.currentTimeMillis();
        sendPingRequest(ipAddress);
        long sek2 = System.currentTimeMillis();
        System.out.println(sek2-sek1);

    }
}
