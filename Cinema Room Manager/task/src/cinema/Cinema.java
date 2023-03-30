package cinema;

import java.util.Scanner;

public class Cinema {
    private static char[][] seats;
    private static final Scanner scanner = new Scanner(System.in);
    private static int purchasedTickets;
    private static double percentage;
    private static int currentIncome;

    public static void main(String[] args) {
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scanner.nextInt();
        initialiseCinema(rows, seatsPerRow);
        showMenu();
        int menuChoice = scanner.nextInt();
        while (menuChoice != 0) {
            if (menuChoice == 1) {
                printCinema();
            }
            if (menuChoice == 2) {
                bookSeat();
            }
            if (menuChoice == 3) {
                showStatistics();
            }
            showMenu();
            menuChoice = scanner.nextInt();
        }

    }

    public static int calculateTotalIncome(int rows, int seatsPerRow) {
        int seats = calculateSeats(rows, seatsPerRow);
        if (seats <= 60) {
            return seats * 10;
        } else {
            int firstHalf = rows / 2;
            int secondHalf = rows - firstHalf;
            return (calculateSeats(firstHalf, seatsPerRow) * 10 + calculateSeats(secondHalf, seatsPerRow) * 8);
        }
    }


    private static void showStatistics() {
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        System.out.printf("Percentage: %.2f%%\n", percentage*100);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + calculateTotalIncome(seats.length - 1, seats[0].length - 1));

    }

    public static void showMenu() {
        System.out.println("""
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit""");
    }

    public static int calculateSeats(int rows, int seatsPerRow) {
        return rows * seatsPerRow;
    }

    public static int calculateSeatPrice(int rows, int seatsPerRow, int rowNo) {
        int seats = calculateSeats(rows, seatsPerRow);
        if (seats <= 60) {
            return 10;
        } else {
            if (rowNo < rows / 2) {
                return 10;
            } else return 8;
        }
    }

    public static void initialiseCinema(int rows, int seatsPerRow) {
        seats = new char[rows + 1][seatsPerRow + 1];
        purchasedTickets = 0;
        percentage = 0;
        currentIncome = 0;

        //populate number rows&columns
        seats[0][0] = ' ';
        for (int i = 1; i <= seatsPerRow; i++) {
            char c = (char) (i + '0');
            seats[0][i] = c;
        }
        for (int j = 1; j <= rows; j++) {
            char c = (char) (j + '0');
            seats[j][0] = c;
        }
        for (int i = 1; i < seats.length; i++) {
            for (int j = 1; j < seats[0].length; j++) {
                seats[i][j] = 'S';
            }
        }
    }

    public static void bookSeat() {
        System.out.println("Enter a row number:");
        int rowNo = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seatNo = scanner.nextInt();
        if (rowNo > seats.length - 1 || seatNo > seats[0].length - 1) {
            System.out.println("Wrong input!");
            bookSeat();
        }

        int seatPrice = calculateSeatPrice(seats.length, seats[0].length, rowNo);
        for (int i = 1; i < seats.length; i++) {
            for (int j = 1; j < seats[0].length; j++) {
                if (i == rowNo && j == seatNo) {
                    if (seats[i][j] == 'B') {
                        System.out.println("That ticket has already been purchased!");
                        bookSeat();
                    }
                    seats[i][j] = 'B';
                    purchasedTickets++;
                    percentage = (double) purchasedTickets / calculateSeats(seats.length - 1, seats[0].length - 1);
                    currentIncome += seatPrice;
                }
            }
        }
        System.out.println("Ticket price: $" + seatPrice);
    }

    public static void printCinema() {
        System.out.println("Cinema:");
        for (int i = 0; i < seats.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < seats[0].length; j++) {
                sb.append(seats[i][j]).append(" ");
            }
            System.out.println(sb);
        }
    }
}