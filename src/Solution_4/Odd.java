package Solution_4;

import java.util.Objects;

public class Odd implements Number{
    int num;

    Odd(){}
    Odd(int _num){
        try {
            if (_num%2 == 0)
                throw new AssertionError("Need an odd number but was: " + _num);
            setNum(_num);
        }catch (AssertionError e){
            e.printStackTrace();
        }
    }

    //Get method
    public int getNum(){
        return this.num;
    }

    //Set method
    public void setNum(int _num){
        this.num = _num;
    }

    @Override   //Return even number or odd number in accordance with the addend
    public Number add(Number _n){
        int res = this.num + _n.getNum();
        //Get the class name of the addend

        String className = _n.getClass().toString().split("\\.")[1];
        //Conditional return
        return switch (className){
            case "Odd" -> new Even(res);
            case "Even" -> new Odd(res);
            default -> null;
        };
    }

    @Override   //Return even number or odd number in accordance with the multiplicand
    public Number multiply(Number _n){
        int res = this.getNum() * _n.getNum();
        //Get the class name of the multiplicand
        String className = _n.getClass().toString().split("\\.")[1];
        //Conditional return
        return switch (className){
            case "Odd" -> new Odd(res);
            case "Even" -> new Even(res);
            default -> null;
        };
    }

    @Override
    public String getClassName() {
        return "Odd";
    }

    @Override   //Override toString()
    public String toString(){
        return new String("(odd)" + num);
    }

    @Override   //Override equals(), the repeating element is judged by method equals()
    public boolean equals(Object _obj){
        if (_obj == this)
            return true;
        if (_obj == null || this.getClass() != _obj.getClass())
            return false;
        Odd _odd = (Odd)_obj;
        return this.num == _odd.num;
    }

    @Override   //Override hashCode()
    public int hashCode(){
        return Objects.hash(num);
    }
}
