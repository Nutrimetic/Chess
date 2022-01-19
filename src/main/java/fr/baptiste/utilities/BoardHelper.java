package fr.baptiste.utilities;

import fr.baptiste.domain.*;
import fr.baptiste.domain.builder.BoardBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BoardHelper {

    protected final BoardBuilder boardBuilder;

    protected BoardHelper(BoardBuilder boardBuilder) {
        this.boardBuilder = boardBuilder;
    }

    /**
     * TODO
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
     * Permet de déterminer si le roi du joueur ayant le tempo se trouve en echec
     *
     * @param board
     * @param pieceMove un mouvement de piece du joueur qui a le tempo
     * @return true si le roi du joueur qui a le tempo est en echec
     */
    protected boolean isKingCheck(Board board, PieceMove pieceMove) {
        Board boardAfterMove = boardBuilder.build(board, pieceMove);
        if (Color.WHITE.equals(boardAfterMove.getTempo())) {
            Piece whiteKing = boardAfterMove.getPiecePlayerWhite().stream().filter(piece -> piece.getType().equals(Type.KING)).findFirst().get();
            return boardAfterMove.getPiecePlayerBlack().stream()
                    .anyMatch(piece -> this.getAllPossibleTakenMove(boardAfterMove, piece).stream()
                            .anyMatch(blackMove -> blackMove.getPieceAfterMove().getLigne() == whiteKing.getLigne() &&
                                    blackMove.getPieceAfterMove().getColonne() == whiteKing.getColonne())
                    );
        } else {
            Piece blackKing = boardAfterMove.getPiecePlayerBlack().stream().filter(piece -> piece.getType().equals(Type.KING)).findFirst().get();
            return boardAfterMove.getPiecePlayerWhite().stream()
                    .anyMatch(piece -> this.getAllPossibleTakenMove(boardAfterMove, piece).stream()
                            .anyMatch(blackMove -> blackMove.getPieceAfterMove().getLigne() == blackKing.getLigne() &&
                                    blackMove.getPieceAfterMove().getColonne() == blackKing.getColonne())
                    );
        }
    }

    /**
     * todo différencier les méthodes de déplacement des méthodes de prise de pièce. Les méthodes de mouvement "classique" ne permettent pas de jouer sur une pièce de même couleur.
     * Une méthode de prise de pièce doit prendre en compte des mouvements sur des pièces alliés?
     * Détermine tous les coups de prise potentiel. Les mouvements de prises renvoyés ne prennent pas en compte une potentielle mise en echec (voir méthode isKingCheck)
     *
     * @param board
     * @param piece
     * @return TODO Tous les coups renvoyés correspondent à une prise de pièces? ou on renvoie tous les coups possible pouvant permettre une prise de pièce?
     * todo dans le cas 1, il faut changer le anyMatch du isKingCheck
     */
    private List<PieceMove> getAllPossibleTakenMove(Board board, Piece piece) {
        List<PieceMove> pieceMoves;
        if (piece.getType().equals(Type.KING)) {
            pieceMoves = this.kingAvailableMove(board, piece);
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

    protected List<PieceMove> getAllPossibleMove(Board board, Piece piece) {
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

    //todo coder l'action "castle"
    protected List<PieceMove> kingAvailableMove(Board board, Piece piece) {
        List<PieceMove> result = new ArrayList<>();
        Optional<PieceMove> pieceMove;

        pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() - 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne());
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() + 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece, piece.getLigne(), piece.getColonne() - 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece, piece.getLigne(), piece.getColonne());
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece, piece.getLigne(), piece.getColonne() + 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() - 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne());
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() + 1);
        pieceMove.ifPresent(result::add);

        return result;
    }

    protected List<PieceMove> queenAvailableMove(Board board, Piece piece) {
        List<PieceMove> result = new ArrayList<>();

        //on teste horizontalement et verticalement
        result.addAll(rockAvailableMove(board, piece));

        //on teste les diagonales
        result.addAll(bishopAvailableMove(board, piece));

        return result;
    }

    protected List<PieceMove> rockAvailableMove(Board board, Piece piece) {
        List<PieceMove> result = new ArrayList<>();
        Optional<PieceMove> pieceMove;

        //on teste la colonne entière
        for (int ligne = piece.getLigne() + 1; ligne < 8; ligne++) {
            pieceMove = addPotentialMove(board, piece, ligne, piece.getColonne());
            pieceMove.ifPresent(result::add);
            if(this.isThereObstacle(board, ligne, piece.getColonne())) {
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

    protected List<PieceMove> knightAvailableMove(Board board, Piece piece) {
        List<PieceMove> result = new ArrayList<>();
        Optional<PieceMove> pieceMove;

        pieceMove = addPotentialMove(board, piece,piece.getLigne() - 2, piece.getColonne() + 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece,piece.getLigne() - 2, piece.getColonne() - 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece,piece.getLigne() + 2, piece.getColonne() + 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece,piece.getLigne() + 2, piece.getColonne() - 1);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece,piece.getLigne() + 1, piece.getColonne() - 2);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece,piece.getLigne() - 1, piece.getColonne() - 2);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece,piece.getLigne() + 1, piece.getColonne() + 2);
        pieceMove.ifPresent(result::add);
        pieceMove = addPotentialMove(board, piece,piece.getLigne() - 1, piece.getColonne() + 2);
        pieceMove.ifPresent(result::add);

        return result;
    }

    protected List<PieceMove> bishopAvailableMove(Board board, Piece piece) {
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

    protected List<PieceMove> ponAvailableMove(Board board, Piece piece) {
        List<PieceMove> result = new ArrayList<>();
        Optional<PieceMove> pieceMove;

        //deplacement sans prise de pièces
        if (piece.getColor().equals(Color.WHITE)) {
            //le pion peut avancer d'une case
            pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne());
            pieceMove.ifPresent(result::add);

            //le pion peut se déplacer de 2 cases en avant quand il n'a jamais été joué
            if (piece.getLigne() == 6) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() - 2, piece.getColonne());
                pieceMove.ifPresent(result::add);
            }

            //le pion peut capturer une pièce sur son côté gauche
            if (isThereAlreadyAPieceAtThisPosition(board, piece.getLigne() - 1, piece.getColonne() - 1, Color.BLACK).isPresent()) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() - 1);
                pieceMove.ifPresent(result::add);
            }
            //le pion peut capturer une pièce sur son côté droit
            if (isThereAlreadyAPieceAtThisPosition(board, piece.getLigne() - 1, piece.getColonne() + 1, Color.BLACK).isPresent()) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() + 1);
                pieceMove.ifPresent(result::add);
            }

            //prise en passant
            if (board.getEnPassant().isPresent() &&
                    board.getEnPassant().get().getColor().equals(Color.BLACK) &&
                    board.getEnPassant().get().getLigne() == piece.getLigne()
            ) {
                //prise en passant à gauche
                if ((piece.getColonne() - 1) == board.getEnPassant().get().getColonne()) {
                    pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() - 1);
                    pieceMove.ifPresent(result::add);
                }
                //prise en passant à droite
                if ((piece.getColonne() + 1) == board.getEnPassant().get().getColonne()) {
                    pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() + 1);
                    pieceMove.ifPresent(result::add);
                }
            }
        } else {
            //le pion peut avancer d'une case
            pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne());
            pieceMove.ifPresent(result::add);

            //le pion peut se déplacer de 2 cases en avant quand il n'a jamais été joué
            if (piece.getLigne() == 1) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() + 2, piece.getColonne());
                pieceMove.ifPresent(result::add);
            }

            //le pion peut capturer une pièce sur son côté gauche
            if (isThereAlreadyAPieceAtThisPosition(board, piece.getLigne() + 1, piece.getColonne() - 1, Color.WHITE).isPresent()) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() - 1);
                pieceMove.ifPresent(result::add);
            }
            //le pion peut capturer une pièce sur son côté droit
            if (isThereAlreadyAPieceAtThisPosition(board, piece.getLigne() + 1, piece.getColonne() + 1, Color.WHITE).isPresent()) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() + 1);
                pieceMove.ifPresent(result::add);
            }

            //prise en passant
            if (board.getEnPassant().isPresent() &&
                    board.getEnPassant().get().getColor().equals(Color.WHITE) &&
                    board.getEnPassant().get().getLigne() == piece.getLigne()
            ) {
                //prise en passant à gauche
                if ((piece.getColonne() - 1) == board.getEnPassant().get().getColonne()) {
                    pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() - 1);
                    pieceMove.ifPresent(result::add);
                }
                //prise en passant à droite
                if ((piece.getColonne() + 1) == board.getEnPassant().get().getColonne()) {
                    pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() + 1);
                    pieceMove.ifPresent(result::add);
                }
            }
        }
        return result;
    }

    protected List<PieceMove> ponAvailableTakenMove(Board board, Piece piece) {
        List<PieceMove> result = new ArrayList<>();
        Optional<PieceMove> pieceMove;

        if (piece.getColor().equals(Color.WHITE)) {
            //le pion peut capturer une pièce sur son côté gauche
            if (isThereAlreadyAPieceAtThisPosition(board, piece.getLigne() - 1, piece.getColonne() - 1, Color.BLACK).isPresent()) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() - 1);
                pieceMove.ifPresent(result::add);
            }
            //le pion peut capturer une pièce sur son côté droit
            if (isThereAlreadyAPieceAtThisPosition(board, piece.getLigne() - 1, piece.getColonne() + 1, Color.BLACK).isPresent()) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() + 1);
                pieceMove.ifPresent(result::add);
            }

            //prise en passant
            if (board.getEnPassant().isPresent() &&
                    board.getEnPassant().get().getColor().equals(Color.BLACK) &&
                    board.getEnPassant().get().getLigne() == piece.getLigne()
            ) {
                //prise en passant à gauche
                if ((piece.getColonne() - 1) == board.getEnPassant().get().getColonne()) {
                    pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() - 1);
                    pieceMove.ifPresent(result::add);
                }
                //prise en passant à droite
                if ((piece.getColonne() + 1) == board.getEnPassant().get().getColonne()) {
                    pieceMove = addPotentialMove(board, piece, piece.getLigne() - 1, piece.getColonne() + 1);
                    pieceMove.ifPresent(result::add);
                }
            }
        } else {
            //le pion peut capturer une pièce sur son côté gauche
            if (isThereAlreadyAPieceAtThisPosition(board, piece.getLigne() + 1, piece.getColonne() - 1, Color.WHITE).isPresent()) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() - 1);
                pieceMove.ifPresent(result::add);
            }
            //le pion peut capturer une pièce sur son côté droit
            if (isThereAlreadyAPieceAtThisPosition(board, piece.getLigne() + 1, piece.getColonne() + 1, Color.WHITE).isPresent()) {
                pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() + 1);
                pieceMove.ifPresent(result::add);
            }

            //prise en passant
            if (board.getEnPassant().isPresent() &&
                    board.getEnPassant().get().getColor().equals(Color.WHITE) &&
                    board.getEnPassant().get().getLigne() == piece.getLigne()
            ) {
                //prise en passant à gauche
                if ((piece.getColonne() - 1) == board.getEnPassant().get().getColonne()) {
                    pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() - 1);
                    pieceMove.ifPresent(result::add);
                }
                //prise en passant à droite
                if ((piece.getColonne() + 1) == board.getEnPassant().get().getColonne()) {
                    pieceMove = addPotentialMove(board, piece, piece.getLigne() + 1, piece.getColonne() + 1);
                    pieceMove.ifPresent(result::add);
                }
            }
        }

        return result;
    }

    /**
     * todo créer une structure position (ligne, colonne)
     * Cette méthode détermine si une pièce a atteins un obstacle (fin de l'échiquier, pièce allié, pièce adverse, etc)
     *
     * @param board
     * @param ligneTeste
     * @param colonneTeste
     * @return true si un obstacle se trouvant à la position ligneTeste, colonneTeste
     */
    private boolean isThereObstacle(Board board, int ligneTeste, int colonneTeste) {
        if (!isOutOfBoard(ligneTeste, colonneTeste)) {
            Optional<Piece> whitePiece = isThereAlreadyAPieceAtThisPosition(board, ligneTeste, colonneTeste, Color.WHITE);
            Optional<Piece> blackPiece = isThereAlreadyAPieceAtThisPosition(board, ligneTeste, colonneTeste, Color.BLACK);
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
    //todo mettre ca dans un builder ?
    protected Optional<PieceMove> addPotentialMove(Board board, Piece piece, int ligneTeste, int colonneTeste) {
        if (isThisMovePossible(board, ligneTeste, colonneTeste, piece.getColor())) {
            Optional<Piece> enemyPiece;
            if (Color.WHITE.equals(piece.getColor())) {
                enemyPiece = isThereAlreadyAPieceAtThisPosition(board, ligneTeste, colonneTeste, Color.BLACK);
            } else {
                enemyPiece = isThereAlreadyAPieceAtThisPosition(board, ligneTeste, colonneTeste, Color.WHITE);
            }

            if (enemyPiece.isPresent()) {
                return Optional.of(new PieceMove(piece, new Piece(piece.getColor(), piece.getType(), ligneTeste, colonneTeste), enemyPiece.get()));
            } else {
                return Optional.of(new PieceMove(piece, new Piece(piece.getColor(), piece.getType(), ligneTeste, colonneTeste)));
            }
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
    protected boolean isThisMovePossible(Board board, int line, int colonne, Color pieceColor) {
        if (isOutOfBoard(line, colonne)) return false;
        return isThereAlreadyAPieceAtThisPosition(board, line, colonne, pieceColor).isEmpty();
    }

    private boolean isOutOfBoard(int line, int colonne) {
        if ((line < 0 || line > 7) || (colonne < 0 || colonne > 7)) {
            return true;
        }
        return false;
    }

    protected Optional<Piece> isThereAlreadyAPieceAtThisPosition(Board board, int line, int colonne, Color pieceColor) {
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
}
