package generic;

import java.util.ArrayList;
import java.util.List;

abstract class Car {
	String brand;
	int velocity;
	
	public Car(String brand, int velocity) {
		this.brand = brand;
		this.velocity = velocity;
	}
	
	public abstract void showBrandAndVelocity();
}

class Honda extends Car {
	boolean warranty;
	
	public Honda(boolean warranty, String brand, int velocity) {
		super(brand, velocity);
		this.warranty = warranty;
	}

	@Override
	public void showBrandAndVelocity() {
		System.out.println("Marca: " + brand);
		System.out.println("Velocidade: " + velocity);
		if(warranty) {
			System.out.println("O Carro possui garantia.");
		} else {
			System.out.println("Carro não possui garantia.");
		}
	}
}

class Nissan extends Car {
	boolean gas;
	
	public Nissan(boolean gas, String brand, int velocity) {
		super(brand, velocity);
		this.gas = gas;
	}

	@Override
	public void showBrandAndVelocity() {
		System.out.println("Marca: " + brand);
		System.out.println("Velocidade: " + velocity);
		if(gas) {
			System.out.println("O Carro possui gasolina.");
		} else {
			System.out.println("O Tanque do Carro esta vazio.");
		}
	}
}

public class Main {
	public static void main(String[] args) {
		List<Car> cars = new ArrayList<Car>();
		cars.add(new Nissan(true, "Nissan", 330));
		cars.add(new Honda(true, "Honda", 330));
		cars.add(new Nissan(false, "Nissan", 280));
		cars.add(new Honda(false, "Honda", 250));
		
		for (Car car : cars) {
			car.showBrandAndVelocity();
		}
	}
}
