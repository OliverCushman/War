import java.util.*;

public class WarGame {
    private Deck deck;
    private Player player1;
    private Player player2;
    private List<Card> secondDeck1 = new ArrayList<Card>();
    private List<Card> secondDeck2 = new ArrayList<Card>();
    private boolean shouldContinue = true;
    private boolean player1Win = false;
    private boolean player2Win = false;

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
        if (!playerHasCards(player1)) {
            player2Win = true;
        } else if (!playerHasCards(player2)) {
            player1Win = true;
        }
        if (player1Win) {
            System.out.println("Player 1 Wins!!!!!!!!!!!");
        } else if (player2Win) {
            System.out.println("Player 2 Wins!!!!!!!!!!!");
        }
        System.out.println("Game End");
    }

    public void videoGames() {
        transferDecks(player1, secondDeck1);
        transferDecks(player2, secondDeck2);
        Card p1Deal = player1.deck.deal();
        Card p2Deal = player2.deck.deal();
        System.out.println(p1Deal.getName() + " vs. " + p2Deal.getName());
        if (p1Deal.getRank() > p2Deal.getRank()) {
            secondDeck1.add(p1Deal);
            secondDeck1.add(p2Deal);
            System.out.println("Player 1 wins pile!");
        } else if (p1Deal.getRank() < p2Deal.getRank()) {
            secondDeck2.add(p1Deal);
            secondDeck2.add(p2Deal);
            System.out.println("Player 2 wins pile!");
        } else {
            System.out.println("So it's War then...");
            war();
        }
    }

    public void war() {
        List<Card> warPile1 = new ArrayList<Card>();
        List<Card> warPile2 = new ArrayList<Card>();
        Card p1Deal;
        Card p2Deal;
        boolean continueWar = true;
        while (playerCanWar(player1, secondDeck1) && playerCanWar(player2, secondDeck2) && continueWar) {
            for (int i = 0; i < 3; i++) {
                transferDecks(player1, secondDeck1);
                warPile1.add(player1.deck.deal());
                transferDecks(player2, secondDeck2);
                warPile2.add(player2.deck.deal());
            }
            p1Deal = player1.deck.deal();
            p2Deal = player2.deck.deal();
            warPile1.add(p1Deal);
            warPile2.add(p2Deal);
            if (p1Deal.getRank() > p2Deal.getRank()) {
                secondDeck1.addAll(warPile1);
                secondDeck1.addAll(warPile2);
                continueWar = false;
                System.out.println("Player 1 wins war!");
            } else if (p2Deal.getRank() > p1Deal.getRank()) {
                secondDeck2.addAll(warPile1);
                secondDeck2.addAll(warPile2);
                continueWar = false;
                System.out.println("Player 2 wins war!");
            }
        }
        if (!playerCanWar(player1, secondDeck1)) {
            player2Win = true;
            shouldContinue = false;
        } else if (!playerCanWar(player2, secondDeck2)) {
            player1Win = true;
            shouldContinue = false;
        }
    }

    public int deckLength(Player player) {
        return player.deck.length();
    }

    public boolean playerHasCards(Player player) {
        return deckLength(player) > 0 || secondDeck1.size() > 0;
    }

    public boolean playerCanWar(Player player, List<Card> secondDeck) {
        return deckLength(player) + secondDeck.size() >= 4;
    }
    
    public void transferDecks(Player player, List<Card> secondDeck) {
        if (deckLength(player) == 0) {
            player.deck = new Deck(secondDeck);
        }
    }
}