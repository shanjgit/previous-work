package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
//import java.util.ArrayList;
import org.junit.Test;
//import java.util.List;
public class ExtractTest {

    /* Test Extract.getTimeSpan
     * Testing strategy
     *
     * Partition the inputs as follows:
     * Tweet.timestamp: Instant.EPOCH, Instant.MIN, Instant.MAX
     *      
     * 
     */

    /* Test Extract.getMentionedUsers
     * Testing strategy
     *
     * Partition the inputs as follows:
     * username type: alphabet only, alphabet + 0-9+_+-, 0-9 only
     * username.lenghth(): <= 16, > 16 
     * Tweet.getText() : empty, one legal mentioned username, two legal mentioned usernames
     * list of Tweet: one Tweet object, 6 Tweet object     
     * 
     */
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant dEpo = Instant.parse(Instant.EPOCH.toString());
    private static final Instant dMax = Instant.parse(Instant.MAX.toString());
    private static final Instant dMin = Instant.parse(Instant.MIN.toString());
    private static final Instant d6 = Instant.parse("1955-02-17T11:00:00Z");
    private static final Instant d7 = Instant.parse("1955-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "adffdf","#feelhappy i am @batman and i love",dEpo);
    private static final Tweet tweet4 = new Tweet(4, "mitid","", dMax);
    private static final Tweet tweet5 = new Tweet(5, "madana", "@Superman,I love @948794@kwan, his is a handsome man, more handsome tham @Ironman ", dMin);
    private static final Tweet tweet6 = new Tweet(6, "aladin", "i hate @1A6_a-f Mit@gmail.com also hate @Madana.",d6);
    private static final Tweet tweet7 = new Tweet(7, "strange", "i love @rachael and i love @penny.",d7);
    private static final Tweet tweet8 = new Tweet(8, "strange", "both @batman and @BAtmaN are the same.",d7);
    
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetTimespanEmpty() {
        Timespan timespan = Extract.getTimespan(Arrays.asList());
        assertTrue(timespan.getEnd().equals(timespan.getStart()));
        

    }
    
    
    
    
    
    
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    
    // covers only one point  
    @Test
    public void testGetTimespanTwoTweetsOnlyOnePoint() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    
    
    
    // covers Instant.MIN < Instant.EPOCH <  Instant.MAX
    //  start = Instant.MIN
    //  end = Instant.MAX
    @Test
    public void testGetTimepanThreeTweets(){
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet3, tweet4, tweet5));
        
        assertEquals("expected start",dMin,timespan.getStart() );
        assertEquals("expected end", dMax, timespan.getEnd());
    }
    
    
    // covers Instant.MIN < Instant.EPOCH < testCase  
    //  start = Instant.MIN
    //  end = testcase
    @Test
    public void testGetTimepanThreeTweets2(){
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet3, tweet5));
        
        assertEquals("expected start",dMin,timespan.getStart() );
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    // covers testCase<Instant.EPOCH < Instant.MAX  
    //  start = testCase
    //  end = Instant.MAX
    @Test
    public void testGetTimepanThreeTweets3(){
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet3, tweet4, tweet6));
        
        assertEquals("expected start",d6,timespan.getStart() );
        assertEquals("expected end", dMax, timespan.getEnd());
    }
    
    // Test if a Tweets is case insensitive
    @Test
    public void testGetMentionedUsersCaseInsenitive() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet8));
        Set<String> newMentioned = new HashSet<>();
        for (String item : mentionedUsers){
            newMentioned.add(item.toLowerCase());
        }
        
       assertTrue(mentionedUsers.size() == newMentioned.size());
        //assertEquals("expect batman",newSet,mentionedUsers);
    }
    
    
    
    
    
    
    
    
    //covers empty string
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        Set<String> mentionedUsersEmpty = Extract.getMentionedUsers(Arrays.asList(tweet4));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
        assertTrue("expected empty set", mentionedUsersEmpty.isEmpty());
    }
    
    // covers one mentioned string
    @Test
    public void testGetMentionedUsersOneMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        Set<String> newSet = new HashSet<String>();
        newSet.add("batman");
        
        assertEquals(newSet.toString().toLowerCase(),mentionedUsers.toString().toLowerCase());
        //assertEquals("expect batman",newSet,mentionedUsers);
    }
    
    // covers two mentioned names in one string
    @Test
    public void testGetMentionedUsersTwoMentionOneString() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet7));
        Set<String> newSet = new HashSet<String>();
        newSet.add("rachael");
        newSet.add("penny");
        
        for (String name : mentionedUsers){
            assertTrue(newSet.contains(name.toLowerCase()));
        }
        
        assertEquals(newSet.toString().toLowerCase(),mentionedUsers.toString().toLowerCase());
        //assertEquals("expect batman",newSet,mentionedUsers);
    }
    
    
    //covers two 6 Tweet objects
    @Test
    public void testGetMentionedUsersmMultipleMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1,tweet2,tweet3,tweet4,tweet5,tweet6));
        Set<String> newSet = new HashSet<String>(Arrays.asList("948794","ironman","1a6_a-f", "madana","batman","superman"));
        
        //assertEquals(newSet, mentionedUsers);
        for (String user : mentionedUsers){
           assertTrue("expected user in set",newSet.contains(user.toLowerCase()));
        }
    }
    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
