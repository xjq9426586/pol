package interfaceoftest;

public class test {
    public static void main(String[] args) {
        Person[] p = {new X(), new Y()};
        for (Person person : p) {
            System.out.println(person.say());
        }
    }
}
