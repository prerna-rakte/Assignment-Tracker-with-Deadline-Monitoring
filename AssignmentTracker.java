import java.util.*;
class Assignment {
    String subject;
    int day, month, year;
    String status;

    Assignment(String subject, int day, int month, int year) {
        this.subject = subject;
        this.day = day;
        this.month = month;
        this.year = year;
        this.status = "Pending";
    }

    int totalDays() {
        return year * 365 + month * 30 + day;
    }

    int getRemainingDays() {
        Calendar cal = Calendar.getInstance();

        int cDay = cal.get(Calendar.DAY_OF_MONTH);
        int cMonth = cal.get(Calendar.MONTH) + 1;
        int cYear = cal.get(Calendar.YEAR);

        int today = cYear * 365 + cMonth * 30 + cDay;

        return totalDays() - today;
    }

    static boolean isValidDate(int d, int m, int y) {
        if (y < 2020 || m < 1 || m > 12 || d < 1) return false;

        int[] daysInMonth = {31,28,31,30,31,30,31,31,30,31,30,31};

        // Leap year check
        if ((y % 4 == 0 && y % 100 != 0) || (y % 400 == 0)) {
            daysInMonth[1] = 29;
        }

        return d <= daysInMonth[m - 1];
    }
}

class AssignmentTracker {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Assignment> list = new ArrayList<>();
        int choice = 0;

        do {
            try {
                System.out.println("\n===== ASSIGNMENT TRACKER =====");
                System.out.println("1. Add Assignment");
                System.out.println("2. View Assignments");
                System.out.println("3. Mark Completed");
                System.out.println("4. Exit");
                System.out.print("Enter choice: ");

                choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        sc.nextLine();

                        System.out.print("Enter Subject: ");
                        String subject = sc.nextLine();

                        int d = 0, m = 0, y = 0;

                        while (true) {
                            try {
                                System.out.print("Enter Deadline (DD MM YYYY): ");
                                d = sc.nextInt();
                                m = sc.nextInt();
                                y = sc.nextInt();

                                if (Assignment.isValidDate(d, m, y)) {
                                    break;
                                } else {
                                    System.out.println("Invalid date! Enter correct date.");
                                }

                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input! Please enter numbers only.");
                                sc.nextLine(); // clear buffer
                            }
                        }

                        list.add(new Assignment(subject, d, m, y));
                        System.out.println("Assignment added successfully!");
                        break;

                    case 2:
                        if (list.isEmpty()) {
                            System.out.println("No assignments found");
                        } else {
                            for (int i = 0; i < list.size(); i++) {
                                Assignment a = list.get(i);
                                int days = a.getRemainingDays();

                                System.out.println("\nAssignment #" + (i + 1));
                                System.out.println("Subject: " + a.subject);
                                System.out.println("Deadline: " + a.day + "-" + a.month + "-" + a.year);
                                System.out.println("Status: " + a.status);
                                System.out.println("Remaining Days: " + days);

                                if (a.status.equals("Completed")) {
                                    System.out.println("Great job! Completed!");
                                } else {
                                    if (days < 0) {
                                        System.out.println("Deadline passed!");
                                    } else if (days == 0) {
                                        System.out.println("Due today!");
                                    } else if (days <= 2) {
                                        System.out.println("Very close deadline!");
                                    } else {
                                        System.out.println("Enough time");
                                    }
                                }
                            }
                        }
                        break;

                    case 3:
                        try {
                            System.out.print("Enter assignment number: ");
                            int num = sc.nextInt();

                            if (num > 0 && num <= list.size()) {
                                list.get(num - 1).status = "Completed";
                                System.out.println("Marked as completed!");
                            } else {
                                System.out.println("Invalid assignment number");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Enter a valid number!");
                            sc.nextLine();
                        }
                        break;

                    case 4:
                        System.out.println("Exiting program... Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again");
                }

            } catch (InputMismatchException e) {
                System.out.println("Wrong input! Please enter numbers only.");
                sc.nextLine(); // clear invalid input
            }

        } while (choice != 4);

        sc.close();
    }
}