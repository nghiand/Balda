package balda.model;

/**
 * Player
 */
public class Player extends AbstractPlayer {
    /**
     * Constructor
     * @param name name of player
     * @param field game field
     * @param database database dictionary
     * @param used common dictionary, in which used words
     */
    public Player(String name, GameField field, 
            Database database, UsedDictionary used){
        super(name, field, database, used);
    }
}
