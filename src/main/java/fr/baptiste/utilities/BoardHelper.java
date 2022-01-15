package fr.baptiste.utilities;

import fr.baptiste.domain.Board;
import fr.baptiste.domain.Color;
import fr.baptiste.domain.Piece;
import fr.baptiste.domain.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BoardHelper {

    /**
     * Détermine si le board est dans un situation ou un joueur à gagné.
     * Afin de savoir si un joueur a gagné, il suffit de simuler tous les coups possibles du joueur qui a le tempo et de voir si le roi
     * est toujours en état de prise après chaque coup.
     *
     * @param board
     * @return true si le joueur qui n'a pas le tempo a gagné
     */
    public boolean isThereAWinner(Board board) {
        if (board.getTempo().equals(Color.WHITE)) {

        }

        return false;
    }

    public List<PieceMove> availableMove(Board board, Piece piece) {
        if (piece.getType().equals(Type.KING)) {
            return this.kingAvailableMove(board, piece);
        } else if (piece.getType().equals(Type.QUEEN)) {
            return this.queenAvailableMove(board, piece);
        } else if (piece.getType().equals(Type.ROCK)) {
            return this.rockAvailableMove(board, piece);
        } else if (piece.getType().equals(Type.BISHOP)) {
            return this.bishopAvailableMove(board, piece);
        } else if (piece.getType().equals(Type.KNIGHT)) {
            return this.knightAvailableMove(board, piece);
        } else {
            return this.ponAvailableMove(board, piece);
        }
    }


    private List<PieceMove> kingAvailableMove(Board board, Piece piece) {
        List<PieceMove> result = new ArrayList<>();
        addPotentialMove(board, piece, result, piece.getLigne() - 1, piece.getColonne() - 1);
        addPotentialMove(board, piece, result, piece.getLigne() - 1, piece.getColonne());
        addPotentialMove(board, piece, result, piece.getLigne() - 1, piece.getColonne() + 1);

        addPotentialMove(board, piece, result, piece.getLigne(), piece.getColonne() - 1);
        addPotentialMove(board, piece, result, piece.getLigne(), piece.getColonne());
        addPotentialMove(board, piece, result, piece.getLigne(), piece.getColonne() + 1);

        addPotentialMove(board, piece, result, piece.getLigne() + 1, piece.getColonne() - 1);
        addPotentialMove(board, piece, result, piece.getLigne() + 1, piece.getColonne());
        addPotentialMove(board, piece, result, piece.getLigne() + 1, piece.getColonne() + 1);

        return result;
    }

    private List<PieceMove> queenAvailableMove(Board board, Piece piece) {
        List<PieceMove> result = new ArrayList<>();

        //on teste horizontalement et verticalement
        result.addAll(rockAvailableMove(board, piece));

        //on teste les diagonales
        result.addAll(bishopAvailableMove(board, piece));

        return result;
    }

    private List<PieceMove> rockAvailableMove(Board board, Piece piece) {
        List<PieceMove> result = new ArrayList<>();

        //on teste la colonne entière
        for (int ligne = piece.getLigne() + 1; ligne < 8; ligne++) {
            if (!addPotentialMove(board, piece, result, ligne, piece.getColonne())) {
                break;
            }
        }
        for (int ligne = piece.getLigne() - 1; ligne >= 0; ligne--) {
            if (!addPotentialMove(board, piece, result, ligne, piece.getColonne())) {
                break;
            }
        }

        //on teste la ligne entière
        for (int colonne = piece.getColonne() + 1; colonne < 8; colonne++) {
            if (!addPotentialMove(board, piece, result, piece.getLigne(), colonne)) {
                break;
            }
        }
        for (int colonne = piece.getColonne() - 1; colonne >= 0; colonne--) {
            if (!addPotentialMove(board, piece, result, piece.getLigne(), colonne)) {
                break;
            }
        }

        return result;
    }

    private List<PieceMove> knightAvailableMove(Board board, Piece piece) {
        List<PieceMove> result = new ArrayList<>();
        addPotentialMove(board, piece, result, piece.getLigne() - 2, piece.getColonne() + 1);
        addPotentialMove(board, piece, result, piece.getLigne() - 2, piece.getColonne() - 1);

        addPotentialMove(board, piece, result, piece.getLigne() + 2, piece.getColonne() + 1);
        addPotentialMove(board, piece, result, piece.getLigne() + 2, piece.getColonne() - 1);

        addPotentialMove(board, piece, result, piece.getLigne() + 1, piece.getColonne() - 2);
        addPotentialMove(board, piece, result, piece.getLigne() - 1, piece.getColonne() - 2);

        addPotentialMove(board, piece, result, piece.getLigne() + 1, piece.getColonne() + 2);
        addPotentialMove(board, piece, result, piece.getLigne() - 1, piece.getColonne() + 2);

        return result;
    }

    private List<PieceMove> bishopAvailableMove(Board board, Piece piece) {
        List<PieceMove> result = new ArrayList<>();
        int ligne;
        int colonne;

        //on teste la première diagonale qui démarre dans le bas gauche de l'échiquier et finis en haut à droite
        ligne = piece.getLigne();
        colonne = piece.getColonne();
        for (int i = 0; i < 8; i++) {
            ligne = ligne + 1;
            colonne = colonne + 1;
            if (!addPotentialMove(board, piece, result, ligne, colonne)) {
                break;
            }
        }
        ligne = piece.getLigne();
        colonne = piece.getColonne();
        for (int i = 0; i < 8; i++) {
            ligne = ligne - 1;
            colonne = colonne - 1;
            if (!addPotentialMove(board, piece, result, ligne, colonne)) {
                break;
            }
        }

        //on teste la deuxième diagonale qui démarre dans le haut gauche de l'échiquier et finis en bas à droite
        ligne = piece.getLigne();
        colonne = piece.getColonne();
        for (int i = 0; i < 8; i++) {
            ligne = ligne + 1;
            colonne = colonne - 1;
            if (!addPotentialMove(board, piece, result, ligne, colonne)) {
                break;
            }
        }
        ligne = piece.getLigne();
        colonne = piece.getColonne();
        for (int i = 0; i < 8; i++) {
            ligne = ligne - 1;
            colonne = colonne + 1;
            if (!addPotentialMove(board, piece, result, ligne, colonne)) {
                break;
            }
        }

        return result;
    }

    private List<PieceMove> ponAvailableMove(Board board, Piece piece) {
        List<PieceMove> result = new ArrayList<>();

        if (piece.getColor().equals(Color.WHITE)) {
            //le pion peut avancer d'une case
            addPotentialMove(board, piece, result, piece.getLigne() - 1, piece.getColonne());

            //le pion peut se déplacer de 2 cases en avant quand il n'a jamais été joué
            if (piece.getLigne() == 6) {
                addPotentialMove(board, piece, result, piece.getLigne() - 2, piece.getColonne());
            }

            //le pion peut capturer une pièce sur son côté gauche
            if (isThereAlreadyAPieceAtThisPosition(board, piece.getLigne() - 1, piece.getColonne() - 1, Color.BLACK)) {
                addPotentialMove(board, piece, result, piece.getLigne() - 1, piece.getColonne() - 1);
            }
            //le pion peut capturer une pièce sur son côté droit
            if (isThereAlreadyAPieceAtThisPosition(board, piece.getLigne() - 1, piece.getColonne() + 1, Color.BLACK)) {
                addPotentialMove(board, piece, result, piece.getLigne() - 1, piece.getColonne() + 1);
            }

            //prise en passant
            if (board.getEnPassant().isPresent() &&
                    board.getEnPassant().get().getColor().equals(Color.BLACK) &&
                    board.getEnPassant().get().getLigne() == piece.getLigne()
            ) {
                //prise en passant à gauche
                if ((piece.getColonne() - 1) == board.getEnPassant().get().getColonne()) {
                    addPotentialMove(board, piece, result, piece.getLigne() - 1, piece.getColonne() - 1);
                }
                //prise en passant à droite
                if ((piece.getColonne() + 1) == board.getEnPassant().get().getColonne()) {
                    addPotentialMove(board, piece, result, piece.getLigne() - 1, piece.getColonne() + 1);
                }
            }
        } else {
            //le pion peut avancer d'une case
            addPotentialMove(board, piece, result, piece.getLigne() + 1, piece.getColonne());

            //le pion peut se déplacer de 2 cases en avant quand il n'a jamais été joué
            if (piece.getLigne() == 1) {
                addPotentialMove(board, piece, result, piece.getLigne() + 2, piece.getColonne());
            }

            //le pion peut capturer une pièce sur son côté gauche
            if (isThereAlreadyAPieceAtThisPosition(board, piece.getLigne() + 1, piece.getColonne() - 1, Color.WHITE)) {
                addPotentialMove(board, piece, result, piece.getLigne() + 1, piece.getColonne() - 1);
            }
            //le pion peut capturer une pièce sur son côté droit
            if (isThereAlreadyAPieceAtThisPosition(board, piece.getLigne() + 1, piece.getColonne() + 1, Color.WHITE)) {
                addPotentialMove(board, piece, result, piece.getLigne() + 1, piece.getColonne() + 1);
            }

            //prise en passant
            if (board.getEnPassant().isPresent() &&
                    board.getEnPassant().get().getColor().equals(Color.WHITE) &&
                    board.getEnPassant().get().getLigne() == piece.getLigne()
            ) {
                //prise en passant à gauche
                if ((piece.getColonne() - 1) == board.getEnPassant().get().getColonne()) {
                    addPotentialMove(board, piece, result, piece.getLigne() + 1, piece.getColonne() - 1);
                }
                //prise en passant à droite
                if ((piece.getColonne() + 1) == board.getEnPassant().get().getColonne()) {
                    addPotentialMove(board, piece, result, piece.getLigne() + 1, piece.getColonne() + 1);
                }
            }
        }

        return result;
    }

    private boolean addPotentialMove(Board board, Piece piece, List<PieceMove> result, int ligneTeste, int colonneTeste) {
        if (isThisMovePossible(board, ligneTeste, colonneTeste, piece.getColor())) {
            result.add(new PieceMove(piece, new Piece(piece.getColor(), piece.getType(), ligneTeste, colonneTeste)));
            return true;
        }
        return false;
    }

    /**
     * Cette méthode gère aussi les cas de figure ou le movement sort du plateau
     *
     * @param ligne
     * @param colonne
     * @return
     */
    private boolean isThisMovePossible(Board board, int ligne, int colonne, Color pieceColor) {
        if ((ligne < 0 || ligne > 7) || (colonne < 0 || colonne > 7)) {
            return true;
        }
        return isThereAlreadyAPieceAtThisPosition(board, ligne, colonne, pieceColor);
    }

    private boolean isThereAlreadyAPieceAtThisPosition(Board board, int ligne, int colonne, Color pieceColor) {
        if (pieceColor.equals(Color.WHITE)) {
            return board.getPiecePlayerWhite().stream().anyMatch(piece -> piece.getColonne() == colonne && piece.getLigne() == ligne);
        } else {
            return board.getPiecePlayerBlack().stream().anyMatch(piece -> piece.getColonne() == colonne && piece.getLigne() == ligne);
        }
    }

    /**
     * Permet de déterminer si le roi du joueur ayant le tempo se trouve en echec
     *
     * @param board
     * @param pieceMove un mouvement de piece du joueur qui a le tempi
     * @return true si le roi du joueur qui a le tempo est en echec
     */
    private boolean isKingCheck(Board board, PieceMove pieceMove) {
        return false;
    }

    public static class PieceMove {
        private Piece pieceBeforeMove;
        private Piece pieceAfterMove;

        public PieceMove(Piece pieceBeforeMove, Piece pieceAfterMove) {
            this.pieceBeforeMove = pieceBeforeMove;
            this.pieceAfterMove = pieceAfterMove;
        }

        public Piece getPieceBeforeMove() {
            return pieceBeforeMove;
        }

        public Piece getPieceAfterMove() {
            return pieceAfterMove;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PieceMove)) return false;
            PieceMove pieceMove = (PieceMove) o;
            return pieceBeforeMove.equals(pieceMove.pieceBeforeMove) &&
                    pieceAfterMove.equals(pieceMove.pieceAfterMove);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pieceBeforeMove, pieceAfterMove);
        }

        @Override
        public String toString() {
            return "PieceMove{" +
                    "pieceBeforeMove=" + pieceBeforeMove +
                    ", pieceAfterMove=" + pieceAfterMove +
                    '}';
        }
    }
}
