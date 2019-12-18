package trendelenburg.de.Models;

import java.util.ArrayList;
import java.util.Comparator;

public class Try implements Comparator {

    private ArrayList<Move> dna;
    private int score;

    public Try(ArrayList<Move> moves, int score) {
        this.dna = moves;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Move> getDNA() {
        return dna;
    }

    @Override
    public int compare(Object o1, Object o2) {
        if(o1 instanceof Try){
            Try tryOne = (Try) o1;
            if(o2 instanceof Try) {
                Try tryTo = (Try) o1;
                return compare(tryOne.getScore(), tryTo.getScore());
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Score: " + score + " DNA: [");
        if(dna.size()<=5){
            for (Move movs:dna) {
                sb.append(movs.toString());
                sb.append(", ");
            }
        }else{
            for (int i = 0; i < 5; i++) {
                sb.append(dna.get(i).toString());
                sb.append(", ");
            }
        }

        return sb.toString() + "]";
    }
}
