package trendelenburg.de.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import trendelenburg.de.KIPlugin;


public class StopAICommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        KIPlugin.oarThread.interrupt();
        return true;
    }
}
