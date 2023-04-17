package Solution_3;

public class JDPay extends DebitPayment{
    String jdAcct;
    JDPay(String _jdAcct, PayTypeEnum _payType){
        this.jdAcct = _jdAcct;
        this.payType = _payType;
    }

    @Override
    void payMethod(double _cost, PayTypeEnum _payType){
        String[] buffer = this.getClass().toString().split("\\.");
        System.out.println(buffer[1]+" pays: "+_cost);
        super.payMethod(_cost, _payType);
    }

    @Override
    void payMethod(double _cost){
        payMethod(_cost, this.payType);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
