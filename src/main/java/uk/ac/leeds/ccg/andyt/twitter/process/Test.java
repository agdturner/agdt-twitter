/*
 * Copyright 2017 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.andyt.twitter.process;

//import twitter4j.api;
//import twitter4j.auth.;
//import twitter4j.conf.;
//import twitter4j.json.;
//import twitter4j.management.;
//import twitter4j.util;
//import twitter4j.util.function.;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RawStreamListener;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import uk.ac.leeds.ccg.andyt.data.core.Data_Environment;
import uk.ac.leeds.ccg.andyt.data.format.Data_ReadTXT;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_IO;

public class Test implements StatusListener {

    /**
     * Main entry of this application.
     *
     * @param args arguments doesn't take effect with this example
     * @throws TwitterException when Twitter service or network is unavailable
     */
    public static void main(String[] args) {
        try {
        List<String> l = searchtweets();
        Iterator<String> ite;
        ite = l.iterator();
        while (ite.hasNext()) {
            System.out.println(ite.next());
        }
//        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
//        RawStreamListener listener = new RawStreamListener() {
//            @Override
//            public void onMessage(String rawJSON) {
//                System.out.println(rawJSON);
//            }
//
//            @Override
//            public void onException(Exception ex) {
//                ex.printStackTrace();
//            }
//        };
//        twitterStream.addListener(listener);
//        twitterStream.sample();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    static Twitter getTwitterinstance() throws IOException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        File d = new File(System.getProperty("user.dir"), "data");
        d = new File(d, "twitter");
        d = new File(d, "config");
        File f = new File(d, "twitter4j.properties");
        Data_ReadTXT reader = new Data_ReadTXT(new Data_Environment());
        ArrayList<String> lines = reader.read(f, f.getParentFile(), 7);
        Iterator<String> ite;
        ite = lines.iterator();
        String consumerKey = ite.next().replace("oauth.consumerKey", "");
        String consumerSecret = ite.next().replace("oauth.consumerSecret", "");
        String accessToken = ite.next().replace("oauth.accessToken", "");
        String accessTokenSecret = ite.next().replace("oauth.accessTokenSecret", "");
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return twitter;
    }

    public static List<String> searchtweets() throws TwitterException, IOException {

        ArrayList<String> result;
        result = new ArrayList<>();
        Twitter twitter = getTwitterinstance();
        //Query query = new Query("source:twitter4j agdturner");
//        Query query = new Query("test");
//        QueryResult result = twitter.search(query);

        try {
            Query query = new Query("source:twitter4j agdturner");
            //Query query = new Query("Test");
            QueryResult queryResult;
            queryResult = twitter.search(query);
            List<Status> tweets = queryResult.getTweets();
            for (Status tweet : tweets) {
                result.add("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
            }
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }

//        return result.getTweets().stream()
//                .map(item -> item.getText())
//                .collect(Collectors.toList());
        return result;
    }

    @Override
    public void onStatus(Status status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice sdn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onTrackLimitationNotice(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onScrubGeo(long l, long l1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onStallWarning(StallWarning sw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onException(Exception excptn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
