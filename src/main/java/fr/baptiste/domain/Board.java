package fr.baptiste.domain;

import java.util.List;
import java.util.Optional;

public class Board {
    private Player playerWhite;
    private List<Piece> piecePlayerWhite;
    private Player playerBlack;
    private List<Piece> piecePlayerBlack;
    private Color tempo;
    private Piece enPassant;

    public Board(Player playerWhite, List<Piece> piecePlayerWhite, Player playerBlack, List<Piece> piecePlayerBlack, Color tempo) {
        this.playerWhite = playerWhite;
        this.piecePlayerWhite = piecePlayerWhite;
        this.playerBlack = playerBlack;
        this.piecePlayerBlack = piecePlayerBlack;
        this.tempo = tempo;
        enPassant = null;
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

    public Optional<Piece> getEnPassant() {
        return Optional.ofNullable(enPassant);
    }

    public void setEnPassant(Piece enPassant) {
        this.enPassant = enPassant;
    }
}
