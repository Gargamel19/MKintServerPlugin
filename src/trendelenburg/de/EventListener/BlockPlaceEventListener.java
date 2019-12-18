package trendelenburg.de.EventListener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import trendelenburg.de.KIPlugin;

public class BlockPlaceEventListener implements Listener {

    @EventHandler
    public void dropItem(BlockPlaceEvent e){
        Material drop = e.getBlockPlaced().getType();

        //compute Points
        if(drop.name().endsWith("LOG")){
            KIPlugin.addToScore(computeScore(KIPlugin.woodFactor), "Placed " + drop.name());
        }else if(drop.name().endsWith("STONE")){
            KIPlugin.addToScore(computeScore(KIPlugin.stoneFactor), "Placed " + drop.name());
        }


        KIPlugin.placedBlocks.add(e.getBlockPlaced().getLocation());
    }

    private int computeScore(int factor){
        return Math.round(factor*-1);
    }

}
