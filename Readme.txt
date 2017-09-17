ID: 0051 Bhuvanesh Rajakarthikeyan
Execution Steps:
1.	The project folder can be imported using Eclipse IDE or IntelliJ Idea IDE.
2.	The FILE folder contains three files (whale.jpeg, logan.html, sample.txt) for testing the 	project.
3.	Run the ServerMain.java with the PORT Number as argument.
     i.	Java ServerMain <portno>       =   java ServerMain 6789
4.	The Port Number is optional. By default it is 8080.
5.	In the Web Browser, enter the following addresses for different responses
     i.	 localhost:8080		   =  Default response
     ii.	 localhost:8080/file/logan.html = HTML file will be displayed
     iii.	 localhost:8080/file/whale.jpeg = JPEG file will be displayed
     iv.	 localhost:8080/file/sample.txt = TEXT file will be displayed
      v.	 localhost:8080/file/one.html    = 404 response if file not found
     vi.	 localhost:8080/file/style.css      = 400 response for unsupported file types
6.	To run the Client.java program, we can use the following commands with different arguments,
      i.	Java Client  <serverAddress> <portno> <filename>
For example:       java Client 127.0.0.1 6789 sample.txt
7.	The above can be run without any arguments. For example, 
              java Client  
        Here the default values will be used.
8.	The Client can be run with only one argument which is file name
      i.	Java Client <filename>
                For example: java Client sample.txt


REFERENCES:
http://javarevisited.blogspot.com/2015/09/how-to-read-file-into-string-in-java-7.html

http://javarevisited.blogspot.com/2015/06/how-to-create-http-server-in-java-serversocket-example.html

https://stackoverflow.com/questions/13505130/java-socket-html-response

https://stackoverflow.com/questions/35891427/display-or-download-image-through-the-browser

http://java2s.com/Tutorials/Java/Socket/How_to_use_Java_Socket_class_to_create_a_HTTP_client.htm