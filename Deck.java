import java.util.*;

public class Deck {
    private List<Card> cards = new ArrayList<Card>();
    private String[] suits = {"spades", "hearts", "diamonds", "spades"};
    private String[] names = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};

    public Deck() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 4; j++) {
                cards.add(new Card(names[i], i, suits[j]));
            }
        }
    }

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card deal() {
        Card temp = cards.get(0);
        cards.remove(0);
        return temp;
    }

    public int length() {
        return cards.size();
    }

    public List<Card> getCards() {
        return cards;
    }
}