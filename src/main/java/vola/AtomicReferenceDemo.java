package vola;

import jdk.nashorn.internal.objects.annotations.Getter;

import java.util.concurrent.atomic.AtomicReference;


class User{
    String userName;
    int age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}

//原子引用来解决ABA问题
public class AtomicReferenceDemo {

    public static void main(String[] args) {


        User z3 = new User("z3",12);
        User l4 = new User("l4",22);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(z3);//存入主内存

        System.out.println( atomicReference.compareAndSet(z3, l4)+"\t"+atomicReference.get().toString());
        System.out.println( atomicReference.compareAndSet(z3, l4)+"\t"+atomicReference.get().toString());
    }

}
