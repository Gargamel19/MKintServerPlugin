package trendelenburg.de;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import trendelenburg.de.Commands.MakeBestTryCommand;
import trendelenburg.de.Commands.ResetAllCommand;
import trendelenburg.de.Commands.StartAICommand;
import trendelenburg.de.Commands.StopAICommand;
import trendelenburg.de.EventListener.*;
import trendelenburg.de.Models.Move;
import trendelenburg.de.Models.PointEvent;
import trendelenburg.de.Models.ScoreBoardShower;
import trendelenburg.de.Models.Try;
import trendelenburg.de.Runnables.LearningRunner;
import trendelenburg.de.WM.PlayerMakeSomethingSkeleton;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class KIPlugin extends JavaPlugin {

    //public static String prefix = "§8[§eKIPlugin§8]§7 ";
    private static String consol_prefix = "[KIPlugin] ";

    private static int worldID = 0;

    public static ArrayList<Try> trys = new ArrayList<>();

    public static PlayerMakeSomethingSkeleton playerSkeleton;

    public static LearningRunner oar = new LearningRunner();
    public static Thread oarThread = new Thread(oar);

    private static int score = 0;
    public static int currentCount = 0;
    public static int roundTime = 600;
    public static ArrayList<PointEvent> pointEvents = new ArrayList<>();

    public static ScoreBoardShower scoreBoardShower;

    public static int woodFactor = 10;
    public static int stickFactor = 5;
    public static int stoneFactor = 100;

    public static ArrayList<Location> placedBlocks = new ArrayList<>();
    public static HashMap<Material, Integer> droppedList = new HashMap<>();

    @Override
    public void onDisable() {

        saveMoves();

    }

    @Override
    public void onEnable() {
        loadMoves();
        registerEvents();
        registerCommands();
    }

    public static void addToScore(int value, String reason){
        score = score + value;
        if(scoreBoardShower==null){
            scoreBoardShower = new ScoreBoardShower();
        }
        pointEvents.add(new PointEvent(value, reason));
        scoreBoardShower.showStats();
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        KIPlugin.score = score;
        scoreBoardShower.showStats();
    }

    public static void resetWorld() {
        if(scoreBoardShower==null){
            scoreBoardShower = new ScoreBoardShower();
        }
        scoreBoardShower.showStats();
        pointEvents = new ArrayList<>();
        score = 0;
        placedBlocks = new ArrayList<>();
        worldID++;
        Bukkit.getScheduler().runTask(Bukkit.getServer().getPluginManager().getPlugin("KIPlugin"), () -> {
            WorldCreator wc = new WorldCreator("world" + worldID).seed(13241356);
            Bukkit.createWorld(wc);
            for (Player player: Bukkit.getOnlinePlayers()) {
                player.getInventory().clear();
                player.setHealth(20);
                player.setFoodLevel(20);
                player.teleport(Bukkit.getWorld("world" + worldID).getSpawnLocation());
            }
            Bukkit.unloadWorld(("world" + (worldID-1)), false);

        });

        boolean isWorldReady = false;
        while(!isWorldReady) {
            Iterator<? extends Player> iterator = Bukkit.getOnlinePlayers().iterator();
            if(iterator.hasNext()){
                isWorldReady = iterator.next().getWorld().getName().equals("world"+worldID);
            }
        }
    }

    private void registerEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(new RegenerationEventListener(),this);
        System.out.println(consol_prefix + "REGISTERED RegenerationEventListener");
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDamageEventListener(),this);
        System.out.println(consol_prefix + "REGISTERED PlayerDamageEventListener");
        Bukkit.getServer().getPluginManager().registerEvents(new BlockBreakEventListener(),this);
        System.out.println(consol_prefix + "REGISTERED BlockBreakEventListener");
        Bukkit.getServer().getPluginManager().registerEvents(new EntityPickupItemEventListener(),this);
        System.out.println(consol_prefix + "REGISTERED EntityPickupItemEventListener");
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDropEventListener(),this);
        System.out.println(consol_prefix + "REGISTERED PlayerDropEventListener");
        Bukkit.getServer().getPluginManager().registerEvents(new BlockPlaceEventListener(),this);
        System.out.println(consol_prefix + "REGISTERED BlockPlaceEventListener");
        Bukkit.getServer().getPluginManager().registerEvents(new CraftingEventListener(),this);
        System.out.println(consol_prefix + "REGISTERED CraftingEventListener");
    }

    private void registerCommands() {
        getCommand("startAI").setExecutor(new StartAICommand());
        System.out.println(consol_prefix + "REGISTERED Start AI COMMAND (/startAI)");
        getCommand("stopAI").setExecutor(new StopAICommand());
        System.out.println(consol_prefix + "REGISTERED Stop AI COMMAND (/stopAI)");
        getCommand("makeBestTry").setExecutor(new MakeBestTryCommand());
        System.out.println(consol_prefix + "REGISTERED Make Best Try COMMAND (/makebesttry)");
        getCommand("resetAI").setExecutor(new ResetAllCommand());
        System.out.println(consol_prefix + "REGISTERED Reset All COMMAND (/resetAI)");

    }

    private void saveMoves() {
        String fileName = "moveList" + roundTime + ".trys";
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(new File(fileName)));

            for (Try trmoTry : trys) {
                br.write("--" + trmoTry.getScore() + ";");
                for (int j = 0; j < trmoTry.getDNA().size(); j++) {
                    br.write(trmoTry.getDNA().get(j).toString() + ";");

                }
            }
            br.flush();

        } catch (IOException fnfe){
            fnfe.printStackTrace();
        }
    }

    private void loadMoves() {
        String fileName = "moveList" + roundTime + ".trys";
        try{
            BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
            StringBuilder bs = new StringBuilder();
            br.lines().forEach(bs::append);
            String input = bs.toString();
            if(!input.equals("")){
                String[] splittedInPut  = input.split("--");
                for (String s : splittedInPut) {
                    if (!s.isEmpty()) {
                        String[] splittedSplittedInPut = s.split(";");
                        int score = Integer.parseInt(splittedSplittedInPut[0]+";");
                        ArrayList<Move> moves = new ArrayList<>();
                        for (int j = 1; j < splittedSplittedInPut.length - 1; j++) {
                            moves.add(Move.parseMove(splittedSplittedInPut[j]+";"));
                        }
                        trys.add(new Try(moves, score));
                    }
                }
            }

        }catch (FileNotFoundException ignored){

        }
    }

}
