package com.example.serverclasses;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServerClasses {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4000)) {
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message = in.readLine();
                System.out.println("Message from client: " + message);
                
                String response = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/WuGpJ8EEFNw?si=tszMng1cTHndBAcA\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>";
                
                if (message.equals("1")) {
                    response = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/CB5zuxQl5ro?si=pORLWs29a5TUyu7y\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen ></iframe>\n";
                }

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(response);

                clientSocket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}