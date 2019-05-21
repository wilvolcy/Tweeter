package tweeter.util;

import java.util.function.Consumer;

import tweeter.menu.EntryMenu;
import tweeter.menu.LoggedInMenu;
import tweeter.menu.MemuList;
import tweeter.menu.TweetMenu;

public class UiFormat {
	

   public static Simple welcome=()->System.out.println("      >>Tweeter<<");
   
   public static Simple drawTweetTable=()->{
        
            System.out.println("Date#\t\tTweet#\t\tTweet\tComments#\tLikes#");
         
    };
   public static Simple drawProfileTable=()->{
       
        System.out.println("UserID#\tUserName\tFollowing#\tFollower#\tTweets#");
     

    };
 
   public static Consumer<Integer> startBar=(x)->{
       System.out.println("Number\tAction");
            switch (x){
            case 1:entryMenuList();break;
            case 2:logInMenuList();break;
            case 3:tweetMenuList();break;
        }
    };
    public static void entryMenuList() {
    	System.out.println("Press - 1 \tTo create User");
    	System.out.println("Press - 2 \tTo login");
    	System.out.println("Press - 0 \tTo Quit");
    	System.out.print("Your choice :");
         
    }
    public static void logInMenuList() {
        int startIndex=EntryMenu.values().length - 1;
        int i = startIndex;
        
        for (String lm : MemuList.chaineStrings) {
            System.out.println(Integer.toString((i == startIndex) ? 0: i) + " : \t" + lm );
            i++; 
        }
        System.out.print("Your choice : ");
    }
    public  static void tweetMenuList() {
        int startIndex=EntryMenu.values().length + LoggedInMenu.values().length- 2;
     
        int i = startIndex;
        for (TweetMenu tm : TweetMenu.values()) {
          System.out.println(Integer.toString((i == startIndex) ? 0 : i) + " :\t" +tm);
          i++;
    }
        
        System.out.print(" Your choice :");
    }


}
