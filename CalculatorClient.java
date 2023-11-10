import java.io.*;
import java.net.*;

public class CalculatorClient {
    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReader();

        try {
            Socket socket = new Socket(configReader.getServerIp(), configReader.getServerPort());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected to the Calculator Server.");
            System.out.println("Enter your calculations (e.g., 5 + 3) or 'exit' to quit.");

            String input;
            while (true) {
                input = userInput.readLine();
//				exit part
                if (input.equalsIgnoreCase("exit")) {
                    break;
                }
                out.println(input);
                String response = in.readLine();
                System.out.println("Server: " + response);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

