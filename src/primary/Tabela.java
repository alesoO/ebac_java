package primary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Tabela {
	String value();
}

@Tabela("Pessoa")
public class Pessoa {
	private String name;
	private int age;
	
	public Pessoa(String name, int age) {
		this.name = name;
		this.age = age;
	}
		
	@Override
	public String toString() {
		return "Pessoa{" +
	           "nome='" + name + '\'' +
	           ", idade=" + age +
	           '}';
	}
		
	public static void main(String[] args) {
		Pessoa pessoa = new Pessoa("Caio", 23);
		pessoa.toString();
			
		System.out.println(pessoa.toString());
	}
}
