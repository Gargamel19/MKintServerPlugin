package trendelenburg.de.Models;

import trendelenburg.de.Models.Moves.*;

import java.util.ArrayList;

public abstract class Move {

    private MoveEnums me;
    private ArrayList<String> args = new ArrayList<>();

    public Move(MoveEnums me) {
        this.me = me;
    }

    protected void setArgs(ArrayList<String> args) {
        this.args = args;
    }

    @Override
    public String toString() {
        StringBuilder sp = new StringBuilder(me.toString() + ":");
        for (String arg: args) {
            sp.append(" ");
            sp.append(arg);
        }
        return sp.toString() + ";";
    }

    public static Move parseMove(String input) {
        String[] inoutList = input.split(" ");
        String inoutString = inoutList[0].substring(0, inoutList[0].length()-1);
        MoveEnums me = MoveEnums.valueOf(inoutString);
        if(me.equals(MoveEnums.hot_bar)){
            return new HotBar(Integer.parseInt(inoutList[1]));
        }else if(me.equals(MoveEnums.make_mouse_move)){
            return new MakeMouseMove(Integer.parseInt(inoutList[1]), Integer.parseInt(inoutList[2]));
        }else if(me.equals(MoveEnums.open_inv)){
            return new OpenInv();
        }else if(me.equals(MoveEnums.start_digging)){
            return new StartDigging();
        }else if(me.equals(MoveEnums.start_jump)){
            return new StartJump();
        }else if(me.equals(MoveEnums.start_placing)){
            return new StartPlacing();
        }else if(me.equals(MoveEnums.start_player_move)){
            return new StartPlayerMove();
        }else if(me.equals(MoveEnums.stop_digging)){
            return new StopDigging();
        }else if(me.equals(MoveEnums.stop_jump)){
            return new StopJump();
        }else if(me.equals(MoveEnums.stop_placing)){
            return new StopPlacing();
        }else if(me.equals(MoveEnums.stop_player_move)){
            return new StopPlayerMove();
        }else if(me.equals(MoveEnums.nothing)){
            return new Nothing();
        }
        return null;
    }

}
