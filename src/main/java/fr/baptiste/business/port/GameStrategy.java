package fr.baptiste.business.port;

import fr.baptiste.domain.Board;

public interface GameStrategy {
    public Board playAMove(Board board);
}
