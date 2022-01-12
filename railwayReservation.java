// Console railway ticket booking application
// All the user/admin credentials and available datas are static and resets 
// to default once the application has stopped runninng.

import java.util.*;

public class railwayReservation {

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static boolean checkString(String str) {
        return ((!str.equals(""))
                && (str != null)
                && (str.matches("^[a-zA-Z]*$")));
    }

    static boolean checkInteger(String str) {
        String regex = "[0-9]+";
        return str.matches(regex);
    }

    static HashMap<String, Integer> generateCredentials(boolean user) {
        HashMap<String, Integer> userData = new HashMap<>();
        if (user) {
            userData.put("Hario", 1234);
            userData.put("kowsi", 321);
        }
        if (!user) {
            userData.put("hario", 12345);
        }
        return userData;
    }

    static HashMap<String, List<String>> generateTrainData() {
        HashMap<String, List<String>> trainData = new HashMap<>();
        List<String> pathData = new ArrayList<>(
                Arrays.asList("chennai wakanda", "chennai coimbatore", "wakanda coimbatore"));
        List<String> expressData = new ArrayList<>(
                Arrays.asList("chennai express", "coimbatore express", "wakanda express"));
        List<String> stationData = new ArrayList<>(
                Arrays.asList("chennai central", "coimbatore central", "wakanda central"));
        for (int i = 0; i < pathData.size(); i++) {
            String[] split = pathData.get(i).split(" ");
            List<String> finalData = new ArrayList<>(Arrays.asList(split));
            finalData.add(stationData.get(i));
            trainData.put(expressData.get(i), finalData);
        }
        return trainData;
    }

    static List<String> getNames(String parameters) {
        Scanner sc = new Scanner(System.in);
        List<String> data = new ArrayList<>();
        clearScreen();
        while (true) {
            try {
                System.out.printf("%s LOGIN\n", parameters.toUpperCase());
                System.out.println("Enter your name : ");
                String name = sc.nextLine();
                System.out.println("Enter your password : ");
                String pass = sc.nextLine();
                if (checkInteger(pass) && checkString(name)) {
                    data.add(name);
                    data.add(pass);
                    // System.out.println(data);
                    return data;
                } else {
                    clearScreen();
                    System.out.println("Invalid Entry");
                }
            } catch (Exception e) {
                clearScreen();
                System.err.println(e);
            }
        }
    }

    static boolean validateUser(HashMap<String, Integer> db, String name, int password) {
        for (Map.Entry<String, Integer> entry : db.entrySet()) {
            if (entry.getKey().equals(name) && (entry.getValue() == password)) {
                return true;
            }
        }
        return false;
    }

    static void printRoutes(HashMap<String, List<String>> data) {
        int i = 0;
        System.out.printf("%5s %15s %26s %18s\n", "SNo", "Name", "Start Point", "End Point", "Station");
        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            System.out.printf("%4s %20s %20s %20s\n", i, entry.getKey(), entry.getValue().get(0),
                    entry.getValue().get(1), entry.getValue().get(2));
            i++;
        }
    }

    static List<String> getRouteDetails() {
        Scanner sc = new Scanner(System.in);
        List<String> details = new ArrayList<>();
        while (true) {
            try {
                System.out.println("Enter name of the express : ");
                String name = sc.nextLine();
                details.add(name + " " + "express");
                System.out.println("Enter the start and stop destination(Separate using spaces) : ");
                String dest = sc.nextLine();
                String[] split = dest.split(" ");
                if (split[0].equals(split[1])) {
                    clearScreen();
                    System.out.println("Source and destination can't be same");
                    continue;
                } else {
                    try {
                        details.add(split[0]);
                        details.add(split[1]);
                    } catch (Exception e) {
                        clearScreen();
                        System.out.println("Please match the requested format");
                        continue;
                    }
                }
                System.out.println("Enter station name");
                String trainName = sc.nextLine();
                details.add(trainName + " " + "central");
                return details;
            } catch (Exception e) {
                clearScreen();
                System.err.println(e);
            }
        }
    }

    static HashMap<String, List<String>> deleteData(HashMap<String, List<String>> data) {
        Scanner sc = new Scanner(System.in);
        // while (true){
        clearScreen();
        System.out.println("Enter the name of the train you want to delete : ");
        String name = sc.nextLine() + " " + "express";
        try {
            data.remove(name);
            System.out.printf("Removed train %s\n", name);
        } catch (Exception e) {
            clearScreen();
            System.out.println("No such name");
        }
        return data;
        // }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        clearScreen();
        HashMap<String, Integer> userCredentials = generateCredentials(true);
        HashMap<String, Integer> adminCredentials = generateCredentials(false);
        HashMap<String, List<String>> trainData = generateTrainData();
        // List<String> loginData = new ArrayList<>();
        System.out.println("Welcome to Railway Reservation System");
        // System.out.println(trainData);
        while (true) {
            int choice;
            while (true) {
                try {
                    System.out.println("Home Screen");
                    System.out.printf("1. Admin Login\n2. User Login\n3. Exit\n");
                    choice = sc.nextInt();
                    if (choice > 3) {
                        clearScreen();
                        System.out.println("Invalid Option");
                    } else {
                        clearScreen();
                        break;
                    }
                } catch (Exception e) {
                    clearScreen();
                    System.err.println("Invalid entry");
                }
            }

            if (choice == 1) {
                clearScreen();
                List<String> data = getNames("Admin");
                String userName = data.get(0);
                int userPassword = Integer.parseInt(data.get(1));
                if (validateUser(adminCredentials, userName, userPassword)) {
                    clearScreen();
                    int adminChoice;
                    while (true) {
                        System.out.printf("Welcome Admin %s\n", userName);
                        while (true) {
                            try {
                                System.out.println("1. Add Routes\n2. View Routes\n3. Remove Routes\n4. Exit");
                                adminChoice = sc.nextInt();
                                if (adminChoice > 4) {
                                    clearScreen();
                                    System.out.println("Invalid Option");
                                } else {
                                    clearScreen();
                                    break;
                                }
                            } catch (Exception e) {
                                clearScreen();
                                System.err.println("Inalid input");
                                continue;
                            }
                        }
                        if (adminChoice == 4) {
                            clearScreen();
                            break;
                        }

                        if (adminChoice == 1) {
                            clearScreen();
                            List<String> routeDetails = getRouteDetails();
                            System.out.println(routeDetails);
                            List<String> keyData = new ArrayList<>(
                                    Arrays.asList(routeDetails.get(1), routeDetails.get(2), routeDetails.get(3)));
                            printRoutes(trainData);
                            trainData.put(routeDetails.get(0), keyData);
                            printRoutes(trainData);
                        }

                        if (adminChoice == 2) {
                            clearScreen();
                            printRoutes(trainData);

                        }

                        if (adminChoice == 3) {
                            clearScreen();
                            trainData = deleteData(trainData);
                        }
                    }
                } else {
                    clearScreen();
                    System.out.println("Invalid Credentials");
                }
            }
            if (choice == 3) {
                clearScreen();
                break;
            }

            if (choice == 2) {
                clearScreen();
                List<String> data = getNames("User");
                String userName = data.get(0);
                int userPassword = Integer.parseInt(data.get(1));
                if (validateUser(userCredentials, userName, userPassword)) {
                    clearScreen();
                    System.out.printf("Welcome User %s", userName);
                } else {
                    clearScreen();
                    System.out.println("Invalid Credentials");
                }
            }

            sc.close();
        }
    }
}
