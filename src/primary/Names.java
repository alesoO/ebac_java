package primary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Names {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		List<String> listMasc = new ArrayList<>();
		List<String> listFem = new ArrayList<>();
		List<String> listAll = new ArrayList<>();
		
		System.out.println("Quais os nomes?(nome -m/f,): ");
		
		String name = scanner.nextLine();
		String[] nameList = name.split(",\\s");
		for (int i = 0; i < nameList.length; i++) {
			listAll.add(nameList[i]);
		}
		
		for (int i = 0; i < listAll.size(); i++) {
			String temp = listAll.get(i).toString();
			String[] temp2 = temp.split("-");
			
			if(temp2.length >= 2) {
				if(temp2[1].equals("m")) {
					listMasc.add(temp2[0]);
				} else if (temp2[1].equals("f")) {
					listFem.add(temp2[0]);
				}
			}
		}
		
		Collections.sort(listFem);
		Collections.sort(listMasc);
		Collections.sort(listAll);
		System.out.println("Lista Original: " + listAll);
		System.out.println("Nomes Femininos: " + listFem);
		System.out.println("Nomes Masculinos: " + listMasc);
		
		scanner.close();
	}

}
