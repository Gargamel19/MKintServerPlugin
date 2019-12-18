package trendelenburg.de.WM;

import java.io.*;
import java.net.*;

public class PlayerMakeSomethingSkeleton {

    private Socket socket = new Socket();
    private SocketAddress socketAddress = new InetSocketAddress("localhost", 9999);
    private BufferedReader br;
    private BufferedWriter bw;

    public PlayerMakeSomethingSkeleton() {

        try{
            socket.connect(socketAddress);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch (UnknownHostException ukhe){
            ukhe.printStackTrace();
        }catch (IOException ignored){

        }

    }

    public void startPlayerMove(){
        write("start_player_move:;");
    }

    public void stopPlayerMove(){
        write("stop_player_move:;");
    }

    public void startJump(){
        write("start_jump:;");
    }

    public void stopJump(){
        write("stop_jump:;");
    }

    public void openInv(){
        write("open_inv:;");
    }

    public void hotBar(int indexOfHotbar){
        write("hot_bar: " + indexOfHotbar + ";");
    }

    public void startDigging(){
        write("start_digging:;");
    }

    public void stopDigging(){
        write("stop_digging:;");
    }

    public void startPlacing(){
        write("start_placing:;");
    }

    public void stopPlacing(){
        write("start_placing:;");
    }

    public void placeOne(){
        write("start_placing:;");
        write("stop_placing:;");
    }

    public void makeMouseMove(int x, int y){
        write("make_mouse_move: " + x + " " + y + ";");
    }


    public void write(String out){
        try{
            bw.write(out);
            bw.flush();
        } catch (IOException ioe){
        }
    }

}
