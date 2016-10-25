/* Author: Dillon Henschen
 * Date: 9/24/2016
 * Project 1 Computer Networks
 * Your SMTPConnection.java source code
 * Note: the rest of the source code is attached last
 */

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Open an SMTP connection to a mailserver and send one mail.
 *
 */
public class SMTPConnection {
    
    private static final int SMTP_PORT = 25;
    private static final String CRLF = "\r\n";
    
    private Socket connection;

    private BufferedReader fromServer;
    private DataOutputStream toServer;

    private boolean isConnected = false;

    /* Create an SMTPConnection object. Create the socket and the 
       associated streams. Initialize SMTP connection. */
    public SMTPConnection(Envelope envelope) throws IOException {
      connection = new Socket(envelope.DestAddr, SMTP_PORT);
      fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      toServer = new DataOutputStream(connection.getOutputStream());
 
      /* Read a line from server and check that the reply code is 220.
      	If not, throw an IOException. */
      String readLine = fromServer.readLine();
      if (parseReply(readLine) != 220) {
    	  throw new IOException();
      }

      
      /* SMTP handshake. We need the name of the local machine.
      	Send the appropriate SMTP handshake command. */
      String localhost = connection.getLocalAddress().getHostName();
      sendCommand("HELO " + localhost + CRLF, 250);
      isConnected = true;
    }

    /* Send the message. Write the correct SMTP-commands in the
       correct order. No checking for errors, just throw them to the
       caller. */
    public void send(Envelope envelope) throws IOException {
    	/* Send all the necessary commands to send a message. Call
        	sendCommand() to do the dirty work. Do _not_ catch the
        	exception thrown from sendCommand(). */
    	sendCommand("MAIL FROM:<" + envelope.Sender + ">" + CRLF, 250);
    	sendCommand("RCPT TO:<" + envelope.Recipient + ">" + CRLF, 250);
    	if (envelope.Message.hasCc()) {
    		sendCommand("RCPT TO:<" + envelope.Message.getCc() + ">" + CRLF, 250);
    	}
    	sendCommand("DATA" + CRLF, 354);
    	sendCommand(envelope.Message + CRLF + "." + CRLF, 250);
    }

    /* Close the connection. First, terminate on SMTP level, then
       close the socket. */
    public void close() {
		isConnected = false;
		try {
			sendCommand("QUIT" + CRLF, 221);
			connection.close();
		} catch (IOException e) {
			System.out.println("Unable to close connection: " + e);
			isConnected = true;
		}
    }

    /* Send an SMTP command to the server. Check that the reply code is
       what is is supposed to be according to RFC 821. */
    private void sendCommand(String command, int rc) throws IOException {
    	System.out.println("SENT: " + command);
    	/* Write command to server and read reply from server. */
    	toServer.writeBytes(command);
    	/* Check that the server's reply code is the same as the parameter
			rc. If not, throw an IOException. */
    	String readLine = fromServer.readLine();
    	System.out.println("RCVD: " + readLine);
    	if (parseReply(readLine) != rc) {
    		throw new IOException();
    	}
    	
    }

    /* Parse the reply line from the server. Returns the reply code. */
    private int parseReply(String reply) {
    	Scanner scanner = new Scanner(reply);
    	if (scanner.hasNextInt()) {
    		int nextInt = scanner.nextInt();
    		scanner.close();
    		return nextInt;
    	}
    	scanner.close();
    	return 0;
    }

    /* Destructor. Closes the connection if something bad happens. */
    protected void finalize() throws Throwable {
      if(isConnected) {
        close();
      }
      super.finalize();
    }
}