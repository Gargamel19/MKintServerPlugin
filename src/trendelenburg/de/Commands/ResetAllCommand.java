package trendelenburg.de.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import trendelenburg.de.KIPlugin;
import trendelenburg.de.WM.PlayerMakeSomethingSkeleton;

public class ResetAllCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(KIPlugin.playerSkeleton==null){
            KIPlugin.playerSkeleton = new PlayerMakeSomethingSkeleton();
        }
        KIPlugin.playerSkeleton.write("reload:;");
        return true;
    }
}
