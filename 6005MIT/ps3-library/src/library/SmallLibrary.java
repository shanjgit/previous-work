package library;

import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.ArrayList;

/** 
 * SmallLibrary represents a small collection of books, like a single person's home collection.
 */
public class SmallLibrary implements Library {

    // This rep is required! 
    // Do not change the types of inLibrary or checkedOut, 
    // and don't add or remove any other fields.
    // (BigLibrary is where you can create your own rep for
    // a Library implementation.)

    // rep
    private Set<BookCopy> inLibrary;
    private Set<BookCopy> checkedOut;
    
    // Rep invariant:
    //    the intersection of inLibrary and checkedOut is the empty set
    //
    // Abstraction function:
    //    represents the collection of books inLibrary union checkedOut,
    //      where if a book copy is in inLibrary then it is available,
    //      and if a copy is in checkedOut then it is checked out

    // All the fields are private. inLibrary and checkedOut are mutable Set containing mutable BookCopy objects,  
    // but allCopies() make the new Set it returns; so does availableCopies(). find() returns a new instance of List.
    // Further, although some parameters and return value are mutable BookCopy, they are not the representation of 
    // SmallLibrary, so...they will not cause rep exposure.
    // All the other return values are void.      
    
    public SmallLibrary() {
        this.inLibrary = new HashSet<BookCopy>();
        this.checkedOut = new HashSet<BookCopy>();
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        if (!this.inLibrary.isEmpty() && !this.checkedOut.isEmpty()){
        for (BookCopy item : this.inLibrary) assert !this.checkedOut.contains(item);
            }
    }

    @Override
    public BookCopy buy(Book book) {
        BookCopy bookCopy = new BookCopy(book);
        this.inLibrary.add(bookCopy);
        checkRep();
        return bookCopy;
    }
    
    @Override
    public void checkout(BookCopy copy) {
        this.inLibrary.remove(copy); 
        this.checkedOut.add(copy);
       checkRep();
    }
    
    @Override
    public void checkin(BookCopy copy) {
        this.checkedOut.remove(copy);
        this.inLibrary.add(copy);
        checkRep();
    }
    
    @Override
    public boolean isAvailable(BookCopy copy) {
        return this.inLibrary.contains(copy);
    }
    
    @Override
    public Set<BookCopy> allCopies(Book book) {
        Set<BookCopy> allCopiesSet = new HashSet<BookCopy>();
        
        for (BookCopy copy : this.inLibrary){
            Book thatBook = copy.getBook();
            if (thatBook.equals(book)) allCopiesSet.add(copy);
        }
        
        for (BookCopy copy : this.checkedOut){
            Book thatBook = copy.getBook();
            if (thatBook.equals(book)) allCopiesSet.add(copy);
        }
        checkRep();
        return allCopiesSet;
    }
    
    @Override
    public Set<BookCopy> availableCopies(Book book) {
        Set<BookCopy> allCopiesSet = new HashSet<BookCopy>();
        
        for (BookCopy copy : this.inLibrary){
            Book thatBook = copy.getBook();
            if (thatBook.equals(book)) allCopiesSet.add(copy);
        }
        checkRep();
        return allCopiesSet;
    }
   
    @Override
    public List<Book> find(String query) {
        
        List<Book> myList = new ArrayList<Book>();
        
        for (BookCopy copy : this.inLibrary){
            Book thisBook = copy.getBook();
            if (thisBook.getTitle().equals(query) || thisBook.getAuthors().contains(query)){
                if ( !myList.contains(thisBook) ) myList.add(thisBook);
                }
        }
        
        
        for (BookCopy copy : this.checkedOut){
            Book thisBook = copy.getBook();
            if (thisBook.getTitle().equals(query) || thisBook.getAuthors().contains(query)){
                if ( !myList.contains(thisBook) ) myList.add(thisBook);
                }
        }
        
        Collections.sort(myList);
        checkRep();
        return myList;
        
    
    }
    
    @Override
    public void lose(BookCopy copy) {
        if (this.inLibrary.contains(copy)) this.inLibrary.remove(copy);
        else this.checkedOut.remove(copy);
        checkRep();
    }

    // uncomment the following methods if you need to implement equals and hashCode,
    // or delete them if you don't
    // @Override
    // public boolean equals(Object that) {
    //     throw new RuntimeException("not implemented yet");
    // }
    // 
    // @Override
    // public int hashCode() {
    //     throw new RuntimeException("not implemented yet");
    // }
    

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
