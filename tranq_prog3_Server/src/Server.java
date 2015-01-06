import java.io.*;
import java.net.*;
import java.util.*;
/**
This class is the class of server object
@author Quang Tran
*/
public class Server 
{
   private Socket sock;
   private OutputStream out ;
   private int CHUNK_SIZE = 1024;
   
   /**
   This is the main method of Server class
   */
   public static void main(String argv[])
   {
      Server server = new Server();
      server.run();
   }
   
   /**
   This is the run method of server class
   */
   private void run()
   {
      try
      {
         int portNum = 5976;
         ServerSocket servSock = new ServerSocket(portNum);
         System.out.println("Server Running...");
         while(true)
         {
            try
            {
               Date d = new Date();
               System.out.println("Waiting for conenction");
               sock = servSock.accept();
               System.out.println("Got a connection : " + d.toString() );
               System.out.println("Connected to: " 
                                   + sock.getInetAddress() + "   Port: " 
                                   + sock.getPort());
               out = sock.getOutputStream();
               String name = getNullTerminatedString();
               System.out.println("Got file name: " + name);
               Long size = Long.parseLong(getNullTerminatedString());
               System.out.println("File size: " + size);
               getFile(name,size);
               sock.close();
            }
            catch(Exception ex)
            {
               System.out.println(ex.toString());
            }
         }
      }
      catch (Exception ex)
      {
         System.out.println(ex.toString());
      }
   }
   /**
    * This method reads the bytes (terminated by ‘\0’) sent from the Client, one
    * byte at a time, and turns the bytes into a String.
    */ 
   private String getNullTerminatedString()
   {
      String Name = "";
      try
      {
         byte buff[] = new byte[CHUNK_SIZE];
         int count; 
         InputStream in = sock.getInputStream();
         count = in.read(buff,0,1);
         while(  (buff[0]) != 0 )
         {
            Name += new String(buff).trim();
            count = in.read(buff,0,1);
         }
      }
      catch ( Exception ex)
      {
         System.out.println(ex.toString());
      }
      return Name;
   }
   /**
    * This method takes an output file name and its file size, reads the binary
    * data (in a 1024-byte chunk) sent from the Client, and writes to the output
    * file a chunk at a time.
    */
    private void getFile(String filename, long size)
    {
       try
       {
          long totalRead = 0;
          InputStream in = sock.getInputStream();
          FileOutputStream ff = new FileOutputStream (filename);
          byte buff[] = new byte[CHUNK_SIZE];
          while(totalRead < size)
          {
             int numRead = in.read(buff);
             if( numRead != -1)
             {
                ff.write(buff, 0, numRead);
                totalRead += numRead;
             }
             else
             {
                out.write('Q');
                throw new Exception("Dont get the file") ; 
             }
          }
          if(totalRead == size)
          {
             out.write('@');
             System.out.println("Got the file");
          }

       }
       catch ( Exception ex)
       {
          System.out.println(ex.toString());
       } 
    }
}
