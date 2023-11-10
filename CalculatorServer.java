import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalculatorServer {
    private static final int PORT = 12345;
    private static final int THREAD_POOL_SIZE = 10;
//multi client can use this server by thread pool
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running and waiting for client connections...");

            while (true) {
//            	check clientsocket and activate calculator calss
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                CalculatorHandler calculatorHandler = new CalculatorHandler(clientSocket);
                executorService.execute(calculatorHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
//Make Calculator class using runnable interface
class CalculatorHandler implements Runnable {
    private Socket clientSocket;

    public CalculatorHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            double result = 0;

            while ((inputLine = in.readLine()) != null) {
                String[] tokens = inputLine.split(" ");
//                check input data using length 
                if (tokens.length > 3) {
                    out.println("Error message: Too many arguments");
                    continue;
                } else if (tokens.length < 3) {
                    out.println("Error message: Not enough arguments");
                    continue;
                }

                try {
                    double operand1 = Double.parseDouble(tokens[1]);
                    double operand2 = Double.parseDouble(tokens[2]);
                    String operator = tokens[0].toUpperCase();
//                    check operator and decision method
                    if ("ADD".equals(operator)) {
                        result = operand1 + operand2;
                    } else if ("SUB".equals(operator)) {
                        result = operand1 - operand2;
                    } else if ("MUL".equals(operator)) {
                        result = operand1 * operand2;
                    } else if ("DIV".equals(operator)) {
                        if (operand2 != 0) {
                            result = operand1 / operand2;
                        } else {
                            out.println("Error message: Division by zero.");
                            continue;
                        }
                    } else {
                        out.println("Error message: Invalid command. Supported commands are ADD, SUB, MUL, DIV.");
                        continue;
                    }

                    out.println("Answer: " + result);
                } catch (NumberFormatException e) {
                    out.println("Error message: Wrong format. Please provide valid numbers.");
                    continue;
                } catch (ArithmeticException e) {
                    out.println("Error message: " + e.getMessage());
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}