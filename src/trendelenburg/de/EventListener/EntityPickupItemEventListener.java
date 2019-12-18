package trendelenburg.de.EventListener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import trendelenburg.de.KIPlugin;

public class EntityPickupItemEventListener  implements Listener {

    @EventHandler
    public void pickUp(EntityPickupItemEvent e){
        if(e.getEntity() instanceof Player){

            Material drop = e.getItem().getItemStack().getType();


            //compute Points
            if(drop.name().endsWith("LOG")){
                addPointsFor(drop, KIPlugin.woodFactor);

            }else if(drop.equals(Material.STICK)){
                addPointsFor(drop, KIPlugin.stickFactor);

            }else if(drop.name().endsWith("STONE")){
                addPointsFor(drop, KIPlugin.stoneFactor);
            }


            //addtodroppedList
            if(KIPlugin.droppedList.containsKey(drop)){

                int amount = KIPlugin.droppedList.get(drop) - 1;
                if(amount>0){
                    KIPlugin.droppedList.put(drop, amount);
                }else{
                    KIPlugin.droppedList.put(drop, 0);
                }
            }
        }

    }

    private void addPointsFor(Material drop, int factor){
        if(KIPlugin.droppedList.containsKey(drop)){
            if(KIPlugin.droppedList.get(drop)<=0){
                KIPlugin.addToScore(computeScore(factor), "Picked Up " + drop.name());
            }else{
                KIPlugin.addToScore(computeScoreWOTime(factor), "Picked self-Placed Block Up " + drop.name());
            }
        }else{
            KIPlugin.addToScore(computeScore(factor), "Picked Up " + drop.name());
        }
    }

    private int computeScore(int factor){
        return Math.round((KIPlugin.roundTime - KIPlugin.currentCount)*factor);
    }

    private int computeScoreWOTime(int factor){
        return Math.round(factor);
    }

}
