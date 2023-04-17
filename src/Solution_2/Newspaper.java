package Solution_2;
import java.util.*;

public class Newspaper {
    private String name;
    private List<String> articles;
    private List<Reader> readers;

    Newspaper(String name) {
        articles = new ArrayList<String>();
        readers = new ArrayList<Reader>();
        this.name = name;
    }

    //Add the article to the articles list and make an announcement.
    void addArticle(String article) {
        //
        articles.add(article);
        announce();
    }

    //Get the issue number for this article
    String getArticle(int issue) {
        // the issue number (aka the size of the list when the article was added)
        // equals to the index+1
        return this.name + ": " + articles.get(issue-1);
    }

    //issue number is the number to track the added articles
    //hint: the issue number of newly added article is the size of articles
    int getIssue() {
        return articles.size();
    }

    //Add a new reader to the readers list
    void register(Reader reader) {
        readers.add(reader);
    }

    //remove a reader from the readers list
    void unregister(Reader reader) {
        readers.remove(reader);
    }

    //Let all readers who subscribe this newspaper read this article
    void announce() {
        //Traverse all readers and let each one read the newly added article
        for (Reader r : readers){
            r.readArticle(this);
        }
    }
}
