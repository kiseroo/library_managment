package com.library.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationServer {
    private final int port;
    private ServerSocket serverSocket;
    private final ExecutorService pool = Executors.newFixedThreadPool(10);
    private final Map<String, PrintWriter> clientWriters = new HashMap<>();

    public NotificationServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Notification server started on port " + port);
            
            pool.execute(() -> {
                while (!serverSocket.isClosed()) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        handleClient(clientSocket);
                    } catch (IOException e) {
                        if (!serverSocket.isClosed()) {
                            System.err.println("Error accepting client connection: " + e.getMessage());
                        }
                    }
                }
            });
        } catch (IOException e) {
            System.err.println("Could not start notification server: " + e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) {
        pool.execute(() -> {
            try {
                String registrationNumber = "temp";
                
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                synchronized (clientWriters) {
                    clientWriters.put(registrationNumber, writer);
                }
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            }
        });
    }

    public void sendMessage(String registrationNumber, String message) {
        synchronized (clientWriters) {
            PrintWriter writer = clientWriters.get(registrationNumber);
            if (writer != null) {
                writer.println(message);
            }
        }
    }

    public void broadcastMessage(String message) {
        synchronized (clientWriters) {
            for (PrintWriter writer : clientWriters.values()) {
                writer.println(message);
            }
        }
    }

    public void stop() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            pool.shutdown();
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters.values()) {
                    writer.close();
                }
                clientWriters.clear();
            }
        } catch (IOException e) {
            System.err.println("Error closing server: " + e.getMessage());
        }
    }
} 