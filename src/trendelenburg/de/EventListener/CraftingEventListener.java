package trendelenburg.de.EventListener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import trendelenburg.de.KIPlugin;

public class CraftingEventListener implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent e){

        if(e.getRecipe().getResult().getType().name().endsWith("PLANKS")){
            KIPlugin.addToScore(computeScore(100), "Crafting " + e.getRecipe().getResult().getType().name());
        }else if(e.getRecipe().getResult().getType().equals(Material.CRAFTING_TABLE)){
             KIPlugin.addToScore(computeScore(200), "Crafting " + e.getRecipe().getResult().getType().name());
        }else if(e.getRecipe().getResult().getType().equals(Material.STICK)){
            KIPlugin.addToScore(computeScore(500), "Crafting " + e.getRecipe().getResult().getType().name());
        }else if(e.getRecipe().getResult().getType().equals(Material.WOODEN_PICKAXE)){
            KIPlugin.addToScore(computeScore(1000), "Crafting " + e.getRecipe().getResult().getType().name());
        }

    }

    private int computeScore(int factor){
        return Math.round((KIPlugin.roundTime - KIPlugin.currentCount)*factor);
    }

}
