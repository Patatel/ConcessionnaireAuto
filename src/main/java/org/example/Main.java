package org.example;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        int choice;
        String file = "C:\\Users\\Léa\\Documents\\Java\\ConcessionnaireAuto\\data.txt";
        Interface inter = new DBimpl();
        CarList cars = new CarList();

        cars = inter.readFile(file);
        //Affiche la liste des voitures récupérées
        System.out.println("Liste des voitures récupérées");
        for (Car car : cars.getList()){
            car.display();
        }

        cars = inter.Less20y(cars);
        //Affiche la liste des voitures de moin de 20ans
        System.out.println("Liste des voitures de moins de 20ans");
        for (Car car : cars.getList()){
            car.display();
        }

        //Ajout des voitures de moin de 20ans dans la base
        inter.saveInDB(cars);

        do {
            System.out.println("Menu :");
            System.out.println("1 - Afficher les véhicules");
            System.out.println("2 - Modifier un véhicule");
            System.out.println("3 - Ajouter un véhicule");
            System.out.println("4 - Supprimer un véhicule");
            System.out.println("5 - Afficher les véhicules d'un age donné");
            System.out.println("6 - Afficher les véhicules dont le prix est comprise entre deux prix");
            System.out.println("7 - Quitter");

            // Création d'un objet Scanner pour lire l'entrée utilisateur
            Scanner mainScan = new Scanner(System.in);
            // Demander à l'utilisateur de choisir son action
            System.out.print("Donner le numéro de l'action a effectuer :");
            choice = Integer.parseInt(mainScan.nextLine());

            //Afficher touts les véhicules
            if (choice == 1){
                inter.log("Got list of all car");
                cars = inter.getAllCar();
                //Affiche la liste des voitures
                System.out.println("Liste des voitures");
                for (Car car : cars.getList()){
                    car.display();
                }

                //Modifier un véhicule
            }else if (choice == 2) {
                String imc;
                String price;
                inter.log("Updated a car");

                cars = inter.getAllCar();
                //Affiche la liste des voitures
                System.out.println("Liste des voitures");
                for (Car car : cars.getList()){
                    car.display();
                }

                // Demander à l'utilisateur de choisir l'imc à modifier
                System.out.print("Donner l'imatriculation :");
                imc = mainScan.nextLine();
                // Demander à l'utilisateur de choisir l'imc à modifier
                System.out.print("Donner le nouveau prix :");
                price = mainScan.nextLine();

                inter.updateCar(imc,price);

                //Ajouter un véhicule
            }else if (choice == 3) {
                CarList newCar = new CarList();
                String imc;
                String brand;
                String model;
                int price;
                inter.log("Added a car");

                // Demander à l'utilisateur de choisir l'imc
                System.out.print("Donner l'imatriculation :");
                imc = mainScan.nextLine();
                // Demander à l'utilisateur de choisir la marque
                System.out.print("Donner la marque :");
                brand = mainScan.nextLine();
                // Demander à l'utilisateur de choisir le modele
                System.out.print("Donner le modele :");
                model = mainScan.nextLine();
                // Demander à l'utilisateur de choisir le prix
                System.out.print("Donner le prix :");
                price = Integer.parseInt(mainScan.nextLine());

                // Obtenir la date actuelle
                LocalDate currentDate = LocalDate.now();
                String ddt = ""+currentDate;
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;
                try {
                    date = formatter.parse(ddt);
                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                }

                Car car  = new Car(brand,model,imc,date,price);
                newCar.addList(car);

                inter.saveInDB(newCar);

                //Supprimer un véhicule
            }else if (choice == 4) {
                inter.log("Deleted a car");
                String imc;

                cars = inter.getAllCar();
                //Affiche la liste des voitures
                System.out.println("Liste des voitures");
                for (Car car : cars.getList()){
                    car.display();
                }

                // Demander à l'utilisateur de choisir l'imc à supprimer
                System.out.print("Donner l'imatriculation :");
                imc = mainScan.nextLine();

                //Afficher selon l'age
            }else if (choice == 5) {
                int age;

                // Demander à l'utilisateur de choisir l'age'
                System.out.print("Donner l'age :");
                age = Integer.parseInt(mainScan.nextLine());

                inter.log("Got list of "+age+"yo car");
                cars = inter.getAllCar();

                // Obtenir la date actuelle
                LocalDate currentDate = LocalDate.now();
                // Récupérer l'année actuelle à partir de la date actuelle
                int currentYear = currentDate.getYear();

                for (Car car : cars.getList()) {
                    if (currentYear - (car.getDate().getYear()+1900) >= age) {
                        car.display();
                    }
                }

                //Afficher entre deux prix
            }else if (choice == 6) {
                int minPrice;
                int maxPrice;

                // Demander à l'utilisateur de choisir les prix
                System.out.print("Donner le prix minimum :");
                minPrice = Integer.parseInt(mainScan.nextLine());
                System.out.print("Donner le prix maximum :");
                maxPrice = Integer.parseInt(mainScan.nextLine());

                inter.log("Got list of price car between "+minPrice+" and "+maxPrice);
                cars = inter.getAllCar();
                for (Car car : cars.getList()) {
                    if (car.getPrice() >= minPrice | car.getPrice() <= maxPrice) {
                        car.display();
                    }
                }

                //Quitter
            }else if (choice == 7) {
                System.exit(0);
            }else{
                System.out.println("Le numéro d'action n'existe pas");
            }
        }while (choice != 7);
    }
}