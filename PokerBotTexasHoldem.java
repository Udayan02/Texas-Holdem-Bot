import java.util.*;


// THE CODE DOES NOT COMPILE YET, THERE ARE A FEW INCOMPLETE METHODS
// ALSO, THE METHODS MIGHT NOT RUN CORRECTLY YET. IGNORE ALL OF THAT
// AT LEAST FOR NOW.
public class PokerBotTexasHoldem {
	private final String[][] cardMatrix;
	private int handScore;
	private List<Character> table;
	private String[] hand;
	
	public PokerBotTexasHoldem() {
		handScore = 1;
		cardMatrix = new String[13][13];
		int row = 14;
// 		int column = row;
		for (int i = 0; i < 13; i++) {
			int column = 14;
			for (int j = 0; j < 13; j++) {
				cardMatrix[i][j] = getCardSymbol(Math.max(row, column))
								   + getCardSymbol(Math.min(row, column));
				if (i > j) cardMatrix[i][j] += "o";
				else if (i < j) cardMatrix[i][j] += "s";
				else cardMatrix[i][j] += "p";
				column--;
			}
			row--;
		}
	}
	
	private String getCardSymbol(int cardNum) {
		if (cardNum == 14) return "A";
		else if (cardNum == 13) return "K";
		else if (cardNum == 12) return "Q";
		else if (cardNum == 11) return "J";
		else if (cardNum == 10) return "T";
		return "" + cardNum;
	}
	
	public String[][] cardMatrix() {
		return cardMatrix;
	}
	
	private int getCardNum(char card) {
		if (card == 'K') return 13;
		else if (card == 'Q') return 12;
		else if (card == 'J') return 11;
		else if (card == 'T') return 10;
		return (int) card;
	}
	
	public double rateHand(String hand) {
		if (hand.charAt(2) == 'p') {
			handScore *= 17;
		}
		if (hand.charAt(2) == 's') {
			handScore *= 10.3;
		}
		int range = getCardNum(hand.charAt(0)) - getCardNum(hand.charAt(1));
		if (getCardNum(hand.charAt(0)) > 10) {
			handScore *= 6.2;
			if (getCardNum(hand.charAt(1)) > 10) {
				handScore += 3.8;
			}
		} else if (getCardNum(hand.charAt(1)) > 10) {
			handScore *= 6.2;
			if (getCardNum(hand.charAt(0)) > 10) {
				handScore += 3.8;
			}
		}
		if (range <= 3) {
			// handScore *= (range + (range
		}
	}
	
	public void flop(String[] cards) {
		for (String card : cards) {
			table.add(card);
		}
	}
	
	public void hand(String[] cards) {
		for (String card : cards) {
			hand.add(card);
		}	
	}
	
	public void preFlop() {
		String handSymbol = "";
		handSymbol += hand[0].charAt(0) + hand[1].charAt(0);
		if (hand[0].charAt(1) == hand[1].charAt(1)) {
			handSymbol += "s";
		} else if (hand[0].charAt(0) == hand[1].charAt(0)) {
			handSymbol += "p";
		} else {
			handSymbol += "o";
		}
	} 
	
}
