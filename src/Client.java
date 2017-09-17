import java.io.*;
import java.net.Socket;


/**
 * Created by Bhuvanesh Rajakarthikeyan ID:1001410051 on 6/18/2017.
 */
public class Client {

    static int PORTnum=0;
    Socket socket = null;
    static String serverAddress="";
    Thread t;

    public static void main(String[] args) {
        String fileName="";
        //Condition where all the three arguments are provided by the user - Server Address, Port Number, File Name
        if(args.length>1) {
            PORTnum = Integer.parseInt(args[1]);
            serverAddress = args[0];
            fileName=args[2];
        }
        else if(args.length==1)                                 //Condition where only the file name was provided by the user
        {
            PORTnum = 8080;
            serverAddress ="127.0.0.1";
            fileName=args[0];
        }
        else                                                   //None of the arguments are not provided, Hence the default values are assigned
        {
            PORTnum = 8080;
            serverAddress ="127.0.0.1";
            fileName="default.txt";
        }
        Client obj = new Client();
        obj.request(serverAddress,""+PORTnum,fileName);         //Request the server
        obj.response();                                                     //Reading the response from the server
        System.exit(0);
    }

    Client() {

        try {
            socket = new Socket(serverAddress, PORTnum);                //Initialize the socket object
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void request(String serverAddress,String Portnum,String fileName) {
        try {

                System.out.println("Request...");                                   //Log the request from the client onto the console.
                System.out.println("GET /file/"+fileName+" HTTP/1.1");              //filename as given by the user
                System.out.println("Host: "+serverAddress+":"+Portnum);             //Server and Port assigned from the main method
                System.out.println("Connection: Close");
                System.out.println("User-Agent: Java/1.8");
                System.out.println("");
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);   //Object for writing into Socket output stream
                out.println("GET /file/"+fileName+" HTTP/1.1");                             //filename as given by the user
                out.println("Host: "+serverAddress+":"+Portnum);                            //Server and Port assigned from the main method
                out.println("Connection: Close");
                out.println("User-Agent: Java/1.8");
                out.println("");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void response()
    {
        try {
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());             //Reading from socket input stream
            BufferedReader reader = new BufferedReader(isr);
            String line = null;

            boolean loop = true;
            StringBuilder sb = new StringBuilder(8096);                         //String builder object to store the read data.
            while (loop) {
                if (true) {
                    int i = 0;
                    while (i != -1) {                                   //if reader reaches the end, it returns -1, so the loop will end.
                        i = reader.read();
                        sb.append((char) i);
                    }
                        loop=false;
                }
            }
            System.out.println("\nIncoming Message:\n");
            System.out.println(sb.toString());                              //Logging the response onto the console for viewing
            reader.close();
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

