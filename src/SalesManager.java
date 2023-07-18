import java.io.File;
import java.util.ArrayList;
import java.lang.String;
import java.util.HashMap;

public class SalesManager {
    public ArrayList<Sale> sales = new ArrayList<>();
    public ArrayList<Month> records = new ArrayList<>();
    public HashMap<Integer, ArrayList<Sale>> months = new HashMap<>();
    public HashMap<Integer, ArrayList<Month>> year = new HashMap<>();
    FileReader fileReader = new FileReader();


    public HashMap<Integer, ArrayList<Sale>> readMonth(ArrayList<Integer> arrayOfMonths, ArrayList<String> arrayOfFileNames) {

        for (int n = 0; n < arrayOfFileNames.size(); n++) {
            sales = new ArrayList<>();
            String content = fileReader.readFileContents(arrayOfFileNames.get(n));
            String[] lines = content.split("\r?\n"); // разделение на отдельные строки
            for (int j = 1; j < lines.length; j++) {
                String line = lines[j];
                String[] parts = line.split(","); // разделение строки на отдельные значения
                String itemName = parts[0];
                boolean isExpense = Boolean.parseBoolean(parts[1]);
                var quantity = Integer.parseInt(parts[2]);
                int unitPrice = Integer.parseInt(parts[3]);
                Sale sale = new Sale(itemName, isExpense, quantity, unitPrice);
                sales.add(sale);
            }
            months.put(arrayOfMonths.get(n), sales);

        }

        return months;
    }


    public void printMostProfitableItem(ArrayList<Sale> oneMonth) {
        int sum = 0;
        String titleOfMostProfitableItem = "";

        for (int n = 0; n < oneMonth.size(); n++) {
            if (oneMonth.get(n).isExpense == false) {
                if (titleOfMostProfitableItem.equals(oneMonth.get(n).itemName)) {
                    sum += oneMonth.get(n).unitPrice * oneMonth.get(n).quantity;
                } else if (sum < oneMonth.get(n).unitPrice * oneMonth.get(n).quantity) {
                    sum = oneMonth.get(n).unitPrice * oneMonth.get(n).quantity;
                    titleOfMostProfitableItem = oneMonth.get(n).itemName;
                }
            }
        }
        System.out.println("Самый прибыльный товар за месяц: " + titleOfMostProfitableItem + " с суммой: " + sum);
    }

    public void printBiggestExpense(ArrayList<Sale> sales) {
        int biggestExpense = 0;
        String titleOfBiggestExpense = "";

        for (int n = 0; n < sales.size(); n++) {
            if (sales.get(n).isExpense == true) {
                if (biggestExpense < sales.get(n).unitPrice * sales.get(n).quantity) {
                    biggestExpense = sales.get(n).unitPrice * sales.get(n).quantity;
                    titleOfBiggestExpense = sales.get(n).itemName;
                }
            }
        }
        System.out.println("Самая большая трата за месяц: " + biggestExpense + " по товару: " + titleOfBiggestExpense);

    }

    public ArrayList<Integer> makeListOfMonths() {
        final int srcBegin = 7;
        final int srcEnd = 8;

        File dir = new File("./resources/"); //path указывает на директорию
        ArrayList<Integer> monthLst = new ArrayList<>();

        if (dir.list().length == 0) System.out.println("Невозможно прочитать файлы с отчётами. Возможно, файлы отсутствует в нужной директории.");
        else {
            for (File file : dir.listFiles()) {
                if ((file.getName().startsWith("m.")) &&
                        (file.getName().endsWith(".csv"))) {
                    String name = file.getName().substring(srcBegin, srcEnd);
                    int nam = Integer.parseInt(name);
                    monthLst.add(nam);
                }
            }
        }
        return monthLst;
    }

    public ArrayList<String> makeArrayOfFilesMonthly() {

        File dir = new File("./resources/"); //path указывает на директорию
        ArrayList<String> monthNamesLst = new ArrayList<>();

            for (File file : dir.listFiles()) {
                if (file.exists())
                    System.out.println("Невозможно прочитать файлы с отчётами. Возможно, файлы отсутствует в нужной директории.");
                if ((file.getName().startsWith("m.")) &&
                        (file.getName().endsWith(".csv"))) {
                    String nameOfFile = file.getName();
                    monthNamesLst.add(nameOfFile);
                }

            }


        return monthNamesLst;
    }

    public ArrayList makeArrayOfFilesYearly() {
        File dir = new File("./resources/"); //path указывает на директорию
        ArrayList<String> yearLst = new ArrayList<>();
        if (dir.list().length == 0) System.out.println("Невозможно прочитать файлы с отчётами. Возможно, файлы отсутствует в нужной директории.");
        else {
            for (File file : dir.listFiles()) {
                if (file.exists())
                    System.out.println("Невозможно прочитать файлы с отчётами. Возможно, файлы отсутствует в нужной директории.");
                if ((file.getName().startsWith("y.")) &&
                        (file.getName().endsWith(".csv"))) {
                    String name = file.getName().toString();
                    yearLst.add(name);
                }
            }
        }

        return yearLst;
    }

    public HashMap<Integer, ArrayList<Month>> readYear(ArrayList<String> arrayOfYears) {

        for (int n = 0; n < arrayOfYears.size(); n++) {
            records = new ArrayList<>();
            String content = fileReader.readFileContents(arrayOfYears.get(n));
            String[] lines = content.split("\r?\n"); // разделение на отдельные строки
            for (int j = 1; j < lines.length; j++) {
                String line = lines[j];
                String[] parts = line.split(","); // разделение строки на отдельные значения
                int month = Integer.parseInt(parts[0]);
                int amount = Integer.parseInt(parts[1]);
                boolean isExpense = Boolean.parseBoolean(parts[2]);
                Month record = new Month(month, amount, isExpense);
                records.add(record);
            }
            String name = arrayOfYears.get(n).substring(2, 6);
            int nam = Integer.parseInt(name);
            year.put(nam, records);
        }


        return year;
    }

    public void printIncomeForEachMonth(ArrayList<Month> records) {
        int income;
        for (int o = 1; o <= 12; o++) {
            income = 0;
            for (Month rec : records) {
                if (rec.month == o) {
                    if (rec.isExpense == true) {
                        income -= rec.amount;
                    }
                    if (rec.isExpense == false) {
                        income += rec.amount;
                    }
                }
            }
            System.out.println("Прибыль по месяцу " + o + " составляет: " + income);
        }

    }

    public void printAverageExpensePerOperation(ArrayList<Month> records) {
        int averageExpense = 0;
        int amountOfOperations = 0;
        for (Month rec : records) {
            if (rec.isExpense == true) {
                averageExpense += rec.amount;
                amountOfOperations++;
            }
        }
        System.out.println("Средний расход за все имеющиеся операции в году составляет: " + averageExpense / amountOfOperations);
    }

    public void printAverageIncomePerOperation(ArrayList<Month> records) {
        int averageIncome = 0;
        int amountOfOperations = 0;
        for (Month rec : records) {
            if (rec.isExpense == false) {
                averageIncome += rec.amount;
                amountOfOperations++;
            }
        }
        System.out.println("Средний доход за все имеющиеся операции в году составляет: " + averageIncome / amountOfOperations);
    }

    public void verification(HashMap<Integer, ArrayList<Sale>> months, HashMap<Integer, ArrayList<Month>> year) {
        HashMap<Integer, Integer> sumOfIncomeMonthly = new HashMap<>();
        HashMap<Integer, Integer> sumOfExpenseMonthly = new HashMap<>();

        HashMap<Integer, Integer> sumOfIncomeYearly = new HashMap<>();
        HashMap<Integer, Integer> sumOfExpenseYearly = new HashMap<>();
        if (months.isEmpty()) {
            System.out.println("Сперва нужно считать отчеты за месяцы");
            return;
        }
        if (year.isEmpty()) {
            System.out.println("Сперва нужно считать отчет за год");
            return;
        }


        for (Integer monthNumber : months.keySet()) {
            int sumOfIncome = 0;
            int sumOfExpense = 0;
            int ind = Integer.parseInt(String.valueOf(monthNumber));
            for (Sale monthSales : months.get(monthNumber)) {
                if (monthSales.isExpense == false) {
                    sumOfIncome += monthSales.unitPrice * monthSales.quantity;
                    sumOfIncomeMonthly.put(ind, sumOfIncome);
                }
                if (monthSales.isExpense == true) {
                    sumOfExpense += monthSales.unitPrice * monthSales.quantity;
                    sumOfExpenseMonthly.put(ind, sumOfExpense);
                }
            }
        }

        for (Integer oneYear: year.keySet()) {
            for (Month rec : year.get(oneYear)) {
            int index = Integer.parseInt(String.valueOf(rec.month));
                    if (rec.isExpense == false) {
                    sumOfIncomeYearly.put(index, rec.amount);
                }
                if (rec.isExpense == true) {
                    sumOfExpenseYearly.put(index, rec.amount);
                }
            }

        }

        for (int res = 1; res <= 12; res++) {
            if ((sumOfIncomeYearly.get(res) != null) && (sumOfIncomeMonthly.get(res) != null)) {
                if (sumOfIncomeYearly.get(res).equals(sumOfIncomeMonthly.get(res))) {
                  } else {
                    System.out.println("Ошибка в " + res + " месяце, в доходах. ");
                }
            }
        }
        for (int res = 1; res <= 12; res++) {
            if ((sumOfExpenseYearly.get(res) != null) && (sumOfExpenseMonthly.get(res) != null)) {
                if (sumOfExpenseYearly.get(res).equals(sumOfExpenseMonthly.get(res))) {
                 } else if (sumOfExpenseYearly.get(res) != sumOfExpenseMonthly.get(res)) {
                    System.out.println("Ошибка в " + res + " месяце, в расходах.");
                }
            }
        }

    }
}




