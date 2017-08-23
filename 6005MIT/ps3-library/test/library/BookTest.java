package library;

import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for Book ADT.
 */
public class BookTest {

    /*
     * Testing strategy
     * 
     * Partition the inputs as follows:
     *  title.length :  1, > 1
     *  year : 1, > 1 
     *  authors: 1 author, >1 authors
     *  
     *  cover each part at least once
     */
    Book book1 = new Book("A", Arrays.asList("Adam Smith"), 1);
    
    // covers
    // TITLE.length > 1
    // YEAR > 1
    // Authors > 1
    @Test
    public void testExampleTest() {
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        assertEquals("This Test Is Just An Example", book.getTitle());
        assertEquals(Arrays.asList("You Should", "Replace It", "With Your Own Tests"),book.getAuthors());
        assertTrue(1990 == book.getYear());
    }
    

    // covers
    // TITLE.length > 1
    // YEAR > 1
    // Authors > 1
    @Test
    public void testEquals() {
        Book book1 = new Book("Wikipedia Volume II", Arrays.asList("Adam Smith", "John Locke", "Tsai Ing Weng"), 2016);
        Book book2 = new Book("Wikipedia Volume II", Arrays.asList("Adam Smith", "John Locke", "Tsai Ing Weng"), 2016);
        Object book3 = book2;
        assertEquals(book1,book2);
        assertEquals(book1,book3);
        //assertTrue(book1.equals(book2));
        assertTrue(book1.hashCode() == book2.hashCode());
    }
   
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // covers title.length = 1
    // year = 1
    // authors = 1
    @Test
    public void testBoundarycase1() {
        assertEquals("A", book1.getTitle());
        assertTrue(1 == book1.getYear());
        assertEquals(Arrays.asList("Adam Smith"),book1.getAuthors());
    }
    


    
    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
