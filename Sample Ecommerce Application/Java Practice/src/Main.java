import java.util.*;

class A {
    public void display() {
        System.out.println("From A");
    }

    public void prin() {
        System.out.println("Printing from A");
    }
}

class B extends A {
    public void display() {
        System.out.println("From B");
    }

    public void check() {
        System.out.println("Printing from B");
    }
}


record alien(int id, String name) {
}

public class Main {
    public static void main(String[] args) {

        A a = new A();
//
//        a.display();
//        a.prin();
//        a.check();

        List<Integer> lst = new ArrayList<>(Arrays.asList(1, 3, 0, 2));

        class Student implements  Comparable<Student>{
            int age;
            String name;

            Student(int age, String name) {
                this.age = age;
                this.name = name;
            }

            @Override
            public String toString() {
                return "Name : " + this.name + "Age :" + this.age;
            }

            @Override
            public int compareTo(Student o) {
                return this.name.length()>o.name.length()?1:-1;
            }
        }

        List<Student> std = new ArrayList<Student>();
        std.add(new Student(11, "rama"));
        std.add(new Student(10, "Dhoni"));
        std.add(new Student(29, "Jon"));


        Collections.sort(std);

        System.out.println(std.toString());

    }
}