import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;


class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SalesManager salesManager = new SalesManager();

        ArrayList<Integer> arrayOfMonths;
        ArrayList<String> arrayOfFileNames;
        ArrayList<String> arrayOfYears;

        HashMap<Integer, ArrayList<Sale>> allMonthlyReports = new HashMap<>();
        HashMap<Integer, ArrayList<Month>> yearReport = new HashMap<>();



        while (true) {
            printMenu();

            String i = scanner.nextLine();
            //String b = scanner.nextLine();

            if (i.equals("1")) {
                arrayOfMonths = salesManager.makeListOfMonths();
                arrayOfFileNames = salesManager.makeArrayOfFilesMonthly();
                allMonthlyReports = salesManager.readMonth(arrayOfMonths, arrayOfFileNames);
            } else if (i.equals("2")) {
                arrayOfYears = salesManager.makeArrayOfFilesYearly();
                yearReport = salesManager.readYear(arrayOfYears);
            } else if (i.equals("3")) {
                salesManager.verification(allMonthlyReports, yearReport);
            } else if (i.equals("4")) {
                if (allMonthlyReports.isEmpty()) {
                    System.out.println("Сперва нужно считать отчеты за месяцы");
                }
                for (Integer monthIndex : allMonthlyReports.keySet()) {
                    System.out.println("Номер месяца: " + monthIndex);
                    salesManager.printMostProfitableItem(allMonthlyReports.get(monthIndex));
                    salesManager.printBiggestExpense(allMonthlyReports.get(monthIndex));
                }
            } else if (i.equals("5")) {
                if (yearReport.isEmpty()) {
                    System.out.println("Сперва нужно считать отчет за год");
                }
                for (Integer yearIndex : yearReport.keySet()) {
                    System.out.println("Рассматриваемый год: " + yearIndex);
                    salesManager.printIncomeForEachMonth(yearReport.get(yearIndex));
                    salesManager.printAverageExpensePerOperation(yearReport.get(yearIndex));
                    salesManager.printAverageIncomePerOperation(yearReport.get(yearIndex));
                }
            } else if (i.equals("0123")) {
                System.out.println("Пока!");
                scanner.close();
                return;
            } else {
                System.out.println("Такой команды нет");
            }
        }
    }

    static void printMenu() {
        // Вывод доступных команд
        System.out.println("Что вы хотите сделать? ");
        System.out.println("1 - Считать все месячные отчеты");
        System.out.println("2 - Считать годовой отчет");
        System.out.println("3 - Сверить отчеты за месяцы и за год");
        System.out.println("4 - Вывести информацию обо всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("Чтобы завершить работу с программой нажмите: 0123");
    }

}