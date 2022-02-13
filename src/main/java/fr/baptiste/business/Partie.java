package fr.baptiste.business;

import fr.baptiste.business.port.PlayerStrategy;
import fr.baptiste.domain.*;
import fr.baptiste.business.utilities.AvailableMovement;
import fr.baptiste.domain.builder.BoardBuilder;

import java.util.ArrayList;
import java.util.Collections;

public class Partie {
    private final AvailableMovement availableMovement;
    private final PlayerStrategy strategyPlayer1;
    private final PlayerStrategy strategyPlayer2;
    private Board board;

    public Partie(BoardBuilder boardBuilder, AvailableMovement availableMovement, PlayerStrategy strategyPlayer1, PlayerStrategy strategyPlayer2) {
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
    }
}
