// Name:- Bhaskar Ranjan , ERP Number:- RU-25-10383
import java.util.*;
import java.io.*;

class Student {
    String name, grade;
    int m1, m2, m3, total;
    double percentage;

    Student(String name, int a, int b, int c) {
        this.name = name;
        m1 = a;
        m2 = b;
        m3 = c;

        total = a + b + c;
        percentage = total / 3.0;

        if (percentage >= 90) grade = "A+";
        else if (percentage >= 75) grade = "A";
        else if (percentage >= 60) grade = "B";
        else if (percentage >= 40) grade = "C";
        else grade = "F";
    }
}

public class ResultAnalysisSystem {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Student> list = new ArrayList<>();
    static HashMap<String, Student> map = new HashMap<>();

    public static void main(String[] args) {

        load();

        while (true) {
            System.out.println("\n1.Add Result");
            System.out.println("2.Search Student");
            System.out.println("3.Rank List");
            System.out.println("4.Topper & Average");
            System.out.println("5.Exit");
            System.out.println("Enter the number: ");

            int ch = Integer.parseInt(sc.nextLine());

            switch (ch) {
                case 1: add(); break;
                case 2: search(); break;
                case 3: rank(); break;
                case 4: topper(); break;
                case 5: save(); return;
                default: System.out.println("Invalid choice");
            }
        }
    }

    // Add student
    static void add() {
        System.out.print("Enter name: ");
        String name = sc.nextLine().trim();
        String key = name.toLowerCase(Locale.ROOT);

        if (name.isEmpty() || name.contains("|") || map.containsKey(key)) {
            System.out.println("Invalid or duplicate name");
            return;
        }

        System.out.print("Enter 3 marks: ");
        String[] marks = sc.nextLine().trim().split("\\s+");

        if (marks.length != 3) {
            System.out.println("Enter exactly 3 marks");
            return;
        }

        try {
            int a = Integer.parseInt(marks[0]);
            int b = Integer.parseInt(marks[1]);
            int c = Integer.parseInt(marks[2]);

            if (a < 0 || a > 100 || b < 0 || b > 100 ||
                c < 0 || c > 100) {
                System.out.println("Invalid marks. Enter 0-100");
                return;
            }

            Student s = new Student(name, a, b, c);
            list.add(s);
            map.put(key, s);

            System.out.printf("Result added: %s | Total: %d | %.1f%% | Grade %s%n",
                    name, s.total, s.percentage, s.grade);

        } catch (NumberFormatException e) {
            System.out.println("Enter valid numbers");
        }
    }

    // Search student
    static void search() {
        System.out.print("Enter name: ");
        Student s = map.get(sc.nextLine().trim().toLowerCase(Locale.ROOT));

        if (s == null)
            System.out.println("Student not found");
        else
            System.out.printf("%s | Total: %d | %.1f%% | Grade %s%n",
                    s.name, s.total, s.percentage, s.grade);
    }

    // Rank list
    static void rank() {
        ArrayList<Student> temp = new ArrayList<>(list);

        temp.sort((a, b) -> Integer.compare(b.total, a.total));

        int i = 1;
        for (Student s : temp) {
            System.out.println(i++ + ". " + s.name + " | " +
                    s.total + " | " + s.percentage + "%");
        }
    }

    // Topper and average
    static void topper() {
        if (list.isEmpty()) {
            System.out.println("No records found");
            return;
        }

        Student top = list.get(0);
        double sum = 0;

        for (Student s : list) {
            if (s.total > top.total)
                top = s;

            sum += s.percentage;
        }

        System.out.println("Topper: " + top.name + " (" + top.total + ")");
        System.out.printf("Class average: %.1f%%%n", sum / list.size());
    }

    // Save data
    static void save() {
        try (PrintWriter pw = new PrintWriter("results.txt")) {
            for (Student s : list)
                pw.println(s.name + "|" + s.m1 + "|" + s.m2 + "|" + s.m3);

            System.out.println("Data saved successfully");
        } catch (IOException e) {
            System.out.println("File error");
        }
    }

    // Load data
    static void load() {
        try (Scanner file = new Scanner(new File("results.txt"))) {
            while (file.hasNextLine()) {
                String[] d = file.nextLine().split("\\|");

                if (d.length != 4) continue;

                try {
                    String key = d[0].toLowerCase(Locale.ROOT);
                    int a = Integer.parseInt(d[1]);
                    int b = Integer.parseInt(d[2]);
                    int c = Integer.parseInt(d[3]);

                    if (d[0].isEmpty() || map.containsKey(key) ||
                        a < 0 || a > 100 || b < 0 || b > 100 ||
                        c < 0 || c > 100) continue;

                    Student s = new Student(d[0], a, b, c);
                    list.add(s);
                    map.put(key, s);
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous records found");
        }
    }
}

