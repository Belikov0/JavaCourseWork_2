package Solution_3;

import java.util.ArrayList;
import java.util.HashMap;

public class MyMobilePayment {
    // For each way of payment, set a name to mark it
    // Sementically demonstrate the application used to pay
    public enum PayNamesEnum {ALI_PAY, JD_PAY, UNION_PAY, WE_CHAT_PAY};
    // Basic debit payment
    DebitPayment debitPayment;
    // Use hashmap to store the name of payment and itself
    // which is convenient for us to find corresponding way of payment
    HashMap<PayNamesEnum, DebitPayment> myPayment;

    //Constructors
    MyMobilePayment(){
        debitPayment = new DebitPayment();
        myPayment = new HashMap<>();
    }
    MyMobilePayment(String _bankAcct, double _bankBalance, String _creditCardNum, double _creditLimit){
        //Bind bank account and credit card to the phone
        debitPayment = new DebitPayment(_bankAcct, _bankBalance, _creditCardNum, _creditLimit);
        myPayment = new HashMap<>();
    }

    // Add a way of payment into current phone
    // Every way of payment can use the bank account and credit card bound in the phone
    void addDebitPayment(DebitPayment _payment, PayNamesEnum _payName){
        myPayment.put(_payName, _payment);
    }

    //Pay using a certain way of payment designated by payName
    void pay(double _cost, PayNamesEnum _payName){
        myPayment.get(_payName).payMethod(_cost);
    }

    //Pay using a certain way of payment and designated pay type
    void pay(double _cost, PayNamesEnum _payName, DebitPayment.PayTypeEnum _payType){
        myPayment.get(_payName).payMethod(_cost, _payType);
    }

    public static void main(String[] args) {
        // Create an instance of mobile payment
        // which includes four ways of payment and an account for payment
        MyMobilePayment myMobilePayment = new MyMobilePayment("123456", 10000, "1234123", 10000);

        // Create instances of ways of payment and add them into phone, which reflect various application in the phone
        myMobilePayment.addDebitPayment(new AliPay("ali123", DebitPayment.PayTypeEnum.BANK), PayNamesEnum.ALI_PAY);
        myMobilePayment.addDebitPayment(new JDPay("jd123", DebitPayment.PayTypeEnum.CREDIT), PayNamesEnum.JD_PAY);
        myMobilePayment.addDebitPayment(new UnionPay("union123", DebitPayment.PayTypeEnum.BANK_FIRST), PayNamesEnum.UNION_PAY);
        myMobilePayment.addDebitPayment(new WeChatPay("WeChat123", DebitPayment.PayTypeEnum.CREDIT_FIRST), PayNamesEnum.WE_CHAT_PAY);

        // Use four ways of payment to consume, the payType is default
        myMobilePayment.pay(7000, PayNamesEnum.ALI_PAY);
        myMobilePayment.pay(6000, PayNamesEnum.JD_PAY);
        myMobilePayment.pay(3500, PayNamesEnum.UNION_PAY);
        myMobilePayment.pay(3300, PayNamesEnum.WE_CHAT_PAY);
    }
}
