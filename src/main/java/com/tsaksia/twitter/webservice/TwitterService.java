package com.tsaksia.twitter.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by samyboukhris on 19/05/2018.
 */
@RestController
@RequestMapping("/api")
public class TwitterService {

    private static final String URL = "https://api.twitter.com/1.1";
    private final Twitter twitter;

    @Autowired
    private TwitterService(Twitter twitter) {
        this.twitter = twitter;
    }


    /**
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/lookfor")
    public List<TwitterProfile> lookForPopular(@PathParam("name") String name) {
       /* Trends results = this.twitter.searchOperations().getLocalTrends(615702L);
        return results.getTrends();*/

        List<TwitterProfile> results = this.twitter.userOperations().searchForUsers(name);
        List<TwitterProfile> collect = results.stream()
                                              .filter(twitterProfile -> twitterProfile.getFollowersCount() > 100)
                                              .filter(twitterProfile -> twitterProfile.getFriendsCount() < 20)
                                              .filter(TwitterProfile::isVerified)
                                              .collect(Collectors.toList());
        return collect;
    }

    /**
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/indice")
    public Integer influenceIndic(@PathParam("name") String name) {
        List<TwitterProfile> results = this.twitter.userOperations().searchForUsers(name);
        Integer friendsNumber = results.stream()
                                       .map(TwitterProfile::getFriendsCount)
                                       .reduce(0, (tp, tp2) -> tp + tp2);
        Integer sumFollowers = results.stream()
                                      .map(TwitterProfile::getFollowersCount)
                                      .reduce(0, (a, b) -> a + b);
        return friendsNumber + sumFollowers;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/indicetag")
    public long influenceIndicHashTag(@PathParam("name") String name) {
        SearchResults results = this.twitter.searchOperations().search("#" + name);
        long integerStream = results.getTweets()
                                    .stream()
                                    .map(Tweet::getRetweetCount)
                                    .reduce(0, (a, b) -> a + b);
        return integerStream;
    }

  /*  @RequestMapping(method = RequestMethod.GET, path = "/rest")
    public List<Trend> rest() {
        Trends search = this.twitter.restOperations().getLocalTrends(615702L);
        return search.getTrends();
    }*/

}
//Paris : 615702
//Los Angeles California	United States	2442047
//Madrid Madrid	Spain	20224256

//NONE Tokyo Prefecture	Japan	1118370
//NONE Tokyo Prefecture	Japan	2345889

//San Francisco California	United States	2487956

//Greater London England	United Kingdom	44418