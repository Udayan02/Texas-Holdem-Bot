import java.util.*;

///////////////////
// WORKS WITH AN ERROR MARGIN OF 2.5% AT MAX
//////////////////

//////////////////
// NEXT TASK IS TO OPTIMIZE AND INCLUDE STAGES OF THE GAME
//////////////////

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
	private int totalPairs;
	private int totalTwoPairs;
	private int totalSets;
	private int totalStraights;
	private int totalFlushes;
	private int totalFullHouses;
	private int totalQuads;
	private int totalStraightFlushes;
	private int totalRoyalFlushes;
	
	
	public static final int TIMES = 200000;
	    
    public EquityCalculator(Card[] hand, Card[] villain) {
		this.hand = hand;
		this.villain = villain;
	}
	
    public void preFlop() {
		int totalWins = 0;
		int totalTies = 0;
        for (int i = 0; i < TIMES; i++) {
			generateTable();
			boolean win = false;
			boolean tie = false;
// 			System.out.println("\n" + printTable());
			int highestCard = checkHighCard()[0];
			int secondHighestCard = checkHighCard()[1];
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
			int[] highestFullHouse = checkFullHouse();
			fullHouse = (highestFullHouse[0] != 0);
			int highestQuads = checkQuads();
			quads = (highestQuads != 0);
			int highestStraightFlush = checkStraightFlush();
			straightFlush = (highestStraightFlush != 0);
			royalFlush = checkRoyalFlush();
			boolean[] results = {pair, twoPair, set, straight, flush, fullHouse,
								 quads, straightFlush, royalFlush};
		 	String[] results_names = {"pair", "twoPair", "set", "straight", "flush",
									  "fullHouse", "quads", "straightFlush", "royalFlush"};
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
			if (highestCombo == -1) {
// 				System.out.println("We got: highCard" + highestCard);
			} else {
// 				System.out.println("We got: " + results_names[highestCombo]);
			}
			
			for (Card card : villain) {
				table.add(card);
			}
			Collections.sort(table);
// 			System.out.println(printTable());
			int highestCardV = checkHighCard()[0];
			int secondHighestCardV = checkHighCard()[1];
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
			int[] highestFullHouseV = checkFullHouse();
			fullHouse = (highestFullHouseV[0] != 0);
			int highestQuadsV = checkQuads();
			quads = (highestQuadsV != 0);
			int highestStraightFlushV = checkStraightFlush();
			straightFlush = (highestStraightFlushV != 0);
			royalFlush = checkRoyalFlush();
			boolean[] resultsV = {pair, twoPair, set, straight, flush, fullHouse,
								  quads, straightFlush, royalFlush};
			int highestComboV = -1;
			for (int j = resultsV.length - 1; j > -1; j--) {
				if (resultsV[j]) {
					highestComboV = j;
					break;
				}
			}
// 			if (highestComboV == -1) {
// 				System.out.println("Villain got: highCard" + highestCardV);
// 			} else {
// 				System.out.println("Villain got: " + results_names[highestComboV]);
// 			}
			if (highestCombo > highestComboV) {
				win = true;
			} else if (highestCombo == highestComboV) {
				if (highestCombo == -1) {
					if (hand[1].num > villain[1].num) {
						win = true;
					} else if (hand[1].num == villain[1].num) {
						if (hand[0].num > villain[0].num) {
							win = true;
						} else if (hand[0].num == villain[0].num) {
							tie = true;
						}
					}
				} else if (highestCombo == 0 && highestPair >= highestPairV) {
// 					System.out.println("Our pair: " + highestPair + "  Villain Pair: " + highestPairV);
					if (highestPair == highestPairV) {
						if (hand[1].num > villain[1].num) {
							win = true;
						} else if (hand[1].num == villain[1].num) {
							if (hand[0].num > villain[0].num) {
								win = true;
							} else if (hand[0].num == villain[0].num) {
								tie = true;
							}
						}
					} else if (highestPair > highestPairV) {
						win = true;
					}
				} else if (highestCombo == 1) {
					if (highestTwoPair[0] > highestTwoPairV[0]) {
						win = true;
					} else if (highestTwoPair[0] == highestTwoPairV[0]) {
						if (highestTwoPair[1] > highestTwoPairV[1]) {
							win = true; 
						} else if (highestTwoPair[1] == highestTwoPairV[1]) {
							if (hand[1].num > villain[1].num) {
								win = true;
							} else if (hand[1].num == villain[1].num) {
								if (hand[0].num > villain[0].num) {
									win = true;
								} else if (hand[0].num == villain[0].num) {
									tie = true;
								}
							}
						}
					}
				} else if (highestCombo == 2) {
					if (highestSet > highestSetV) {
						win = true;
					} else if (highestSet == highestSetV) {
						if (hand[1].num > villain[1].num) {
							win = true;
						} else if (hand[1].num == villain[1].num) {
							if (hand[0].num > villain[0].num) {
								win = true;
							} else if (hand[0].num == villain[0].num) {
								tie = true;
							}
						}
					}
				} else if (highestCombo == 3) {
					if (highestStraight > highestStraightV) {
						win = true;
					} else if (highestStraight == highestStraightV) {
						if (hand[1].num > villain[1].num) {
							win = true;
						} else if (hand[1].num == villain[1].num) {
							if (hand[0].num > villain[0].num) {
								win = true;
							} else if (hand[0].num == villain[0].num) {
								tie = true;
							}
						}
					}
				} else if (highestCombo == 4) {
					if (highestFlush > highestFlushV) {
						win = true;
					} else if (highestFlush == highestFlushV) {
						if (hand[1].num > villain[1].num) {
							win = true;
						} else if (hand[1].num == villain[1].num) {
							if (hand[0].num > villain[0].num) {
								win = true;
							} else if (hand[0].num == villain[0].num) {
								tie = true;
							}
						}
					}
				} else if (highestCombo == 5) {
					if (highestFullHouse[0] > highestFullHouseV[0]) {
						win = true;
					} else if (highestFullHouse[0] == highestFullHouseV[0]) {
						if (highestFullHouse[1] > highestFullHouseV[1]) {
							win = true;
						} else if (highestFullHouse[1] == highestFullHouseV[1]) {
							if (hand[1].num > villain[1].num) {
								win = true;
							} else if (hand[1].num == villain[1].num) {
								if (hand[0].num > villain[0].num) {
									win = true;
								} else if (hand[0].num == villain[0].num) {
									tie = true;
								}
							}
						}
					}
				} else if (highestCombo == 6) {
					if (highestQuads > highestQuadsV) {
						win = true;
					} else if (highestQuads == highestQuadsV) {
						if (hand[1].num > villain[1].num) {
							win = true;
						} else if (hand[1].num == villain[1].num) {
							if (hand[0].num > villain[0].num) {
								win = true;
							} else if (hand[0].num == villain[0].num) {
								tie = true;
							}
						}
					}
				} else if (highestCombo == 7) {
					if (highestStraightFlush > highestStraightFlushV) {
						win = true;
					} else if (highestStraightFlush == highestStraightFlushV) {
						if (hand[1].num > villain[1].num) {
							win = true;
						} else if (hand[1].num == villain[1].num) {
							if (hand[0].num > villain[0].num) {
								win = true;
							} else if (hand[0].num == villain[0].num) {
								tie = true;
							}
						}
					}
					System.out.println("Straight Flush detected");
				} else if (highestCombo == 8) {
					if (hand[1].num > villain[1].num) {
						win = true;
					} else if (hand[1].num == villain[1].num) {
						if (hand[0].num > villain[0].num) {
							win = true;
						} else if (hand[0].num == villain[0].num) {
							tie = true;
						}
					}
					System.out.println("ROYAL FU*KIN FLUSH!!!!!!");
				}
			}
			if (win) {
				totalWins++;
// 				System.out.println("We WIN!!");
			} else if (tie) {
				totalTies++;
// 				System.out.println("We CHOP!!");
			} else {
// 				System.out.println("We LOSE D:");
			}
		}
		double winningPercentage = ((totalWins * 1.0) / TIMES) * 100;
		double tiePercentage = ((totalTies * 1.0) / TIMES) * 100;
// 		System.out.println("DONE\n\n\n");
		System.out.println("\nWe win " + winningPercentage + "% of the times!");
// 		System.out.println(tiePercentage);
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
	
	private int[] checkHighCard() {
		int[] result = {table.get(table.size() - 1).num, table.get(table.size() - 2).num};
		return result;
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
			if (counts.get(num) == 2) {
				highestPair = Math.max(num, highestPair);
			}
		}
		return highestPair;
	}
	
	private int[] checkTwoPair() {
		int[] result = new int[2];
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
		return result;	
	}
	
	private int checkTrips() {
		int highestTrips = 0;
		for (int num : counts.keySet()) {
			if (counts.get(num) == 3) {
				highestTrips = Math.max(highestTrips, num);
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
	
	private int[] checkFullHouse() {
		int[] highestFull = new int[2];
		int pair = checkPair();
		int set = checkTrips();
// 		if (pair != set && pair != 0 && set != 0) {
			highestFull[0] = set;
			highestFull[1] = pair;
// 		}
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
			// for (Card card2 : hand) {
// 				if (card.num == card2.num && card.suit == card2.suit) {
// 					matchesHand = true;
// 				}
// 			}
			if (!matchesHand) {
				result += card.toString() + " ";
			}
		}
		return result;
	}
}
