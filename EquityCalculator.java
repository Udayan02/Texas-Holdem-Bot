import java.util.*;

///////////
// CODE RUNS SUCCESSFULLY BUT I THINK THE PROBABILITIES ARE OFF
///////////

public class EquityCalculator {
    private Card[] hand;
	private Card[] villain;
	private List<Card> table;
	private List<Card> table2;
	private Map<Integer, Integer> counts;
	private boolean pair;
	private boolean twoPair;
	private boolean set;
	private boolean flush;
	private boolean straight;
	private boolean fullHouse;
	private boolean quads;
	private boolean straightFlush;
	private boolean royalFlush;
	
	
	public static final int TIMES = 1000000;
	    
    public EquityCalculator(Card[] hand, Card[] villain) {
		this.hand = hand;
		this.villain = villain;
	}
	
    public void preFlop() {
		int totalWins = 0;
        for (int i = 0; i < TIMES; i++) {
			generateTable();
// 			System.out.println(printTable());
			String highCard = checkHighCard();
			int highestPair = checkPair();
			pair = (highestPair != 0);
			int[] highestTwoPair = checkTwoPair();
			twoPair = (highestTwoPair[0] != 0 && highestTwoPair[1] != 0);
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
			boolean[] results = {pair, twoPair, set, straight, flush, fullHouse,
								 quads, straightFlush, royalFlush};
			int highestCombo = -1;
			for (int j = results.length - 1; j > -1; j--) {
				if (results[j]) {
					highestCombo = j;
					break;
				}
			}
			for (Card card : hand) {
				table.remove(card);
			}
			
			
			for (Card card : villain) {
				table.add(card);
			}
			int highestPairV = checkPair();
 			pair = (highestPairV != 0);
			int[] highestTwoPairV = checkTwoPair();
			twoPair = (highestTwoPairV[0] != 0 && highestTwoPairV[1] != 0);
			int highestSetV = checkTrips();
			set = (highestSetV != 0);
			int highestStraightV = checkStraight();
			straight = (highestStraightV != 0);
			int highestFlushV = checkFlush();
			flush = (highestFlushV != 0);
			int highestFullHouseV = checkFullHouse();
			fullHouse = (highestFullHouseV != 0);
			int highestQuadsV = checkQuads();
			quads = (highestQuadsV != 0);
			int highestStraightFlushV = checkStraightFlush();
			straightFlush = (highestStraightFlushV != 0);
			royalFlush = checkRoyalFlush();
			boolean[] resultsV = {pair, twoPair, set, straight, flush, fullHouse,
								  quads, straightFlush, royalFlush};
			int highestComboV = -1;
			for (int j = results.length - 1; j > -1; j--) {
				if (results[j]) {
					highestComboV = j;
					break;
				}
			}
			if (highestCombo > highestComboV) {
				totalWins++;
			} else if (highestCombo == highestComboV) {
				if (highestCombo == 0 && highestPair > highestPairV) {
					totalWins++;
				} else if (highestCombo == 1) {
					if (highestTwoPair[0] > highestTwoPairV[0]) {
						totalWins++;
					} else if (highestTwoPair[0] >= highestTwoPairV[0] &&
							   highestTwoPair[1] > highestTwoPairV[1]) {
						totalWins++;	   
					}
				} else if (highestCombo == 2 && highestSet > highestSetV) {
					totalWins++;
				} else if (highestCombo == 3 && highestStraight > highestStraightV) {
					totalWins++;
				} else if (highestCombo == 4 && highestFlush > highestFlushV) {
					totalWins++;
				} else if (highestCombo == 5 && highestFullHouse > highestFullHouseV) {
					totalWins++;
				} else if (highestCombo == 6 && highestQuads > highestQuadsV) {
					totalWins++;
				} else if (highestCombo == 7 && highestStraightFlush > highestStraightFlushV) {
					totalWins++;
				}
			}
		}
		System.out.println(totalWins);
		double winningPercentage = ((totalWins * 1.0) / TIMES) * 100;
		System.out.println("DONE\n\n\n");
		System.out.println(winningPercentage);
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
			boolean shouldAdd = true;
			for (Card card2 : table) {
				if ((card.num == card2.num && card.suit == card2.suit) || 
					(card.num == villain[0].num && card.suit == villain[0].suit) ||
					(card.num == villain[1].num && card.suit == villain[1].suit)) {
					shouldAdd = false;				
				}
			}
			if (shouldAdd) {
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
	
	private int[] checkTwoPair() {
		int[] result = new int[2];
		if (pair) {
			int highestFirst = 0;
			int highestSecond = 0;
			List<Integer> allPairs = new ArrayList<>();
			for (int num : counts.keySet()) {
				if (counts.get(num) >= 2) {
					allPairs.add(num);
				}
			}
			if (allPairs.size() >= 2) {
				highestFirst = Math.max(allPairs.get(0), allPairs.get(1));
				for (int i = 1; i < allPairs.size(); i++) {
					highestFirst = Math.max(allPairs.get(i), highestFirst);
					if (allPairs.get(i - 1) != highestFirst) {
						highestSecond = Math.max(allPairs.get(i - 1), highestSecond);
					}
				}
				result[0] = highestFirst;
				result[1] = highestSecond;
			}
		}
		return result;	
	}
	
	private int checkTrips() {
		int highestTrips = 0;
		if (pair) {
			for (int num : counts.keySet()) {
				if (counts.get(num) >= 3) {
					highestTrips = num;
				}
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
		if (set) {
			for (int num : counts.keySet()) {
				if (counts.get(num) == 4) {
					highestQuads = num;
				}
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
				highestStraightFlush = Math.max(highestStraightFlush, table.get(i + 4).num);
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
				if (card.num == card2.num && card.suit == card2.suit) {
					matchesHand = true;
				}
			}
			if (!matchesHand) {
				result += card.toString() + " ";
			}
		}
		return result;
	}
}
