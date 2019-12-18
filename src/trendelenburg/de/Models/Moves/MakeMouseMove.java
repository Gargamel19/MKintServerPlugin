package trendelenburg.de.Models.Moves;

import trendelenburg.de.Models.Move;
import trendelenburg.de.Models.MoveEnums;

import java.util.ArrayList;

public class MakeMouseMove extends Move {

    public MakeMouseMove(int x, int y) {
        super(MoveEnums.make_mouse_move);
        ArrayList<String> args = new ArrayList<>();
        args.add(x+"");
        args.add(y+"");
        this.setArgs(args);
    }
}
