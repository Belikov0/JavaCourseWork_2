package Solution_3;

public class DebitPayment {
    public enum PayTypeEnum {BANK, CREDIT, BANK_FIRST, CREDIT_FIRST};

    //Use static variables to store public account message
    //All ways of  payment can access the public data when they call payMethod
    static String bankAcct;        //   Bank account
    static double bankBalance;      //  Bank balance
    static String creditCardNum;    //  Credit card number
    static double creditLimit;      //  Credit Limit
    PayTypeEnum payType;            //  the type of payment

    //Constructors
    DebitPayment(){}
    DebitPayment(String _bankAcct, double _bankBalance, String _creditCardNum, double _creditLimit){
        bankAcct = _bankAcct;
        bankBalance = _bankBalance;
        creditCardNum = _creditCardNum;
        creditLimit = _creditLimit;
    }

    //For user to set a new type of payment of a way of payment, four subclasses can inherit the method
    void setPayType(PayTypeEnum _payType){
        this.payType = _payType;
    }

    //Overrided by subclasses, pay by designated way of payment
    void payMethod(double _cost, PayTypeEnum _payType){
        String beforeStr = this.toString(); //Store the payment message before payment
        //Get the way of payment by class name using polymorphism

        //Get the final type of payment
        PayTypeEnum finalPayType = switch (_payType){
            case BANK -> payByBank(_cost);
            case CREDIT -> payByCredit(_cost);
            case BANK_FIRST -> payByBankFirst(_cost);
            case CREDIT_FIRST -> payByCreditFirst(_cost);
            default -> _payType;
        };

        //When payment failed
        if (finalPayType == null){
            return;
        }

        //Print message of the payment
        System.out.println("Pay Type: " + finalPayType.toString());
        System.out.println("Before consumption: ");
        System.out.println(beforeStr);
        System.out.println("After consumption: ");
        System.out.println(this.toString() + "\n");

    }

    //Overrided by subclasses, pay by default way of payment
    void payMethod(double _cost){
        payMethod(_cost, this.payType);
    }

    //Four concrete ways of payment
    //The type of bank
    PayTypeEnum payByBank(double _cost){
        try {
            if (_cost > DebitPayment.bankBalance){
                throw new BalanceSufficientException();
            }
            bankBalance -= _cost;
        }catch (BalanceSufficientException e){
            e.bankBalanceSufficientException(bankBalance, _cost);
        }
        return PayTypeEnum.BANK;
    }

    //The type of credit
    PayTypeEnum payByCredit(double _cost){
        try {
            if (_cost > DebitPayment.creditLimit){
                throw new BalanceSufficientException();
            }
            creditLimit -= _cost;
        }catch (BalanceSufficientException e){
            e.creditLimitSufficientException(creditLimit, _cost);
        }

        return PayTypeEnum.CREDIT;
    }

    //The type of bank first
    PayTypeEnum payByBankFirst(double _cost){
        PayTypeEnum finalPayType = null;
        try {
            if (_cost <= bankBalance){
                bankBalance -= _cost;
                finalPayType = PayTypeEnum.BANK;
            }else if(_cost <= creditLimit){
                creditLimit -= _cost;
                finalPayType = PayTypeEnum.CREDIT;
            }else{
                throw new BalanceSufficientException();
            }
        }catch (BalanceSufficientException e){
            e.bothSufficientException(bankBalance, creditLimit, _cost);
        }
        return finalPayType;
    }

    //The type of credit first
    PayTypeEnum payByCreditFirst(double _cost){
        PayTypeEnum finalPayType = null;
        try {
            if (_cost <= creditLimit){
                creditLimit -= _cost;
                finalPayType = PayTypeEnum.CREDIT;
            }else if(_cost <= bankBalance){
                bankBalance -= _cost;
                finalPayType = PayTypeEnum.BANK;
            }else{
                throw new BalanceSufficientException();
            }
        }catch (BalanceSufficientException e){
            e.bothSufficientException(bankBalance, creditLimit, _cost);
        }
        return finalPayType;
    }

    @Override
    public String toString(){
        return new String("\tBank Balance: "+bankBalance+"\n\tCredit Limit: "+creditLimit);
    }
}
