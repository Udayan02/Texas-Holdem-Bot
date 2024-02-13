import java.util.*;
import java.util.stream.IntStream;
import java.util.concurrent.atomic.AtomicInteger;

public class EquityCalculator2 {
	List<Card> table;
	List<Card> villainTable;
	Card[] hand;
	Card[] villain;
	int[] forbiddenIndices;
	List<Card> cards52;
	
	public static final int NUM_TIMES = 300000;
	
	public EquityCalculator2(Card[] hand, Card[] villain) {
		this.hand = hand;
		this.villain = villain;
		table = new ArrayList<>();
		villainTable = new ArrayList<>();
		forbiddenIndices = new int[4];
		forbiddenIndices[0] = ((hand[0].num) - 1) * ((hand[0].suit) + 1);
		forbiddenIndices[1] = ((hand[1].num) - 1) * ((hand[1].suit) + 1);
		forbiddenIndices[2] = ((villain[0].num) - 1) * ((villain[0].suit) + 1);
		forbiddenIndices[3] = ((villain[1].num) - 1) * ((villain[1].suit) + 1);
		cards52 = new ArrayList<>();
		for (int i = 2; i < 15; i++) {
			for (int j = 0; j < 4; j++) {
				cards52.add(new Card(i, j));
			}
		}
	}
	
	public EquityCalculator2(Card[] hand, Card[] villain, List<Card> table) {
		this(hand, villain);
		this.table = table;
	}
	
	private void generateTable() {
		while (table.size() < 5) {
			Random rand = new Random();
			int num = rand.nextInt(0, 52); 
			Card card = cards52.get(num);
			boolean shouldAdd = true;
// 			for (int i = 0; i < forbiddenIndices.length; i++) {
// 				if (num == forbiddenIndices[i]) {
// 					shouldAdd = false;
// 				}
// 			}
            for (int i = 0; i < 2; i++) {
                if (card == hand[i] || card == villain[i]) {
                    shouldAdd = false;
                }
            }
			if (shouldAdd) {
				table.add(cards52.get(num));
			}
		}
		for (Card card : table) {
			villainTable.add(card);
		}
		table.add(hand[0]);
		table.add(hand[1]);
		villainTable.add(villain[0]);
		villainTable.add(villain[1]);
		Collections.sort(table);
	}
	
	public void generateEquities() {
		AtomicInteger wins = new AtomicInteger(0);
		AtomicInteger ties = new AtomicInteger(0);
//         IntStream.range(0, NUM_TIMES).parallel().forEach(i -> {
		for (int i = 0; i < NUM_TIMES; i++) {
            table = new ArrayList<>();
            villainTable = new ArrayList<>();
			generateTable();
// 			printTable();
			Equity calculator = new Equity(table, villainTable);
			int win = calculator.generateEquity();
			if (win == 0) {
				wins.incrementAndGet();
//                 System.out.println("WINNNNNNN");
			} else if (win == 1) {
				ties.incrementAndGet();
			}
		}
        int totalWins = wins.get();
        int totalTies = ties.get();
		System.out.println("We win: " + ((1.0 * totalWins) / NUM_TIMES) * 100 + " % of times!");
		System.out.println("We chop: " + ((1.0 * totalTies) / NUM_TIMES) * 100 + " % of times!");
	}
	
	private void printTable() {
		String result = "";
        String villainResult = "";
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
        for (Card card : villainTable) {
			boolean matchesHand = false;
			// for (Card card2 : hand) {
// 				if (card.num == card2.num && card.suit == card2.suit) {
// 					matchesHand = true;
// 				}
// 			}
			if (!matchesHand) {
				villainResult += card.toString() + " ";
			}
		}// 
// 		System.out.println(result);
//         System.out.println(villainResult);
	}
}