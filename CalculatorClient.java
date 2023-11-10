import java.io.*;
import java.net.*;

public class CalculatorClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
//          Tells user what the format looks like
            System.out.println("Connected to the Calculator Server. Enter your calculations (e.g., ADD 5 3) or 'exit' to quit.");

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

