package primary;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Streams {
	public static void main(String[] args) {
		Scanner scanner = new 	Scanner(System.in);
		
		System.out.println("Insira a lista:");
		String entry = scanner.nextLine();
		
		String[] persons = entry.split(",");
		List<String> groupM = new ArrayList<>();
		List<String> groupF = new ArrayList<>();
		
		for (String person : persons) {
			String[] parts = person.split("-");
			String name = parts[0];
			String gender = parts[1];
			
			if ("M".equalsIgnoreCase(gender)) {
				groupM.add(name);
			} else if ("F".equalsIgnoreCase(gender)) {
				groupF.add(name);
			}
		}
		
		System.out.println("\nGrupo M: ");
		for (String name : groupM) {
			System.out.println(name);
		}
		
		System.out.println("\nGrupo F: ");
		for (String name : groupF) {
			System.out.println(name);
		}
		
		List<String> female = groupF.stream().collect(Collectors.toList());
		System.out.println("\nLista só com mulheres em lambda e stram:");
		female.forEach(System.out::println);
		
		scanner.close();
	}
}
