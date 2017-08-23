package twitter;
 

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.time.Instant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        //throw new RuntimeException("not implemented");
        Instant start = Instant.MAX;
        Instant end = Instant.MIN;
        
        if (tweets.size() == 0) {
            Timespan ans = new Timespan(start,start);
            return ans ;}
        
        for (Tweet item : tweets){ // loop through the list, find the min timestamp and the max timestamp
            Instant temp = item.getTimestamp();
            if (temp.isBefore(start)) {start = temp;}
            if (temp.isAfter(end)) {end = temp;}
                        
        }
        Instant finalStart = Instant.parse(start.toString());
        Instant finalEnd = Instant.parse(end.toString());
        
        Timespan ans = new Timespan(finalStart,finalEnd);
        return ans;
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        //throw new RuntimeException("not implemented");  
        
        String patternString = "(?<![a-zA-Z0-9_-])@([a-zA-Z0-9_-]+)";
        Pattern patternText = Pattern.compile(patternString);
        Set<String> container = new HashSet<String>();
        
        for (Tweet item : tweets){
           String currentText = item.getText();
           Matcher matcher = patternText.matcher(currentText);
           
           while (matcher.find()) {
               String currentString = matcher.group().toLowerCase();
                              
               if (currentString.length() <= 16 && !currentString.equals("@tweet")) {
                   container.add(currentString.substring(1).toLowerCase());}
           }
        }
        return container;
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
