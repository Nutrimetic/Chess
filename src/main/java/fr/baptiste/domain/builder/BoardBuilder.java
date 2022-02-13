package fr.baptiste.domain.builder;

import fr.baptiste.business.utilities.Move;
import fr.baptiste.domain.*;

import java.util.*;

public class BoardBuilder {
    private final Move move;
    private List<PieceMove> pieceMoves;

    public BoardBuilder(Move move) {
        this.move = move;
    }

    public BoardBuilder reset() {
        this.pieceMoves = null;
        return this;
    }

    public Board initBoard() {
        Set<Piece> blackPiece = new HashSet<>();
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 6, 0));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 6, 1));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 6, 2));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 6, 3));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 6, 4));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 6, 5));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 6, 6));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 6, 7));
        blackPiece.add(new Piece(Color.BLACK, Type.ROCK,  7, 0));
        blackPiece.add(new Piece(Color.BLACK, Type.KNIGHT,  7, 1));
        blackPiece.add(new Piece(Color.BLACK, Type.BISHOP,  7, 2));
        blackPiece.add(new Piece(Color.BLACK, Type.QUEEN, 7, 3));
        blackPiece.add(new Piece(Color.BLACK, Type.KING, 7, 4));
        blackPiece.add(new Piece(Color.BLACK, Type.BISHOP, 7, 5));
        blackPiece.add(new Piece(Color.BLACK, Type.KNIGHT, 7, 6));
        blackPiece.add(new Piece(Color.BLACK, Type.ROCK, 7, 7));

        Set<Piece> whitePiece = new HashSet<>();
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 1, 0));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 1, 1));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 1, 2));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 1, 3));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 1, 4));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 1, 5));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 1, 6));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 1, 7));
        whitePiece.add(new Piece(Color.WHITE, Type.ROCK,  0, 0));
        whitePiece.add(new Piece(Color.WHITE, Type.KNIGHT, 0, 1));
        whitePiece.add(new Piece(Color.WHITE, Type.BISHOP, 0, 2));
        whitePiece.add(new Piece(Color.WHITE, Type.QUEEN, 0, 3));
        whitePiece.add(new Piece(Color.WHITE, Type.KING, 0, 4));
        whitePiece.add(new Piece(Color.WHITE, Type.BISHOP, 0, 5));
        whitePiece.add(new Piece(Color.WHITE, Type.KNIGHT, 0, 6));
        whitePiece.add(new Piece(Color.WHITE, Type.ROCK, 0, 7));

        return new Board(
                new Player(Color.WHITE, Collections.emptyList()),
                whitePiece,
                new Player(Color.BLACK, Collections.emptyList()),
                blackPiece,
                Color.WHITE);
    }

    public BoardBuilder setHistory(List<PieceMove> pieceMoves) {
        this.pieceMoves = pieceMoves;
        return this;
    }

    public Board build() {
        Board board = initBoard();
        if(pieceMoves != null) {
            for(PieceMove pieceMove : pieceMoves) {
                board = move.move(board, pieceMove);
            }
        }
        return board;
    }
}
