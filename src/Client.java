import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        try(Socket socket = new Socket("localhost", 3345);
            BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream()); )
        {

            System.out.println("Клиент подключён к сокету.");
            System.out.println();
            System.out.println("Client writing channel = oos & reading channel = ois initialized.");

            while(!socket.isOutputShutdown()){

                if(br.ready()){

                    System.out.println("Клиент пишет...");
                    Thread.sleep(1000);
                    String clientCommand = br.readLine();

                    oos.writeUTF(clientCommand);
                    oos.flush();
                    System.out.println("Клиент отправил сообщение "
                            + clientCommand +
                    " на сервер");
                    Thread.sleep(1000);

                    if(clientCommand.equalsIgnoreCase("Выход"))
                    {

                        System.out.println("Клиент оборвал соединение");
                        Thread.sleep(1000);

                        if(ois.read() > -1)
                        {
                            System.out.println("reading...");
                            String in = ois.readUTF();
                            System.out.println(in);
                        }

                        break;
                    }

                    System.out.println("Client sent message & start waiting for data from server...");

                    if(ois.read() > -1)
                    {
                        System.out.println("reading...");
                        String in = ois.readUTF();
                        System.out.println(in);
                    }
                }
            }
            System.out.println("Закрытие соединение на стороне клиента - DONE.");

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}