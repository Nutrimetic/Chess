package fr.baptiste.utilities;

import fr.baptiste.domain.*;
import fr.baptiste.domain.builder.BoardBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoardHelperTest {

    /************************************************************************************************************************
     *
     *                                              KING
     *
     ***********************************************************************************************************************/

    @Test
    public void itShouldCalculateKingMoveWithoutWhiteAndBlackPiece() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece king = new Piece(Color.WHITE, Type.KING, 4, 4);
        ArrayList<Piece> piecePlayerWhite = new ArrayList<>();
        piecePlayerWhite.add(king);
        Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                piecePlayerWhite,
                new Player(Color.BLACK, Collections.emptyList()),
                Collections.emptyList(),
                Color.WHITE);
        BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, king);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(king, new Piece(Color.WHITE, Type.KING, 3, 3)),
                new PieceMove(king, new Piece(Color.WHITE, Type.KING, 4, 3)),
                new PieceMove(king, new Piece(Color.WHITE, Type.KING, 5, 3)),
                new PieceMove(king, new Piece(Color.WHITE, Type.KING, 3, 4)),
                new PieceMove(king, new Piece(Color.WHITE, Type.KING, 5, 4)),
                new PieceMove(king, new Piece(Color.WHITE, Type.KING, 3, 5)),
                new PieceMove(king, new Piece(Color.WHITE, Type.KING, 4, 5)),
                new PieceMove(king, new Piece(Color.WHITE, Type.KING, 5, 5)));
    }

     @Test
    public void itShouldCalculateKingMoveWithWhitePiece() {
         //GIVEN
         final BoardBuilder boardBuilder = new BoardBuilder();
         final Piece king = new Piece(Color.WHITE, Type.KING, 4, 4);
         final Piece bishop = new Piece(Color.WHITE, Type.BISHOP, 3, 4);
         final Piece pon1 = new Piece(Color.WHITE, Type.BISHOP, 3, 3);
         final Piece pon2 = new Piece(Color.WHITE, Type.BISHOP, 5, 4);
         Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                 Arrays.asList(king, bishop, pon1, pon2),
                 new Player(Color.BLACK, Collections.emptyList()),
                 Collections.emptyList(),
                 Color.WHITE);
         BoardHelper boardHelper = new BoardHelper(boardBuilder);

         //WHEN
         final List<PieceMove> result = boardHelper.availableMove(board, king);

         //THEN
         Assertions.assertThat(result).containsExactlyInAnyOrder(
                 new PieceMove(king, new Piece(Color.WHITE, Type.KING, 4, 3)),
                 new PieceMove(king, new Piece(Color.WHITE, Type.KING, 5, 3)),
                 new PieceMove(king, new Piece(Color.WHITE, Type.KING, 3, 5)),
                 new PieceMove(king, new Piece(Color.WHITE, Type.KING, 4, 5)),
                 new PieceMove(king, new Piece(Color.WHITE, Type.KING, 5, 5)));
     }

    @Test
    public void itShouldCalculateKingMoveWithoutWhiteAndWithBlackPiece() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 4, 4);
        final Piece blackPon = new Piece(Color.BLACK, Type.PON, 3, 3);
        final Piece blackRock = new Piece(Color.BLACK, Type.ROCK, 4, 3);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                Collections.singletonList(whiteKing),
                new Player(Color.BLACK, Collections.emptyList()),
                Arrays.asList(blackPon, blackRock),
                Color.WHITE);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, whiteKing);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                //new PieceMove(whiteKing, new Piece(Color.WHITE, Type.KING, 3, 3)),
                new PieceMove(whiteKing, new Piece(Color.WHITE, Type.KING, 4, 3), blackRock),
                //new PieceMove(whiteKing, new Piece(Color.WHITE, Type.KING, 5, 3)),
                new PieceMove(whiteKing, new Piece(Color.WHITE, Type.KING, 3, 4)),
                new PieceMove(whiteKing, new Piece(Color.WHITE, Type.KING, 5, 4)),
                new PieceMove(whiteKing, new Piece(Color.WHITE, Type.KING, 3, 5)),
                //new PieceMove(whiteKing, new Piece(Color.WHITE, Type.KING, 4, 5)),
                new PieceMove(whiteKing, new Piece(Color.WHITE, Type.KING, 5, 5)));
    }

    @Test
    public void itShouldCalculateKingMoveWithWhiteAndBlackPiece() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 4, 4);
        final Piece whiteKnight = new Piece(Color.WHITE, Type.KNIGHT, 2, 4);
        final Piece blackPon = new Piece(Color.BLACK, Type.PON, 2, 2);
        final Piece blackRock = new Piece(Color.BLACK, Type.ROCK, 0, 4);
        List<Piece> piecePlayerWhite = new ArrayList<>();
        piecePlayerWhite.add(whiteKing);
        final Board board = new Board(
                new Player(Color.WHITE, Collections.emptyList()),
                Arrays.asList(whiteKing, whiteKnight),
                new Player(Color.BLACK, Collections.emptyList()),
                Arrays.asList(blackPon, blackRock),
                Color.WHITE);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, whiteKing);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                //new PieceMove(whiteKing, new Piece(Color.WHITE, Type.KING, 3, 3)),
                new PieceMove(whiteKing, new Piece(Color.WHITE, Type.KING, 4, 3)),
                new PieceMove(whiteKing, new Piece(Color.WHITE, Type.KING, 5, 3)),
                new PieceMove(whiteKing, new Piece(Color.WHITE, Type.KING, 3, 4)),
                new PieceMove(whiteKing, new Piece(Color.WHITE, Type.KING, 5, 4)),
                new PieceMove(whiteKing, new Piece(Color.WHITE, Type.KING, 3, 5)),
                new PieceMove(whiteKing, new Piece(Color.WHITE, Type.KING, 4, 5)),
                new PieceMove(whiteKing, new Piece(Color.WHITE, Type.KING, 5, 5)));
    }

    @Test
    public void itShouldCalculateKingMoveWithBlackPiece() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece king = new Piece(Color.BLACK, Type.KING, 4, 4);
        final Piece bishop = new Piece(Color.BLACK, Type.BISHOP, 3, 4);
        final Piece pon1 = new Piece(Color.BLACK, Type.BISHOP, 3, 3);
        final Piece pon2 = new Piece(Color.BLACK, Type.BISHOP, 5, 4);
        Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                Collections.emptyList(),
                new Player(Color.BLACK, Collections.emptyList()),
                Arrays.asList(king, bishop, pon1, pon2),
                Color.BLACK);
        BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, king);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(king, new Piece(Color.BLACK, Type.KING, 4, 3)),
                new PieceMove(king, new Piece(Color.BLACK, Type.KING, 5, 3)),
                new PieceMove(king, new Piece(Color.BLACK, Type.KING, 3, 5)),
                new PieceMove(king, new Piece(Color.BLACK, Type.KING, 4, 5)),
                new PieceMove(king, new Piece(Color.BLACK, Type.KING, 5, 5)));
    }

    @Test
    public void itShouldCalculateKingMoveWithoutBlackAndWithWhitePiece() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 4, 4);
        final Piece whitePon = new Piece(Color.WHITE, Type.PON, 3, 3);
        final Piece whiteRock = new Piece(Color.WHITE, Type.ROCK, 4, 3);
        final Board board = new Board(new Player(Color.BLACK, Collections.emptyList()),
                List.of(whitePon, whiteRock),
                new Player(Color.BLACK, Collections.emptyList()),
                Collections.singletonList(blackKing),
                Color.BLACK);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, blackKing);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                //new PieceMove(blackKing, new Piece(Color.BLACK, Type.KING, 3, 3)),
                new PieceMove(blackKing, new Piece(Color.BLACK, Type.KING, 4, 3), whiteRock),
                //new PieceMove(blackKing, new Piece(Color.BLACK, Type.KING, 5, 3)),
                new PieceMove(blackKing, new Piece(Color.BLACK, Type.KING, 3, 4)),
                new PieceMove(blackKing, new Piece(Color.BLACK, Type.KING, 5, 4)),
                new PieceMove(blackKing, new Piece(Color.BLACK, Type.KING, 3, 5)),
                //new PieceMove(blackKing, new Piece(Color.BLACK, Type.KING, 4, 5)), //?
                new PieceMove(blackKing, new Piece(Color.BLACK, Type.KING, 5, 5)));
    }

    @Test
    public void itShouldCalculateWhiteKnightMove() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 4, 4);
        final Piece whitePon = new Piece(Color.WHITE, Type.PON, 3, 3);
        final Piece whiteRock = new Piece(Color.WHITE, Type.ROCK, 4, 3);
        final Board board = new Board(new Player(Color.BLACK, Collections.emptyList()),
                List.of(whitePon, whiteRock),
                new Player(Color.BLACK, Collections.emptyList()),
                Collections.singletonList(blackKing),
                Color.BLACK);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, blackKing);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                //new PieceMove(blackKing, new Piece(Color.BLACK, Type.KING, 3, 3)),
                new PieceMove(blackKing, new Piece(Color.BLACK, Type.KING, 4, 3), whiteRock),
                //new PieceMove(blackKing, new Piece(Color.BLACK, Type.KING, 5, 3)),
                new PieceMove(blackKing, new Piece(Color.BLACK, Type.KING, 3, 4)),
                new PieceMove(blackKing, new Piece(Color.BLACK, Type.KING, 5, 4)),
                new PieceMove(blackKing, new Piece(Color.BLACK, Type.KING, 3, 5)),
                //new PieceMove(blackKing, new Piece(Color.BLACK, Type.KING, 4, 5)), //?
                new PieceMove(blackKing, new Piece(Color.BLACK, Type.KING, 5, 5)));
    }

    //todo faire un test des coups possible du cavalier quand le roi est déja en cours d'echec
    //todo faire un test pour castle

    /************************************************************************************************************************
     *
     *                                              KNIGHT
     *
     ***********************************************************************************************************************/

    @Test
    public void itShouldCalculateWhiteKnightMoves() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 4, 4);
        final Piece whiteKnight = new Piece(Color.WHITE, Type.KNIGHT, 5, 6);
        final Board board = new Board(new Player(Color.BLACK, Collections.emptyList()),
                List.of(whiteKing, whiteKnight),
                new Player(Color.BLACK, Collections.emptyList()),
                Collections.emptyList(),
                Color.WHITE);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, whiteKnight);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(whiteKnight, new Piece(Color.WHITE, Type.KNIGHT, 7, 7)),
                new PieceMove(whiteKnight, new Piece(Color.WHITE, Type.KNIGHT, 7, 5)),
                new PieceMove(whiteKnight, new Piece(Color.WHITE, Type.KNIGHT, 6, 4)),
                new PieceMove(whiteKnight, new Piece(Color.WHITE, Type.KNIGHT, 3, 5)),
                new PieceMove(whiteKnight, new Piece(Color.WHITE, Type.KNIGHT, 3, 7))
                );
    }

    @Test
    public void itShouldCalculateWhiteKnightMovesWhenHeIsPinned() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 4, 4);
        final Piece whiteKnight = new Piece(Color.WHITE, Type.KNIGHT, 3, 4);
        final Piece blackQueen = new Piece(Color.BLACK, Type.QUEEN, 0, 4);
        final Board board = new Board(new Player(Color.BLACK, Collections.emptyList()),
                List.of(whiteKing, whiteKnight),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackQueen),
                Color.WHITE);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, whiteKnight);

        //THEN
        Assertions.assertThat(result).isEmpty();
    }

    /************************************************************************************************************************
     *
     *                                              BISHOP
     *
     ***********************************************************************************************************************/

    @Test
    public void itShouldCalculateBishopMoves() {

    }
}