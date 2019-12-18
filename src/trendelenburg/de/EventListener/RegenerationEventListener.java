package trendelenburg.de.EventListener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import trendelenburg.de.KIPlugin;

public class RegenerationEventListener implements Listener {

    @EventHandler
    public void onReg(EntityRegainHealthEvent e){
        if(e.getEntity() instanceof Player){
            KIPlugin.addToScore(computeScore(e.getAmount(), 10), "Heal");
        }

    }

    private int computeScore(double amount, int factor){
        return Math.toIntExact(Math.round(factor * amount));
    }

}
