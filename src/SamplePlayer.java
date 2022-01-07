import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import game.engine.utils.Utils;
import game.oth.OthelloAction;
import game.oth.OthelloGame;

import static game.oth.OthelloGame.setAction;

public class SamplePlayer extends game.oth.OthelloPlayer {
    private ArrayList<OthelloAction> actions = new ArrayList<OthelloAction>();
    public SamplePlayer(int color, int[][] board, Random random) {
        super(color, board, random);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                actions.add(new OthelloAction(i, j));
            }
        }
    }

    @Override
    public OthelloAction getAction(OthelloAction prevAction, long[] remainingTimes) {
        if (prevAction != null) {
            setAction(board, prevAction.i, prevAction.j, 1 - color);
        }
        Collections.shuffle(actions, random);
        OthelloAction action = null;
        int flipped = 0;
        for (OthelloAction a : actions) {
            if (OthelloGame.isValid(board, a.i, a.j, color)) {
                int f = setAction(Utils.copy(board), a.i, a.j, color);
                if (flipped < f ) {
                    flipped = f;
                    action = a;
                }
                if (a.j== board.length-3){
                    action = a;
                   // System.out.println("valami");
                }
            }
        }


        setAction(board, action.i, action.j, color);
        return action;
    }

}
