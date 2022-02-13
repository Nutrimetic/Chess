package fr.baptiste.domain;

import java.util.*;
import java.util.stream.Collectors;

public class Board implements Cloneable {
    private Player playerWhite;
    private Set<Piece> piecePlayerWhite;
    private Player playerBlack;
    private Set<Piece> piecePlayerBlack;
    private Color tempo;
    private List<PieceMove> history;

    public Board(Player playerWhite, Set<Piece> piecePlayerWhite, Player playerBlack, Set<Piece> piecePlayerBlack, Color tempo) {
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

    public Set<Piece> getPiecePlayerWhite() {
        return piecePlayerWhite;
    }

    public void setPiecePlayerWhite(Set<Piece> piecePlayerWhite) {
        this.piecePlayerWhite = piecePlayerWhite;
    }

    public Player getPlayerBlack() {
        return playerBlack;
    }

    public void setPlayerBlack(Player playerBlack) {
        this.playerBlack = playerBlack;
    }

    public Set<Piece> getPiecePlayerBlack() {
        return piecePlayerBlack;
    }

    public void setPiecePlayerBlack(Set<Piece> piecePlayerBlack) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return playerWhite.equals(board.playerWhite) && piecePlayerWhite.equals(board.piecePlayerWhite) && playerBlack.equals(board.playerBlack) && piecePlayerBlack.equals(board.piecePlayerBlack) && tempo == board.tempo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerWhite, piecePlayerWhite, playerBlack, piecePlayerBlack, tempo);
    }

    @Override
    public Board clone() throws CloneNotSupportedException {
        Board result = new Board(
                playerWhite.clone(),
                piecePlayerWhite.stream().map(Piece::clone).collect(Collectors.toSet()),
                playerBlack.clone(),
                piecePlayerBlack.stream().map(Piece::clone).collect(Collectors.toSet()),
                this.tempo);
        result.addHistoryMove(this.history);
        return result;
    }
}
