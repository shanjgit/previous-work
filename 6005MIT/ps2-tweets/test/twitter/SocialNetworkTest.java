package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant dEpo = Instant.parse(Instant.EPOCH.toString());
    private static final Instant dMax = Instant.parse(Instant.MAX.toString());
    private static final Instant dMin = Instant.parse(Instant.MIN.toString());
    private static final Instant d6 = Instant.parse("1955-02-17T11:00:00Z");
    private static final Instant d7 = Instant.parse("1959-02-17T11:00:00Z");
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "@Madana @Erik talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alfred","@Alfred, @Ironman #feelhappy i am @Batman and @alfred",dEpo);
    private static final Tweet tweet4 = new Tweet(4, "peter","@madana is a hot chick", dMax);
    private static final Tweet tweet5 = new Tweet(5, "madana", "I love his is a handsome man, more handsome tham ", dMin);
    private static final Tweet tweet6 = new Tweet(6, "batman", "i hate @peTer Mit@gmail.com also hate @mADana.",d6);
    private static final Tweet tweet7 = new Tweet(7, "Batman", "i hate @madanA Mit@gmail.com also hate @Madana.",d7);
    private static final Tweet tweet8 = new Tweet(8, "Batman", "i hate @baTMan @BATMAn @madanA Mit@gmail.com also hate @Madana.",d7);
    
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
        

    //test no mentioned case
    @Test
    public void testGuessFollowsGraphNotmentioned(){
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
        Set<String> name = followsGraph.keySet();
        //Set<String> value = new HashSet<String>() ;
        
        for (String n : name) assertTrue(followsGraph.get(n).isEmpty());

        
        
    }
  
    //test tweets mentioned its own author name and other usernames
    @Test
    public void testOneTweetGuessFollowsGraphmentionedSeveralNames(){
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3));
        Set<String> name = followsGraph.keySet();
        Set<String> value = new HashSet<String>(Arrays.asList("ironman","batman"));
        
        for (String n : name){
            for (String v : followsGraph.get(n)){
                if (!v.isEmpty()) assertTrue(value.contains(v.toLowerCase()));
                
            } 
        }
        
    }
    /**
    //test 2 tweets objects, all mention others, each mentioned different names
    @Test
    public void testTwoTweetGuessFollowsGraphmentionedSeveralNames(){
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2,tweet6));
        Set<String> name = new HashSet<String>(Arrays.asList("peter","madana","erik",tweet2.getAuthor(),tweet6.getAuthor()));
        Set<String> value2 = new HashSet<String>(Arrays.asList("madana","erik"));
        Set<String> value6 = new HashSet<String>(Arrays.asList("madana","peter"));
        assertTrue(followsGraph.keySet().size() == 2);
        assertEquals(value2,followsGraph.get(tweet2.getAuthor()));
        assertEquals(value6,followsGraph.get(tweet6.getAuthor()));
        
    }
    
    */
    
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
        
    }
    
    
    
    @Test
    public void testInfluencersNoMentionedName() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected size-one list", influencers.size() == 1);
    }
    
    @Test
    public void testInfluencersNotOnlyFirstIn() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2,tweet3));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.size() == 6);
    }
    
    
    
    @Test
    public void testInfluencersOneTweets() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet4));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        List<String> expectedAnsList = new ArrayList<String>();
        expectedAnsList.add(tweet4.getAuthor());
        expectedAnsList.add("madana");
        
        for (String item : influencers){
            assertTrue("expected",expectedAnsList.contains(item.toLowerCase()));
        }

    }
    
    /**
    @Test
    public void testInfluencersCaseInsenitive() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet8));
        List<String> influencers = SocialNetwork.influencers(followsGraph);     

        assertEquals("", 2, influencers.size());
    }
    */
    
    @Test
    public void testInfluencersTwoTweets() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet4,tweet5,tweet6,tweet7));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        List<String> expectedAnsList = new ArrayList<String>();
        expectedAnsList.add("madana");
        expectedAnsList.add("peter");
        expectedAnsList.add("batman");

        
        
        assertEquals("", expectedAnsList, influencers);
        assertEquals("", expectedAnsList.size(), influencers.size());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
