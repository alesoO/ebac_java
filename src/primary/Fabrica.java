package primary;

class NewCarFactory implements CarFactory{
	private String color;
	private int age;
	
	@Override
	public Car createCar(String color) {
		Car car = new NewCar();
		car.setColor(color);
		return car;
	}

	@Override
	public Engine createEngine(int age) {
		if(age > 30) {
			return new V8();
		} else {
			return new NewEngine();
		}
	}
}
