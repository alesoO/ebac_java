package primary;

public class Cliente {
	private CarFactory factory;
	private String name;
	private int age;
	private String color;
	
	public Cliente(CarFactory factory, String name, int age, String color) {
		this.factory = factory;
		this.name = name;
		this.age = age;
		this.color = color;
	}
	
	public void BuildCar() {
		Car car = factory.createCar(color);
		Engine engine = factory.createEngine(age);
		System.out.println("Cliente: " + name + ", Idade: " + age + ", Cor do carro: " + color);
		car.showInfo();
		engine.showType();
	}
	
	public static void main(String[] args) {
		Cliente client = new Cliente(new NewCarFactory(), "dude", 35, "vermelha");
		client.BuildCar();
	}
}
