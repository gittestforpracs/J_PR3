import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.http.WebSocket;

public class Server {
    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        try (ServerSocket server= new ServerSocket(3345)){
            Socket client = server.accept();


            System.out.print("Соединение установлено");

            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            System.out.println("DataOutputStream  created");

            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println("DataInputStream created");

            while(!client.isClosed()){

                System.out.println("Server reading from channel");

                String entry = in.readUTF();

                System.out.println("READ from client message - "+entry);

                System.out.println("Server try writing to channel");

                if(entry.equalsIgnoreCase("quit")){
                    System.out.println("Client initialize connections suicide ...");
                    out.writeUTF("Server reply - "+entry + " - OK");
                    out.flush();
                    Thread.sleep(5000);
                    break;
                }

                out.writeUTF("Server reply - "+entry + " - OK");
                System.out.println("Сервер написал сообщение клиенту.");

                out.flush();

            }

            System.out.println("Клиент отключён");
            System.out.println("Закрытие клиента и соединения");

            in.close();
            out.close();

            client.close();

            System.out.println("Закрытие соедиенения - ГОТОВО");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
