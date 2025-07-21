package Internship;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("💬 Welcome to Multi-User Chat App");
        System.out.print("Choose mode (1 - Server, 2 - Client): ");
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline

        if (choice == 1) {
            ChatServer.startServer();
        } else if (choice == 2) {
            ChatClient.startClient();
        } else {
            System.out.println("❌ Invalid choice. Exiting...");
        }
    }
}

// -------------------- SERVER CLASS --------------------
class ChatServer {
    private static final int PORT = 12345;
    private static Set<ClientHandler> clientHandlers = Collections.synchronizedSet(new HashSet<>());

    public static void startServer() {
        System.out.println("🟢 Server started. Waiting for clients...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("✅ New client connected: " + socket);
                ClientHandler handler = new ClientHandler(socket, clientHandlers);
                clientHandlers.add(handler);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// -------------------- CLIENT HANDLER CLASS --------------------
class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Set<ClientHandler> clientHandlers;

    public ClientHandler(Socket socket, Set<ClientHandler> clientHandlers) {
        this.socket = socket;
        this.clientHandlers = clientHandlers;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("🟢 Connected to the chat server!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String message;
        try {
            while ((message = in.readLine()) != null) {
                broadcast("User " + socket.getPort() + ": " + message);
            }
        } catch (IOException e) {
            System.out.println("❌ Client disconnected: " + socket);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {}
            clientHandlers.remove(this);
            broadcast("User " + socket.getPort() + " left the chat.");
        }
    }

    private void broadcast(String message) {
        for (ClientHandler handler : clientHandlers) {
            if (handler != this) {
                handler.out.println(message);
            }
        }
    }
}

// -------------------- CLIENT CLASS --------------------
class ChatClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void startClient() {
        try (
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
        ) {
            // Receive messages
            Thread receiveThread = new Thread(() -> {
                String msg;
                try {
                    while ((msg = in.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    System.out.println("📴 Disconnected from server.");
                }
            });

            receiveThread.start();

            // Send messages
            System.out.println("✏️ Start chatting (type & Enter):");
            while (true) {
                String input = scanner.nextLine();
                out.println(input);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
