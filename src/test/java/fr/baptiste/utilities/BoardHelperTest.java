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
    public void itShouldCalculateWhiteKnightMovesWithBlackPiece() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 1, 2);
        final Piece whiteKnight = new Piece(Color.WHITE, Type.KNIGHT, 5, 6);
        final Piece blackQueen = new Piece(Color.BLACK, Type.QUEEN, 7, 7);
        final Piece blackPon = new Piece(Color.BLACK, Type.PON, 3, 7);
        final Board board = new Board(new Player(Color.BLACK, Collections.emptyList()),
                List.of(whiteKing, whiteKnight),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackQueen, blackPon),
                Color.WHITE);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, whiteKnight);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(whiteKnight, new Piece(Color.WHITE, Type.KNIGHT, 7, 7), blackQueen),
                new PieceMove(whiteKnight, new Piece(Color.WHITE, Type.KNIGHT, 7, 5)),
                new PieceMove(whiteKnight, new Piece(Color.WHITE, Type.KNIGHT, 6, 4)),
                new PieceMove(whiteKnight, new Piece(Color.WHITE, Type.KNIGHT, 3, 5)),
                new PieceMove(whiteKnight, new Piece(Color.WHITE, Type.KNIGHT, 4, 4)),
                new PieceMove(whiteKnight, new Piece(Color.WHITE, Type.KNIGHT, 3, 7), blackPon)
        );
    }

    @Test
    public void itShouldCalculateBlackKnightMovesifKingIsCheckedByOnePiece() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 4, 4);
        final Piece blackKnight = new Piece(Color.BLACK, Type.KNIGHT, 5, 6);
        final Piece whiteQueen = new Piece(Color.WHITE, Type.QUEEN, 7, 7);
        final Piece whitePon = new Piece(Color.WHITE, Type.PON, 3, 7);
        final Board board = new Board(new Player(Color.BLACK, Collections.emptyList()),
                List.of(whitePon, whiteQueen),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackKnight, blackKing),
                Color.BLACK);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, blackKnight);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(blackKnight, new Piece(Color.BLACK, Type.KNIGHT, 7, 7), whiteQueen)
        );
    }

    @Test
    public void itShouldCalculateBlackKnightMovesifKingIsCheckedByTwoPiece() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 4, 4);
        final Piece blackKnight = new Piece(Color.BLACK, Type.KNIGHT, 5, 6);
        final Piece whiteQueen = new Piece(Color.WHITE, Type.QUEEN, 7, 7);
        final Piece whitePon = new Piece(Color.WHITE, Type.PON, 5, 3);
        final Board board = new Board(new Player(Color.BLACK, Collections.emptyList()),
                List.of(whitePon, whiteQueen),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackKnight, blackKing),
                Color.BLACK);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, blackKnight);

        //THEN
        Assertions.assertThat(result).isEmpty();
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

    @Test
    public void itShouldCalculateBlackKnightMoves() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 4, 4);
        final Piece blackKnight = new Piece(Color.BLACK, Type.KNIGHT, 5, 6);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                Collections.emptyList(),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackKnight, blackKing),
                Color.BLACK);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, blackKnight);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(blackKnight, new Piece(Color.BLACK, Type.KNIGHT, 7, 7)),
                new PieceMove(blackKnight, new Piece(Color.BLACK, Type.KNIGHT, 7, 5)),
                new PieceMove(blackKnight, new Piece(Color.BLACK, Type.KNIGHT, 6, 4)),
                new PieceMove(blackKnight, new Piece(Color.BLACK, Type.KNIGHT, 3, 5)),
                new PieceMove(blackKnight, new Piece(Color.BLACK, Type.KNIGHT, 3, 7))
        );
    }

    @Test
    public void itShouldCalculateBlackKnightMovesWithWhitePiece() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 1, 2);
        final Piece blackKnight = new Piece(Color.BLACK, Type.KNIGHT, 5, 6);
        final Piece whiteQueen = new Piece(Color.WHITE, Type.QUEEN, 7, 7);
        final Piece whitePon = new Piece(Color.WHITE, Type.PON, 3, 7);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteQueen, whitePon),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackKnight, blackKing),
                Color.BLACK);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, blackKnight);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(blackKnight, new Piece(Color.BLACK, Type.KNIGHT, 7, 7), whiteQueen),
                new PieceMove(blackKnight, new Piece(Color.BLACK, Type.KNIGHT, 7, 5)),
                new PieceMove(blackKnight, new Piece(Color.BLACK, Type.KNIGHT, 6, 4)),
                new PieceMove(blackKnight, new Piece(Color.BLACK, Type.KNIGHT, 3, 5)),
                new PieceMove(blackKnight, new Piece(Color.BLACK, Type.KNIGHT, 4, 4)),
                new PieceMove(blackKnight, new Piece(Color.BLACK, Type.KNIGHT, 3, 7), whitePon)
        );
    }

    @Test
    public void itShouldCalculateWhiteKnightMovesifKingIsCheckedByOnePiece() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 4, 4);
        final Piece whiteKnight = new Piece(Color.WHITE, Type.KNIGHT, 5, 6);
        final Piece blackQueen = new Piece(Color.BLACK, Type.QUEEN, 7, 7);
        final Piece blackPon = new Piece(Color.BLACK, Type.PON, 3, 7);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteKing, whiteKnight),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackQueen, blackPon),
                Color.WHITE);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, whiteKnight);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(whiteKnight, new Piece(Color.WHITE, Type.KNIGHT, 7, 7), blackQueen)
        );
    }

    @Test
    public void itShouldCalculateWhiteKnightMovesifKingIsCheckedByTwoPiece() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 4, 4);
        final Piece whiteKnight = new Piece(Color.WHITE, Type.KNIGHT, 5, 6);
        final Piece blackQueen = new Piece(Color.BLACK, Type.QUEEN, 7, 7);
        final Piece blackPon = new Piece(Color.BLACK, Type.PON, 3, 3);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteKing, whiteKnight),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackQueen, blackPon),
                Color.WHITE);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, whiteKnight);

        //THEN
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void itShouldCalculateBlackKnightMovesWhenHeIsPinned() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 4, 4);
        final Piece blackKnight = new Piece(Color.BLACK, Type.KNIGHT, 3, 4);
        final Piece whiteQueen = new Piece(Color.WHITE, Type.QUEEN, 0, 4);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteQueen),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackKnight, blackKing),
                Color.BLACK);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, blackKnight);

        //THEN
        Assertions.assertThat(result).isEmpty();
    }

    /************************************************************************************************************************
     *
     *                                              BISHOP
     *
     ***********************************************************************************************************************/

    @Test
    public void itShouldCalculateWhiteBishopMoves() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 4, 4);
        final Piece whiteBishop = new Piece(Color.WHITE, Type.BISHOP, 3, 4);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteBishop, whiteKing),
                new Player(Color.BLACK, Collections.emptyList()),
                Collections.emptyList(),
                Color.WHITE);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, whiteBishop);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 2, 3)),
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 1, 2)),
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 0, 1)),

                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 4, 5)),
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 5, 6)),
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 6, 7)),

                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 4, 3)),
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 5, 2)),
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 6, 1)),
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 7, 0)),

                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 2, 5)),
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 1, 6)),
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 0, 7))
        );
    }

    @Test
    public void itShouldCalculateBlackBishopMoves() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 4, 4);
        final Piece blackBishop = new Piece(Color.BLACK, Type.BISHOP, 3, 4);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                Collections.emptyList(),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackKing, blackBishop),
                Color.BLACK);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, blackBishop);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 2, 3)),
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 1, 2)),
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 0, 1)),

                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 4, 5)),
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 5, 6)),
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 6, 7)),

                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 4, 3)),
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 5, 2)),
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 6, 1)),
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 7, 0)),

                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 2, 5)),
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 1, 6)),
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 0, 7))
        );
    }

    @Test
    public void itShouldCalculateWhiteBishopMoveWhenThereAreBlackAndWhitePiece() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 4, 5);
        final Piece whiteBishop = new Piece(Color.WHITE, Type.BISHOP, 3, 4);
        final Piece blackPon = new Piece(Color.BLACK, Type.PON, 2, 3);
        final Piece blackKnight = new Piece(Color.BLACK, Type.KNIGHT, 6, 1);
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 6, 7);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteKing , whiteBishop),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackPon, blackKnight, blackKing),
                Color.WHITE);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, whiteBishop);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 2, 3), blackPon),

                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 4, 3)),
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 5, 2)),
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 6, 1), blackKnight),

                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 2, 5)),
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 1, 6)),
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 0, 7))
        );
    }

    @Test
    public void itShouldCalculateBlackBishopMoveWhenThereAreBlackAndWhitePiece() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 4, 5);
        final Piece blackBishop = new Piece(Color.BLACK, Type.BISHOP, 3, 4);
        final Piece whitePon = new Piece(Color.WHITE, Type.PON, 2, 3);
        final Piece whiteKnight = new Piece(Color.WHITE, Type.KNIGHT, 6, 1);
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 6, 7);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteKing , whiteKnight, whitePon),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackKing, blackBishop),
                Color.BLACK);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, blackBishop);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 2, 3), whitePon),

                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 4, 3)),
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 5, 2)),
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 6, 1), whiteKnight),

                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 2, 5)),
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 1, 6)),
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 0, 7))
        );
    }

    @Test
    public void itShouldCalculateWhiteBishopMoveWhenheIsPinned() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 4, 5);
        final Piece whiteBishop = new Piece(Color.WHITE, Type.BISHOP, 3, 4);
        final Piece blackQueen = new Piece(Color.BLACK, Type.QUEEN, 0, 1);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteKing , whiteBishop),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackQueen),
                Color.WHITE);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, whiteBishop);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 2, 3)),
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 1, 2)),
                new PieceMove(whiteBishop, new Piece(Color.WHITE, Type.BISHOP, 0, 1), blackQueen)
        );
    }

    @Test
    public void itShouldCalculateBlackBishopMoveWhenheIsPinned() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 4, 5);
        final Piece blackBishop = new Piece(Color.BLACK, Type.BISHOP, 3, 4);
        final Piece whiteQueen = new Piece(Color.WHITE, Type.QUEEN, 0, 1);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteQueen),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackKing, blackBishop),
                Color.BLACK);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board,blackBishop);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 2, 3)),
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 1, 2)),
                new PieceMove(blackBishop, new Piece(Color.BLACK, Type.BISHOP, 0, 1), whiteQueen)
        );
    }

    /************************************************************************************************************************
     *
     *                                              Rock
     *
     ***********************************************************************************************************************/

    @Test
    public void itShouldCalculateWhiteRockMoves() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 4, 4);
        final Piece whiteRock = new Piece(Color.WHITE, Type.ROCK, 3, 3);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteKing, whiteRock),
                new Player(Color.BLACK, Collections.emptyList()),
                Collections.emptyList(),
                Color.WHITE);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, whiteRock);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 2, 3)),
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 1, 3)),
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 0, 3)),
                
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 4, 3)),
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 5, 3)),
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 6, 3)),
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 7, 3)),
                
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 3, 2)),
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 3, 1)),
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 3, 0)),

                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 3, 4)),
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 3, 5)),
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 3, 6)),
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 3, 7))
        );
    }

    @Test
    public void itShouldCalculateWhiteRockMovesWithBlackAndWhitePieces() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 3, 4);
        final Piece whiteRock = new Piece(Color.WHITE, Type.ROCK, 3, 3);
        final Piece blackRock = new Piece(Color.BLACK, Type.ROCK, 5, 3);
        final Piece blackPon = new Piece(Color.BLACK, Type.ROCK, 1, 3);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteKing, whiteRock),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackPon, blackRock),
                Color.WHITE);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, whiteRock);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 2, 3)),
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 1, 3), blackPon),

                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 4, 3)),
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 5, 3), blackRock),

                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 3, 2)),
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 3, 1)),
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 3, 0))
        );
    }

    @Test
    public void itShouldCalculateWhiteRockMovesIfPinned() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 3, 4);
        final Piece whiteRock = new Piece(Color.WHITE, Type.ROCK, 3, 3);
        final Piece blackRock = new Piece(Color.BLACK, Type.ROCK, 3, 0);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteKing, whiteRock),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackRock),
                Color.WHITE);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, whiteRock);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 3, 2)),
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 3, 1)),
                new PieceMove(whiteRock, new Piece(Color.WHITE, Type.ROCK, 3, 0), blackRock)
        );
    }

    @Test
    public void itShouldCalculateBlackRockMoves() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 4, 4);
        final Piece blackRock = new Piece(Color.BLACK, Type.ROCK, 3, 3);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                Collections.emptyList(),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackKing, blackRock),
                Color.BLACK);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board,blackRock);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 2, 3)),
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 1, 3)),
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 0, 3)),

                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 4, 3)),
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 5, 3)),
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 6, 3)),
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 7, 3)),

                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 3, 2)),
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 3, 1)),
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 3, 0)),

                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 3, 4)),
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 3, 5)),
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 3, 6)),
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 3, 7))
        );
    }

    @Test
    public void itShouldCalculateBlackRockMovesWithBlackAndWhitePieces() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 3, 4);
        final Piece blackRock = new Piece(Color.BLACK, Type.ROCK, 3, 3);
        final Piece whiteRock = new Piece(Color.WHITE, Type.ROCK, 5, 3);
        final Piece whitePon = new Piece(Color.WHITE, Type.ROCK, 1, 3);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whitePon, whiteRock),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackKing, blackRock),
                Color.BLACK);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, blackRock);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 2, 3)),
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 1, 3), whitePon),

                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 4, 3)),
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 5, 3), whiteRock),

                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 3, 2)),
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 3, 1)),
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 3, 0))
        );
    }

    @Test
    public void itShouldCalculateBlackRockMovesIfPinned() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 3, 4);
        final Piece blackRock = new Piece(Color.BLACK, Type.ROCK, 3, 3);
        final Piece whiteRock = new Piece(Color.WHITE, Type.ROCK, 3, 0);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteRock),
                new Player(Color.BLACK, Collections.emptyList()),
                List.of(blackKing, blackRock),
                Color.BLACK);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, blackRock);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 3, 2)),
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 3, 1)),
                new PieceMove(blackRock, new Piece(Color.BLACK, Type.ROCK, 3, 0), whiteRock)
        );
    }

    /************************************************************************************************************************
     *
     *                                              PON
     *
     ***********************************************************************************************************************/

    @Test
    public void itShouldCalculateWhitePonMove() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 3, 4);
        final Piece whitePon = new Piece(Color.WHITE, Type.PON, 4, 0);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteKing, whitePon),
                new Player(Color.WHITE, Collections.emptyList()),
                Collections.emptyList(),
                Color.WHITE);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, whitePon);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(whitePon, new Piece(Color.WHITE, Type.PON, 3, 0))
        );
    }

    @Test
    public void itShouldCalculateWhitePonMoveWithDefaultPosition() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 3, 4);
        final Piece whitePon = new Piece(Color.WHITE, Type.PON, 6, 0);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteKing, whitePon),
                new Player(Color.WHITE, Collections.emptyList()),
                Collections.emptyList(),
                Color.WHITE);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, whitePon);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(whitePon, new Piece(Color.WHITE, Type.PON, 5, 0)),
                new PieceMove(whitePon, new Piece(Color.WHITE, Type.PON, 4, 0))
        );
    }

    @Test
    public void itShouldCalculateWhitePonMoveWithWhiteAndBlackPiece() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece whiteKing = new Piece(Color.WHITE, Type.KING, 0, 4);
        final Piece whitePon = new Piece(Color.WHITE, Type.PON, 6, 1);
        final Piece blackPon = new Piece(Color.BLACK, Type.PON, 4, 1);
        final Piece blackQueen = new Piece(Color.BLACK, Type.QUEEN, 5, 2);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteKing, whitePon),
                new Player(Color.WHITE, Collections.emptyList()),
                List.of(blackPon, blackQueen),
                Color.WHITE);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, whitePon);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(whitePon, new Piece(Color.WHITE, Type.PON, 5, 1)),
                new PieceMove(whitePon, new Piece(Color.WHITE, Type.PON, 5, 2), blackQueen)
        );
    }

    //todo faire un test de prise en passant

    @Test
    public void itShouldCalculateBlackPonMove() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 3, 4);
        final Piece blackPon = new Piece(Color.BLACK, Type.PON, 4, 0);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                Collections.emptyList(),
                new Player(Color.WHITE, Collections.emptyList()),
                List.of(blackKing, blackPon),
                Color.BLACK);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, blackPon);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(blackPon, new Piece(Color.BLACK, Type.PON, 5, 0))
        );
    }

    @Test
    public void itShouldCalculateBlackPonMoveWithDefaultPosition() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 3, 4);
        final Piece blackPon = new Piece(Color.BLACK, Type.PON, 1, 0);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                Collections.emptyList(),
                new Player(Color.WHITE, Collections.emptyList()),
                List.of(blackKing, blackPon),
                Color.BLACK);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, blackPon);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(blackPon, new Piece(Color.BLACK, Type.PON, 2, 0)),
                new PieceMove(blackPon, new Piece(Color.BLACK, Type.PON, 3, 0))
        );
    }

    @Test
    public void itShouldCalculateBlackPonMoveWithWhiteAndBlackPiece() {
        //GIVEN
        final BoardBuilder boardBuilder = new BoardBuilder();
        final Piece blackKing = new Piece(Color.BLACK, Type.KING, 1, 4);
        final Piece blackPon = new Piece(Color.BLACK, Type.PON, 1, 1);
        final Piece whitePon = new Piece(Color.WHITE, Type.PON, 3, 1);
        final Piece whiteQueen = new Piece(Color.WHITE, Type.QUEEN, 2, 2);
        final Board board = new Board(new Player(Color.WHITE, Collections.emptyList()),
                List.of(whiteQueen, whitePon),
                new Player(Color.WHITE, Collections.emptyList()),
                List.of(blackPon, blackKing),
                Color.BLACK);
        final BoardHelper boardHelper = new BoardHelper(boardBuilder);

        //WHEN
        final List<PieceMove> result = boardHelper.availableMove(board, blackPon);

        //THEN
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new PieceMove(blackPon, new Piece(Color.BLACK, Type.PON, 2, 1)),
                new PieceMove(blackPon, new Piece(Color.BLACK, Type.PON, 2, 2), whiteQueen)
        );
    }

    //todo faire un test de prise en passant

}