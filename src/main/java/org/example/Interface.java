package org.example;

import java.sql.SQLException;

public interface Interface {
    CarList readFile(String nomFichier) throws ClassNotFoundException, SQLException;

    CarList Less20y(CarList cars) throws ClassNotFoundException, SQLException;

    void saveInDB(CarList cars) throws ClassNotFoundException, SQLException;

    CarList getAllCar() throws ClassNotFoundException, SQLException;

    void log(String log) throws ClassNotFoundException, SQLException;

    void updateCar(String imc, String price) throws ClassNotFoundException, SQLException;

    void deleteCar(String imc) throws ClassNotFoundException, SQLException;
}
