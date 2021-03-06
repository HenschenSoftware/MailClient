/* Author: Dillon Henschen
 * Date: 9/24/2016
 * Project 1 Computer Networks
 * Your Message.java source code
 */

import java.util.*;
import java.text.*;

public class Message {
    /* The headers and the body of the message. */
    public String Headers;
    public String Body;

    /* Sender and recipient. With these, we don't need to extract them
       from the headers. */
    private String From;
    private String To;
    private String Cc;
    
    /* To make it look nicer */
    private static final String CRLF = "\r\n";

    /* Create the message object by inserting the required headers from
       RFC 822 (From, To, Date). */
    public Message(String from, String to, String cc, String subject, String text) {
      /* Remove whitespace */
      From = from.trim();
      To = to.trim();
      Cc = cc.trim();
      Headers = "From: " + From + CRLF;
      Headers += "To: " + To + CRLF;
      if (!Cc.isEmpty()) {
    	  Headers += "Cc: " + Cc + CRLF; 
      }
      Headers += "Subject: " + subject.trim() + CRLF;

      /* A close approximation of the required format. Unfortunately
       only GMT. */
      SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'");
      String dateString = format.format(new Date());
      Headers += "Date: " + dateString + CRLF;
      Body = text;
    }

    /* Two functions to access the sender and recipient. */
    public String getFrom() {
      return From;
    }

    public String getTo() {
      return To;
    }

    public String getCc() {
        return Cc;
    }

    public boolean hasCc() {
    	if (!Cc.isEmpty()) {
    		return true;
    	}
    	return false;
    }
    
    /* Check whether the message is valid. In other words, check that
       both sender and recipient contain only one @-sign. */
    public boolean isValid() {
      	if (hasCc()) {
      		if(!isVaildEmail(Cc)) {
      			System.out.println("Cc address is invalid");
      			return false;
      		}
		}
		if(!isVaildEmail(From)) {
			System.out.println("Sender address is invalid");
			return false;
		}
		if(!isVaildEmail(To)) {
			System.out.println("Recipient address is invalid");
			return false;
		} 
		return true;
    }

    public boolean isVaildEmail(String email) {
    	int emailat = email.indexOf('@');
    	if(emailat < 1 || (email.length() - emailat) <= 1) {
			return false;
		}
		if(emailat != email.lastIndexOf('@')) {
			return false;
		}
		return true;
    }
        
    /* For printing the message. */
    public String toString() {
      String res;
      res = Headers + CRLF;
      res += Body;
      return res;
    }
}