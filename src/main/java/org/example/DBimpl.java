package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class DBimpl implements Interface {

    @Override
    public CarList readFile(String nomFichier) throws ClassNotFoundException, SQLException {
        CarList vehicules = new CarList();
        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String immatriculation = parts[0].trim();
                    String marque = parts[1].trim();
                    String modele = parts[2].trim();
                    String annee = parts[3].trim();
                    int prix = Integer.parseInt(parts[4].trim());

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = null;
                    try {
                        date = formatter.parse(annee);
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                    Car car = new Car(marque, modele, immatriculation, date, prix);
                    vehicules.addList(car);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehicules;
    }

    @Override
    public CarList Less20y(CarList cars) throws ClassNotFoundException, SQLException {
        CarList less = new CarList();

        // Obtenir la date actuelle
        LocalDate currentDate = LocalDate.now();
        // Récupérer l'année actuelle à partir de la date actuelle
        int currentYear = currentDate.getYear();

        for (Car car : cars.getList()) {
            if (currentYear - (car.getDate().getYear()+1900) >= 20) {
                less.addList(car);
            }
        }
        return less;
    }

    @Override
    public void saveInDB(CarList cars) throws ClassNotFoundException, SQLException {
        // Obtenir la configuration de la base de données
        DBconfig config = new DBconfig();
        // Obtenir une connexion à la base de données
        Connection connection = config.getConnection();

        String sql = "INSERT INTO car (registration_number, brand, model, date_of_first_registration, price) VALUES (?, ?, ?, ?, ?)";
        for (Car car : cars.getList()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, car.getImc());
            pstmt.setString(2, car.getBrand());
            pstmt.setString(3, car.getModel());

            //Conversion de la date
            Date date = car.getDate();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            pstmt.setDate(4, sqlDate);
            pstmt.setInt(5, car.getPrice());
            pstmt.executeUpdate();
        }
    }

    @Override
    public CarList getAllCar() throws ClassNotFoundException, SQLException {
        CarList all = new CarList();

        // Obtenir la configuration de la base de données
        DBconfig config = new DBconfig();
        // Obtenir une connexion à la base de données
        Connection connection = config.getConnection();

        PreparedStatement sql = connection.prepareStatement("SELECT * from car");
        ResultSet resultSet = sql.executeQuery();

        while (resultSet.next()) {
            String immatriculation = resultSet.getString("registration_number");
            String marque = resultSet.getString("brand");
            String modele = resultSet.getString("model");
            String annee = resultSet.getString("date_of_first_registration");
            int prix = resultSet.getInt("price");

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            try {
                date = formatter.parse(annee);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
            Car car = new Car(marque, modele, immatriculation, date, prix);
            all.addList(car);
        }

        // Fermer les ressources pour éviter les fuites de mémoire
        resultSet.close();
        sql.close();
        connection.close();

        return all;
    }

    @Override
    public void log(String log) throws ClassNotFoundException, SQLException {
        BufferedWriter writer = null;

        try {
            String filePath = "C:\\Users\\Léa\\Documents\\Java\\ConcessionnaireAuto\\log.txt";

            writer = new BufferedWriter(new FileWriter(filePath,true));
            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            writer.write(""+log+" "+date+"\n");
            writer.flush();
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (writer != null){
                    writer.close();

                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateCar(String imc, String price) throws ClassNotFoundException, SQLException {
        // Obtenir la configuration de la base de données
        DBconfig config = new DBconfig();
        // Obtenir une connexion à la base de données
        Connection connection = config.getConnection();

        // Requête SQL pour mettre à jour les détails de l'étudiant
        String updateQuery = "UPDATE car SET price = ? WHERE registration_number = ?";
        // Préparer une déclaration pour la mise à jour
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        // Attribuer les nouvelles valeurs au prénom et au nom de l'étudiant ainsi que son identifiant à la déclaration préparée
        preparedStatement.setString(1, price);
        preparedStatement.setString(2, imc);
        // Exécuter la mise à jour de la base de données
        preparedStatement.executeUpdate();

        // Fermer la déclaration et la connexion pour éviter les fuites de mémoire
        preparedStatement.close();
        connection.close();
    }

    @Override
    public void deleteCar(String imc) throws ClassNotFoundException, SQLException {
        // Obtenir la configuration de la base de données
        DBconfig config = new DBconfig();
        // Obtenir une connexion à la base de données
        Connection connection = config.getConnection();

        // Requête SQL pour supprimer un étudiant en fonction de son identifiant
        String deletionQuery = "DELETE FROM car WHERE registration_number = ?";
        // Préparer une déclaration pour la suppression
        PreparedStatement preparedStatement = connection.prepareStatement(deletionQuery);
        // Attribuer l'identifiant de l'étudiant à supprimer à la déclaration préparée
        preparedStatement.setString(1, imc);
        // Exécuter la mise à jour de la base de données
        preparedStatement.executeUpdate();

        // Fermer la déclaration et la connexion pour éviter les fuites de mémoire
        preparedStatement.close();
        connection.close();
    }
}
