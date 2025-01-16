package primary;

class NewCar implements Car {
	private String color;
	
	public void showInfo() {
		System.out.println("Seu carro é a gasolina e é da cor: " + color);
	}

	public void setColor(String color) {
		this.color = color;
	}
}

class NewEngine implements Engine {
	public void showType() {
		System.out.println("Seu motor é a Gasolina");
	}
	
}

class V8 implements Engine{
	public void showType() {
		System.out.println("Seu motor é V8");
	}
}