package primary;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Names {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Escreva o nome e o sexo da pessoa, separados por vírgulas: ");
		
		String input = scanner.nextLine();
		String[] entries = input.split(",");

		Map<String, List<String>> genderGroups = new HashMap<>();

		genderGroups.put("M", new LinkedList<>());
		genderGroups.put("F", new LinkedList<>());
 
		for (String entry : entries) {
			String[] parts = entry.trim().split("-");
			if (parts.length == 2) {
				String name = parts[0].trim();
				String gender = parts[1].trim().toUpperCase();
				if (genderGroups.containsKey(gender)) {
					genderGroups.get(gender).add(name);
				}
			}
		}
		for (Map.Entry<String, List<String>> group : genderGroups.entrySet()) {
			System.out.println("Grupo " + group.getKey() + ": " + group.getValue());
		}
		scanner.close();
	}
}
