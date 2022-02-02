package fr.baptiste.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Player implements Cloneable {
    private Color color;
    private List<Piece> piecesCapture;

    public Player(Color color, List<Piece> piecesCapture) {
        this.color = color;
        this.piecesCapture = new ArrayList<>();
        this.piecesCapture.addAll(piecesCapture);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Piece> getPiecesCapture() {
        return piecesCapture;
    }

    public void addPiecesCapture(Piece piece) {
        this.piecesCapture.add(piece);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return color == player.color &&
                piecesCapture.equals(player.piecesCapture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, piecesCapture);
    }

    @Override
    public String toString() {
        return "Player{" +
                "color=" + color +
                ", piecesCapture=" + piecesCapture +
                '}';
    }

    @Override
    protected Player clone() throws CloneNotSupportedException {
        return new Player(this.color, this.getPiecesCapture().stream().map(Piece::clone).collect(Collectors.toList()));
    }
}
