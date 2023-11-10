import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigReader {
    private static final String DEFAULT_SERVER_IP = "localhost";
    private static final int DEFAULT_SERVER_PORT = 1234;

    private String serverIp;
    private int serverPort;

    public ConfigReader() {
        this.serverIp = DEFAULT_SERVER_IP;
        this.serverPort = DEFAULT_SERVER_PORT;
        readConfigFile();
    }

    public String getServerIp() {
        return serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    private void readConfigFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("server_info.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    serverIp = parts[0].trim();
                    serverPort = Integer.parseInt(parts[1].trim());
                }
            }
        } catch (IOException | NumberFormatException e) {
            // Use default values if the file is not found or has incorrect data
            System.out.println("Error reading configuration file. Using default values.");
        }
    }
}
