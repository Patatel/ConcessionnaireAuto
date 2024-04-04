package org.example;

import java.util.ArrayList;
import java.util.List;

public class CarList {
    private final ArrayList<Car> cars = new ArrayList<>();
    public void addList(Car car){
        cars.add(car);
    }
    public List<Car> getList(){
        return cars;
    }
    public ArrayList<Car> remove(int nb){ return remove(nb); }
    public ArrayList<Car> clear(){ return clear(); }
}
