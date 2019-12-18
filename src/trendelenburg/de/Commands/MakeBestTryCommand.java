package trendelenburg.de.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import trendelenburg.de.KIPlugin;
import trendelenburg.de.Runnables.MakeBestTryRunnable;
import trendelenburg.de.WM.PlayerMakeSomethingSkeleton;


public class MakeBestTryCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(KIPlugin.playerSkeleton == null){
            KIPlugin.playerSkeleton = new PlayerMakeSomethingSkeleton();
        }
        MakeBestTryRunnable mbor = new MakeBestTryRunnable();
        Thread mborT = new Thread(mbor);
        mborT.start();

        return true;
    }
}
