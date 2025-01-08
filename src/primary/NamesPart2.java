package primary;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class NamesPart2 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite os nomes para adicionar na lista e separe-os por vírgulas: ");
		
		String input = scanner.nextLine();
		String[] names = input.split(",");
		Map<String, String> sortedNames = new TreeMap<>();
		
		for (String name: names) {
			sortedNames.put(name.trim(), name.trim());
		}
		System.out.println("Ordem Alfabética: ");
		
		for (String name : sortedNames.values()) {
			System.out.println(name);
		}
		scanner.close();
	}
}
