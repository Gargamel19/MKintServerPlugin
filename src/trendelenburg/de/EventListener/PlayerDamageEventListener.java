package trendelenburg.de.EventListener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import trendelenburg.de.KIPlugin;

public class PlayerDamageEventListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();
            if(player.getHealth()<=e.getFinalDamage()){
                KIPlugin.setScore(-100);
                if(KIPlugin.oarThread.isAlive()){
                    KIPlugin.oar.interThread.interrupt();
                }
                e.setCancelled(true);
            }else{
                KIPlugin.addToScore(computeScore(e.getFinalDamage()), "Damage");
            }
        }

    }

    private int computeScore(double amount){
        return -1*Math.toIntExact(Math.round(20 * amount));
    }

}
