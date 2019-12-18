package trendelenburg.de.Models.Moves;

import trendelenburg.de.Models.Move;
import trendelenburg.de.Models.MoveEnums;

import java.util.ArrayList;

public class HotBar extends Move {

    public HotBar(int index) {
        super(MoveEnums.hot_bar);
        ArrayList<String> args = new ArrayList<>();
        args.add(index+"");
        this.setArgs(args);

    }
}
