package library;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;

/**
 * BigLibrary represents a large collection of books that might be held by a city or
 * university library system -- millions of books.
 * 
 * In particular, every operation needs to run faster than linear time (as a function of the number of books
 * in the library).
 */
public class BigLibrary implements Library {

    //rep
    private Set<BookCopy> inLibrary;
    private Set<BookCopy> checkedOut;
    private Map<String,List<Book>> nameIndexMap;
    private Map<String,List<Book>> titleIndexMap;
    private Map<Book,List<BookCopy>> copyMap;
    private Map<Book,List<BookCopy>> availCopyMap;
    
    
    // rep invariant:
    //    the intersection of inLibrary and checkedOut is the empty set
    //    titleIndexMap, titleIndexMap, copyMap, availCopyMap are not null
    //
    // abstraction function:
    //    represents the collection of books inLibrary union checkedOut,
    //      where if a book copy is in inLibrary then it is available,
    //      and if a copy is in checkedOut then it is checked out
    // Safety from rep exposure argument
    
    // All the fields are private. inLibrary and checkedOut are mutable Set containing mutable BookCopy objects,  
    // but allCopies() make the new Set it returns; so does availableCopies(). find() returns a new instance of List.
    // Further, although some parameters and return value are mutable BookCopy,  BookCopy is not part of the representation of 
    // SmallLibrary, so...they will not cause rep exposure.
    // nameIndexMap and titleIndexMap are mutable Map containing immutable String as key and mutable List, which contains immutable Book,
    // as value. copyMap and availCopyMap are mutable Map containing immutable String as key and mutable List, which contains mutable BookCopy,
    // as value.  But Map type is never passed or returned from an operation, and List objects are made immutable by unmodifiable wrappers
    // before being returned.
    // All the other return values are void.   
    
    public BigLibrary() {
        
        this.inLibrary = new HashSet<BookCopy>();
        
        this.checkedOut = new HashSet<BookCopy>();
        
        titleIndexMap = new HashMap<String,List<Book>>();
        
        nameIndexMap = new HashMap<String,List<Book>>();
        
        copyMap = new HashMap<Book,List<BookCopy>>();
        
        availCopyMap = new HashMap<Book,List<BookCopy>>();

        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        if (!this.inLibrary.isEmpty() && !this.checkedOut.isEmpty()){
        for (BookCopy item : this.inLibrary) assert !this.checkedOut.contains(item);
            }
        assert nameIndexMap != null;
        assert titleIndexMap != null;
        assert copyMap != null;
        assert availCopyMap != null;
    }

    @Override
    public BookCopy buy(Book book) {
        BookCopy bookCopy = new BookCopy(book);
        inLibrary.add(bookCopy);
        
        if ( !copyMap.containsKey(book)){
            List<BookCopy> l1 = new ArrayList<BookCopy>();
            List<BookCopy> l2 = new ArrayList<BookCopy>();
            List<Book> l3 = new ArrayList<Book>();
            l1.add(bookCopy);
            l2.add(bookCopy);
            l3.add(book);
            copyMap.put(book, l1);
            availCopyMap.put(book, l2);
            if(!titleIndexMap.containsKey(book.getTitle())) {titleIndexMap.put(book.getTitle(), l3 );}else{
                titleIndexMap.get(book.getTitle()).add(book);
            }
           
            for (String name : book.getAuthors()){
                if (nameIndexMap.containsKey(name)){nameIndexMap.get(name).add(book);
                    }else {
                        List<Book> l = new ArrayList<Book>();
                        l.add(book);
                        nameIndexMap.put(name, l);}
            }
        }
        
        else {
            copyMap.get(book).add(bookCopy);
            availCopyMap.get(book).add(bookCopy);
            
            
            if (titleIndexMap.containsKey(book.getTitle())
                    && !titleIndexMap.get(book.getTitle()).contains(book)) titleIndexMap.get(book.getTitle()).add(book);

            
            for (String name : book.getAuthors()){
                if ( nameIndexMap.containsKey(name) && !nameIndexMap.get(name).contains(book) ) 
                    nameIndexMap.get(name).add(book);}
            
            
            
        }
        
        checkRep();
        return bookCopy;
    }
    
    @Override
    public void checkout(BookCopy copy) {
        this.inLibrary.remove(copy);
        this.checkedOut.add(copy);
        availCopyMap.get(copy.getBook()).remove(copy);
        checkRep();
    }
    
    @Override
    public void checkin(BookCopy copy) {
        this.checkedOut.remove(copy);
        this.inLibrary.add(copy);
        availCopyMap.get(copy.getBook()).add(copy);
        checkRep();
    }
    
    @Override
    public Set<BookCopy> allCopies(Book book) {
        Set<BookCopy> ans = new HashSet<BookCopy>();
        if (copyMap.containsKey(book)) ans = new HashSet<BookCopy>(copyMap.get(book));
        checkRep();
        return ans;
    }

    @Override
    public Set<BookCopy> availableCopies(Book book) {
        Set<BookCopy> ans = new HashSet<BookCopy>();
        if (availCopyMap.containsKey(book)) ans = new HashSet<BookCopy>(availCopyMap.get(book));
        checkRep();
        return ans;
    }
    
    @Override
    public boolean isAvailable(BookCopy copy) {
        return inLibrary.contains(copy);
    }
    
    @Override
    public List<Book> find(String query) {
        
        List<Book> list1 = titleIndexMap.get(query) ;
        List<Book> list2 = nameIndexMap.get(query);
        if (list1 != null && !list1.isEmpty()) {
            Collections.sort(list1);
            checkRep();
            return Collections.unmodifiableList(list1) ;
        }else if(list2 != null && !list2.isEmpty()){
            Collections.sort(list2);
            checkRep();
            return Collections.unmodifiableList(list2);
            
        }else return new ArrayList<Book>();

    }
    
    @Override
    public void lose(BookCopy copy) {
        if (inLibrary.contains(copy)){
            inLibrary.remove(copy);
            copyMap.get(copy.getBook()).remove(copy);
            availCopyMap.get(copy.getBook()).remove(copy);
            
            if (copyMap.get(copy.getBook()).isEmpty()){
                
                for (String name : copy.getBook().getAuthors()){
                    if ( nameIndexMap.containsKey(name) && nameIndexMap.get(name).contains(copy.getBook()) ) 
                        nameIndexMap.get(name).remove(copy.getBook());}
                    
                if ( titleIndexMap.containsKey(copy.getBook().getTitle()) 
                        && titleIndexMap.get(copy.getBook().getTitle()).contains(copy.getBook()) ) 
                    titleIndexMap.get(copy.getBook().getTitle()).remove(copy.getBook());
                
                }
            }
        else{ 
            
            checkedOut.remove(copy);
            copyMap.get(copy.getBook()).remove(copy);
            
            if (copyMap.get(copy.getBook()).isEmpty()){
                
                for (String name : copy.getBook().getAuthors()){
                    if ( nameIndexMap.containsKey(name) && nameIndexMap.get(name).contains(copy.getBook()) ) 
                        nameIndexMap.get(name).remove(copy.getBook());}
                    
                if ( titleIndexMap.containsKey(copy.getBook().getTitle()) 
                        && titleIndexMap.get(copy.getBook().getTitle()).contains(copy.getBook()) ) 
                    titleIndexMap.get(copy.getBook().getTitle()).remove(copy.getBook());
                
                }
        }


            
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
