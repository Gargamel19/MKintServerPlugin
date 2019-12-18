package trendelenburg.de.Models;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import trendelenburg.de.KIPlugin;
import trendelenburg.de.Runnables.LearningRunner;

public class ScoreBoardShower {

    public ScoreBoardShower() {
    }

    public void showStats(){

        /*
         *  Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
         *         Objective obj = scoreboard.getObjective("aiScore");
         *         if (obj == null) {
         *             obj = scoreboard.registerNewObjective("aiScore", "dummy", "");
         *             obj.setDisplaySlot(DisplaySlot.SIDEBAR);
         *         }
         *         obj = scoreboard.getObjective("aiScore");
         *
         *         scoreboard.resetScores("Score Now:");
         *         scoreboard.resetScores("Score Best:");
         *         scoreboard.resetScores("Best Try:");
         *
         *
         *         obj.getScore("Score Now:").setScore(0);//KIPlugin.getScore());
         *         obj.getScore("Score Best:").setScore(1);//LearningRunner.getBestTry().getScore());
         *         obj.getScore("Best Try:").setScore(2);//(LearningRunner.getIntOfBestTry()+1));
         */


        for(int i = 0; i < 100; i++) {
            for (Player player: Bukkit.getOnlinePlayers()) {
                player.sendMessage("");
            }
        }
        for (Player player: Bukkit.getOnlinePlayers()) {

            for (PointEvent pi: KIPlugin.pointEvents) {
                player.sendMessage(pi.toString());
            }
            player.sendMessage("");
            player.sendMessage("------------------------");
            player.sendMessage("Time: " + KIPlugin.currentCount + "/" + KIPlugin.roundTime);
            player.sendMessage("Score: " + KIPlugin.getScore() );
            if(LearningRunner.getBestTry()!=null){
                player.sendMessage("Score Best Try: " + LearningRunner.getBestTry().getScore());
                player.sendMessage("Best Try: " + (LearningRunner.getIntOfBestTry()+1) + "/" + KIPlugin.trys.size());
            }
            player.sendMessage("------------------------");
        }



    }

}
