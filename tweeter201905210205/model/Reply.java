package tweeter.model;

import java.time.LocalDate;

import tweeter.App;
import tweeter.service.UserService;

public class Reply {
    private String reply;
    private int tweetId;
    private int userId; 
    private LocalDate date;
   public Reply(){}
    protected Reply(String comment, int tweetId, int userId) {
        this.reply = comment;
        this.tweetId = tweetId;
        this.userId = userId;
        this.date=LocalDate.now(); 
    }

    protected String getReply() {
        return reply;
    }

    protected LocalDate getDate() {
        return date;
    }

    
    
    public int getTweetId() {
		return tweetId;
	}
	public int getUserId() {
		return userId;
	}
	public void setReply(String reply) {
		this.reply = reply; 
	}
	public void setTweetId(int tweetId) {
		this.tweetId = tweetId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public void print() {
        System.out.println("\tBy: "+UserService.findByUserId.apply(App.getUserList.get(),userId) +
                "\n\t  "+ reply +"\n\t  " + date);
    }
}
