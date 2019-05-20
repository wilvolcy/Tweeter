package tweeter;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//User ericUser = new User("eric","123".1);
		
		Tweet t1 = new Tweet("message Jean wilbert @volcy");
		Tweet t2 = new Tweet("message de  Jean wilbert ");
		Tweet t3 = new Tweet("message de  Jean wilbert ");
		
		List<Tweet> list=new ArrayList<Tweet>();
		
		list.add(t1);
		list.add(t1);
		list.add(t1);
		
		String aString="@volcy";
	
		String bString=aString.substring(1);
		
		System.out.println(bString);
		
		System.out.println(TweetValidate.viewAllTweetsMention1.apply(list, "volcy"));
		
	 
	}

}
