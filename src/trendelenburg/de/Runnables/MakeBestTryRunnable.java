package trendelenburg.de.Runnables;

import trendelenburg.de.KIPlugin;
import trendelenburg.de.Models.Try;

public class MakeBestTryRunnable implements Runnable {

    private Thread interrThread;

    @Override
    public void run() {

        KIPlugin.resetWorld();
        try {
            MakeTryRunnable runrun;
            Try bestTrys = getBestTry();
            runrun = new MakeTryRunnable(bestTrys.getDNA(), false);
            Thread runRunThread = new Thread(runrun);
            interrThread = new Thread(new InterruptRunnable(KIPlugin.roundTime, runRunThread));
            runRunThread.start();
            interrThread.start();
            runRunThread.join();

        }catch (InterruptedException ire){
            Thread.currentThread().interrupt();
        }


    }

    private static Try getBestTry(){
        Try sestTrys = null;
        for (int i = 0; i < KIPlugin.trys.size(); i++) {
            if(sestTrys==null || sestTrys.getScore()< KIPlugin.trys.get(i).getScore()){
                sestTrys = KIPlugin.trys.get(i);
            }
        }
        return sestTrys;
    }

}
