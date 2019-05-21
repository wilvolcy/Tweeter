package tweeter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class User {

    private String userName;
    private String password;
    private int userId;
    private List<User> followers;
    private List<User> followings;
    private List<Tweet> tweets;

    public User(){

    }

    public User(String userName, String password, int id) {
        this.userName = userName;
        this.password = password;
        this.userId= id;
        followers=new ArrayList<>();
        followings=new ArrayList<>();
        tweets=new ArrayList<>();

    }

    public String getUserName() {return userName;}
    public String getPassword() {return password;}
    public int getUserId() {return userId;}
    public List<User> getFollowers() {return followers;}
    public List<User> getFollowings() {return followings;}
    public List<Tweet> getTweets() {return tweets;}
    @Override
    public String toString(){return this.userId+" "+this.userName;}

    public void addTweet(Tweet tweet){
        tweet.setUserId(this.userId);
        tweet.setUser(this);
        tweet.setTweetId(RandomID.getID());
        this.tweets.add(tweet);
    }
    public void addRetweet(Tweet reTweet){
        reTweet.setUserId(this.userId);
        reTweet.setUser(this);
        reTweet.addRetweet(reTweet);
        this.tweets.add(reTweet);
    }

    public void addFollower(User user) {
        if (getFollower(user) != null) {
            System.out.println("This user is already following you.");
            return;
        }
        this.followers.add(user);
    }

    public void removeFollower(User user){
        if (getFollower(user) == null) {
            System.out.println("This user is not following you.");
            return;
        }

        followers.remove(user);
    }

    private User getFollower(User user) {
        return followers
                .stream()
                .reduce(null, (accum, user2) -> user2.getUserId() == user.getUserId() ? user2 : accum);
    }

    public void addFollowing(User user){
        this.followings.add(user);
        user.followers.add(this);
    }
    private User findByUserId(List<User> users, int id) {
        return users
                .stream()
                .reduce(null, (accum, user) -> user.getUserId() == id ? user : accum);
    }


    public void print() {
        System.out.println(userId + "\t" + userName + "\t" + followings.size() +
                "\t" + followers.size() + "\t" + tweets.size());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        User user = (User) obj;
        return user.getUserId() == this.userId;
    }

    static class RandomID {
        private RandomID(){}

        static final List<Integer>  numbers = new Random(1000).ints(0,350).limit(100)
                .boxed()
                .distinct()
                .collect(Collectors.toList());
        static int i=-1;
        public static int getID(){
            return numbers.get(++i);
        }

    }

}
