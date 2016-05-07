package floriangoeteyn.com.bettingapp.repo;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import floriangoeteyn.com.bettingapp.model.Bet;
import floriangoeteyn.com.bettingapp.model.BetCategory;
import floriangoeteyn.com.bettingapp.model.Option;
import floriangoeteyn.com.bettingapp.rest.BetCallback;

/**
 * Created by floriangoeteyn on 22-Apr-16.
 */
public class Repository {

    private static List<Bet> bets_ = new ArrayList<>();
    private static List<LoadedListener> loadedListeners = new ArrayList<>();
    private static BetCallback callback;

    public static List<Bet> getBets(){
        return bets_;
    }

    public static void loadBets(){
        callback = new BetCallback();
    }

    public static void registerListener(LoadedListener listener){
        loadedListeners.add(listener);
    }

    public static void onBetsLoaded(List<Bet> bets){
        bets_=bets;
        notifyListenersBetsLoaded();
    }

    public static void upvoteAnswer(Bet bet, Option option){
        if(bet.getOptionA()==option){
            bet.getOptionA().upvote();
        }
    }

    public static void onLoadFailed(){

    }

    private static void notifyListenersBetsLoaded(){
        for(LoadedListener listener: loadedListeners){
            listener.onBetsLoaded();
        }
    }


}
