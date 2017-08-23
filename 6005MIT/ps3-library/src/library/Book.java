package library;

import java.util.List;
import java.util.ArrayList;

/**
 * Book is an immutable type representing an edition of a book -- not the physical object, 
 * but the combination of words and pictures that make up a book.  Each book is uniquely
 * identified by its title, author list, and publication year.  Alphabetic case and author 
 * order are significant, so a book written by "Fred" is different than a book written by "FRED".
 */
public class Book implements Comparable<Book>{

    // rep
    private final String TITLE;
    private final List<String> authors;
    private final int YEAR;
    
    // Rep invariant
    //      TITLE contains at least one non-space character
    //      authors contains at least one name, and each name contains at least one non-space char
    //      YEAR > 0
    // Abstraction function
    //      represents an edition of a book, identified by its title, authors, and published year
    // Safety from rep exposure argument:
    //      All fields are private. TITLE (String obj) and YEAR (int obj) are immutable; 
    //      authors is a mutable List type obj, but getAuthors makes defensive copy of the List it 
    //      returns. Further, all the other return values are immutale int, String, boolean, or void.
    //
    
    /**
     * Make a Book.
     * @param title Title of the book. Must contain at least one non-space character.
     * @param authors Names of the authors of the book.  Must have at least one name, and each name must contain 
     * at least one non-space character.
     * @param year Year when this edition was published in the conventional (Common Era) calendar.  Must be nonnegative. 
     */
    public Book(String title, List<String> authors, int year) {
        this.TITLE = title;
        //Collections.unmodifiablelist(authors)
        this.authors = new ArrayList<String>(authors);
        this.YEAR = year;
        
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
     * @return the title of this book
     */
    public String getTitle() {
        return this.TITLE;

    }
    
    /**
     * @return the authors of this book
     */
    public List<String> getAuthors() {
        List<String> ans = new ArrayList<String>(this.authors);
        checkRep();
        return ans;

    }

    /**
     * @return the year that this book was published
     */
    public int getYear() {
        return this.YEAR;

    }

    /**
     * @return human-readable representation of this book that includes its title,
     *    authors, and publication year
     */
    public String toString() {
        String ans = "Title: "+ this.TITLE +"\n"+ "Authors: ";
        String authorString = "\n" ;
        for (String item : this.authors){
            authorString = authorString + item + "\n";
        }
        ans = ans + authorString + "Publication year: " + this.YEAR;
        checkRep();
        return ans;
    }

    // uncomment the following methods if you need to implement equals and hashCode,
    // or delete them if you don't
       @Override
       public boolean equals(Object that) {
         if (!(that instanceof Book)) return false;
         Book thatBook = (Book) that;
         checkRep();
         return this.toString().equals(thatBook.toString());
       }
    // 
     @Override
      public int hashCode() {
          return toString().hashCode();
     }
     
     @Override
     public int compareTo(Book thatBook)  {
         if (thatBook.getYear() > this.YEAR) return 1;
         else if(thatBook.getYear() < this.YEAR) return -1;
         else return 0;
   }



    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
