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
// 		List<String> nw = new ArrayList<>();
// 		System.out.println(Collections.max(nw));
	}
}