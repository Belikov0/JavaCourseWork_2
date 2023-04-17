package Solution_4;

import java.util.Objects;

public class Even implements Number{
    public int num;

    Even(){}
    Even(int _num){
        try {
            if (_num%2 != 0)
                throw new AssertionError("Need an even number but was: " + _num);
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
        String className = _n.getClass().toString().split("\\.")[1];
        return switch (className){
            case "Odd" -> new Odd(res);
            case "Even" -> new Even(res);
            default -> null;
        };
    }

    @Override   //Even number multiplies another even number, result is absolutely even
    public Number multiply(Number _n){
        int res = this.getNum() * _n.getNum();
        return new Even(res);
    }

    @Override
    public String getClassName() {
        return "Even";
    }

    @Override   //Override toString()
    public String toString(){
        return new String("(even)" + num);
    }

    @Override   //Override equals(), the repeating element is judged by method equals()
    public boolean equals(Object _obj){
        if (_obj == this)
            return true;
        if (_obj == null || this.getClass() != _obj.getClass())
            return false;
        Even _even = (Even)_obj;
        return this.num == _even.num;
    }

    @Override   //Override hashCode()
    public int hashCode(){
        return Objects.hash(num);
    }
}
