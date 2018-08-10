import java.util.InputMismatchException;
import java.util.Scanner;

public class TotalEdgesInKn {
	public static void main(String[] args) {
		System.out.println("This is a simple program that gives you the total number of edges in a complete graph");
		System.out.println();
		Scanner console = new Scanner(System.in);
		start(console);
		console.close();
	}

	private static void start(Scanner console) {
		// asks users to number of vertices
		int vertices = 0;
		try {
		System.out.print("How many vertices does the graph have?: ");
		vertices = console.nextInt();
		compute(vertices, console);	
		}
		catch(InputMismatchException exception) {
			System.out.println("That number is not supported. Try again");
			console.next();
			System.out.println();
			start(console);
		}
	}

	private static void compute(int vertices, Scanner console) {
		int totalEdges = Math.abs(((vertices - 1) * vertices) / 2);
		System.out.println("The total number of edges is: " + totalEdges);
		playAgain(console);
		
	}

	private static void playAgain(Scanner console) {
		System.out.println();
		System.out.print("Click Y to try again or N to quit: ");
		String answer = console.next();
		answer = answer.toUpperCase();
		if (answer.equals("N")) {
			System.out.println("Thank you!");
		}
		else if (answer.equals("Y")) {
			start(console);
		}
		else if (!answer.equals("N") || !answer.equals("Y")) {
			playAgain(console);
		}
	}
}