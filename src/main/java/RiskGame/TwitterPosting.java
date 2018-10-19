package RiskGame;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterPosting {
    static String consumerKey = "yB07xEainC1uXoWj2mcQaiO5k";
    static String consumerSecret="uP88IeWmJFIuOLUDpfdxWF4BXxXpEileyDwVzrnx4LXUIh8vb1";
    static String accessTokenStr= "808529587915423744-bQeJe6njlRaCCI6DjVGDMrnsuaAlduu";
    static String accessTokenKeyStr="3GGFJARvF16MRAGuz1Eixld6gmPdWEO7wUbxL2SKyDr9K";

    public void tweetOut(String message) {
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer(consumerKey, consumerSecret);
            AccessToken accessToken = new AccessToken(accessTokenStr, accessTokenKeyStr);
            twitter.setOAuthAccessToken(accessToken);
            twitter.updateStatus(message);
            System.out.println("Successfully updated the status in Twitter");
        }catch(TwitterException te){
            te.printStackTrace();
        }
    }
}
