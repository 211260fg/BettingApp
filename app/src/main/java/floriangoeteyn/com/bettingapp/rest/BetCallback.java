package floriangoeteyn.com.bettingapp.rest;

import android.util.Log;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.List;

import floriangoeteyn.com.bettingapp.model.Bet;
import floriangoeteyn.com.bettingapp.repo.Repository;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by floriangoeteyn on 22-Apr-16.
 */
public class BetCallback implements Callback<List<Bet>> {


    private RestClient restClient;


    public BetCallback() {

        restClient = new RestClient("", "");

        restClient.getItemClient().getBets().enqueue(this);
    }

    @Override
    public void onResponse(Response<List<Bet>> response) {
        if(response.isSuccess()) {
            Repository.onBetsLoaded(response.body());
            Log.d("load success", "size = "+response.body().size());
        }
        else {
            Repository.onLoadFailed();
            Log.d("load failed", String.valueOf(response.errorBody()));
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Repository.onLoadFailed();
        Log.d("load failed", "no response: \n"+ExceptionUtils.getStackTrace(t));
    }
}
