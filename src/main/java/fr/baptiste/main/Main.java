package fr.baptiste.main;

import fr.baptiste.business.Partie;
import fr.baptiste.business.strategy.PlayerStrategy;
import fr.baptiste.business.utilities.AvailableMovement;
import fr.baptiste.business.utilities.Move;
import fr.baptiste.domain.builder.BoardBuilder;
import fr.baptiste.presentation.App;

public class Main {
    public static void main(String[] args) {
        Move move = new Move();
        BoardBuilder boardBuilder = new BoardBuilder(move);
        AvailableMovement availableMovement = new AvailableMovement(move, boardBuilder);
        PlayerStrategy strategyPlayer1 = new PlayerStrategy(availableMovement, move);
        PlayerStrategy strategyPlayer2 = new PlayerStrategy(availableMovement, move);
        Partie partie = new Partie(boardBuilder, availableMovement, strategyPlayer1, strategyPlayer2);

        partie.play();
        //App.main(args);
    }
}
