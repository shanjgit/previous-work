package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /* Test Filter.writtenBy
     * Testing strategy
     * Partition the inputs as follows:
     * 0 wanted username, 1 wanted username, 2 wanted username
     * 
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-05-17T11:00:00Z");
    private static final Instant d4 = Instant.MIN;
    private static final Instant d5 = Instant.MAX;
    private static final Instant d6 = Instant.parse("2016-05-18T11:00:00Z");
    private static final Instant d7 = Instant.parse("2016-05-19T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much? hi ", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa","what a wonderful night",d3);
    private static final Tweet tweet4 = new Tweet(4, "utatda","hi, hi i'm utada , it's good to see you.",d4);
    private static final Tweet tweet5 = new Tweet(5, "bruce", " i am melancholy , private #splendid#, ---4574 private21 ill ha.",d5);
    private static final Tweet tweet6 = new Tweet(6, "bruce", " i want to kill Joker!",d6);
    private static final Tweet tweet7 = new Tweet(7, "", " bad one",d7);


    
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testWrittenByMultipleTweetsEmptyResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2,tweet3), "amanda");
        assertEquals("expected empty list", 0, writtenBy.size());
 
    } 
    
    @Test
    public void testWrittenByTwoTweetsWithOneEmptyUsername() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet7,tweet5,tweet6), "bruce");
        assertEquals("expected empty list", 2, writtenBy.size());
        assertTrue(writtenBy.contains(tweet6));
        assertTrue(writtenBy.contains(tweet5));
    } 
    
    
    // one returned value, test if case insensitive
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2,tweet3), "Bbitdiddle");
        List<Tweet> expectedOutPutList = Arrays.asList(tweet2);
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertFalse("expected list to contain tweet", writtenBy.contains(tweet1));
        assertFalse("expected list to contain tweet", writtenBy.contains(tweet3));
        assertTrue(writtenBy.equals(expectedOutPutList));
    }
    
    @Test
    public void testWrittenByMultipleTweetsTwoResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2,tweet3, tweet4, tweet5), "alyssa");
        List<Tweet> expectedOutPutList = Arrays.asList(tweet1,tweet3);
        assertEquals("expected singleton list", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet3));
        assertTrue(writtenBy.equals(expectedOutPutList));
    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        List<Tweet> inTimespan2 = Filter.inTimespan(Arrays.asList(tweet4, tweet5), new Timespan(testStart, testEnd));
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
        assertTrue("expected empty list", inTimespan2.isEmpty());
    }
    
    // test if inTimeSpan catches tweets sent at extreme time 
    @Test
    public void testInTimespanMultipleTweetsExtremeTimeResults() {
        Instant testStart = Instant.parse(d4.toString());
        Instant testEnd = Instant.parse(d5.toString());
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet4, tweet1, tweet5), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet4, tweet5)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet4));
    }
    
    
    @Test
    public void testContainingDoNotReturnListContainingSubString() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet6, tweet5), Arrays.asList("ill"));
       
        assertTrue(containing.get(0) == tweet5);
        assertFalse(containing.contains(tweet6));
        assertTrue(containing.size() == 1);
    }
    
    
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet5, tweet1, tweet2), Arrays.asList("talk"));
       
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    
    // Test if the method catches a word at the beginning and the end of texts
    @Test
    public void testContainingSingleWordAtBeginningAndEnd() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet5,tweet4 , tweet3, tweet1, tweet2), Arrays.asList("hi"));
       
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet4, tweet1)));
        assertEquals("expected same order", 1, containing.indexOf(tweet1));
        assertEquals("expected same order", 0, containing.indexOf(tweet4));
    }
    
    // Test if the method catches multiple Tweet object having the word contained in the list
    @Test
    public void testContainingMultipleWord() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet5, tweet4 , tweet3, tweet1, tweet2), Arrays.asList("hi","utada","melancholy","private"));
       
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet5, tweet4, tweet1)));
        assertEquals("expected same order", 2, containing.indexOf(tweet1));
        assertEquals("expected same order", 1, containing.indexOf(tweet4));
        assertEquals("expected same order", 0, containing.indexOf(tweet5));
        assertEquals("expected size",3, containing.size());
        
    }
    
    // Test if the method catches a word at the beginning and the end of texts
    @Test
    public void testContainingSingleWordAtMid() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet4 , tweet3, tweet1, tweet2,tweet5), Arrays.asList("private21"));
       
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet5)));
        assertEquals("expected same order", 0, containing.indexOf(tweet5));
    }
    

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
