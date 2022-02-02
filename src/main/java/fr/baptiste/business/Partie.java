package fr.baptiste.business;

import fr.baptiste.business.port.PlayerStrategy;
import fr.baptiste.domain.*;
import fr.baptiste.business.utilities.AvailableMovement;

import java.util.ArrayList;
import java.util.Collections;

public class Partie {
    private final AvailableMovement availableMovement;
    private final PlayerStrategy strategyPlayer1;
    private final PlayerStrategy strategyPlayer2;
    private Board board;

    public Partie(AvailableMovement availableMovement, PlayerStrategy strategyPlayer1, PlayerStrategy strategyPlayer2) {
        this.availableMovement = availableMovement;
        this.strategyPlayer1 = strategyPlayer1;
        this.strategyPlayer2 = strategyPlayer2;
        this.initBoard();
    }

    public void play() {
        while (!availableMovement.isThereAWinner(board)) {
            if (board.getTempo().equals(Color.WHITE)) {
                board = strategyPlayer1.playAMove(board);
            } else {
                board = strategyPlayer2.playAMove(board);
            }
        }
    }

    private void initBoard() {
        final var whitePiece = new ArrayList<Piece>();
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 6, 0));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 6, 1));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 6, 2));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 6, 3));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 6, 4));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 6, 5));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 6, 6));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 6, 7));
        whitePiece.add(new Piece(Color.WHITE, Type.ROCK, 7, 0));
        whitePiece.add(new Piece(Color.WHITE, Type.KNIGHT, 7, 1));
        whitePiece.add(new Piece(Color.WHITE, Type.BISHOP, 7, 2));
        whitePiece.add(new Piece(Color.WHITE, Type.QUEEN, 7, 3));
        whitePiece.add(new Piece(Color.WHITE, Type.KING, 7, 4));
        whitePiece.add(new Piece(Color.WHITE, Type.BISHOP, 7, 5));
        whitePiece.add(new Piece(Color.WHITE, Type.KNIGHT, 7, 6));
        whitePiece.add(new Piece(Color.WHITE, Type.ROCK, 7, 7));

        final ArrayList<Piece> blackPiece = new ArrayList<>();
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 1, 0));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 1, 1));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 1, 2));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 1, 3));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 1, 4));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 1, 5));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 1, 6));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 1, 7));
        blackPiece.add(new Piece(Color.BLACK, Type.ROCK, 0, 0));
        blackPiece.add(new Piece(Color.BLACK, Type.KNIGHT, 0, 1));
        blackPiece.add(new Piece(Color.BLACK, Type.BISHOP, 0, 2));
        blackPiece.add(new Piece(Color.BLACK, Type.QUEEN, 0, 3));
        blackPiece.add(new Piece(Color.BLACK, Type.KING, 0, 4));
        blackPiece.add(new Piece(Color.BLACK, Type.BISHOP, 0, 5));
        blackPiece.add(new Piece(Color.BLACK, Type.KNIGHT, 0, 6));
        blackPiece.add(new Piece(Color.BLACK, Type.ROCK, 0, 7));

        this.board = new Board(
                new Player(Color.WHITE, Collections.emptyList()),
                whitePiece,
                new Player(Color.BLACK, Collections.emptyList()),
                blackPiece,
                Color.WHITE);
    }


}
