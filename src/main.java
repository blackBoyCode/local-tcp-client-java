import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class main {

    //the socket to transfer data
    private static AsynchronousSocketChannel clientSocket;

    //this class is used to store address and ports (eg while opening a client)
    private static InetSocketAddress hostAddress;

    //represent a pending result support asynchronous operations
    private static Future future;

    //hold temporary data from reading and writing stream (well that how I see it )
    private static ByteBuffer buffer;


    public static void main(String[] args) {

        try {
            setClient();
        } catch (IOException| ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void setClient() throws IOException, ExecutionException, InterruptedException {

        //open socket channel..
        clientSocket = AsynchronousSocketChannel.open();

        //prepare our port(2019)"can be from any range port" and ip address(localhost = 127.0.0.1)
        hostAddress = new InetSocketAddress("localhost", 2019);

        //connect to our address from the server..
        future = clientSocket.connect(hostAddress);

        future.get();//blocking call while executing our connect

        System.out.println("client connected: "  + clientSocket.isOpen() );

        //Questions: I need to confirm whether or not that I really need a
        // while loop for constantly reading input from the server
        while ((clientSocket != null) && (clientSocket.isOpen())) {

            //create a new buffer to a specify size "allocate(size)"
            buffer = ByteBuffer.allocate(32);//? for json file I don't know how large...
            //if you don't know how much to allocate: "allocate(message.length() + 1)"

            ///THIS IS THE READ PART///

            //read what I've send from server..
            Future result = clientSocket.read(buffer);

            //could do different operation while waiting for result

            result.get(); //execute our future

            //translate our buffer to string message
            String message = new String(buffer.array()).trim();
            System.out.println(message);


            buffer.clear();//clear our buffer

            ///END OF READ///

            //prepare our message
            buffer.put("I've receive your message".getBytes());

            //I need to flip the buffer before a write and Buffer.clear() before a read or put
            buffer.flip();

            //write back to server or send message
            clientSocket.write(buffer);

        }

    }
}
