package primary;

@Tabela (name = "Pessoa")
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
		Pessoa pessoa = new Pessoa("Lucas", 25);
		
		Class<?> classPessoa = pessoa.getClass();
		
		if (classPessoa.isAnnotationPresent(Tabela.class)) {
			Tabela annotation = classPessoa.getAnnotation(Tabela.class);
			String tableName = annotation.name();
			System.out.println("Nome da Tabela: " + tableName);
		} else {
			System.out.println("A anotação Tabela não está presente na classe Pessoa.");
		}
		
		System.out.println(pessoa.toString());
	}

}
