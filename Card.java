import java.util.*;

public class Card implements Comparable<Card> {
	public int num;
	public char suit;
	
	public Card(int num, String suit) {
		
	}
	
	public Card(int num, int suit) {
		if (num > 14 || num < 1 || suit < 0 || suit > 3) {
			throw new IllegalArgumentException();
		}
		char[] suits = {'C', 'D', 'S', 'H'};
		this.num = num;
		if (num == 1) {
			this.num = 14;
		}
		this.suit = suits[suit];
	}
	
	public int compareTo(Card other) {
		// if (this.num == other.num) {
// 			if (this.suit == other.suit) {
// 				return 0;
// 			}
// 			return 1;
// 		}
		return this.num - other.num;
	}
	
	public String getCardSymbol() {
		if (num == 14) return "A";
		else if (num == 13) return "K";
		else if (num == 12) return "Q";
		else if (num == 11) return "J";
		else if (num == 10) return "T";
		return "" + num;
	}
}