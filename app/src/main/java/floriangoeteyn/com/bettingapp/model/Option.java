package floriangoeteyn.com.bettingapp.model;

/**
 * Created by floriangoeteyn on 05-Apr-16.
 */
public class Option {
    private String text;
    private String user;
    private int votes;

    public Option(String text, String user) {
        this.text = text;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public String getUser() {
        return user;
    }

    public int getVotes() {
        return votes;
    }

    public void upvote(){
        votes++;
    }

}
