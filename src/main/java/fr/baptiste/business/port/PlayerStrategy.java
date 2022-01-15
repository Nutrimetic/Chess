package fr.baptiste.business.port;

import fr.baptiste.domain.Board;

public interface PlayerStrategy {
    public Board playAMove(Board board);
}
