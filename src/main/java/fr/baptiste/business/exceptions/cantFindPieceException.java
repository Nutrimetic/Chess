package fr.baptiste.business.exceptions;

import fr.baptiste.domain.Color;
import fr.baptiste.domain.Type;

public class cantFindPieceException extends RuntimeException {
    public cantFindPieceException(Type type, Color color) {
        super(String.format("Impossible de trouver la pièce : %s de couleur %s", type.name(), color.name()));
    }

    public cantFindPieceException(Type type, Color color, int ligne, int colonne) {
        super(String.format("Impossible de trouver la pièce : %s de couleur %s à la ligne %d, colonne %d", type.name(), color.name(), ligne, colonne));
    }
}
