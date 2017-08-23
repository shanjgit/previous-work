package library;
import java.util.List;
import java.util.ArrayList;

/**
 * BookCopy is a mutable type representing a particular copy of a book that is held in a library's
 * collection.
 */
public class BookCopy {

    // Rep
    private final String TITLE;
    private final List<String> authors;
    private final int YEAR;
    private Condition state;
    
    
    // Rep invariant
    //      TITLE contains at least one non-space character
    //      authors contains at least one name, and each name contains at least one non-space char
    //      YEAR > 0
    // Abstraction function
    //      representing a particular copy of a book, identified by its title, authors, and published year
    // Safety from rep exposure argument:
    //      All fields are private. TITLE (String obj), YEAR (int obj) and state (Condition obj) are immutable; 
    //      authors is a mutable List type obj, but getAuthors makes defensive copy of the List it 
    //      returns. Also, getBook() and toString() make a copy of authors before putting it into constructor Book().  
    //      
    //      Further, all the other parameters are immutable, and all the return values are immutable int, String, boolean, or void.
    //
    
    public static enum Condition {
        GOOD, DAMAGED
    };
    
    /**
     * Make a new BookCopy, initially in good condition.
     * @param book the Book of which this is a copy
     */
    public BookCopy(Book book) {
        this.state = Condition.GOOD;
        this.YEAR = book.getYear();
        this.authors = new ArrayList<String>(book.getAuthors());
        this.TITLE = book.getTitle();
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        //throw new RuntimeException("not implemented yet");
        if (this.TITLE.length() == 1){
            assert this.TITLE != " ";
        }
        assert this.YEAR > 0;
        assert !this.authors.isEmpty();
        for (String item : this.authors){
            assert item != " ";
        }
    }
    
    /**
     * @return the Book of which this is a copy
     */
    public Book getBook() {
        List<String> alist = new ArrayList<String>(this.authors);
        Book ans = new Book(this.TITLE, alist, this.YEAR);
        checkRep();
        return ans;

    }
    
    /**
     * @return the condition of this book copy
     */
    public Condition getCondition() {
        return this.state;
    }

    /**
     * Set the condition of a book copy.  This typically happens when a book copy is returned and a librarian inspects it.
     * @param condition the latest condition of the book copy
     */
    public void setCondition(Condition condition) {
        this.state = condition;
        checkRep();
    }
    
    /**
     * @return human-readable representation of this book that includes book.toString()
     *    and the words "good" or "damaged" depending on its condition
     */
    public String toString() {
        List<String> alist = new ArrayList<String>(this.authors);
        Book origin = new Book(this.TITLE, alist, this.YEAR);
        String ans = origin.toString() + "\n"+"Condition: "+ this.state.toString().toLowerCase();
        checkRep();
        return ans;
    }

    // uncomment the following methods if you need to implement equals and hashCode,
    // or delete them if you don't
       //@Override
       //public boolean equals(Object that) {
           
     
      //}
    // 
      //@Override
      //public int hashCode() {
          
      //}
    //
    //public int compareTo(BookCopy thatCopy)  {
    //    String s = "\" dsfgsd \" ";
     //   if (thatCopy.getBook().getYear() > this.YEAR) return 1;
     //   else if(thatCopy.getBook().getYear() < this.YEAR) return -1;
      //  else return 0;
 // }



    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
