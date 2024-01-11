// GUIDE:
// 	-> Look at Card.java for constructor details.
// 	-> enter a card as (num, <int for suit>)
// 	-> Legend: [0, 1, 2, 3] === [Clubs, Diamonds, Spades, Hearts] === ['C', 'D', 'S', 'H']
// 	-> Cards1 and Cards2 are our hole cards
// 	-> Cards3 and Cards4 are the villain's hole cards

public class RunCalculator {
	public static void main(String[] args) {
		Card card1 = new Card(5, 2);
		Card card2 = new Card(9, 2);
		Card card3 = new Card(8, 3);
		Card card4 = new Card(6, 1);
		System.out.println(card1.toString());
		System.out.println(card2.toString());
		Card[] hand = {card1, card2};
		Card[] villain = {card3, card4};
		EquityCalculator calc = new EquityCalculator(hand, villain);
		calc.preFlop();
	}
}
