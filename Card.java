public class Card {
    private int rank;
    private String name;
    private String suit;

    public Card(String name, int rank, String suit) {
        this.rank = rank;
        this.name = name;
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public String getSuit() {
        return suit;
    }

}
