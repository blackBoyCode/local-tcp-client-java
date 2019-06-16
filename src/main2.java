import java.io.*;
import java.net.Socket;
import java.util.Scanner;

//this will be the client example
//not satifying solution considering
public class main2 {

    private static Socket clientSocket;
    private static String host = "localHost";
    private static int port = 2019;
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;

    public static void main(String[] args) {

        try {

            //connecting to a localhost (computer home: 127.0.0.1) on port 2019
            clientSocket = new Socket(host,port);

            //get our socket Streams I/O
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();

            //add a more functionality to our I/O Streams
            //such as read UTF8 and write UTF8
            dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(outputStream));

            //may be better to use bytes for object?? like Json??

            System.out.println("connected to: " + clientSocket.getPort());

            System.out.println(dataInputStream.readUTF());
            //returnValue(inputStream);


            //readInputStream(inputStream);

            //returnValue(inputStream);

            //System.out.println(readInputStreamTwo(inputStream));



            //System.out.println(readInputStringThree(inputStream));

            //String temp = new String("" + inputStream.read());

            //System.out.println(temp);


        } catch (IOException e) {

            e.printStackTrace();

        }


        //System.out.println("hello world");

        //clientSocket.getInputStream().read();


    }


    public static void returnValue(InputStream is) throws IOException {

        int i;
        char c;
        // reads till the end of the stream
        while((i = is.read())!=-1) {

            // converts integer to character
            c = (char)i;

            // prints character
            System.out.print(c);
        }
    }

    private static void readInputStream(InputStream inputStream){

        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        System.out.println(result);
    }

    private static String readInputStreamTwo(InputStream inputStream) throws IOException {

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }


    private static String  readInputStringThree(InputStream inputStream) throws IOException{

        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "UTF-8");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

//    private static void testTwo(InputStream is, OutputStream os ){
//
//        BufferedReader inputStream = null;
//        PrintWriter outputStream = null;
//
//        try {
//            inputStream = new BufferedReader(is);
//            outputStream = new PrintWriter(os);
//
//            String l;
//            while ((l = inputStream.readLine()) != null) {
//                outputStream.println(l);
//            }
//        } finally {
//            if (inputStream != null) {
//                inputStream.close();
//            }
//            if (outputStream != null) {
//                outputStream.close();
//            }
//        }
//    }



    private static void writeOutputStream(OutputStream outputStream){


    }

}
