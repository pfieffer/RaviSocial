package np.com.ravipun.ravisocial;


import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class FacebookFragment extends  Fragment implements SwipeRefreshLayout.OnRefreshListener{
    WebView facebookPage;

    int small = 0, big = 0;

    private SwipeRefreshLayout mSwipeRefreshLayout;  //object for swipeRefresh


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_facebook, container, false);



        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        facebookPage = (WebView) v.findViewById(R.id.facebook_page);
        data_loading();


        return v;


    }


    @Override
    public void onRefresh() {
        data_loading();

    }


    public void data_loading() {

        ConnectivityManager connectivity = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        if (connectivity.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            facebookPage.setVisibility(View.VISIBLE);
            facebookPage.setBackgroundColor(0);
            facebookPage.getSettings().setJavaScriptEnabled(true);
            facebookPage.setWebViewClient(new WebViewClient());


            DisplayMetrics dm = getActivity().getApplicationContext().getResources().getDisplayMetrics();
            int densityDpi = dm.densityDpi;

            Log.e("value=", String.valueOf(densityDpi));

            if (densityDpi > 240) {
                facebookPage.loadUrl("file:///android_asset/facebook_page.html");
                big = 1;
            } else if (densityDpi <= 240) {
                facebookPage.loadUrl("file:///android_asset/facebook_smallpage.html");
                small = 1;
            }

            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }


        } else {

            facebookPage.setVisibility(View.INVISIBLE);

            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }

        }

    }

}
