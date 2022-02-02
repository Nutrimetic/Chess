package fr.baptiste.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Board implements Cloneable {
    private Player playerWhite;
    private List<Piece> piecePlayerWhite;
    private Player playerBlack;
    private List<Piece> piecePlayerBlack;
    private Color tempo;
    private List<PieceMove> history;

    public Board(Player playerWhite, List<Piece> piecePlayerWhite, Player playerBlack, List<Piece> piecePlayerBlack, Color tempo) {
        this.playerWhite = playerWhite;
        this.piecePlayerWhite = piecePlayerWhite;
        this.playerBlack = playerBlack;
        this.piecePlayerBlack = piecePlayerBlack;
        this.tempo = tempo;
        history = new ArrayList<>();
    }

    public Player getPlayerWhite() {
        return playerWhite;
    }

    public void setPlayerWhite(Player playerWhite) {
        this.playerWhite = playerWhite;
    }

    public List<Piece> getPiecePlayerWhite() {
        return piecePlayerWhite;
    }

    public void setPiecePlayerWhite(List<Piece> piecePlayerWhite) {
        this.piecePlayerWhite = piecePlayerWhite;
    }

    public Player getPlayerBlack() {
        return playerBlack;
    }

    public void setPlayerBlack(Player playerBlack) {
        this.playerBlack = playerBlack;
    }

    public List<Piece> getPiecePlayerBlack() {
        return piecePlayerBlack;
    }

    public void setPiecePlayerBlack(List<Piece> piecePlayerBlack) {
        this.piecePlayerBlack = piecePlayerBlack;
    }

    public Color getTempo() {
        return tempo;
    }

    public void setTempo(Color tempo) {
        this.tempo = tempo;
    }

    public List<PieceMove> getHistory() {
        return history;
    }

    public void addHistoryMove(List<PieceMove> pieceMove) {
        this.history.addAll(pieceMove);
    }

    @Override
    public Board clone() throws CloneNotSupportedException {
        Board result = new Board(
                playerWhite.clone(),
                piecePlayerWhite.stream().map(Piece::clone).collect(Collectors.toList()),
                playerBlack.clone(),
                piecePlayerBlack.stream().map(Piece::clone).collect(Collectors.toList()),
                this.tempo);
        result.addHistoryMove(this.history);
        return result;
    }
}
