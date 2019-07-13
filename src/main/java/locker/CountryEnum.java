package locker;

/*
 * 枚举jsk1.5出现的新特性，它可以作为数据库来使用。
 * 枚举就是数据版的Mysql数据库
 *
 */
public enum CountryEnum {

    ONE(1,"齐"),TWO(2,"楚"),THREE(3,"燕"),FOUR(4,"赵"),FIVE(5,"魏"),SIX(6,"韩");

    private Integer retCode;
    private String retMessage;

    public Integer getRetCode() {
        return retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    CountryEnum(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public static CountryEnum foreach_CountryEnum(int index){
        CountryEnum[] myArray = CountryEnum.values();
        for (CountryEnum element : myArray){
            if (index == element.getRetCode()){
                return element;
            }
        }

        return null;
    }


}

/*mysql dbName(数据库名)  = CountryEnum
table(表名) one
ID userName age
1   齐      12

ONE(1,"齐",v2,v3,v4,v5)*/