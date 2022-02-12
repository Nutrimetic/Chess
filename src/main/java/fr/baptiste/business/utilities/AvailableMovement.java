package fr.baptiste.business.utilities;

import fr.baptiste.business.exceptions.cantFindPieceException;
import fr.baptiste.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AvailableMovement {

    private final Move move;

    public AvailableMovement(Move move) {
        this.move = move;
    }

    /**
     * Détermine si le board est dans un situation ou un joueur à gagné.
     * Afin de savoir si un joueur a gagné, il suffit de simuler tous les coups possibles du joueur qui a le tempo et de voir si le roi du joueur ayant le tempo
     * est toujours en état de prise après chaque coup.
     *
     * @param board
     * @return true si le joueur qui n'a pas le tempo a gagné
     */
    public boolean isThereAWinner(Board board) {
        if (board.getTempo().equals(Color.WHITE)) {
            return board.getPiecePlayerWhite().stream()
                    .flatMap(piece -> this.availableMove(board, piece).stream())
                    .allMatch(pieceMove -> isKingCheck(board, pieceMove));
        } else {
            return board.getPiecePlayerBlack().stream()
                    .flatMap(piece -> this.availableMove(board, piece).stream())
                    .allMatch(pieceMove -> isKingCheck(board, pieceMove));
        }
    }

    /**
     * Détermine si le board est dans un situation ou une égalité est définis
     * Voici la liste des cas de figure ou il y a égalité :
     * - Un des 2 joueurs n'a plus de coup disponible
     * - répétiton 3 fois des méme coups
     *
     * @param board
     * @return true si il y a un draw
     */
    public boolean isThereADraw(Board board) {
        return false;
    }

    /**
     * Détermine tous les mouvement possible sur le board pour une pièce en prenant en compte le fait que le roi ne doit pas être exposé
     *
     * @param board l'echiquier
     * @param piece la pièce qui peut être joué
     * @return la liste des coups disponibles
     */
    public List<PieceMove> availableMove(Board board, Piece piece) {
        List<PieceMove> allPossibleMove = this.getAllPossibleMove(board, piece);
        return allPossibleMove.stream()
                .filter(pieceMove -> !isKingCheck(board, pieceMove))
                .collect(Collectors.toList());
    }

    /**
     * Une méthode de prise de pièce qui prends en compte les mouvements sur des pièces alliés
     * Détermine tous les coups de prise potentiel. Les mouvements de prises renvoyés ne prennent pas en compte une potentielle mise en echec (voir méthode isKingCheck)
     *
     * @param board
     * @param piece
     * @return renvoie tous les coups potentiels en comptant les prises de pièce
     */
    private List<PieceMove> getAllPossibleTakenMove(Board board, Piece piece) {
        List<PieceMove> pieceMoves;
        if (piece.getType().equals(Type.KING)) {
            pieceMoves = this.kingAvailableTakenMove(board, piece);
        } else if (piece.getType().equals(Type.QUEEN)) {
            pieceMoves = this.queenAvailableMove(board, piece);
        } else if (piece.getType().equals(Type.ROCK)) {
            pieceMoves = this.rockAvailableMove(board, piece);
        } else if (piece.getType().equals(Type.BISHOP)) {
            pieceMoves = this.bishopAvailableMove(board, piece);
        } else if (piece.getType().equals(Type.KNIGHT)) {
            pieceMoves = this.knightAvailableMove(board, piece);
        } else {
            pieceMoves = this.ponAvailableTakenMove(board, piece);
        }
        return pieceMoves;
    }

    private List<PieceMove> getAllPossibleMove(Board board, Piece piece) {
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

    private List<PieceMove> kingAvailableMove(Board board, Piece king) {
        List<PieceMove> result = new ArrayList<>();
        Optional<PieceMove> pieceMove;

        pieceMove = addPotentialMove(board, king, king.getLigne() - 1, king.getColonne() - 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, king, king.getLigne() - 1, king.getColonne());
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, king, king.getLigne() - 1, king.getColonne() + 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, king, king.getLigne(), king.getColonne() - 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, king, king.getLigne(), king.getColonne());
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, king, king.getLigne(), king.getColonne() + 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, king, king.getLigne() + 1, king.getColonne() - 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, king, king.getLigne() + 1, king.getColonne());
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, king, king.getLigne() + 1, king.getColonne() + 1);
        pieceMove.ifPresent(result::add);

        if (!isKingCheck(board, king.getColor())) {
            if (canKingCastleRight(board, king)) {
                if (Color.WHITE.equals(king.getColor())) {
                    if (!isTherePotentielMoveAtPosition(board, Color.BLACK, 0, 7) &&
                            !isTherePotentielMoveAtPosition(board, Color.BLACK, 0, 6) &&
                            !isTherePotentielMoveAtPosition(board, Color.BLACK, 0, 5)) {
                        result.add(new PieceMove(
                                Arrays.asList(king, getPieceAtThisPosition(board, 0, 7, Color.WHITE).orElseThrow(() -> new cantFindPieceException(Type.ROCK, Color.WHITE, 0, 7))),
                                Arrays.asList(new Piece(Color.WHITE, Type.KING, 0, 6), new Piece(Color.WHITE, Type.ROCK, 0, 5))
                        ));
                    }
                } else {
                    if (!isTherePotentielMoveAtPosition(board, Color.WHITE, 7, 7) &&
                            !isTherePotentielMoveAtPosition(board, Color.WHITE, 7, 6) &&
                            !isTherePotentielMoveAtPosition(board, Color.WHITE, 7, 5)) {
                        result.add(new PieceMove(
                                Arrays.asList(king, getPieceAtThisPosition(board, 7, 7, Color.BLACK).orElseThrow(() -> new cantFindPieceException(Type.ROCK, Color.BLACK, 7, 7))),
                                Arrays.asList(new Piece(Color.BLACK, Type.KING, 7, 6), new Piece(Color.BLACK, Type.ROCK, 7, 5))
                        ));
                    }
                }
            }
            if (canKingCastleLeft(board, king)) {
                if (Color.WHITE.equals(king.getColor())) {
                    if (!isTherePotentielMoveAtPosition(board, Color.BLACK, 0, 0) &&
                            !isTherePotentielMoveAtPosition(board, Color.BLACK, 0, 1) &&
                            !isTherePotentielMoveAtPosition(board, Color.BLACK, 0, 2) &&
                            !isTherePotentielMoveAtPosition(board, Color.BLACK, 0, 3)) {
                        result.add(new PieceMove(
                                Arrays.asList(king, getPieceAtThisPosition(board, 0, 0, Color.WHITE).orElseThrow(() -> new cantFindPieceException(Type.ROCK, Color.WHITE, 0, 0))),
                                Arrays.asList(new Piece(Color.WHITE, Type.KING, 0, 2), new Piece(Color.WHITE, Type.ROCK, 0, 3))
                        ));
                    }
                } else {
                    if (!isTherePotentielMoveAtPosition(board, Color.WHITE, 7, 3) &&
                            !isTherePotentielMoveAtPosition(board, Color.WHITE, 7, 2) &&
                            !isTherePotentielMoveAtPosition(board, Color.WHITE, 7, 1) &&
                            !isTherePotentielMoveAtPosition(board, Color.WHITE, 7, 0)) {
                        result.add(new PieceMove(
                                Arrays.asList(king, getPieceAtThisPosition(board, 7, 0, Color.BLACK).orElseThrow(() -> new cantFindPieceException(Type.ROCK, Color.BLACK, 7, 0))),
                                Arrays.asList(new Piece(Color.BLACK, Type.KING, 7, 2), new Piece(Color.BLACK, Type.ROCK, 7, 3))
                        ));
                    }
                }
            }
        }

        return result;
    }

    private boolean isTherePotentielMoveAtPosition(Board board, Color color, int ligne, int colonne) {
        if (color.equals(Color.WHITE)) {
            return board.getPiecePlayerWhite().stream()
                    .flatMap(piece -> this.availableMove(board, piece).stream())
                    .anyMatch(pieceMove -> pieceMove.getPieceAfterMove().get(0).getLigne() == ligne &&
                            pieceMove.getPieceAfterMove().get(0).getColonne() == colonne);
        } else {
            return board.getPiecePlayerBlack().stream()
                    .flatMap(piece -> this.availableMove(board, piece).stream())
                    .anyMatch(pieceMove -> pieceMove.getPieceAfterMove().get(0).getLigne() == ligne &&
                            pieceMove.getPieceAfterMove().get(0).getColonne() == colonne);
        }
    }

    private List<PieceMove> kingAvailableTakenMove(Board board, Piece king) {
        List<PieceMove> result = new ArrayList<>();
        Optional<PieceMove> pieceMove;

        pieceMove = addPotentialMove(board, king, king.getLigne() - 1, king.getColonne() - 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, king, king.getLigne() - 1, king.getColonne());
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, king, king.getLigne() - 1, king.getColonne() + 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, king, king.getLigne(), king.getColonne() - 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, king, king.getLigne(), king.getColonne());
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, king, king.getLigne(), king.getColonne() + 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, king, king.getLigne() + 1, king.getColonne() - 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, king, king.getLigne() + 1, king.getColonne());
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, king, king.getLigne() + 1, king.getColonne() + 1);
        pieceMove.ifPresent(result::add);

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
        Optional<PieceMove> pieceMove;

        //on teste la colonne entière
        for (int ligne = piece.getLigne() + 1; ligne < 8; ligne++) {
            pieceMove = addPotentialMove(board, piece, ligne, piece.getColonne());
            pieceMove.ifPresent(result::add);
            if (this.isThereObstacle(board, ligne, piece.getColonne())) {
                break;
            }
        }
        for (int ligne = piece.getLigne() - 1; ligne >= 0; ligne--) {
            pieceMove = addPotentialMove(board, piece, ligne, piece.getColonne());
            pieceMove.ifPresent(result::add);
            if (this.isThereObstacle(board, ligne, piece.getColonne())) {
                break;
            }
        }

        //on teste la ligne entière
        for (int colonne = piece.getColonne() + 1; colonne < 8; colonne++) {
            pieceMove = addPotentialMove(board, piece, piece.getLigne(), colonne);
            pieceMove.ifPresent(result::add);
            if (this.isThereObstacle(board, piece.getLigne(), colonne)) {
                break;
            }
        }
        for (int colonne = piece.getColonne() - 1; colonne >= 0; colonne--) {
            pieceMove = addPotentialMove(board, piece, piece.getLigne(), colonne);
            pieceMove.ifPresent(result::add);
            if (this.isThereObstacle(board, piece.getLigne(), colonne)) {
                break;
            }
        }

        return result;
    }

    private List<PieceMove> knightAvailableMove(Board board, Piece piece) {
        List<PieceMove> result = new ArrayList<>();
        Optional<PieceMove> pieceMove;

        pieceMove = addPotentialMove(board, piece, piece.getLigne() - 2, piece.getColonne() + 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece, piece.getLigne() - 2, piece.getColonne() - 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece, piece.getLigne() + 2, piece.getColonne() + 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece, piece.getLigne() + 2, piece.getColonne() - 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() - 2);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() - 2);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() + 2);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() + 2);
        pieceMove.ifPresent(result::add);

        return result;
    }

    private List<PieceMove> bishopAvailableMove(Board board, Piece piece) {
        List<PieceMove> result = new ArrayList<>();
        Optional<PieceMove> pieceMove;
        int ligne;
        int colonne;

        //on teste la première diagonale qui démarre dans le bas gauche de l'échiquier et finis en haut à droite
        ligne = piece.getLigne();
        colonne = piece.getColonne();
        for (int i = 0; i < 8; i++) {
            ligne = ligne + 1;
            colonne = colonne + 1;
            pieceMove = addPotentialMove(board, piece, ligne, colonne);
            pieceMove.ifPresent(result::add);
            if (this.isThereObstacle(board, ligne, colonne)) {
                break;
            }
        }
        ligne = piece.getLigne();
        colonne = piece.getColonne();
        for (int i = 0; i < 8; i++) {
            ligne = ligne - 1;
            colonne = colonne - 1;
            pieceMove = addPotentialMove(board, piece, ligne, colonne);
            pieceMove.ifPresent(result::add);
            if (this.isThereObstacle(board, ligne, colonne)) {
                break;
            }
        }

        //on teste la deuxième diagonale qui démarre dans le haut gauche de l'échiquier et finis en bas à droite
        ligne = piece.getLigne();
        colonne = piece.getColonne();
        for (int i = 0; i < 8; i++) {
            ligne = ligne + 1;
            colonne = colonne - 1;
            pieceMove = addPotentialMove(board, piece, ligne, colonne);
            pieceMove.ifPresent(result::add);
            if (this.isThereObstacle(board, ligne, colonne)) {
                break;
            }
        }
        ligne = piece.getLigne();
        colonne = piece.getColonne();
        for (int i = 0; i < 8; i++) {
            ligne = ligne - 1;
            colonne = colonne + 1;
            pieceMove = addPotentialMove(board, piece, ligne, colonne);
            pieceMove.ifPresent(result::add);
            if (this.isThereObstacle(board, ligne, colonne)) {
                break;
            }
        }

        return result;
    }

    private List<PieceMove> ponAvailableMove(Board board, Piece piece) {
        List<PieceMove> result = new ArrayList<>();
        Optional<PieceMove> pieceMove;

        //deplacement sans prise de pièces
        if (piece.getColor().equals(Color.WHITE)) {
            //le pion peut avancer d'une case
            pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne());
            if (pieceMove.isPresent() && pieceMove.get().getPieceCaptured().isEmpty()) {
                result.add(pieceMove.get());
            }

            //le pion peut se déplacer de 2 cases en avant quand il n'a jamais été joué
            if (piece.getLigne() == 1) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() + 2, piece.getColonne());
                if (pieceMove.isPresent() && pieceMove.get().getPieceCaptured().isEmpty()) {
                    result.add(pieceMove.get());
                }
            }

            //le pion peut capturer une pièce sur son côté gauche
            if (getPieceAtThisPosition(board, piece.getLigne() + 1, piece.getColonne() - 1, Color.BLACK).isPresent()) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() - 1);
                pieceMove.ifPresent(result::add);
            }
            //le pion peut capturer une pièce sur son côté droit
            if (getPieceAtThisPosition(board, piece.getLigne() + 1, piece.getColonne() + 1, Color.BLACK).isPresent()) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() + 1);
                pieceMove.ifPresent(result::add);
            }

            //prise en passant
            if (this.isLastMoveATwoCaseForwardForPon(board)) {
                //prise en passant à gauche
                if ((piece.getColonne() - 1) == board.getHistory().get(board.getHistory().size() - 1).getPieceAfterMove().get(0).getColonne()) {
                    pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() - 1);
                    pieceMove.ifPresent(result::add);
                }
                //prise en passant à droite
                if ((piece.getColonne() + 1) == board.getHistory().get(board.getHistory().size() - 1).getPieceAfterMove().get(0).getColonne()) {
                    pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() + 1);
                    pieceMove.ifPresent(result::add);
                }
            }
        } else {
            //le pion peut avancer d'une case
            pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne());
            if (pieceMove.isPresent() && pieceMove.get().getPieceCaptured().isEmpty()) {
                result.add(pieceMove.get());
            }

            //le pion peut se déplacer de 2 cases en avant quand il n'a jamais été joué
            if (piece.getLigne() == 6) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() - 2, piece.getColonne());
                if (pieceMove.isPresent() && pieceMove.get().getPieceCaptured().isEmpty()) {
                    result.add(pieceMove.get());
                }
            }

            //le pion peut capturer une pièce sur son côté gauche
            if (getPieceAtThisPosition(board, piece.getLigne() - 1, piece.getColonne() - 1, Color.WHITE).isPresent()) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() - 1);
                pieceMove.ifPresent(result::add);
            }
            //le pion peut capturer une pièce sur son côté droit
            if (getPieceAtThisPosition(board, piece.getLigne() - 1, piece.getColonne() + 1, Color.WHITE).isPresent()) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() + 1);
                pieceMove.ifPresent(result::add);
            }

            //prise en passant
            if (this.isLastMoveATwoCaseForwardForPon(board)) {
                //prise en passant à gauche
                if ((piece.getColonne() - 1) == board.getHistory().get(board.getHistory().size() - 1).getPieceAfterMove().get(0).getColonne()) {
                    pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() - 1);
                    pieceMove.ifPresent(result::add);
                }
                //prise en passant à droite
                if ((piece.getColonne() + 1) == board.getHistory().get(board.getHistory().size() - 1).getPieceAfterMove().get(0).getColonne()) {
                    pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() + 1);
                    pieceMove.ifPresent(result::add);
                }
            }
        }
        return result;
    }

    private List<PieceMove> ponAvailableTakenMove(Board board, Piece piece) {
        List<PieceMove> result = new ArrayList<>();
        Optional<PieceMove> pieceMove;

        if (piece.getColor().equals(Color.WHITE)) {
            //le pion peut capturer une pièce sur son côté gauche
            if (getPieceAtThisPosition(board, piece.getLigne() + 1, piece.getColonne() - 1, Color.BLACK).isPresent()) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() - 1);
                pieceMove.ifPresent(result::add);
            }
            //le pion peut capturer une pièce sur son côté droit
            if (getPieceAtThisPosition(board, piece.getLigne() + 1, piece.getColonne() + 1, Color.BLACK).isPresent()) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() + 1);
                pieceMove.ifPresent(result::add);
            }

            //prise en passant
            if (this.isLastMoveATwoCaseForwardForPon(board)) {
                //prise en passant à gauche
                if ((piece.getColonne() - 1) == board.getHistory().get(board.getHistory().size() - 1).getPieceAfterMove().get(0).getColonne()) {
                    pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() - 1);
                    pieceMove.ifPresent(result::add);
                }
                //prise en passant à droite
                if ((piece.getColonne() + 1) == board.getHistory().get(board.getHistory().size() - 1).getPieceAfterMove().get(0).getColonne()) {
                    pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() + 1);
                    pieceMove.ifPresent(result::add);
                }
            }
        } else {
            //le pion peut capturer une pièce sur son côté gauche
            if (getPieceAtThisPosition(board, piece.getLigne() - 1, piece.getColonne() - 1, Color.WHITE).isPresent()) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() - 1);
                pieceMove.ifPresent(result::add);
            }
            //le pion peut capturer une pièce sur son côté droit
            if (getPieceAtThisPosition(board, piece.getLigne() - 1, piece.getColonne() + 1, Color.WHITE).isPresent()) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() + 1);
                pieceMove.ifPresent(result::add);
            }

            //prise en passant
            if (this.isLastMoveATwoCaseForwardForPon(board)) {
                //prise en passant à gauche
                if ((piece.getColonne() - 1) == board.getHistory().get(board.getHistory().size() - 1).getPieceAfterMove().get(0).getColonne()) {
                    pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() - 1);
                    pieceMove.ifPresent(result::add);
                }
                //prise en passant à droite
                if ((piece.getColonne() + 1) == board.getHistory().get(board.getHistory().size() - 1).getPieceAfterMove().get(0).getColonne()) {
                    pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() + 1);
                    pieceMove.ifPresent(result::add);
                }
            }
        }

        return result;
    }

    /**
     * Permet de déterminer si, après le mouvement de pièce passé en paramètre, le roi de couleur color est en echec
     *
     * @param board
     * @param pieceMove un mouvement de piece du joueur qui a le tempo
     * @return true si le roi du joueur qui a le tempo est en echec
     */
    private boolean isKingCheck(Board board, PieceMove pieceMove) {
        Board boardAfterMove = move.move(board, pieceMove);
        return isKingCheck(boardAfterMove, board.getTempo());
    }

    /**
     * Permet de déterminer si le roi de couleur color est en echec
     *
     * @param board
     * @param color la couleur du roi
     * @return true si le roi du joueur qui a le tempo est en echec
     */
    private boolean isKingCheck(Board board, Color color) {
        if (Color.WHITE.equals(color)) {
            Piece whiteKing = board.getPiecePlayerWhite().stream()
                    .filter(piece -> piece.getType().equals(Type.KING))
                    .findFirst()
                    .orElseThrow(() -> new cantFindPieceException(Type.KING, Color.WHITE));
            return board.getPiecePlayerBlack().stream()
                    .anyMatch(piece -> this.getAllPossibleTakenMove(board, piece).stream()
                            .flatMap(pm -> pm.getPieceAfterMove().stream())
                            .anyMatch(blackMove -> blackMove.getLigne() == whiteKing.getLigne() &&
                                    blackMove.getColonne() == whiteKing.getColonne())
                    );
        } else {
            Piece blackKing = board.getPiecePlayerBlack().stream()
                    .filter(piece -> piece.getType().equals(Type.KING))
                    .findFirst()
                    .orElseThrow(() -> new cantFindPieceException(Type.KING, Color.BLACK));
            return board.getPiecePlayerWhite().stream()
                    .anyMatch(piece -> this.getAllPossibleTakenMove(board, piece).stream()
                            .flatMap(pm -> pm.getPieceAfterMove().stream())
                            .anyMatch(blackMove -> blackMove.getLigne() == blackKing.getLigne() &&
                                    blackMove.getColonne() == blackKing.getColonne())
                    );
        }
    }

    /**
     * Cette méthode détermine si une pièce a atteins un obstacle (fin de l'échiquier, pièce allié, pièce adverse, etc)
     *
     * @param board
     * @param ligneTeste
     * @param colonneTeste
     * @return true si un obstacle se trouvant à la position ligneTeste, colonneTeste
     */
    private boolean isThereObstacle(Board board, int ligneTeste, int colonneTeste) {
        if (!isOutOfBoard(ligneTeste, colonneTeste)) {
            Optional<Piece> whitePiece = getPieceAtThisPosition(board, ligneTeste, colonneTeste, Color.WHITE);
            Optional<Piece> blackPiece = getPieceAtThisPosition(board, ligneTeste, colonneTeste, Color.BLACK);
            if (whitePiece.isPresent() || blackPiece.isPresent()) {
                return true;
            }
        } else {
            return true;
        }

        return false;
    }

    /**
     * Le rôle de cette méthode est de déterminer si un mouvement est possible et si oui de renvoyer la structure PieceMove créer
     *
     * @param board
     * @param piece
     * @param ligneTeste
     * @param colonneTeste
     * @return Cette méthode renvoie une structure PieceMove si le mouvement est possible
     */
    private Optional<PieceMove> addPotentialMove(Board board, Piece piece, int ligneTeste, int colonneTeste) {
        if (isThisMovePossible(board, ligneTeste, colonneTeste, piece.getColor())) {
            Optional<Piece> enemyPiece;
            if (Color.WHITE.equals(piece.getColor())) {
                enemyPiece = getPieceAtThisPosition(board, ligneTeste, colonneTeste, Color.BLACK);
            } else {
                enemyPiece = getPieceAtThisPosition(board, ligneTeste, colonneTeste, Color.WHITE);
            }

            return enemyPiece.map(value -> new PieceMove(List.of(piece), List.of(new Piece(piece.getColor(), piece.getType(), ligneTeste, colonneTeste)), value))
                    .or(() -> Optional.of(new PieceMove(List.of(piece), List.of(new Piece(piece.getColor(), piece.getType(), ligneTeste, colonneTeste)))));
        }
        return Optional.empty();
    }

    /**
     * Cette méthode gère aussi les cas de figure ou le movement sort du plateau
     *
     * @param line
     * @param colonne
     * @return
     */
    private boolean isThisMovePossible(Board board, int line, int colonne, Color pieceColor) {
        if (isOutOfBoard(line, colonne)) return false;
        return getPieceAtThisPosition(board, line, colonne, pieceColor).isEmpty();
    }

    private boolean isOutOfBoard(int line, int colonne) {
        if ((line < 0 || line > 7) || (colonne < 0 || colonne > 7)) {
            return true;
        }
        return false;
    }

    private Optional<Piece> getPieceAtThisPosition(Board board, int line, int colonne, Color pieceColor) {
        if (pieceColor.equals(Color.WHITE)) {
            return board.getPiecePlayerWhite().stream()
                    .filter(piece -> piece.getColonne() == colonne && piece.getLigne() == line)
                    .findFirst();
        } else {
            return board.getPiecePlayerBlack().stream()
                    .filter(piece -> piece.getColonne() == colonne && piece.getLigne() == line)
                    .findFirst();
        }
    }

    private boolean isLastMoveATwoCaseForwardForPon(Board board) {
        if (!board.getHistory().isEmpty()) {
            PieceMove pieceMove = board.getHistory().get(board.getHistory().size() - 1);
            if (Color.WHITE.equals(pieceMove.getPieceAfterMove().get(0).getColor())) {
                if (Type.PON.equals(pieceMove.getPieceAfterMove().get(0).getType()) &&
                        pieceMove.getPieceAfterMove().get(0).getLigne() == pieceMove.getPieceBeforeMove().get(0).getLigne() + 2) {
                    return true;
                }
            } else {
                if (Type.PON.equals(pieceMove.getPieceAfterMove().get(0).getType()) &&
                        pieceMove.getPieceAfterMove().get(0).getLigne() == pieceMove.getPieceBeforeMove().get(0).getLigne() - 2) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canKingCastleRight(Board board, Piece king) {
        boolean kingHasNeverBeenPlayed = board.getHistory().stream()
                .flatMap(pieceMove -> pieceMove.getPieceBeforeMove().stream())
                .noneMatch(pieceMove -> pieceMove.getColor().equals(king.getColor()) &&
                        Type.KING.equals(pieceMove.getType()));
        if (Color.WHITE.equals(king.getColor())) {
            boolean rightRockHasNeverBeenPlayed = board.getHistory().stream()
                    .flatMap(pieceMove -> pieceMove.getPieceBeforeMove().stream())
                    .noneMatch(pieceMove -> pieceMove.getColonne() == 7 &&
                            pieceMove.getLigne() == 0);
            return kingHasNeverBeenPlayed &&
                    rightRockHasNeverBeenPlayed &&
                    !isThereAPieceAtThisPosition(board, 0, 6) &&
                    !isThereAPieceAtThisPosition(board, 0, 5);
        } else {
            boolean rightRockHasNeverBeenPlayed = board.getHistory().stream()
                    .flatMap(pieceMove -> pieceMove.getPieceBeforeMove().stream())
                    .noneMatch(pieceMove -> pieceMove.getColonne() == 7 &&
                            pieceMove.getLigne() == 7);
            return kingHasNeverBeenPlayed &&
                    rightRockHasNeverBeenPlayed &&
                    !isThereAPieceAtThisPosition(board, 7, 6) &&
                    !isThereAPieceAtThisPosition(board, 7, 5);
        }
    }

    private boolean canKingCastleLeft(Board board, Piece king) {
        boolean kingHasNeverBeenPlayed = board.getHistory().stream()
                .flatMap(pieceMove -> pieceMove.getPieceBeforeMove().stream())
                .noneMatch(pieceMove -> pieceMove.getColor().equals(king.getColor()) &&
                        Type.KING.equals(pieceMove.getType()));
        if (Color.WHITE.equals(king.getColor())) {
            boolean leftRockHasNeverBeenPlayed = board.getHistory().stream()
                    .flatMap(pieceMove -> pieceMove.getPieceBeforeMove().stream())
                    .noneMatch(pieceMove -> pieceMove.getColonne() == 0 &&
                            pieceMove.getLigne() == 0);
            return kingHasNeverBeenPlayed &&
                    leftRockHasNeverBeenPlayed &&
                    !isThereAPieceAtThisPosition(board, 0, 1) &&
                    !isThereAPieceAtThisPosition(board, 0, 2);
        } else {
            boolean leftRockHasNeverBeenPlayed = board.getHistory().stream()
                    .flatMap(pieceMove -> pieceMove.getPieceBeforeMove().stream())
                    .noneMatch(pieceMove -> pieceMove.getColonne() == 0 &&
                            pieceMove.getLigne() == 7);
            return kingHasNeverBeenPlayed &&
                    leftRockHasNeverBeenPlayed &&
                    !isThereAPieceAtThisPosition(board, 7, 1) &&
                    !isThereAPieceAtThisPosition(board, 7, 2);
        }
    }

    private boolean isThereAPieceAtThisPosition(Board board, int line, int colonne) {
        boolean isThereAWhitePiece = board.getPiecePlayerWhite().stream()
                .anyMatch(piece -> piece.getColonne() == colonne && piece.getLigne() == line);
        boolean isThereABlackPiece = board.getPiecePlayerBlack().stream()
                .anyMatch(piece -> piece.getColonne() == colonne && piece.getLigne() == line);

        return isThereAWhitePiece || isThereABlackPiece;
    }
}
