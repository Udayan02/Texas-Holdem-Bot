import java.util.*;


/////////
// THIS VERSION COMPILES BUT RUNS WITH A FEW BUGS
////////

public class EquityCalculator {
    private Card[] hand;
// 	private String comparison;
	private List<Card> table;
	private Map<Integer, Integer> counts;
	private boolean pair;
	private boolean set;
	private boolean flush;
	private boolean straight;
	private boolean fullHouse;
	private boolean quads;
	private boolean straightFlush;
	private boolean royalFlush;
	    
    public EquityCalculator(Card[] hand) {
		this.hand = hand;
	}
	
    public void preFlop() {
        for (int i = 0; i < 10; i++) {
			generateTable();
			System.out.println(printTable());
			String highCard = checkHighCard();
			int highestPair = checkPair();
			pair = (highestPair != 0);
			int highestSet = checkTrips();
			set = (highestSet != 0);
			int highestStraight = checkStraight();
			straight = (highestStraight != 0);
			int highestFlush = checkFlush();
			flush = (highestFlush != 0);
			int highestFullHouse = checkFullHouse();
			fullHouse = (highestFullHouse != 0);
			int highestQuads = checkQuads();
			quads = (highestQuads != 0);
			int highestStraightFlush = checkStraightFlush();
			straightFlush = (highestStraightFlush != 0);
			royalFlush = checkRoyalFlush();
			boolean[] results = {pair, set, straight, flush, fullHouse,
								 quads, straightFlush, royalFlush};
			int highestCombo = -1;
			for (int j = results.length - 1; j > -1; j--) {
				if (results[j]) {
					highestCombo = j;
					break;
				}
			}
			System.out.println(highestCombo);
		}
    }
	
	private void generateTable() {	
		table = new ArrayList<>();
		Random rand = new Random();
		for (Card card : hand) {
			table.add(card);
		}
		while (table.size() < 7) {
			int num = rand.nextInt(13) + 1;
			int suit = rand.nextInt(4);
			Card card = new Card(num, suit);
			if (!table.contains(card)) {
				table.add(card);
			}
		}
		Collections.sort(table);
	}
	
	private String checkHighCard() {
		return table.get(table.size() - 1).getCardSymbol();
	}
	
	private int checkPair() {
		counts = new TreeMap<>();
		int highestPair = 0;
		for (Card card : table) {
			if (!counts.containsKey(card.num)) {
				counts.put(card.num, 0);
			}
			counts.put(card.num, counts.get(card.num) + 1);
		}
		for (int num : counts.keySet()) {
			if (counts.get(num) >= 2) {
				highestPair = num;
			}
		}
		return highestPair;
	}
	
	private int checkTrips() {
		int highestTrips = 0;
		for (int num : counts.keySet()) {
			if (counts.get(num) >= 3) {
				highestTrips = num;
			}
		}
		return highestTrips;
	}


	private int checkStraight() {
		int highestStraight = 0;
		for (int i = 0; i < 3; i++) {
			boolean isStraightNested = true;
			for (int j = i + 1; j < i + 5; j++) {
				if (table.get(j).num != table.get(j - 1).num + 1) {
					isStraightNested = false;
				}
			}
			if (isStraightNested) {
				highestStraight = Math.max(highestStraight, table.get(i + 4).num);
			}
		}
		return highestStraight;
	}
	
	private int checkFlush() {
		Map<Character, Integer> suits = new HashMap<>();
		for (Card card : table) {
			if (!suits.containsKey(card.suit)) {
				suits.put(card.suit, 0);
			}
			suits.put(card.suit, suits.get(card.suit) + 1);
		}
		for (int i = table.size() - 1; i > -1; i--) {
			if (suits.get(table.get(i).suit) >= 5) {
				return table.get(i).num;
			}
		}
		return 0;
	}
	
	private int checkFullHouse() {
		int highestFull = 0;
		int pair = checkPair();
		int set = checkTrips();
		if (pair != set && pair != 0 && set != 0) {
			highestFull = set;
		}
		return highestFull;
	}
		
	private int checkQuads() {
		int highestQuads = 0;
		for (int num : counts.keySet()) {
			if (counts.get(num) == 4) {
				highestQuads = num;
			}
		}
		return highestQuads;
	}

	private int checkStraightFlush() {
		int highestStraightFlush = 0;
		for (int i = 0; i < 3; i++) {
			boolean isStraight = true;
			boolean isFlush = true;
			for (int j = i + 1; j < i + 5; j++) {
				if (table.get(j).num != table.get(j - 1).num + 1) {
					isStraight = false;
				}
				if (table.get(j).suit != table.get(j - 1).suit) {
					isFlush = false;
				}
			}
			if (isStraight && isFlush) {
				highestStraightFlush = Math.max(highestStraightFlush, table.get(i + 5).num);
			}
		}
		return highestStraightFlush;
	}
	
	private boolean checkRoyalFlush() {
		return checkStraightFlush() == 14;
	}
	
	private String printTable() {
		String result = "";
		for (Card card : table) {
			boolean matchesHand = false;
			for (Card card2 : hand) {
				if (card.compareTo(card2) == 0) {
					matchesHand = true;
				}
			}
			if (!matchesHand) {
				result += card.num + " ";
			}
		}
		return result;
	}
}
