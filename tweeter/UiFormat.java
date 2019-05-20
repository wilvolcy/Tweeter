package tweeter;

//import jdk.dynalink.beans.StaticClass;

import java.lang.invoke.SwitchPoint;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

class UiFormat {

    static Simple welcome=()->System.out.println("                     >>Tweeter<<");
   // static Simple drawLine =()->System.out.println("--------------------------------------------------");
    static Simple drawTweetTable=()->{
           // drawLine.draw();
            System.out.println("Date#\t\tTweet#\tTweet\tComments#\tLikes#");
           // drawLine.draw();
    };
    static Simple drawProfileTable=()->{
       // drawLine.draw();
        System.out.println("UserID#\tUserName\tFollowing#\tFollower#\tTweets#");
      //  drawLine.draw();

    };
 
    static Consumer<Integer> startBar=(x)->{
       System.out.println("Number\tAction");
            switch (x){
            case 1:entryMenuList();break;
            case 2:logInMenuList();break;
            case 3:tweetMenuList();break;
        }
    };
    static void entryMenuList() {
    	System.out.println("Press - 1 \tTo create User");
    	System.out.println("Press - 2 \tTo login");
    	System.out.println("Press - 0 \tTo Quit");
    	System.out.print("Your choice :");
         
    }
    static void logInMenuList() {
        int startIndex=EntryMenu.values().length - 1;
        int i = startIndex;
        for (LoggedInMenu lm : LoggedInMenu.values()) {
            System.out.println(Integer.toString((i == startIndex) ? 0: i) + " >>\t" + lm);
            i++;
        }
     
        System.out.print("Your choice : ");
    }
    static void tweetMenuList() {
        int startIndex=EntryMenu.values().length + LoggedInMenu.values().length- 2;
        int i = startIndex;
        for (TweetMenu tm : TweetMenu.values()) {
            System.out.println(Integer.toString((i == startIndex) ? 0 : i) + " >>\t" + tm);
            i++;
        }
     
        System.out.print(" Your choice :");
    }


}
