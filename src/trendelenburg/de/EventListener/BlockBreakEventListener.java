package trendelenburg.de.EventListener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import trendelenburg.de.KIPlugin;

public class BlockBreakEventListener implements Listener {

    @EventHandler
    public void blockBreak(BlockBreakEvent e){

        Material drop = e.getBlock().getType();
        if(e.getBlock().getType().name().endsWith("LOG")){
            addPointsFor(drop, KIPlugin.woodFactor, e);
        }else if(e.getBlock().getType().name().endsWith("STONE")){
            addPointsFor(drop, KIPlugin.stoneFactor, e);
        }

    }

    private void addPointsFor(Material drop, int factor, BlockBreakEvent e){
        if(e.getBlock().getDrops(e.getPlayer().getItemOnCursor()).size()!=0){
            if(!KIPlugin.placedBlocks.contains(e.getBlock().getLocation())){
                KIPlugin.addToScore(computeScore(factor), "Breaked " + drop.name());
            }else{
                KIPlugin.addToScore(computeScoreWOTime(factor), "Breaked self-Placed Block " + drop.name());
                KIPlugin.placedBlocks.remove(e.getBlock().getLocation());
                if (KIPlugin.droppedList.containsKey(drop)) {
                    int amount = KIPlugin.droppedList.get(drop) + 1;
                    KIPlugin.droppedList.put(drop, amount);
                } else {
                    KIPlugin.droppedList.put(drop, 1);
                }
            }
        }
    }

    private int computeScore(int factor){
        return Math.round((KIPlugin.roundTime - KIPlugin.currentCount)*factor);
    }
    private int computeScoreWOTime(int factor){
        return Math.round(factor);
    }


}
