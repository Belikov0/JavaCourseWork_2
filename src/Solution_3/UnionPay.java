package Solution_3;

public class UnionPay extends DebitPayment{
    String unionAcct;

    UnionPay(String _unionAcct, PayTypeEnum _payType){
        this.unionAcct = _unionAcct;
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
        this.payMethod(_cost, this.payType);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
