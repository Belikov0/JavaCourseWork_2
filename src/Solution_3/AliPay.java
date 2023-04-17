package Solution_3;


public class AliPay extends DebitPayment{
    String aliAcct;
    AliPay(String _aliAcct, PayTypeEnum _payType){
        this.aliAcct = _aliAcct;
        this.payType = _payType;
    }

    @Override
    void payMethod(double _cost, PayTypeEnum _payType){
        //Get the class name as the
        String[] buffer = this.getClass().toString().split("\\.");
        System.out.println(buffer[1]+" pays: "+_cost);
        super.payMethod(_cost, _payType);

    }

    @Override
    void payMethod(double _cost){
        this.payMethod(_cost, this.payType);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
