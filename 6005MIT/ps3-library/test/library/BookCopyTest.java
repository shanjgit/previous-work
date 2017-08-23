package library;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Test suite for BookCopy ADT.
 */
public class BookCopyTest {

    /*
     * Testing strategy
     * 
     * Partition the inputs as follows:
     *  book with
     *  
     *  title.length :  1, > 1
     *  year : 1, > 1 
     *  authors: 1 author, >1 authors
     *  
     *  Condition: Good, DAMAGED
     *  
     *  cover each part at least once
     */

    // Covers
    // TITLE.length > 1
    // YEAR > 1
    // Authors > 1
    // Condition GOOD
    @Test
    public void testExampleTest() {
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = new BookCopy(book);
        assertEquals(book, copy.getBook());
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // covers title.length = 1
    // year = 1
    // authors = 1
    // Condition : GOOD
    @Test
    public void testBoundarycase1() {
        Book book1 = new Book("A", Arrays.asList("Adam Smith"), 1);
        BookCopy book1Copy = new BookCopy(book1);
        assertEquals(book1, book1Copy.getBook());
        assertEquals(BookCopy.Condition.GOOD,book1Copy.getCondition());
    }
    
    // covers title.length = 1
    // year = 1
    // authors = 1
    // Condition : DAMAGED
    @Test
    public void testBoundarycasseCondition() {
        Book book1 = new Book("A", Arrays.asList("Adam Smith"), 1);
        BookCopy book1Copy = new BookCopy(book1);
        book1Copy.setCondition(BookCopy.Condition.DAMAGED);
        assertEquals(BookCopy.Condition.DAMAGED,book1Copy.getCondition());

    }
    
    // covers title.length = 1
    // year = 1
    // authors = 1
    // Condition : GOOD, DAMAGED
    @Test
    public void testBoundaryCaseListContain() {
        Book book1 = new Book("A", Arrays.asList("Adam Smith"), 1);
        BookCopy book1Copy = new BookCopy(book1);
        List<BookCopy> alist = new ArrayList<BookCopy>();
        alist.add(book1Copy);
        book1Copy.setCondition(BookCopy.Condition.DAMAGED);
        assertTrue(alist.contains(book1Copy));
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
