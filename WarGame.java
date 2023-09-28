import java.util.*;

public class WarGame {
    private Deck deck;
    private Player player1;
    private Player player2;
    private List<Card> secondDeck1 = new ArrayList<Card>();
    private List<Card> secondDeck2 = new ArrayList<Card>();
    private boolean shouldContinue = true;

    public WarGame() {
        this.deck = new Deck();
        deck.shuffle();
        List<Card> player1Cards = new ArrayList<Card>();
        List<Card> player2Cards = new ArrayList<Card>();
        int i = 0;
        int l = deck.length();
        while (i < (int) (l / 2)) {
            player1Cards.add(deck.deal());
            i++;
        }
        while (i < l) {
            player2Cards.add(deck.deal());
            i++;
        }
        player1 = new Player(player1Cards);
        player2 = new Player(player2Cards);
        
    }

    public static void main(String[] args) {
        WarGame game = new WarGame();
        game.game();
    }

    public void game() {
        while (shouldContinue) {
            if (playerHasCards(player1) && playerHasCards(player2)) {
                videoGames();
            } else {
                shouldContinue = false;
            }
        }
        System.out.println("Win..?");
    }

    public void videoGames() {
        Card p1Deal = player1.deck.deal();
        Card p2Deal = player2.deck.deal();
        if (p1Deal.getRank() > p2Deal.getRank()) {
            secondDeck1.add(p1Deal);
            secondDeck1.add(p2Deal);
        } else if (p1Deal.getRank() < p2Deal.getRank()) {
            secondDeck2.add(p1Deal);
            secondDeck2.add(p2Deal);
        } else {
            war();
        }
    }

    public void war() {
        List<Card> warPile1 = new ArrayList<Card>();
        List<Card> warPile2 = new ArrayList<Card>();

        for (int i = 0; i < 4; i++) {
            warPile1.add(player1.deck.deal());
        }
    }

    public int deckLength(Player player) {
        return player.deck.length();
    }

    public boolean playerHasCards(Player player) {
        return deckLength(player1) > 0 || secondDeck1.size() > 0;
    }
}