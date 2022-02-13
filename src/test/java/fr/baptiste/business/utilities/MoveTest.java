package fr.baptiste.business.utilities;

import fr.baptiste.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MoveTest {

    @Test
    public void itShouldCorrecltyCopyTheBoardExceptForHistoryAndPieceMoved() {
        //GIVEN
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 0, 0);
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 5, 0);
        final Piece blackKnight = new Piece(Color.BLACK, Type.KNIGHT, 7, 5);
        final Piece whiteKingAfterMove = new Piece(Color.WHITE, Type.KING, 0, 1);
        final Board board = getBoard(Set.of(whiteKing), Set.of(blackKing, blackKnight), Color.WHITE);
        final PieceMove pieceMove = new PieceMove(whiteKing, whiteKingAfterMove);

        //WHEN
        Board result = new Move().move(board, pieceMove);

        //THEN
        Assertions.assertThat(result.getPiecePlayerBlack()).containsExactlyInAnyOrder(blackKing, blackKnight);
        Assertions.assertThat(result.getPlayerWhite()).isEqualTo(new Player(Color.WHITE, Collections.emptyList()));
        Assertions.assertThat(result.getPlayerBlack()).isEqualTo(new Player(Color.BLACK, Collections.emptyList()));
        Assertions.assertThat(result.getTempo()).isEqualTo(Color.BLACK);
    }

    @Test
    public void itShouldCorrecltyChangeAWhitePiecePosition() {
        //GIVEN
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 0, 0);
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 7, 0);
        final Piece blackKnight = new Piece(Color.BLACK, Type.KNIGHT, 7, 5);
        final Piece whiteKingAfterMove = new Piece(Color.WHITE, Type.KING, 0, 1);
        final Board board = getBoard(Set.of(whiteKing), Set.of(blackKing, blackKnight), Color.WHITE);
        final PieceMove pieceMove = new PieceMove(whiteKing, whiteKingAfterMove);

        //WHEN
        Board result = new Move().move(board, pieceMove);

        //THEN
        Assertions.assertThat(result.getPiecePlayerWhite()).containsExactly(whiteKingAfterMove);
        Assertions.assertThat(result.getHistory()).containsExactly(pieceMove);
    }

    @Test
    public void itShouldCorrecltyChangeAWhitePiecePositionAndDeleteACapturedPiece() {
        //GIVEN
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 0, 0);
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 7, 0);
        final Piece blackQueen = new Piece(Color.BLACK, Type.KING, 0, 1);
        final Piece whiteKingAfterMove = new Piece(Color.WHITE, Type.KING, 0, 1);
        final Board board = getBoard(Set.of(whiteKing), Set.of(blackKing, blackQueen), Color.WHITE);
        final PieceMove pieceMove = new PieceMove(whiteKing, whiteKingAfterMove, blackQueen);

        //WHEN
        Board result = new Move().move(board, pieceMove);

        //THEN
        Assertions.assertThat(result.getPiecePlayerBlack()).containsExactly(blackKing);
        Assertions.assertThat(result.getPlayerWhite().getPiecesCapture()).containsExactly(blackQueen);
    }

    @Test
    public void itShouldCorrecltyChangeABlackPiecePosition() {
        //GIVEN
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 0, 0);
        final Piece whiteKnight = new Piece(Color.WHITE, Type.KNIGHT, 7, 5);
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 7, 0);
        final Piece blackKingAfterMove = new Piece(Color.BLACK, Type.KING, 7, 1);
        final Board board = getBoard(Set.of(whiteKing, whiteKnight), Set.of(blackKing), Color.BLACK);
        final PieceMove pieceMove = new PieceMove(blackKing, blackKingAfterMove);

        //WHEN
        Board result = new Move().move(board, pieceMove);

        //THEN
        Assertions.assertThat(result.getPiecePlayerBlack()).containsExactly(blackKingAfterMove);
        Assertions.assertThat(result.getHistory()).containsExactly(pieceMove);
    }

    @Test
    public void itShouldCorrecltyChangeABlackPiecePositionAndDeleteACapturedPiece() {
        //GIVEN
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 0, 0);
        final Piece whiteQueen = new Piece(Color.WHITE, Type.KING, 7, 1);
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 7, 0);
        final Piece blackKingAfterMove = new Piece(Color.BLACK, Type.KING, 7, 1);
        final Board board = getBoard(Set.of(whiteKing, whiteQueen), Set.of(blackKing), Color.BLACK);
        final PieceMove pieceMove = new PieceMove(blackKing, blackKingAfterMove, whiteQueen);

        //WHEN
        Board result = new Move().move(board, pieceMove);

        //THEN
        Assertions.assertThat(result.getPiecePlayerBlack()).containsExactly(blackKingAfterMove);
        Assertions.assertThat(result.getPlayerBlack().getPiecesCapture()).containsExactly(whiteQueen);
    }

    private Board getBoard(Set<Piece> whitePiece, Set<Piece> blackPiece, Color tempo) {
        final Board board = new Board(
                new Player(Color.WHITE, Collections.emptyList()),
                whitePiece,
                new Player(Color.BLACK, Collections.emptyList()),
                blackPiece,
                tempo);
        return board;
    }
}
