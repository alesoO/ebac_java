package primary;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class Test {
	@Test
	public void testList() {
		List<String> persons = Arrays.asList("Ana-F", "João-M", "Maria-F", "Pedro-M", "Sofia-F", "Carlos-M", "Laura-F", "André-M", "Juliana-F", "Lucas-M");
		List<String> female = getFemaleList(persons);
		
		for (String woman : female) {
			assertTrue("A lista contém elementos que não são mulheres: " + woman, woman.endsWith("-F"));
		}
	}
	
	private List<String> getFemaleList(List<String> persons) {
		List<String> female = new ArrayList<>();
		for (String person : persons) {
			String[] parts = person.split("-");
			if (parts[1].equalsIgnoreCase("F")) {
				female.add(parts[0]);
			}
		}
		return female;
	}
}
