# MailClient

Your assignment is to create a simple GUI Mail Transfer Agent (MTA) that can send an email. Most of the
program has already been written—it is up to you to fill in the missing pieces

Five java files have been provided for you:
MailClient.java – The user interface
Message.java – Mail message
Envelope.java – SMTP envelope around the Message
SMTPConnection.java – Connection to the SMTP server

The only file that requires substantial revisions is SMTPConnection.java. Comments throughout the source code are
provided to guide you in making the appropriate changes. The changes involve adding the Java socket
programming code and the SMTP protocol logic.

In completing this assignment, you should begin by familiarizing yourself with the codebase. Your first step should
be to find where execution begins (in Java programs, execution always begins in public static void main()) and then
follow the logic of the code chronologically. The program framework has been laid out neatly for you; understand
the structure and work within it. You will find that much of the work is already done for you.

Next hone in on SMTPConnection.java and read through the comments. What role does this class play in the
overall application? What pieces of functionality are missing?

Your first programming task should be to get the code to compile and run. Once it is up and running, you can start
debugging. Add System.out.println() statements containing all read input and written output so the program’s
execution can be easily followed.

Once your code compiles, it is time to start adding the SMTP protocol logic. Research SMTP to find out which
socket the mail server listens on, the sequence of commands in the send mail dialogue, and the meaning of the reply
codes. You might start with the SMTP Request for Comments (RFC)
