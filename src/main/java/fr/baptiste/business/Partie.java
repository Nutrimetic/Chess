package fr.baptiste.business;

import fr.baptiste.business.port.GameStrategy;
import fr.baptiste.domain.*;
import fr.baptiste.business.utilities.AvailableMovement;
import fr.baptiste.domain.builder.BoardBuilder;

public class Partie {
    private final AvailableMovement availableMovement;
    private final GameStrategy strategyPlayer1;
    private final GameStrategy strategyPlayer2;
    private Board board;

    public Partie(BoardBuilder boardBuilder, AvailableMovement availableMovement, GameStrategy strategyPlayer1, GameStrategy strategyPlayer2) {
        this.availableMovement = availableMovement;
        this.strategyPlayer1 = strategyPlayer1;
        this.strategyPlayer2 = strategyPlayer2;
        board = boardBuilder.reset().build();
    }

    public void play() {
        while (!availableMovement.isThereAWinner(board)) {
            if (board.getTempo().equals(Color.WHITE)) {
                board = strategyPlayer1.playAMove(board);
            } else {
                board = strategyPlayer2.playAMove(board);
            }
        }
        if(Color.WHITE.equals(board.getTempo())) {
            System.out.println("Victoire du joueur blanc");
        } else {
            System.out.println("Victoire du joueur noir");
        }
    }
}
