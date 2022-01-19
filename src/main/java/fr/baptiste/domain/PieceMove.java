package fr.baptiste.domain;

import java.util.Objects;
import java.util.Optional;

public class PieceMove {
    private final Piece pieceBeforeMove;
    private final Piece pieceAfterMove;
    private final Piece pieceCaptured;

    public PieceMove(Piece pieceBeforeMove, Piece pieceAfterMove) {
        this.pieceBeforeMove = pieceBeforeMove;
        this.pieceAfterMove = pieceAfterMove;
        this.pieceCaptured = null;
    }

    public PieceMove(Piece pieceBeforeMove, Piece pieceAfterMove, Piece pieceCaptured) {
        this.pieceBeforeMove = pieceBeforeMove;
        this.pieceAfterMove = pieceAfterMove;
        this.pieceCaptured = pieceCaptured;
    }

    public Piece getPieceBeforeMove() {
        return pieceBeforeMove;
    }

    public Piece getPieceAfterMove() {
        return pieceAfterMove;
    }

    public Optional<Piece> getPieceCaptured() {
        return Optional.ofNullable(pieceCaptured);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PieceMove pieceMove = (PieceMove) o;
        return pieceBeforeMove.equals(pieceMove.pieceBeforeMove) && pieceAfterMove.equals(pieceMove.pieceAfterMove) && Objects.equals(pieceCaptured, pieceMove.pieceCaptured);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceBeforeMove, pieceAfterMove, pieceCaptured);
    }

    @Override
    public String toString() {
        return "PieceMove{" +
                "pieceBeforeMove=" + pieceBeforeMove +
                ", pieceAfterMove=" + pieceAfterMove +
                ", pieceCaptured=" + pieceCaptured +
                '}';
    }
}
