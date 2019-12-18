package trendelenburg.de.Runnables;

import trendelenburg.de.KIPlugin;
import trendelenburg.de.Models.Move;
import trendelenburg.de.Models.Try;

import java.util.ArrayList;
import java.util.Random;

public class LearningRunner implements Runnable {


    public Thread interThread;

    @Override
    public void run() {
        KIPlugin.resetWorld();
        while (true){
            try {
                MakeTryRunnable runrun;
                if(hasEnougthGoodTrys()){
                    Try bestTrys = getBestTry();
                    Try secBestTry = getRandomBestTry();
                    System.out.println("Crossing Over");
                    ArrayList<Move> crossedMoves = crossover(bestTrys, secBestTry);
                    System.out.println("MUTATE");
                    runrun = new MakeTryRunnable(mutate(crossedMoves), true);
                }else{
                    runrun = new MakeTryRunnable();
                }

                Thread runRunThread = new Thread(runrun);
                interThread = new Thread(new InterruptRunnable(KIPlugin.roundTime, runRunThread));
                runRunThread.start();
                interThread.start();
                runRunThread.join();

            }catch (InterruptedException ire){
                Thread.currentThread().interrupt();
            }
        }

    }

    private boolean hasEnougthGoodTrys(){
        int goodMoves= 0;
        for (int i = 0; i < KIPlugin.trys.size(); i++) {
            if(KIPlugin.trys.get(i).getScore()!=0){
                goodMoves++;
            }
        }
        System.out.println("goodMoves: " + goodMoves);
        return (goodMoves>=1);
    }

    public static Try getBestTry(){
        Try sestTrys = null;
        for (int i = 0; i < KIPlugin.trys.size(); i++) {
            if(sestTrys==null || sestTrys.getScore()< KIPlugin.trys.get(i).getScore()){
                sestTrys = KIPlugin.trys.get(i);
            }
        }
        return sestTrys;
    }

    public static int getIntOfBestTry(){
        int index = -1;
        Try sestTrys = null;
        for (int i = 0; i < KIPlugin.trys.size(); i++) {
            if(sestTrys==null || sestTrys.getScore()< KIPlugin.trys.get(i).getScore()){
                sestTrys = KIPlugin.trys.get(i);
                index = i;
            }
        }
        return index;
    }


    private Try getRandomBestTry(){
        Random random = new Random();
        int index = random.nextInt(KIPlugin.trys.size()+1);

        if(index== KIPlugin.trys.size()){
            return new Try(new ArrayList<>(), Math.round(getBestTry().getScore()/3));
        }else{
            return KIPlugin.trys.get(index);
        }

    }

    private static ArrayList<Move> mutate(ArrayList<Move> bestMoves){
        ArrayList<Move> ergs;
        ergs = (ArrayList<Move>) bestMoves.clone();
        Random random = new Random();
        for (int i = 0; i < ergs.size(); i++) {
            int rand = random.nextInt(30);
            if(rand==0){
                //replacing Moves
                ergs.set(i, MakeTryRunnable.getRandomMove());
            }else if(rand==1){
                //deleting Moves
                ergs.remove(i);
            }else if(rand==2){
                //swapping Moves
                int rand2 = random.nextInt(ergs.size());
                Move tempMove = ergs.get(i);
                ergs.set(i, ergs.get(rand2));
                ergs.set(rand2, tempMove);
            }
        }
        return ergs;
    }

    private static ArrayList<Move> crossover(Try t1, Try t2){
        ArrayList<Move> crossedDNA = new ArrayList<>();
        Random random = new Random();
        int minSize;

        ArrayList<Move> m1 = t1.getDNA();
        ArrayList<Move> m2 = t2.getDNA();

        ArrayList<Move> maxList;
        if(m1.size()<m2.size()){
            minSize = m1.size();
            maxList = m2;
        }else{
            minSize = m2.size();
            maxList = m1;
        }
        System.out.println("Crossing over: score1=" + t1.getScore() + " score2=" + t2.getScore());
        for (int i = 0; i < minSize; i++) {
            int rand = random.nextInt(t1.getScore() + t2.getScore() - Math.round(t2.getScore()/2));
            if(rand<t1.getScore()){
                crossedDNA.add(m1.get(i));
            }else{
                if(i<t2.getDNA().size()-1){
                    crossedDNA.add(m2.get(i));
                }else{
                    crossedDNA.add(m1.get(i));
                }
            }
        }

        for (int i = 0; i < maxList.size()-minSize; i++) {
            crossedDNA.add(maxList.get(i+minSize));
        }

        return crossedDNA;

    }

}
