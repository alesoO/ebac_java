package primary;

interface Car {
	void showInfo();
	void setColor(String color);
}
	
interface Engine {
	void showType();
}
	
interface CarFactory {
	Car createCar(String color);
	Engine createEngine(int age);
}
