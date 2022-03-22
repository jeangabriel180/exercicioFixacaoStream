package application;

import entities.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.printf("Enter full file path: ");
        String path = sc.nextLine();
        System.out.printf("Enter salary: ");
        double salaryValue = sc.nextDouble();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            List<Employee> list = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                list.add(new Employee(parts[0], parts[1], Double.parseDouble(parts[2])));
                line = br.readLine();
            }

            List<String> emails = list.stream()
                    .filter(p -> p.getSalary() > salaryValue)
                    .map(p -> p.getEmail())
                    .sorted((o1, o2) -> o1.toUpperCase().compareTo(o2.toUpperCase()))
                    .collect(Collectors.toList());

            double sum = list.stream()
                    .filter(p -> p.getName().charAt(0) == 'M')
                    .map(p -> p.getSalary())
                    .reduce(0.0, (x, y) -> x + y);

            System.out.println("Email of people whose salary is more than " + String.format("%.2f", salaryValue));
            emails.forEach(System.out::println);
            System.out.println("Sum of salary of people whose name starts with 'M' :" + sum);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
