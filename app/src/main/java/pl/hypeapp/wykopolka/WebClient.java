package pl.hypeapp.wykopolka;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (Uri.parse(url).toString().contains("a.wykop.pl")) {
            Log.e("url", " " + Uri.parse(url).getHost());
            return false;
        }
        Log.e("url false", " " + url);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;
    }

    @Override
    public void onLoadResource(WebView view, String url) {

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Log.e("url webView", url);
    }


}
