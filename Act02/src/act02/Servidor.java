package act02;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket servidor = new ServerSocket(5000);
            System.out.println("Servidor esperando conexiones...");

            // bucle infinito para aceptar varios clientes
            while (true) {
                Socket socket = servidor.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());

                // creamos un hilo para atender a cada cliente
                Thread hilo = new Thread(new ManejadorCliente(socket));
                hilo.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// clase que maneja la comunicacion con un cliente
class ManejadorCliente implements Runnable {
    private Socket socket;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // leer mensaje del cliente
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String mensaje = entrada.readLine();
            System.out.println("Cliente dice: " + mensaje);

            // responder al cliente
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            salida.println("Hola desde el servidor");

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
