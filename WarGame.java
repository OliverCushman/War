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
    private int turns = 0;

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
            System.out.println("Player 1 has " + deckLength(player1) + " cards in their main deck and " + secondDeck1.size() + " cards in their secondary deck.");
            System.out.println("Player 2 has " + deckLength(player2) + " cards in their main deck and " + secondDeck2.size() + " cards in their secondary deck.");
            System.out.println(deckLength(player1) + deckLength(player2) + secondDeck1.size() + secondDeck2.size() + " cards total");
            if (turns >= 300) {
                shouldContinue = false;
            } else if (playerHasCards(player1, secondDeck1) && playerHasCards(player2, secondDeck2)) {
                videoGames();
                turns++;
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                shouldContinue = false;
            }
        }
        if (!playerHasCards(player1, secondDeck1)) {
            player2Win = true;
        } else if (!playerHasCards(player2, secondDeck2)) {
            player1Win = true;
        }
        if (player1Win) {
            System.out.println("Player 1 wins!!!!!!!!!!!");
        } else if (player2Win) {
            System.out.println("Player 2 wins!!!!!!!!!!!");
        } else {
            System.out.println("Draw");
        }
        System.out.println("Game End");
    }

    public void videoGames() {
        transferDecks(player1, secondDeck1);
        transferDecks(player2, secondDeck2);
        Card p1Deal = player1.deck.deal();
        Card p2Deal = player2.deck.deal();
        printCardBattle(p1Deal, p2Deal);
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
            war(p1Deal, p2Deal);
        }
    }

    public void war(Card deal1, Card deal2) {
        List<Card> warPile1 = new ArrayList<Card>();
        warPile1.add(deal1);
        List<Card> warPile2 = new ArrayList<Card>();
        warPile2.add(deal2);
        Card p1Deal;
        Card p2Deal;
        boolean continueWar = true;
        while (playerCanWar(player1, secondDeck1) && playerCanWar(player2, secondDeck2) && continueWar) {
            System.out.println("Player 1 has " + deckLength(player1) + " cards in their main deck and " + secondDeck1.size() + " cards in their secondary deck.");
            System.out.println("Player 2 has " + deckLength(player2) + " cards in their main deck and " + secondDeck2.size() + " cards in their secondary deck.");
            System.out.println(deckLength(player1) + deckLength(player2) + secondDeck1.size() + secondDeck2.size() + " cards total in decks");
            for (int i = 0; i < 3; i++) {
                transferDecks(player1, secondDeck1);
                warPile1.add(player1.deck.deal());
                transferDecks(player2, secondDeck2);
                warPile2.add(player2.deck.deal());
            }
            transferDecks(player1, secondDeck1);
            p1Deal = player1.deck.deal();
            transferDecks(player2, secondDeck2);
            p2Deal = player2.deck.deal();
            warPile1.add(p1Deal);
            warPile2.add(p2Deal);
            
            printCardBattle(p1Deal, p2Deal);
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
        if (continueWar) {
            if (!playerCanWar(player1, secondDeck1)) {
                player2Win = true;
                shouldContinue = false;
            } else if (!playerCanWar(player2, secondDeck2)) {
                player1Win = true;
                shouldContinue = false;
            }
        }
    }

    public int deckLength(Player player) {
        return player.deck.length();
    }

    public boolean playerHasCards(Player player, List<Card> secondDeck) {
        return deckLength(player) > 0 || secondDeck.size() > 0;
    }

    public boolean playerCanWar(Player player, List<Card> secondDeck) {
        return (deckLength(player) + secondDeck.size()) >= 4;
    }
    
    public void transferDecks(Player player, List<Card> secondDeck) {
        if (deckLength(player) == 0) {
            Collections.shuffle(secondDeck);
            for (int i = 0; i < secondDeck.size(); i++) {
                player.deck.addCard(secondDeck.get(i));
            }
            int secondSize = secondDeck.size();
            for (int i = 0; i < secondSize; i++) {
                secondDeck.remove(0);
            }
        }
    }

    public String fullCardName(Card card) {
        return card.getName() + " of " + card.getSuit();
    }

    public void printCardBattle(Card card1, Card card2) {
        System.out.println(fullCardName(card1) + " vs. " + fullCardName(card2));
    }
}