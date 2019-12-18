package trendelenburg.de.Runnables;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import trendelenburg.de.KIPlugin;
import trendelenburg.de.Models.Move;
import trendelenburg.de.Models.MoveEnums;
import trendelenburg.de.Models.Moves.*;
import trendelenburg.de.Models.Try;

import java.util.ArrayList;
import java.util.Random;

public class MakeTryRunnable extends BukkitRunnable {

    private ArrayList<Move> moves = new ArrayList<>();

    private boolean running = false;

    private boolean save;

    private ArrayList<Move> givenMoves = new ArrayList<>();

    MakeTryRunnable() {
        this.save = true;
    }

    MakeTryRunnable(ArrayList<Move> moves, boolean save) {

        this.givenMoves = moves;
        this.save = save;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void stopRunning(){
        running = false;
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {
        running = true;
        if(givenMoves.size()>0){
            int i = 0;
            while(running){
                try {
                    if(givenMoves.size()-1<i){
                        moves.add(makeMove(getRandomMove()));
                    }else{
                        if(givenMoves.get(i)==null){
                            moves.add(makeMove(getRandomMove()));
                        }else{
                            moves.add(makeMove(givenMoves.get(i)));
                        }
                    }
                    Thread.sleep(200);
                }catch (InterruptedException ire){
                    KIPlugin.playerSkeleton.write("reload:;");
                    running = false;
                    Thread.currentThread().interrupt();
                }
                i++;
            }
        }else{
            while(running){
                try {
                    moves.add(makeMove(getRandomMove()));
                    Thread.sleep(200);
                }catch (InterruptedException ire){
                    KIPlugin.playerSkeleton.write("reload:;");
                    running = false;
                    Thread.currentThread().interrupt();
                }
            }
        }
        //KIPlugin.score += computeScore();
        if(KIPlugin.getScore()>0 && save){
            Try bestTry = LearningRunner.getBestTry();
            if(KIPlugin.trys.isEmpty()){
                KIPlugin.trys.add(new Try(moves, KIPlugin.getScore()));
            }else{
                if(KIPlugin.getScore()>bestTry.getScore()){
                    KIPlugin.trys.add(new Try(moves, KIPlugin.getScore()));
                }
            }

        }
        KIPlugin.resetWorld();
    }

    private int computeScore() {
        int score = 0;

        if (Bukkit.getOnlinePlayers().stream().findFirst().isPresent()) {
            Player player = Bukkit.getOnlinePlayers().stream().findFirst().get();
            PlayerInventory inv = player.getInventory();
            for (int i = 0; i < inv.getSize(); i++) {
                if(inv.getItem(i)!=null){
                    Material item = inv.getItem(i).getType();
                    System.out.println(item.name());
                    if(item.name().endsWith("LOG")) {
                        score += (10 * inv.getItem(i).getAmount());
                    }else if(item.name().equals("STICK")){
                        score+=(10*inv.getItem(i).getAmount());
                    }else if(item.name().endsWith("WOOD")){
                        score+=(20*inv.getItem(i).getAmount());
                    }else if(item.name().startsWith("WOODEN")){
                        score+=(200*inv.getItem(i).getAmount());
                    }else if(item.name().startsWith("STONE")){
                        score+=(300*inv.getItem(i).getAmount());
                    }


                }

            }
            return score;
        }
        return score;
    }


    private Move makeMove(Move move) {
        KIPlugin.playerSkeleton.write(move.toString());
        return move;
    }

    static Move getRandomMove(){
        int pick = new Random().nextInt(MoveEnums.values().length);

        Random random = new Random();

        switch (MoveEnums.values()[pick]) {
            case hot_bar:
                int index = random.nextInt(9);
                return new HotBar(index);
            case open_inv:
                return new OpenInv();
            case start_jump:
                return new StartJump();
            case stop_jump:
                return new StopJump();
            case start_digging:
                return new StartDigging();
            case stop_digging:
                return new StopDigging();
            case start_placing:
                return new StartPlacing();
            case stop_placing:
                return new StopPlacing();
            case stop_player_move:
                return new StartPlayerMove();
            case start_player_move:
                return new StopPlayerMove();
            case make_mouse_move:
                int x = random.nextInt(2000);
                int y = random.nextInt(2000);
                return new MakeMouseMove(x-1000, y-1000);
            case nothing:
                return new Nothing();
            default:
                System.out.println("WTF ENUM NOT EXIST");
                break;
        }

        return null;

    }

}
