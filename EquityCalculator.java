import java.util.*;

public class EquityCalculator {
    private Map<Integer, String> hand;
	private String comparison;
	private Map<Integer, String> table;
	private List<Integer> cards = new ArrayList<>();
	private boolean highCard;
	private boolean pair;
	private boolean trips;
	private boolean flush;
	private boolean straight;
	private boolean quads;
	
    
	public final const String[] suits = {"H", "C", "S", "D"};
    
    
    public double preFlop() {
        // Check Royal Flush:
		// If 
    }
	
	private Set<String> generateTable() {
		table = new TreeMap<>();
		for (int card : hand.keySet()) {
			table.put(card, hand.get(card));
		}
		Random rand = new Random();
		while (table.size() < 5) {
			int num = rand.nextInt(13) + 1;
			int suit = rand.nextInt(4);
			if (!table.containsKey(num)) {
				table.put(num, suits[suit]);
			} else {
				if (table.get(num) != suits[suit]) {
					table.put(num, suits[suit]);
				}
			}
		}
		if (table.size() > 7) {
			throw new IllegalArgumentException("table bigger than 7??");
		}
		return table;
	}
	
	private String getCardSymbol(int cardNum) {
		if (cardNum == 14) return "A";
		else if (cardNum == 13) return "K";
		else if (cardNum == 12) return "Q";
		else if (cardNum == 11) return "J";
		else if (cardNum == 10) return "T";
		return "" + cardNum;
	}
	
	private String checkHighCard() {
		cards = new ArrayList<>();
		String highCard = "";
		for (int card : table.keySet()) {
			cards.add(card);
			highCard = generateCardSymbol(card) + ;
		}
		return Math.max(cards)
	}
		
	private boolean checkRoyalFlush() {
		for 
	}
	
	private boolean checkStraight() {
		List<Integer> cards = new ArrayList<>();
		for (int card : table.keySet()) {
			cards.add(card);
		}
		boolean isStraight = false;
		for (int i = 0; i < 3; i++) {
			boolean isStraightNested = true;
			for (int j = i + 1; j < i + 5; j++) {
				if (cards.get(j) != cards.get(j - 1) + 1) {
					isStraightNested = false;
				}
			}
			if (isStraightNested) {
				isStraight = true;
				break;
			}
		}
		return isStraight;
	}
	
	private boolean checkFlush() {
		Map<String, Integer> suits = new HashMap<>();
		for (int card : table.keySet()) {
			if (!suits.containsKey(suit)) {
				suits.put(suit, 0);
			}
			suits.put(suit, suits.get(suit) + 1);
			if (suits.get(suit) >= 5) {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkPair() {
		List<Integer> cards = new ArrayList<>();
		for (int card : table.keySet()) {
			if (card == cards.get(cards.length() - 1)) {
				return true;
			}
			cards.add(card);
		}
		return false;
	}
	
	private boolean checkTrips() {
		if (checkPair) {
			List<Integer> cards = new ArrayList<>();
			for (int card : table.keySet()) {
				cards.add(card);
			} 
		}
	}
	
	private boolean checkStraightFlush() {
		if (checkFlush() && checkStraight()) {
			
		}
	}
}