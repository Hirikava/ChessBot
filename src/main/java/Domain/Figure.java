package Domain;

public class Figure {
    private final Pieces figuresName;
    private final PlayerColour colour;

    public Figure(Pieces FiguresName, PlayerColour colour){
        this.figuresName = FiguresName;
        this.colour = colour;
    }

    public Figure(Figure other)
    {
        this.figuresName = other.getFiguresName();
        this.colour = other.getColour();
    }


    public Pieces getFiguresName(){
        return figuresName;
    }

    public PlayerColour getColour(){
        return colour;
    }
}
