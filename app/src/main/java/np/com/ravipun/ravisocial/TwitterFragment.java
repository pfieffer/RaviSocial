package np.com.ravipun.ravisocial;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;


public class TwitterFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener{
    SwipeRefreshLayout swipeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Twitter.initialize(getActivity());

        if(!checkForConnection()){
            showNoConnectionAlert();
        }else{
            updateTimeline();
        }

    }

    private void showNoConnectionAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("No Internet Connection. Please turn on the Wifi.");
        alertDialogBuilder.setCancelable(true);

        alertDialogBuilder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        updateTimeline();
                    }
                });

        alertDialogBuilder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        updateTimeline();
                    }
                });

        AlertDialog noInternetAlert = alertDialogBuilder.create();
        noInternetAlert.show();
    }

    private void updateTimeline() {

        final UserTimeline userTimeline = new UserTimeline.Builder().screenName("ravigarbuja").build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity())
                .setTimeline(userTimeline)
                .setViewStyle(R.style.tw__TweetLightStyle)
                .build();
        setListAdapter(adapter);


    }


    private Boolean checkForConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onRefresh() {
        if (checkForConnection()){
            updateTimeline();
            swipeLayout.setRefreshing(false);
        }
        else{
            showNoConnectionAlert();
            swipeLayout.setRefreshing(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_twitter, container, false);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        swipeLayout.setOnRefreshListener(this);

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
