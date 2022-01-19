package fr.baptiste.domain.builder;

import fr.baptiste.domain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BoardBuilder {
    public Board initBoard() {
        List<Piece> whitePiece = new ArrayList<>();
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 6, 0));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 6, 1));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 6, 2));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 6, 3));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 6, 4));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 6, 5));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 6, 6));
        whitePiece.add(new Piece(Color.WHITE, Type.PON, 6, 7));
        whitePiece.add(new Piece(Color.WHITE, Type.ROCK,  7, 0));
        whitePiece.add(new Piece(Color.WHITE, Type.KNIGHT,  7, 1));
        whitePiece.add(new Piece(Color.WHITE, Type.BISHOP,  7, 2));
        whitePiece.add(new Piece(Color.WHITE, Type.QUEEN, 7, 3));
        whitePiece.add(new Piece(Color.WHITE, Type.KING, 7, 4));
        whitePiece.add(new Piece(Color.WHITE, Type.BISHOP, 7, 5));
        whitePiece.add(new Piece(Color.WHITE, Type.KNIGHT, 7, 6));
        whitePiece.add(new Piece(Color.WHITE, Type.ROCK, 7, 7));

        List<Piece> blackPiece = new ArrayList<>();
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 1, 0));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 1, 1));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 1, 2));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 1, 3));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 1, 4));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 1, 5));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 1, 6));
        blackPiece.add(new Piece(Color.BLACK, Type.PON, 1, 7));
        blackPiece.add(new Piece(Color.BLACK, Type.ROCK,  0, 0));
        blackPiece.add(new Piece(Color.BLACK, Type.KNIGHT, 0, 1));
        blackPiece.add(new Piece(Color.BLACK, Type.BISHOP, 0, 2));
        blackPiece.add(new Piece(Color.BLACK, Type.QUEEN, 0, 3));
        blackPiece.add(new Piece(Color.BLACK, Type.KING, 0, 4));
        blackPiece.add(new Piece(Color.BLACK, Type.BISHOP, 0, 5));
        blackPiece.add(new Piece(Color.BLACK, Type.KNIGHT, 0, 6));
        blackPiece.add(new Piece(Color.BLACK, Type.ROCK, 0, 7));

        return new Board(
                new Player(Color.WHITE, Collections.emptyList()),
                whitePiece,
                new Player(Color.BLACK, Collections.emptyList()),
                blackPiece,
                Color.WHITE);
    }

    public Board build(Board board, PieceMove pieceMove) {
        List<Piece> whitePiece;
        List<Piece> blackPiece;

        if(Color.WHITE.equals(pieceMove.getPieceBeforeMove().getColor())) {
            blackPiece = board.getPiecePlayerBlack().stream().map(Piece::clone).collect(Collectors.toList());
            whitePiece = board.getPiecePlayerWhite().stream().map(Piece::clone).collect(Collectors.toList());
            whitePiece.remove(pieceMove.getPieceBeforeMove());
            whitePiece.add(pieceMove.getPieceAfterMove());

            if(pieceMove.getPieceCaptured().isPresent()) {
                blackPiece.remove(pieceMove.getPieceCaptured().get());
            }
        } else {
            whitePiece = board.getPiecePlayerWhite().stream().map(Piece::clone).collect(Collectors.toList());
            blackPiece = board.getPiecePlayerBlack().stream().map(Piece::clone).collect(Collectors.toList());
            blackPiece.remove(pieceMove.getPieceBeforeMove());
            blackPiece.add(pieceMove.getPieceAfterMove());

            if(pieceMove.getPieceCaptured().isPresent()) {
                whitePiece.remove(pieceMove.getPieceCaptured().get());
            }
        }

        return new Board(
                board.getPlayerWhite(),
                whitePiece,
                board.getPlayerBlack(),
                blackPiece,
                board.getTempo());
    }
}
