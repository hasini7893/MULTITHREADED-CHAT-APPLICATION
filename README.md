# MULTITHREADED-CHAT-APPLICATION

*COMPANY*:CODTECH IT SOLUTIONS

*NAME*:MOTHUKURI HASINI

*INTERN ID*:CITS0D169

*DOMAIN*:JAVA

*DURATION*:6 WEEKS

*MENTOR*:NEELA SANTOSH

## DESCRIPITION ##
Description: Java Socket Programming Multi-User Chat Application

Using TCP sockets, this Java application mimics a basic multi-user chat system that enables real-time communication between numerous clients via a central server. ChatApp, ChatServer, and ChatClient are the three primary parts of the client-server architecture code.

1. Main Class ChatApp
The entry point is this class. The user is prompted to select one of two modes:

Server Mode (1): Enables the server to receive connections from clients.

Client Mode (2): Launches a chat program that establishes a connection with the server.
ChatServer
On port 12345, the server watches for incoming client connections.
Upon a client's connection:

To handle that client, a new ClientHandler thread is created.

A shared list of connected clients now includes the ClientHandler.

To manage concurrent access, this list is synchronized.

3. Threaded Class ClientHandler
To enable simultaneous communication, each client connection is managed in a different thread.

reads the client's incoming messages.

broadcasts the message to every other client that is connected.

Notifies others and removes the client when disconnected.Client Chat
Using IP localhost and port 12345, a client establishes a connection with the server.

Two threads are created:

One to read server messages (receive thread).

One to transmit user-typed messages to the server.

It enables users connected to the same server to communicate continuously and in real time.

Features: Easy-to-use command-line interface.

Threading is used to support multiple clients.

broadcasting messages in real time.

appropriate handling of client disconnection.

lightweight and simple to test with multiple terminals on a single machine.

## OUTPUT ##

<img width="1344" height="390" alt="Image" src="https://github.com/user-attachments/assets/48abafd0-1af2-4e7f-b51d-c181be91c5a7" />

<img width="1339" height="346" alt="Image" src="https://github.com/user-attachments/assets/ca08ce13-21a0-450e-9cf0-0506f9f54a80" />


