import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Transaction {
    public static void main(String[] args) {
        ArrayList<String> customerID = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<Integer> amount = new ArrayList<>();
        ArrayList<Integer> min = new ArrayList<>();
        ArrayList<Integer> max = new ArrayList<>();
        File file = new File("input.csv");

        try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
                String[] information;
                if (line.equals(",,") || date.size() == 0) {
                    min.add(Integer.MAX_VALUE); // assign new MIN value
                    max.add(Integer.MIN_VALUE); // assign new MAX value
                    if (line.equals(",,")) line = scanner.nextLine(); // new customerID
                    information = line.split(","); // split the lines to customerID, date, amount
                    customerID.add(information[0]); // new customer
                    String MMYYYY = information[1].substring(0, information[1].indexOf("/")) + information[1].substring(information[1].length()-5);
                    date.add(MMYYYY); // create date in format MM/YYYY
                    amount.add(Integer.parseInt(information[2])); // new beginning balance
                } else {
                    information = line.split(","); // split the lines
                    amount.set(amount.size()-1, amount.get(amount.size()-1) + Integer.parseInt(information[2])); // update current balance
                }
                // seek MIN
                if (amount.get(amount.size()-1) < min.get(min.size()-1)) min.set(min.size()-1, amount.get(amount.size()-1));
                // seek MAX
                if (amount.get(amount.size()-1) > max.get(min.size()-1)) max.set(max.size()-1, amount.get(amount.size()-1));
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        System.out.printf("%-12s %-12s %-12s %-12s %-12s\n", "CustomerID", "MM/YYYY", "MinBalance", "MaxBalance", "EndingBalance");
        for (int i = 0; i < date.size(); i++) {
            System.out.printf("%-12s %-12s %-12s %-12s %-12s\n", customerID.get(i), date.get(i), min.get(i), max.get(i), amount.get(i));
        }

        try {  
            FileWriter output = new FileWriter("output.csv"); // output file csv
            output.write("CustomerID,MM/YYYY,MinBalance,MaxBalance,EndingBalance\n"); // header columns
            for (int i = 0; i < date.size(); i++) {
                output.write(customerID.get(i) + "," + date.get(i) + "," + min.get(i) + "," + max.get(i) + "," + amount.get(i) + "\n");
            } // information
            output.close();
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        } 
    }
}
