package tweeter;
import tweeter.model.*;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.CoreMatchers;

import tweeter.model.Tweet;
import tweeter.model.User;
import tweeter.service.TweetService;
import tweeter.service.UserService;
import tweeter.util.UiFormat;


public class App {

    private static Scanner scan;
    private static List<User> userList;
    private static final int start =1;

    App() { 
        userList = new ArrayList<>();
        UiFormat.welcome.draw();  //ok
        scan = new Scanner(System.in); //ok

    }
    

    public static Supplier<List<User>> getUserList=()-> userList;

    private Function<Integer,Integer> getStart=x-> {
        UiFormat.startBar.accept(x);
        return scan.nextInt();
    };

    private void userRegister() {
        String[] userRegInfo = new String[2];
        scan.nextLine();
        System.out.print("\tEnter UserName :");
        userRegInfo[0] = (scan.nextLine());

        //check if the username is already taken
        while (UserService.checkUserName.apply(userList,userRegInfo[0])){
            System.out.println("User name already exists.\nEnter new User Name\n" +
                    "(or enter 'x' to return to main menu\n");
            userRegInfo[0] = (scan.nextLine());
        }
        System.out.print("\tEnter PassWord :");
        userRegInfo[1] = (scan.nextLine());
        userList.add(UserService.createUser.apply(userList,userRegInfo));
        System.out.println(" \tSuccessful !!!\n");
        User loggingUser=UserService.getUser.apply(userList,userRegInfo);
        succeedLogIn.accept(loggingUser);
        
    }
    
    private void userLogIn() { 
        scan.nextLine();
        String[] userInfo = new String[2];
        System.out.println("\tEnter UserName");
        userInfo[0] = (scan.nextLine());
        System.out.println("\tEnter PassWord");
        userInfo[1] = (scan.nextLine());
        User loggingUser=UserService.getUser.apply(userList,userInfo);
        if (loggingUser==null)
            System.out.println("User name and/or password provided does not match with record");
        else {
            System.out.println("\nLog-in Successful\n");
            succeedLogIn.accept(loggingUser);
        }
    }
    public static Consumer<User> succeedLogIn= user -> {
       
        
        UiFormat.startBar.accept(2);
        switch (scan.nextInt()) {
           
            case 0:
                System.out.println("Main Menu");
                return;
            case 3:
                TweetService.addTweet.accept(user,scan);
                break;
            case 4:
                TweetService.viewMyTweets.accept(user,scan);
                break;
            case 5:
                TweetService.viewAllTweets.accept(user);
                break;
            case 6:
                UserService.showFollowers.accept(user);
                break;
            case 7:
                UserService.showFollowings.accept(user);
                break;
            case 8:
                UserService.showRecommendation.accept(userList,user);
                break;
            case 9:
                addFollowing(user);
                break;
            case 10:
            	scan.nextLine();
            	System.out.print("Enter the user :");
            	
            	String reString= scan.nextLine();
            	System.out.println("The result is :"+TweetService.numbOfTimMentionUser.apply(user.getTweets(), reString));
            	 
                break;
            default:
                System.out.println("Select valid number from the list");
                break;
        }
    };

    public static void reactionOnTweet(User user){
        UiFormat.startBar.accept(3);
        int userInput = scan.nextInt();
        switch (userInput) {
      
            case 0:
                System.out.println("Going Back to Profile");
                return;
            case 11:
                reTweet(user);
                break;
            case 12:
                replyOnTweet(user);
                break;
            case 13:
                likeTweet(user);
                break;
            case 14:
                unlikeTweet(user);
                break;
            case 15:
                deleteTweet(user);
                break;
            default:
                System.out.println("Select valid number from the list");
                reactionOnTweet(user);
        }
    }

 
    private static Tweet getTweetFromID(User user){
      
        System.out.println("Enter tweet ID as listed under column:Tweet#");
        int userInput=scan.nextInt();
        if(userInput==0)
            return null;
        Tweet tweet=TweetService.validateTweet.apply(userInput,user);
        if(tweet==null) {
            System.out.println("Invalid tweet ID, type again or enter '0' to go back");
            getTweetFromID(user);
        }
        return tweet;

    }
    private static void reTweet(User user){
        Tweet tweet=getTweetFromID(user);
        if(tweet!=null) 
            user.addRetweet(tweet);
    }

    private static void replyOnTweet(User user){
        Tweet tweet=getTweetFromID(user);
        if(tweet!=null)
        {
            scan.nextLine();
            System.out.print("Type here your reply\n ");
            String reply = scan.nextLine();
            tweet.addReply(reply,user.getUserId());
        }
        succeedLogIn.accept(user);
    }
    private static void likeTweet(User user){
        Tweet tweet=getTweetFromID(user);
        if(tweet!=null){
            tweet.addLikeOnThisTweet(user.getUserId());
        }
    }
    private static void unlikeTweet(User user){
        Tweet tweet=getTweetFromID(user);
        List<User> userAndItsFollowings = new ArrayList<>();
        userAndItsFollowings.add(user);
        userAndItsFollowings.addAll(user.getFollowings());
        if(userAndItsFollowings.stream().anyMatch(u -> u.getTweets().contains(tweet))) {
            assert tweet != null;
            tweet.removeLikeOnThisTweet(user.getUserId());
        }
    }
    private static void deleteTweet(User user){
        Tweet tweet=getTweetFromID(user);
        if(user.getTweets().contains(tweet))
            user.getTweets().remove(tweet); 
        else
            System.out.println("The tweet either doesn't belong to you or it does not exist");
    }




     private static void addFollowing(User user){
        System.out.println("Enter User ID you want to follow:");
        User userToFollow=UserService.findByUserId.apply(UserService.extractedUserList.apply(userList,user),
                scan.nextInt());
        if(userToFollow!=null)
            user.addFollowing(userToFollow);
        else System.out.println("You are already following or ID not found in record");
        succeedLogIn.accept(user);
    }

    public static void main(String[] args) {
        App twitter = new App();

        try {
            while (true) {
                int initValue = twitter.getStart.apply(start);
                switch (initValue) {
                    case 0:
                        System.out.println("Exiting from App");
                        return;
                    case 1:
                        twitter.userRegister();
                        break;
                    case 2:
                        twitter.userLogIn();
                        break;
                    default:
                        System.out.println("\nSelect valid number from the list");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        } finally {
            scan.close();
        }

    }

}
