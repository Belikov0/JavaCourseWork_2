package Solution_2;

class Reader {
    private String name;

    Reader(String name) {
      this.name = name;
    }

   //get the read article name based on newspaper Issue number
    void readArticle(Newspaper newspaper) {
        String str = this.name + " reads: "
                // Get the newly added article by newspaper issue
                // when a new article is added into the newspaper
                + newspaper.getArticle(newspaper.getIssue());
        System.out.println(str);
    }

    //subscribe the newspaper
    void subscribe(Newspaper newspaper) {
        // Let newspaper registers the current reader
        newspaper.register(this);
    }
}
