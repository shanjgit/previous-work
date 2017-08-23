package library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test suite for Library ADT.
 */
@RunWith(Parameterized.class)
public class LibraryTest {
    Book book1 = new Book("Harry Potter and the Half-Blood Prince", Arrays.asList("J. K. Rowling"),2005);
    Book book2 = new Book("Harry Potter and the Philosopher's Stone",Arrays.asList("J. K. Rowling"),1997);
    Book book3 = new Book("Pride and Prejudice",Arrays.asList("Jane Austen","J. K. Rowling"),1813);
    Book book4 = new Book("The Lord of The Rings: The Two Towers",Arrays.asList("J. R. R. Tolkein"),1954);
    /*
     * Note: all the tests you write here must be runnable against any
     * Library class that follows the spec.  JUnit will automatically
     * run these tests against both SmallLibrary and BigLibrary.
     */

    /**
     * Implementation classes for the Library ADT.
     * JUnit runs this test suite once for each class name in the returned array.
     * @return array of Java class names, including their full package prefix
     */
    @Parameters(name="{0}")
    public static Object[] allImplementationClassNames() {
        return new Object[] { 
            "library.SmallLibrary", 
            "library.BigLibrary"
        }; 
    }

    /**
     * Implementation class being tested on this run of the test suite.
     * JUnit sets this variable automatically as it iterates through the array returned
     * by allImplementationClassNames.
     */
    @Parameter
    public String implementationClassName;    

    /**
     * @return a fresh instance of a Library, constructed from the implementation class specified
     * by implementationClassName.
     */
    public Library makeLibrary() {
        try {
            Class<?> cls = Class.forName(implementationClassName);
            return (Library) cls.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    /*
     * Testing strategy
     * 
     * Partition the inputs as follows:
     *  Book number:  0, 1, > 1
     *  Copy number:  0,1, > 1
     *  checkin number : 0, 1, > 1 
     *  checkout number : 0, 1, > 1
     *  Books share the same author: 0, 1, >1
     *  Book.getAuthors().length(): 1, > 1 
     *  cover each part at least once
     */  
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
 
    
    //Cover:
    //Book number:  0
    //checkin number : 0
    //checkout number : 0
    //Books share the same author: null
    //Book.getAuthors().length(): null 
    @Test
    public void testEmptyLibraryFind() {
        Library aLib = makeLibrary();
        List<Book> aList = aLib.find("Harry");
        assertEquals(Collections.emptyList(),aList);
        
    }
    
    //Cover:
    //Book number:  0
    //checkin number : 0
    //checkout number : 0
    //Books share the same author: null
    //Book.getAuthors().length(): null 
    @Test
    public void testEmptyLibraryAllCopies() {
        Library aLib = makeLibrary();
        assertEquals(Collections.emptySet(), aLib.allCopies(book1));
        
    }
    
    
    //Covers
    //Book number:  1
    //checkin number : 1
    //checkout number : 0
    //Books share the same author: 1
    //Book.getAuthors() : 1 
    @Test
    public void testOneLibraryAllCopies() {
        Library aLib = makeLibrary();
        BookCopy copy1 = aLib.buy(book1);
        Set<BookCopy> set = new HashSet<BookCopy>(Arrays.asList(copy1));
        assertEquals(set, aLib.allCopies(book1));
        
    }
    
    
    //Covers
    //Book number:  1
    //checkin number : 0
    //checkout number : 0
    //Books share the same author: 1
    //Book.getAuthors() : 1 
    @Test
    public void testOneLibraryLose() {
        Library aLib = makeLibrary();
        BookCopy copy1 = aLib.buy(book1);
        aLib.lose(copy1);
        assertEquals(Collections.emptySet() , aLib.allCopies(book1));
        assertEquals(Collections.emptySet(), aLib.availableCopies(book1));
        assertFalse(aLib.isAvailable(copy1));
        assertEquals(Collections.emptyList(), aLib.find("J. K. Rowling"));
        
    }
    
    //Covers
    //Book number:  1
    //checkin number : 1
    //checkout number : 0
    //Books share the same author: 1
    //Book.getAuthors() : 1 
    @Test
    public void testOneCopyOneBookLibraryIsAvailable() {
        Library aLib = makeLibrary();
        BookCopy copy1 = aLib.buy(book1);
        assertTrue(aLib.isAvailable(copy1));
        
    }
    
    //Covers
    //Book number:  1
    //checkin number : 0
    //checkout number : 1
    //Books share the same author: 1
    //Book.getAuthors() : 1 
    @Test
    public void testOneCopyOneBookLibraryIsAvailableChekOut() {
        Library aLib = makeLibrary();
        BookCopy copy1 = aLib.buy(book1);
        aLib.checkout(copy1);
        assertFalse(aLib.isAvailable(copy1));
    }
    
    //Covers
    //Book number:  1
    //checkin number : 0
    //checkout number : 1
    //Books share the same author: 1
    //Book.getAuthors() : 1 
    @Test
    public void testOneCopyOneBookLibraryIsAvailableChekOutCheckIn() {
        Library aLib = makeLibrary();
        BookCopy copy1 = aLib.buy(book1);
        aLib.checkout(copy1);
        aLib.checkin(copy1);
        assertTrue(aLib.isAvailable(copy1));
    }
    
    //Covers
    //number of Book :  1
    //number of Copy :  2
    //checkin  : 0
    //checkout : 1
    //Books share the same author: 1
    //Book.getAuthors() : 1 
    @Test
    public void testTwoCopyOneBookLibraryIsAvailableChekOutCheckIn() {
        Library aLib = makeLibrary();
        BookCopy copy1 = aLib.buy(book1);
        BookCopy copy2 = aLib.buy(book1);
        aLib.checkout(copy1);
        Set<BookCopy> set = new HashSet<BookCopy>();
        set.add(copy2);
        assertEquals(set, aLib.availableCopies(book1));
        aLib.checkin(copy1);
        set.add(copy1);
        assertEquals(set, aLib.availableCopies(book1));
    }
    
    //Covers
    //number of Book :  3
    //number of Copy :  (1,2,3)
    //checkin  : (1,2,3)
    //checkout : 0
    //Books share the same author: 2
    //Book.getAuthors() : (1,1,2) 
    @Test
    public void testOneTwoThreeCopyThreeBookLibraryAllCopies() {
        Library aLib = makeLibrary();
        BookCopy copy1 = aLib.buy(book1);
        BookCopy copy2 = aLib.buy(book2);
        BookCopy copy3 = aLib.buy(book2);
        BookCopy copy4 = aLib.buy(book3);
        BookCopy copy5 = aLib.buy(book3);
        BookCopy copy6 = aLib.buy(book3);
        Set<BookCopy> set1 = new HashSet<BookCopy>(Arrays.asList(copy2,copy3));
        Set<BookCopy> set2 = new HashSet<BookCopy>(Arrays.asList(copy5,copy4,copy6));
        assertEquals(set1,aLib.allCopies(book2));
        assertEquals(set2,aLib.allCopies(book3));
    }
    
    //Covers
    //number of Book :  3
    //number of Copy :  (1,2,3)
    //checkin  : (0,0,3)
    //checkout : (1,2,0)
    //lose: (0,0,0)
    //Books share the same author: 3
    //Book.getAuthors() : (1,1,2) 
    @Test
    public void testOneTwoThreeCopyThreeBookLibraryFind() {
        Library aLib = makeLibrary();
        BookCopy copy1 = aLib.buy(book1);
        BookCopy copy2 = aLib.buy(book2);
        BookCopy copy3 = aLib.buy(book2);
        BookCopy copy4 = aLib.buy(book3);
        BookCopy copy5 = aLib.buy(book3);
        BookCopy copy6 = aLib.buy(book3);
        aLib.checkout(copy1);
        aLib.checkout(copy2);
        aLib.checkout(copy3);
        
        assertEquals(Arrays.asList(book1,book2,book3), aLib.find("J. K. Rowling"));

    }
    
    //Covers
    //number of Book :  2
    //number of Copy :  (1,2)
    //checkin  : (1,1)
    //checkout : (0,1)
    //lose: (0,0)
    //Books share the same author: 2
    //Book.getAuthors() : (1,1) 
    @Test
    public void testOneTwoCopyTwoBookLibraryAvailableCopies() {
        Library aLib = makeLibrary();
        BookCopy copy1 = aLib.buy(book1);
        BookCopy copy2 = aLib.buy(book2);
        BookCopy copy3 = aLib.buy(book2);
        aLib.checkout(copy2);
        Set<BookCopy> set = new HashSet<BookCopy>(Arrays.asList(copy3));
        assertEquals(set, aLib.availableCopies(book2));

    }
    
    //Covers
    //number of Book :  2
    //number of Copy :  (1,3)
    //checkin  : (1,0)
    //checkout : (0,1)
    //lose: (0,2)
    //Books share the same author: 2
    //Book.getAuthors() : (1,1) 
    @Test
    public void testOneThreeCopyTwoBookLibraryAvailableCopiesLoseTwo() {
        Library aLib = makeLibrary();
        BookCopy copy1 = aLib.buy(book1);
        BookCopy copy2 = aLib.buy(book2);
        BookCopy copy3 = aLib.buy(book2);
        BookCopy copy4 = aLib.buy(book2);
        aLib.checkout(copy2);
        aLib.checkout(copy3);
        aLib.lose(copy4);
        aLib.lose(copy3);
        Set<BookCopy> set = new HashSet<BookCopy>(Arrays.asList(copy1));
        assertEquals(set, aLib.availableCopies(book1));
        assertEquals(Collections.emptySet(), aLib.availableCopies(book2));

    }
    
    //Covers
    //number of Book :  2
    //number of Copy :  (1,3)
    //checkin  : (1,1)
    //checkout : (0,1)
    //lose: (0,1)
    //Books share the same author: 2
    //Book.getAuthors() : (1,1) 
    @Test
    public void testOneThreeCopyTwoBookLibraryAllCopiesLoseTwo() {
        Library aLib = makeLibrary();
        BookCopy copy1 = aLib.buy(book1);
        BookCopy copy2 = aLib.buy(book2);
        BookCopy copy3 = aLib.buy(book2);
        BookCopy copy4 = aLib.buy(book2);
        aLib.checkout(copy2);
        aLib.lose(copy3);
        Set<BookCopy> set1 = new HashSet<BookCopy>(Arrays.asList(copy1));
        Set<BookCopy> set2 = new HashSet<BookCopy>(Arrays.asList(copy2,copy4));
        assertEquals(set1, aLib.allCopies(book1));
        assertEquals(set2, aLib.allCopies(book2));

    }
    
    @Test
    public void testFind() {
        Library library = makeLibrary();
        Book book1 = new Book("Ulysses", Arrays.asList("James Joyce"), 1922);
        Book book2 = new Book("Infinite Jest", Arrays.asList("David Foster Wallace"), 1996);
        Book book3 = new Book("Consider the Lobster and Other Essays", Arrays.asList("David Foster Wallace"), 2005);
        
        // empty library, no matches
        assertEquals(0, library.find("Ulysses").size());
        
        // one-book library, one match, one copy of match, title search
        library.buy(book1);
        assertEquals(Arrays.asList(book1), library.find("Ulysses"));
        
        // >1 book library, >1 match, >1 copy of each match, author search
        library.buy(book2);
        library.buy(book2);
        library.buy(book3);
        List<Book> result = library.find("David Foster Wallace");
        assertEquals(2, result.size());
        assertEquals(new HashSet<>(Arrays.asList(book2, book3)), new HashSet<>(result));
        
        // 4 matched books with same title/author but different dates must return in decreasing date order
        Book book4 = new Book("Ulysses", Arrays.asList("James Joyce"), 1942);
        Book book5 = new Book("Ulysses", Arrays.asList("James Joyce"), 1965);
        Book book6 = new Book("Ulysses", Arrays.asList("James Joyce"), 2008);
        library.buy(book6);
        library.buy(book4);
        library.buy(book5);
        assertEquals(Arrays.asList(book6, book5, book4, book1), library.find("Ulysses"));
    }
    
    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
