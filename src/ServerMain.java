import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Paths;

/**
 * Created by Bhuvanesh Rajakarthikeyan ID:0051 on 6/18/2017.
 */

public class ServerMain {
    static int PORT = 8080;            //PORT Number
    static ServerSocket serverSocket = null;
    public static void main(String[] args)
    {
        if(args.length>0)                           //If PORT Number is given by user, store it.
            PORT = Integer.parseInt(args[0]);
        try {
            serverSocket = new ServerSocket(PORT);       //Initialize the Server Socket
            System.out.println("Server listening on PORT: "+PORT);
        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                new ClientThread(serverSocket).start();           //Create a thread for each Client as they request connection to the server and start it.
            } catch (Exception e) {
                System.out.println("I/O error: " + e);
            }

        }
    }
}
class ClientThread extends Thread {
    protected Socket socket;

    public ClientThread(ServerSocket ss) {
        try {
            this.socket = ss.accept();         //Accept the socket request from the client
            System.out.println("New Thread started..");
            System.out.println("\nIncoming Request: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {

        while (true) {
            try {
                InputStreamReader isr = new InputStreamReader(socket.getInputStream());         //To read from Socket Input Stream
                BufferedReader reader = new BufferedReader(isr);
                String line = "";
                String requestGET="";
                while (!(line=reader.readLine()).equals("")) {                    //Read the request from the client
                    System.out.println(line);                                       //Log the request as read
                    if (line.contains("GET")) {
                        requestGET = line;                                              //Store the GET request to prepare response

                    }

                }
                if(requestGET.contains("GET"))                                  //Analyse the GET Request and respond based on that.
                {
                    requestGET = requestGET.split(" ")[1];                  //Split the request to retrieve the filepath requested
                    String[] requestfiletype = requestGET.split("\\.");        //Split the file path to determine the MIME Type
                    if (requestfiletype.length > 1) {                                   //If this array greater than 1 means filepath is specified, else respond with default response
                        requestGET = requestGET.substring(1);                               //Remove the slash at the beginning of the filepath
                        switch (requestfiletype[1]) {                               //Switch Case based on the file type
                            case "html":
                                fileResponse(requestGET, "text/html");          //HTML mime type and the File path
                                break;
                            case "htm":
                                fileResponse(requestGET, "text/html");          //HTML mime type and the File path
                                break;
                            case "jpeg":
                                fileResponse(requestGET, "image/jpeg");          //JPEG mime type and the File path
                                break;
                            case "txt":
                                fileResponse(requestGET,"text/plain");          //TXT mime type and the File path
                                break;
                            case "xml":
                                fileResponse(requestGET,"application/xml");
                                break;
                            case "ico":                                                    //Ignore the favicon.ico requests from the client..
                                break;
                            default:
                                genError(400);                                          //Generate 400 Bad request response if file type not supported
                                break;
                        }
                    } else {
                        fileResponse("file/default.txt","text/plain");      //Default response if localhost:8080 is entered with no file path.
                    }
                }

                reader.close();
                isr.close();
                socket.close();


            }
            catch(SocketException ne)
            {

            }
            catch (Exception e) {
                e.printStackTrace();
            }}
    }

    //This method is used for generating responses for JPEG, HTML, TXT,etc requests.If the file is not found, then 404 response will be sent.
    void fileResponse(String loc, String contentType)
    {
        File f = new File(Paths.get(loc).toString());                   // Create the file object for file name given by the user
        if(f.exists() == true) {                                    //Check if the file exists, else return 404 File Not found

            try {
                File file = new File(loc);
                FileInputStream fis = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];                //Byte array obj for image data
                fis.read(data);                                                       //Image data is retrieved from file and stored onto the byte array
                fis.close();
                OutputStream out = socket.getOutputStream();
                DataOutputStream binaryOut = new DataOutputStream(out);               //Object for writing to socket output stream
                binaryOut.writeBytes("HTTP/1.1 200 OK\r\n");                            //HTTP Response is written in the byte format - Status Line
                binaryOut.writeBytes("Content-Type: "+contentType+"\r\n");              //Mime Type
                binaryOut.writeBytes("Content-Length: " + data.length);                 //Size of the data
                binaryOut.writeBytes("\r\n\r\n");
                binaryOut.write(data);                                                      //The image data in byte array format is written
                binaryOut.close();
                System.out.println("\nResponse: ");                                         //The above response that was written to the socket output stream
                System.out.println("HTTP/1.1 200 OK");                                          //is logged to the Console for displaying the message
                System.out.println("Content-Type: "+contentType);
                System.out.println("Content-Length: " + data.length+"\n");
                System.out.println(new String(data));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            genError(404);
        }
    }



    //This method is used for generating 404 or 400 responses for invalid requests.
    void genError(int err)
    {
        try{
            PrintWriter out = new PrintWriter(socket.getOutputStream());       //PrintWriter obj For writing the response to the client
            String response;
            System.out.println("\nResponse: ");
            if(err==404) {
                response = "<h3>404 File Not Found<h3>";
                out.println("HTTP/1.1 404 Not Found");                              //Status Line of the 404 response is written out to the socket
                System.out.println("HTTP/1.1 404 Not Found");                         //The response is logged on the console
            }
            else
            {
                response="<h3>400 Bad Request<h3>";
                out.println("HTTP/1.1 400 Bad Request");                            //Status Line of the 400 response is written out to the socket
                System.out.println("HTTP/1.1 400 Bad Request");                     //The response is logged on the console
            }
            out.println("Connection: Close");                                       //The message is written out to Socket output stream
            out.println("Content-Type: text/html");                                     //Mime type is html as h3 tag is returned as response
            out.println("Content-Length: "+response.length());
            out.println("");
            out.println(response);
            System.out.println("Connection: Close");                                //The above response is logged on to the console for display.
            System.out.println("Content-Type: text/html");
            System.out.println("Content-Length: "+response.length());
            System.out.println("");
            System.out.println(response+"\n\n");
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}