import java.util.*;

public class Equity {
	List<Card> table;
	List<Card> villainTable;
	Map<Integer, Integer> cardCounts;
	Map<Integer, Integer> suitCounts;
	Map<Integer, Integer> villainCardCounts;
	Map<Integer, Integer> villainSuitCounts;
	
	public Equity(List<Card> table, List<Card> villainTable) {
		this.table = table;
		this.villainTable = villainTable;
		cardCounts = new TreeMap<>();
		suitCounts = new HashMap<>();
		villainCardCounts = new TreeMap<>();
		villainSuitCounts = new HashMap<>();
		
		for (int i = 0; i < table.size(); i++) {
			Card card = table.get(i);
			if (!cardCounts.containsKey(card.num)) {
				cardCounts.put(card.num, 0);
			}
			cardCounts.put(card.num, cardCounts.get(card.num) + 1);
			if (!suitCounts.containsKey(card.suit)) {
				suitCounts.put(card.suit, 0);
			}
			suitCounts.put(card.suit, suitCounts.get(card.suit) + 1);
			
			Card villainCard = villainTable.get(i);
			if (!villainCardCounts.containsKey(villainCard.num)) {
				villainCardCounts.put(villainCard.num, 0);
			}
			villainCardCounts.put(villainCard.num, villainCardCounts.get(villainCard.num) + 1);
			if (!villainSuitCounts.containsKey(villainCard.suit)) {
				villainSuitCounts.put(villainCard.suit, 0);
			}
			villainSuitCounts.put(villainCard.suit, villainSuitCounts.get(villainCard.suit) + 1);
		}
	}
	
	public int generateEquity() {
		int[] highCards = getHighCard();
		int[] pairSetQuads = getPairSetQuads();
		int[][] twoPairsHouses = getTwoPairHouse(pairSetQuads);
		int[] straight = getStraight();
		boolean[] flush = getFlush();
		boolean win = false;
		boolean tie = false;
		// Quads:
		if (pairSetQuads[2] != 0 && pairSetQuads[2] > pairSetQuads[5]) {
			if (pairSetQuads[2] > pairSetQuads[5]) {
				win = true;
			} else if (pairSetQuads[2] == pairSetQuads[5]) {
				if (highCards[0] > highCards[1]) {
					win = true;
				} else if (highCards[0] == highCards[1]) {
					tie = true;
				}
			}
		}
		// Full House:
		if (!win) {
			if (twoPairsHouses[1][0] != 0 && twoPairsHouses[1][1] != 0) {
				if (twoPairsHouses[3][0] == 0 || twoPairsHouses[3][1] == 0) {
					win = true;
				} else if (twoPairsHouses[3][0] != 0 && twoPairsHouses[3][1] != 0) {
					if (twoPairsHouses[1][0] > twoPairsHouses[3][0]) {
						win = true;
					} else if (twoPairsHouses[1][0] == twoPairsHouses[3][0]) {
						if (twoPairsHouses[1][1] > twoPairsHouses[3][1]) {
							win = true;
						} else if (twoPairsHouses[1][1] == twoPairsHouses[3][1]) {
							if (highCards[0] > highCards[1]) {
								win = true;
							} else if (highCards[0] == highCards[1]) {
								tie = true;
							}
						}
					}
				}
			}
		}
		// Flush:
		if (!win && !tie) {
			if (flush[0]) {
				if (!flush[1]) {
					win = true;
				} else {
					int highestFlushCard = 0;
					int villainHighestFlush = 0;
					for (int i = 0; i < table.size(); i++) {
						Card card = table.get(i);
						Card villainCard = villainTable.get(i);
						if (suitCounts.get(card.suit) == 5) {
							highestFlushCard = card.num;
						}
						if (villainSuitCounts.get(villainCard.suit) == 5) {
							villainHighestFlush = villainCard.num;
						}					
					}
					if (highestFlushCard > villainHighestFlush) {
						win = true;
					} else if (highestFlushCard == villainHighestFlush) {
						tie = true;
					}
				}
			}
		}
		// Straight:
		if (!win && !tie) {
			if (straight[0] != 0) {
				if (straight[1] == 0) {
					win = true;
				} else {
					if (straight[0] > straight[1]) {
						win = true; 
					} else if (straight[0] == straight[1]) {
						tie = true;
					}
				}
			}
		}
		// Three of a Kind:
		if (!win && !tie) {
			if (pairSetQuads[1] != 0) {
				if (pairSetQuads[4] == 0) {
					win = true;
				} else if (pairSetQuads[1] > pairSetQuads[4]) {
					win = true;
				} else if (pairSetQuads[1] == pairSetQuads[4]) {
					if (highCards[0] > highCards[1]) {
						win = true;
					} else if (highCards[0] == highCards[1]) {
						tie = true;
					} 
				}
			}
		}
		// Two Pair:
		if (!win && !tie) {
			if (twoPairsHouses[0][0] != 0 && twoPairsHouses[0][1] != 0) {
				if (twoPairsHouses[2][0] == 0 || twoPairsHouses[2][1] == 0) {
					win = true;
				} else {
					if (twoPairsHouses[0][0] > twoPairsHouses[2][0]) {
						win = true;
					} else if (twoPairsHouses[0][0] == twoPairsHouses[2][0]) {
						if (twoPairsHouses[0][1] > twoPairsHouses[2][1]) {
							win = true;
						} else if (twoPairsHouses[0][1] == twoPairsHouses[2][1]) {
							if (highCards[0] > highCards[1]) {
								win = true;
							} else if (highCards[0] == highCards[1]) {
								tie = true;
							}	
						}
					}
				}
			}
		}
		// Pair:
		if (!win && !tie) {
			if (pairSetQuads[0] != 0) {
				if (pairSetQuads[3] == 0) {
					win = true;
				} else {
					if (pairSetQuads[0] > pairSetQuads[3]) {
						win = true;
					} else if (pairSetQuads[0] == pairSetQuads[3]) {
						if (highCards[0] > highCards[1]) {
							win = true;
						} else if (highCards[0] == highCards[1]) {
							tie = true;
						}
					}
				}
			}
		}
		// High Card:
		if (!win && !tie) {
			if (highCards[0] > highCards[1]) {
				win = true;
			} else if (highCards[0] == highCards[1]) {
				tie = true;
			}
		}
		if (win) {
            return 0;
        } else if (tie) {
            return 1;
        }
		return 2;
	}
	
	private int[] getHighCard() {
		int highest = table.get(table.size() - 1).num;
		int villainHighest = villainTable.get(villainTable.size() - 1).num; 
		int[] result = {highest, villainHighest};
		return result;
	}
	
	private int[] getPairSetQuads() {
		int highestPair = 0;
		int highestSet = 0;
		int highestQuads = 0;
		int villainHighestPair = 0;
		int villainHighestSet = 0;
		int villainHighestQuads = 0;
		int[] result = {highestPair, highestSet, highestQuads, 
		   				villainHighestPair, villainHighestSet, villainHighestQuads};
		
		for (int num : cardCounts.keySet()) {
			if (cardCounts.get(num) == 2) {
				highestPair = num;
			} else if (cardCounts.get(num) == 3) {
				highestSet = num;
			} else if (cardCounts.get(num) == 4) {
				highestQuads = num;
			}
		}
		for (int num : villainCardCounts.keySet()) {
			if (villainCardCounts.get(num) == 2) {
				villainHighestPair = num;
			} else if (villainCardCounts.get(num) == 3) {
				villainHighestSet = num;
			} else if (villainCardCounts.get(num) == 4) {
				villainHighestQuads = num;
			}
		}
		return result;
	}
	
	private int[][] getTwoPairHouse(int[] combos) {
		int highestSecondPair = 0;
		int villainHighestSecondPair = 0;
// 		int[] combos = getPairSetQuads();
		for (int num : cardCounts.keySet()) {
			if (cardCounts.get(num) == 2) {
				if (num != combos[0]) {
					highestSecondPair = num;
				}
			}
		}
		for (int num : villainCardCounts.keySet()) {
			if (villainCardCounts.get(num) == 2) {
				if (num != combos[3]) {
					villainHighestSecondPair = num;
				}
			}
		}
		int[] highestTwoPair = {combos[0], highestSecondPair};
		int[] villainHighestTwoPair = {combos[3], villainHighestSecondPair};
		int[] highestFullHouse = {combos[1], combos[0]};
		int[] villainHighestFullHouse = {combos[4], combos[3]};
		int[][]result = {highestTwoPair, highestFullHouse, villainHighestTwoPair,
						 villainHighestFullHouse};
		return result;
	}
	
	private int[] getStraight() {
		int highestStraight = 0;
		int villainHighestStraight = 0;
		for (int i = 0; i < 3; i++) {
			boolean isStraightNested = true;
			boolean villainStraightNested = true;
			for (int j = i + 1; j < i + 5; j++) {
				if (table.get(j).num != table.get(j - 1).num + 1) {
					isStraightNested = false;
				}
				if (villainTable.get(j).num != villainTable.get(j - 1).num + 1) {
					villainStraightNested = false;
				}
			}
			if (isStraightNested) {
				highestStraight = Math.max(highestStraight, table.get(i + 4).num);
			}
			if (villainStraightNested) {
				villainHighestStraight = Math.max(villainHighestStraight, villainTable.get(i + 4).num);
			}		
		}
		int[] result = {highestStraight, villainHighestStraight};
		return result;
	}
	
	private boolean[] getFlush() {
		boolean flush = false;
		for (int suit : suitCounts.keySet()) {
			if (suitCounts.get(suit) >= 5) {
				flush = true;
			}
		}
		boolean villainFlush = false;
		for (int suit : villainSuitCounts.keySet()) {
			if (villainSuitCounts.get(suit) >= 5) {
				villainFlush = true;
			}
		}
		boolean[] result = {flush, villainFlush};
		return result;
	}
	
// 	private void getStraightFlush() {
// 	
// 	}
}