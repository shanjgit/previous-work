package twitter;

import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;


/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        
        Map<String, Set<String>> networkMap = new HashMap<String, Set<String>>();
        Set<String> setOfMentionedUserName;
        
        for (Tweet item : tweets ){
            
            setOfMentionedUserName = new HashSet<String>();
            setOfMentionedUserName.addAll(Extract.getMentionedUsers(Arrays.asList(item)));
            
            String itemsUserName = item.getAuthor().toLowerCase();
            if (setOfMentionedUserName.contains(itemsUserName)) setOfMentionedUserName.remove(itemsUserName);
            if (networkMap.get(itemsUserName) == null){
                networkMap.put(itemsUserName, setOfMentionedUserName);
            } else {
                networkMap.get(itemsUserName).addAll(setOfMentionedUserName);
            }
            

        }
        
     return networkMap;   
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        
        Set<String> username = new HashSet<String>(
                setToLowerCase(followsGraph.keySet()));
        
        for (String user : followsGraph.keySet()){
            username.addAll(setToLowerCase(followsGraph.get(user)));
        }
        
        Map<String, Integer> followerCount = new HashMap<String, Integer>();
        
        
        for (String item : username) followerCount.put(item, 0);
        
        for (String keyname : followsGraph.keySet()){
            Set<String> currentSet = setToLowerCase(followsGraph.get(keyname));
            for (String name : username){
                if (currentSet.contains(name)){
                    int tmp = followerCount.get(name) + 1;
                    followerCount.put(name, tmp);
                                             }           
                    }
                }
        
        List<Entry<String,Integer>> entryList = new ArrayList<Entry<String,Integer>>(followerCount.entrySet());
        
        Collections.sort(entryList,
        new Comparator<Entry<String,Integer>>() {
            public int compare(Entry<String,Integer> o1, Entry<String,Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        
        List<String> ansList = new ArrayList<String>();
        for (Entry<String,Integer> item : entryList) {ansList.add(item.getKey());}



        
        
        
        return ansList;
        }
    
    private static Set<String> setToLowerCase(Set<String> strings) {
        Set<String> lowerSet = new HashSet<String>();
        for (String s : strings) {
            lowerSet.add(s.toLowerCase());
        }
        return lowerSet;
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
