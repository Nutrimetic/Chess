package fr.baptiste.domain;

import java.util.Objects;

public class Piece {
    private Color color;
    private Type type;
    private int ligne;
    private int colonne;

    public Piece(Color color, Type type, int ligne, int colonne) {
        this.color = color;
        this.type = type;
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece)) return false;
        Piece piece = (Piece) o;
        return ligne == piece.ligne &&
                colonne == piece.colonne &&
                color == piece.color &&
                type == piece.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type, ligne, colonne);
    }

    @Override
    public String toString() {
        return "Piece{" +
                "color=" + color +
                ", type=" + type +
                ", ligne=" + ligne +
                ", colonne=" + colonne +
                '}';
    }
}
