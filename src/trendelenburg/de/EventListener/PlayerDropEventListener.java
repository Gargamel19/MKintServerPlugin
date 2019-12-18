package trendelenburg.de.EventListener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import trendelenburg.de.KIPlugin;

public class PlayerDropEventListener implements Listener {

    @EventHandler
    public void dropItem(PlayerDropItemEvent e){
        Material drop = e.getItemDrop().getItemStack().getType();

        //compute Points
        if(drop.name().endsWith("LOG")){
            KIPlugin.addToScore(computeScore(KIPlugin.woodFactor), "Droped " + drop.name());
            computeInDropList(drop);
        }else if(drop.name().endsWith("Stick")){
            KIPlugin.addToScore(computeScore(KIPlugin.stickFactor), "Droped " + drop.name());
            computeInDropList(drop);
        }else if(drop.name().endsWith("STONE")){
            KIPlugin.addToScore(computeScore(KIPlugin.stoneFactor), "Droped " + drop.name());
            computeInDropList(drop);
        }
    }

    private void computeInDropList(Material drop){
        if(KIPlugin.droppedList.containsKey(drop)){
            int amount = KIPlugin.droppedList.get(drop) + 1;
            KIPlugin.droppedList.put(drop, amount);
        }else{
            KIPlugin.droppedList.put(drop, 1);
        }
    }

    private int computeScore(int factor){
        return Math.round(factor*-1);
    }

}
