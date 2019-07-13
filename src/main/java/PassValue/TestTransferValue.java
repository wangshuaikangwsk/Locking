package PassValue;

public class TestTransferValue {
    public void changValue1(int age){
        age = 30;
    }

    public void changValue2(Person person){
        person.setPersonName("xxx");
    }

    public void changValue3(String str){
        str = "xxx";
    }


    public static void main(String[] args) {
        TestTransferValue test = new TestTransferValue();
        int age = 20;
        System.out.println("age----"+age);
        test.changValue1(age);

        Person person = new Person("abc");
        test.changValue2(person);
        System.out.println("person----"+person.getPersonName());

        String str = "abc";
        test.changValue3(str);
        System.out.println("String----"+str);

    }
}
