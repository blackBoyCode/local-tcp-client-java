import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * this class is used to communicate between two process using TCP on localhost
 */
public class LocalTCPClient {

    //thing to add : add a closing functionality
    private AsynchronousSocketChannel mAsynchronousSocketChannel;
    private InetSocketAddress mInetSocketAddress;
    private Future pendingConnection, pendingReadingResult, pendingWriteResult;


    public LocalTCPClient(){
        setConnection();
        startReadingProcess();//start a new thread background process
    }

    /**
     * set variable and make a connection to the TCPServer
     */
    private void setConnection(){

        try {
            //open socket channel..
            mAsynchronousSocketChannel = AsynchronousSocketChannel.open();

            //prepare our port(2019)"can be from any range port" and ip address(localhost = 127.0.0.1)
            mInetSocketAddress = new InetSocketAddress("localhost", 2019);

            //connect to our address from the server..
            pendingConnection = mAsynchronousSocketChannel.connect(mInetSocketAddress);

            //get our pending connection to execute
            pendingConnection.get();

        }catch (IOException | ExecutionException | InterruptedException e){
            e.printStackTrace();
        }



    }

    /**
     * will constantly read for remote requests from the TCPServer
     */
    private void readIncommingRequest(){



        //while my socketChannel is not empty and still open then do operation
        while((mAsynchronousSocketChannel != null) && mAsynchronousSocketChannel.isOpen()){

            //create a new buffer to a specify size "allocate(size)"
            //if you don't know how much to allocate: (message.length + 1)
            ByteBuffer buffer = ByteBuffer.allocate(32);

            //read what I've send from server..
            pendingReadingResult = mAsynchronousSocketChannel.read(buffer);

            try {
                pendingReadingResult.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            //translate our buffer to string message
            String message = new String(buffer.array()).trim();
            System.out.println(message);


            buffer.clear(); //is this need here..? (just to be safe)



        }

    }

    /**
     * send messages to the TCPServer
     */
    public void sendRequest(String sendMessage){

        // wrap buffer with message before sending..
        ByteBuffer buffer = ByteBuffer.wrap(sendMessage.getBytes());
        //add our write to a future object
        pendingWriteResult = mAsynchronousSocketChannel.write(buffer);



        try {
            pendingWriteResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //buffer.clear();// clear buffer before putting.. (a method get destroyed when finished, not necessary to clear)

//        buffer.put(sendMessage.getBytes());//add message to buffer
//        buffer.flip();//flip our buffer before we write(why though...?)



        //mAsynchronousSocketChannel.write(buffer);//write to our server




    }

    public void startReadingProcess(){

        Runnable runnable = () -> readIncommingRequest();
        Thread readingThread = new Thread(runnable);
        readingThread.start();

    }

}
