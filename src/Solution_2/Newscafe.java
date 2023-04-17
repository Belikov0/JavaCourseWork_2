package Solution_2;

class Newscafe {

  public static void main(String[] args) {
    Reader jill = new Reader("Jill");
    Reader jack = new Reader("Jack");
    Newspaper times = new Newspaper("The Times");
    Newspaper guardian = new Newspaper("The Guardian");
    
    jill.subscribe(times);
    times.addArticle("Stormy weather!");
    
    jill.subscribe(guardian);
    jack.subscribe(guardian);
    guardian.addArticle("Bad news!");
    guardian.addArticle("Good news!");
    times.addArticle("Sunny weather!");
} }
