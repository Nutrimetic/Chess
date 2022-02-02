package fr.baptiste.business.utilities;

import fr.baptiste.business.exceptions.BoardCloneException;
import fr.baptiste.domain.Board;
import fr.baptiste.domain.Color;
import fr.baptiste.domain.Piece;
import fr.baptiste.domain.PieceMove;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe utilitaire permettant de jouer un mouvement sur le board
 */
public class Move {

    /**
     * Permet de jouer un coup sur le board. Prend en compte les prises de pièce éventuels, les mouvements de plusieurs pièce (ex : castle), met à jour l'historique et la liste
     * des pièces capturés par joueur.
     * @param board le board avant que le coup ne soit joué
     * @param pieceMove le coup à jouer
     * @return le nouveau board avec le mouvement joué
     */
    public Board move(Board board, PieceMove pieceMove) {
        List<Piece> whitePiece;
        List<Piece> blackPiece;
        Color tempo;
        try {
            Board result = board.clone();
            if(Color.WHITE.equals(pieceMove.getPieceBeforeMove().get(0).getColor())) {
                blackPiece = board.getPiecePlayerBlack().stream().map(Piece::clone).collect(Collectors.toList());
                whitePiece = board.getPiecePlayerWhite().stream().map(Piece::clone).collect(Collectors.toList());
                whitePiece.removeAll(pieceMove.getPieceBeforeMove());
                whitePiece.addAll(pieceMove.getPieceAfterMove());

                if(pieceMove.getPieceCaptured().isPresent()) {
                    blackPiece.remove(pieceMove.getPieceCaptured().get());
                    result.getPlayerWhite().addPiecesCapture(pieceMove.getPieceCaptured().get());
                }
                tempo = Color.BLACK;
            } else {
                whitePiece = board.getPiecePlayerWhite().stream().map(Piece::clone).collect(Collectors.toList());
                blackPiece = board.getPiecePlayerBlack().stream().map(Piece::clone).collect(Collectors.toList());
                blackPiece.removeAll(pieceMove.getPieceBeforeMove());
                blackPiece.addAll(pieceMove.getPieceAfterMove());

                if(pieceMove.getPieceCaptured().isPresent()) {
                    whitePiece.remove(pieceMove.getPieceCaptured().get());
                    result.getPlayerBlack().addPiecesCapture(pieceMove.getPieceCaptured().get());
                }
                tempo = Color.WHITE;
            }

            result.setPiecePlayerBlack(blackPiece);
            result.setPiecePlayerWhite(whitePiece);
            result.addHistoryMove(Collections.singletonList(pieceMove));
            result.setTempo(tempo);

            return result;
        } catch (CloneNotSupportedException e) {
            throw new BoardCloneException(String.format("Erreur lors du cloneage du board %s", board), e);
        }
    }
}
