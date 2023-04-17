package Solution_3;

// Handle the exceptions when balance is sufficient
public class BalanceSufficientException extends Throwable{

    // The exception when using bank account to pay
    public void bankBalanceSufficientException(double _bankBalance, double _cost){
        System.out.println("Payment failed: ");
        System.out.println("\tThe bankBalance is sufficient");
        System.out.println("\tThe bankBalance is " + _bankBalance + " while the cost is " + _cost + ".");
    }

    //The exception when using credit card to pay
    public void creditLimitSufficientException(double _creditLimit, double _cost){
        System.out.println("Payment failed: ");
        System.out.println("\tThe creditLimit is sufficient");
        System.out.println("\tThe creditLimit is " + _creditLimit + " while the cost is " + _cost + ".");
    }

    //The exception when using both bank account and credit card to pay
    public void bothSufficientException(double _bankBalance, double _creditLimit, double _cost){
        System.out.println("Payment failed: ");
        System.out.println("\tThe bankBalance and the creditLimit are sufficient");
        System.out.println("\tThe bankBalance is " + _bankBalance + " and the creditLimit is " + _creditLimit + " while the cost is " + _cost + ".");
    }
}
