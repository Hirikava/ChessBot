package Domain;

public class Figure {
    private Pieces FiguresName;
    private PlayerColour colour;

    public Figure(Pieces FiguresName, PlayerColour colour){
        this.FiguresName = FiguresName;
        this.colour = colour;
    }

    public Figure(Figure other)
    {
        this.FiguresName = other.getFiguresName();
        this.colour = other.getColour();
    }


    public Pieces getFiguresName(){
        return FiguresName;
    }

    public PlayerColour getColour(){
        return colour;
    }
}
