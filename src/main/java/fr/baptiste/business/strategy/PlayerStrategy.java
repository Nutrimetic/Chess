package fr.baptiste.business.strategy;

import fr.baptiste.business.port.GameStrategy;
import fr.baptiste.business.utilities.AvailableMovement;
import fr.baptiste.business.utilities.Move;
import fr.baptiste.domain.Board;
import fr.baptiste.domain.Color;
import fr.baptiste.domain.Piece;
import fr.baptiste.domain.PieceMove;

import java.util.Optional;
import java.util.Scanner;

public class PlayerStrategy implements GameStrategy {
    private final AvailableMovement availableMovement;
    private final Move move;

    public PlayerStrategy(AvailableMovement availableMovement, Move move) {
        this.availableMovement = availableMovement;
        this.move = move;
    }

    @Override
    public Board playAMove(Board board) {
        Optional<Piece> pieceBeforeMove = Optional.empty();
        Optional<Piece> pieceAfterMove = Optional.empty();
        PieceMove result = null;

        System.out.println("Vous jouez en tant que : " + board.getTempo());
        while (pieceBeforeMove.isEmpty()) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Veuillez saisir la ligne de la pièce que vous souhaitez jouer : ");
            int pieceLigneBeforeMovement = sc.nextInt();
            System.out.println("Veuillez saisir la colonne de la pièce que vous souhaitez jouer : ");
            int pieceColonneBeforeMovement = sc.nextInt();
            if((pieceLigneBeforeMovement < 0 || pieceLigneBeforeMovement > 8) || (pieceColonneBeforeMovement < 0 || pieceColonneBeforeMovement > 8)){
                System.out.println("Vous avez joué en dehors du plateau");
            } else {
                pieceBeforeMove = this.getPiece(board, pieceLigneBeforeMovement, pieceColonneBeforeMovement);
                if (pieceBeforeMove.isEmpty()) {
                    System.out.println("Il n'y a aucune pièce à cette position");
                }
            }
        }

        while (pieceAfterMove.isEmpty()) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Veuillez saisir la ligne d'arrivé de votre pièce : ");
            int pieceLigneAfterMovement = sc.nextInt();
            System.out.println("Veuillez saisir la colonne d'arrivé de votre pièce : ");
            int pieceColonneAfterMovement = sc.nextInt();
            if((pieceLigneAfterMovement < 0 || pieceLigneAfterMovement > 8) || (pieceColonneAfterMovement < 0 || pieceColonneAfterMovement > 8)){
                System.out.println("Vous avez joué en dehors du plateau");
            } else if(this.getPiece(board, pieceLigneAfterMovement, pieceColonneAfterMovement).isPresent()) {
                System.out.println("Il y a déja une pièce à cette position");
            } else {
                pieceAfterMove = Optional.of(new Piece(pieceBeforeMove.get().getColor(),
                        pieceBeforeMove.get().getType(),
                        pieceLigneAfterMovement,
                        pieceColonneAfterMovement));
                result = new PieceMove(pieceBeforeMove.get(), pieceAfterMove.get());
                if(!this.availableMovement.availableMove(board, pieceBeforeMove.get()).contains(result)) {
                    System.out.println("Ce coup n'est pas possible");
                    pieceAfterMove = Optional.empty();
                }
            }
        }

        return move.move(board, result);
    }

    private Optional<Piece> getPiece(Board board, int lignePieceAvantMouvement, int colonnePieceAvantMouvement) {
        if(Color.WHITE.equals(board.getTempo())) {
            return board.getPiecePlayerWhite().stream()
                    .filter(piece -> piece.getLigne() == lignePieceAvantMouvement && piece.getColonne() == colonnePieceAvantMouvement)
                    .findFirst();
        } else {
            return board.getPiecePlayerBlack().stream()
                    .filter(piece -> piece.getLigne() == lignePieceAvantMouvement && piece.getColonne() == colonnePieceAvantMouvement)
                    .findFirst();
        }
    }
}
