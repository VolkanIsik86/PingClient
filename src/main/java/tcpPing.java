import java.io.*;
import java.net.*;
import java.util.Scanner;

public class tcpPing {
    public static void main(String args[]) {

        // simulerer linje skift man sender et request til host.
        final String CRLF = "\r\n";

        // TCP socket allokeres
        Socket server;
        //Disse input og output bruges til at sende request og modtage respond
        BufferedReader fromServer;
        DataOutputStream toServer;
        // Adressen i denne string for at printe den ud.
        String adress = "";
        // Konsol scanner
        Scanner scan = new Scanner(System.in);

        //Indlæser en adresse som man skal pinge til
        System.out.println("Enter url/ip number to ping ?");
        String pingTo = scan.nextLine();
        //Indlæser timeout
        System.out.println("Set a timeout in milliseconds: ");
        int timeout = scan.nextInt();
        //Indlæser antal gange programmet skal pinge/sende request.
        System.out.println("Set number of requests to send: ");
        int request = scan.nextInt();

        //programmet kører i antal ping/request man ønsker at lave
        for (int i = 0; i < request; i++) {

            try {
                //TCP socket instantieres valgt port 80 for at pinge http og domain.
                server = new Socket(pingTo, 80);
                fromServer = new BufferedReader((new InputStreamReader(server.getInputStream())));
                toServer = new DataOutputStream(server.getOutputStream());

                //Sætter en timeout til forbindelsen
                server.setSoTimeout(timeout);
                adress = server.getInetAddress().toString();

               // System.out.println(server.isConnected()); <------------------- fjern denne kommentar for at fejlfinde

                //Her sendes en request til adressen man har forbundet til. Denne head request spørger om alle information uden body fra en server.
                // Det vigtigtste her er ikke om vifår den korrekte besked men bare vi får hvad som helst en fejl besked er også ok.
                // https://tools.ietf.org/html/rfc2616#section-5.1.1
                toServer.writeBytes("HEAD / HTTP/1.1 " + CRLF + CRLF);
               // toServer.writeBytes("Host: " + pingTo + CRLF + CRLF);  <------------------- fjern denne kommentar for at fejlfinde

                //Gemmer den tidspunkt vi har sendt request afsted
                long time1 = System.currentTimeMillis();

                // Gemmer responsen fra server/host adresse
                String response=" ";
                response = fromServer.readLine();

                //Gemmer tiden efter programmet har læst en respons
                long time2 = System.currentTimeMillis();

                //Udeskriver en respons tid i milisekunder og med adressen
                if(!response.equals(" "))
                System.out.println("Responce time: " + (time2 - time1) + " msec from: " + server.getInetAddress());
                else
                    //Udeskriver en respons tid i milisekunder og med adressen hvis respons string ikke ændrer sig
                    System.out.println("Responce time: " + (time2 - time1) + " msec from: " + server.getInetAddress() + "Did not received any response from host");

                //Lukker forbindelsen
                server.close();
            } catch (IOException e) {
                //Hvis timeour bliver overskrivet ville man få denne besked
                if(e.getMessage().equals("Read timed out"))
                System.out.println("Timeout has exceeded! (timeout set to: " + timeout + " for: " + adress + ")");
                else
                    //printer en fejlbesked
                    e.printStackTrace();
            }
            try {
                //Programmet går i 1 sekunders pause for at have en mere overblik over responserne
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
