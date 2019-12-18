package trendelenburg.de.Runnables;

import trendelenburg.de.KIPlugin;

public class InterruptRunnable implements Runnable{

    private int timeInSeconds;
    private Thread runRunThread;

    InterruptRunnable(int timeInSeconds, Thread runRunThread) {
        this.timeInSeconds = timeInSeconds;
        this.runRunThread = runRunThread;
    }

    @Override
    public void run() {
        try {
            for (KIPlugin.currentCount = 0; KIPlugin.currentCount < timeInSeconds; KIPlugin.currentCount++) {
                KIPlugin.scoreBoardShower.showStats();
                Thread.sleep(1000);
            }
            this.runRunThread.interrupt();
        }catch (InterruptedException ire){
            ire.printStackTrace();
            this.runRunThread.interrupt();
            Thread.currentThread().interrupt();
        }
    }
}
