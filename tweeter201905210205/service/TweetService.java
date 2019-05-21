package tweeter.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tweeter.App;
import tweeter.model.Like;
import tweeter.model.Reply;
import tweeter.model.Tweet;
import tweeter.model.User;
import tweeter.util.UiFormat;

public class TweetService {
	
	public static BiFunction<List<Tweet>, Tweet, List<Tweet>> addReTweetFunction =
			(list,tweet) ->Stream.concat(list.stream(), Stream.of(tweet))
			.collect(Collectors.toList());
			
	public static BiFunction<List<Reply>, Reply, List<Reply>> addComment =
			(list,reply)->Stream.concat(list.stream(), Stream.of(reply))
			.collect(Collectors.toList());
			
			
	public static BiFunction<List<Like>, Like, List<Like>> addLikeFunction =
			(list,like)->Stream.concat(list.stream(), Stream.of(like))
			.collect(Collectors.toList());
			
	
	public static Consumer<User> viewTweets=user -> {
        UiFormat.drawTweetTable.draw();
        user.getTweets().stream()
                .sorted(Comparator.comparing(Tweet::getDate).reversed())
                .forEach(Tweet::print);
    };
    public static Consumer<User> viewAllTweets = user -> {
        UiFormat.drawTweetTable.draw();
        Stream.concat(user.getTweets().stream(),
                user.getFollowings().stream()
                        .flatMap(user1 -> user1.getTweets().stream()))
                .sorted(Comparator.comparing(Tweet::getDate).reversed()).forEach(Tweet::print);
        App.reactionOnTweet(user);
        App.succeedLogIn.accept(user);
    };

 
    public static BiConsumer<User, Scanner> addTweet= (user,scan) -> {
       
        scan.nextLine();
        System.out.print("Type your tweet here:\t");
        user.addTweet(new Tweet(scan.nextLine()));
        System.out.println("Tweet added successfully");
        App.succeedLogIn.accept(user);

    };
    public  static BiConsumer<User, Scanner> viewMyTweets=(user,scan) ->  {
        TweetService.viewTweets.accept(user);
        App.reactionOnTweet(user);
        App.succeedLogIn.accept(user);

    };
    
  
    public static BiFunction<Integer, User, Tweet> validateTweet=(tweetID, user)->{
        return Stream.concat(user.getTweets().stream(),user.getFollowings().stream()
                .flatMap(user1 -> user1.getTweets().stream()))
                .filter(tweet -> tweet.getTweetId()==tweetID)
                .collect(Collectors.toList()).get(0);
    };
    
    public static BiFunction<List<Tweet>, String, Long> numbOfTimMentionUser=(tweets, sc) ->tweets.stream()
    		.flatMap(twe ->Stream.of( twe.getTweet().split(" "))
    				.filter(tw -> tw.startsWith("@"))
    				.filter(name->name.substring(1).equals(sc))).count();
    


}
