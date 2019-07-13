package vola;

class MySort{
    int  a = 0;
    boolean flag = false;


    public void method01(){
        a = 1; //语句一
        flag = true;

    }

//多线程环境中线程交替执行，由于编译器优化重排的存在
//两个多线程中使用的变量能否保证一致性无法确定的，结果无法预测

    public void method02(){
        if (flag){
            a = a + 5;
            System.out.println("输出的值为:"+a);
        }
    }
}


public class ReSortSeDemo {

    public static void main(String[] args) {

        MySort mySort = new MySort();
        mySort.method01();
        mySort.method02();

        System.out.println("输出的值为:"+mySort.flag);
    }

}
