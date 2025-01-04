package primary;

public class Calc {
	
	public static void main(String[] args) {
		double n1 = 4.2;
		double n2 = 7.8;
		double n3 = 10;
		double n4 = 3.6;
		
		double result = (n1 + n2 + n3 + n4)/4;
		
		if (result >= 7) {
			System.out.println("Aprovado | Nota:" + result);
		} else if (result >= 5) {
			System.out.println("Recuperação | Nota:" + result);
		} else {
			System.out.println("Reprovado | Nota:" + result);;
		}
	}

}
