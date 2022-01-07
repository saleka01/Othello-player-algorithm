///salek,h745679@stud.u-szeged.hu
import java.util.ArrayList;
import java.util.Random;
import game.engine.utils.Utils;
import game.oth.OthelloAction;
import game.oth.OthelloGame;
import game.oth.OthelloPlayer;


public class Agent extends OthelloPlayer {
    /**
     * Array for all possible actions.
     * * Creates a greedy player, sets the specified parameters to the super class.
     *    * @param color player's color
     *    * @param board game board
     *    * @param random random number generator
     *    * @see OthelloPlayer#OthelloPlayer(int, int[][], Random)
     *
     */
    private ArrayList<OthelloAction> actions = new ArrayList<OthelloAction>();
    public Agent(int color, int[][] board, Random random) {
        super(color, board, random);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                actions.add(new OthelloAction(i, j));
            }
        }
    }

    /**
     * getAction against greedy algorithm, take corners
     * @param prevAction previous action
     * @param remainingTimes remaining time
     * @return the best action against greedy enemy
     */
    @Override
    public OthelloAction getAction(OthelloAction prevAction, long[] remainingTimes) {
        if (prevAction != null) {
            OthelloGame.setAction(board, prevAction.i, prevAction.j, 1 - color);
        }

        OthelloAction action = null;

        float rate = 0;

        for (OthelloAction a : actions) {
            if (OthelloGame.isValid(board, a.i, a.j, color)) {

                int[][] boardCopy = Utils.copy(board);
                int f = OthelloGame.setAction(boardCopy, a.i, a.j, color);

                float returnValue = greedy(boardCopy, f);

                if (returnValue > rate) {
                    rate = returnValue;
                    action = a;
                }

                /**
                 * this check if it's in the corner (not included board.length)
                 * boolean true if its corner
                 */
                boolean corner= a.i!=0 && a.i!= 9 && a.j!=0 && a.j!= 9 &&(
                        boardCopy[a.i-1][a.j]==2 && boardCopy[a.i][a.j-1]==2 ||
                                boardCopy[a.i-1][a.j]==2 && boardCopy[a.i+1][a.j-1]==2 ||
                                boardCopy[a.i-1][a.j]==2 && boardCopy[a.i][a.j+1]==2
                                || boardCopy[a.i+1][a.j]==2 && boardCopy[a.i][a.j+1]==2
                );
                /**
                 * this check if it's in the corner (only if its on board.length)
                 * boolean true if its corner
                 */
                boolean boardCorner=((a.i==0 || a.i==9 &&(a.j!=9 && a.j!=0)) && (boardCopy[a.i][a.j-1]==2 || boardCopy[a.i][a.j+1]==2)) || ((a.j==0 || a.j==9&&(a.i!=9 && a.i!=0)) && (boardCopy[a.i-1][a.j]==2 || boardCopy[a.i+1][a.j]==2));

                if(corner || boardCorner){
                    action = a;
                    break;
                }
            }
        }

        OthelloGame.setAction(board, action.i, action.j, color);
        return action;
    }

    /**
     *
     * @param board copy of board
     * @param ownFlipped own flipped coins
     * @return with the enemy / mine flipped coin rate
     */
    public float greedy(int[][] board, int ownFlipped) {

        int flipped = 0;

        for (OthelloAction a : actions) {
            if (OthelloGame.isValid(board, a.i, a.j, 1 - color)) {

                int f = OthelloGame.setAction(board, a.i, a.j, 1 - color);

                if (flipped < f) {
                    flipped = f;
                }
            }
        }
        float f_sajatFlipped = (float) ownFlipped;
        float f_greedyFlipped = (float) flipped;

        return f_sajatFlipped / f_greedyFlipped;

    }

}