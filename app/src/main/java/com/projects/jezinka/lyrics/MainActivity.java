package com.projects.jezinka.lyrics;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

public class MainActivity extends AppCompatActivity {

    public static final int AUTH_TOKEN_REQUEST_CODE = 0x10;
    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getCurrentSongClick(View view) {
        clearTextViews();

        if (mToken == null) {
            final AuthenticationRequest request = getAuthenticationRequest(AuthenticationResponse.Type.TOKEN);
            AuthenticationClient.openLoginActivity(this, AUTH_TOKEN_REQUEST_CODE, request);
        } else {
            new SpotifyTrackController(this).start(mToken);
        }
    }

    private void clearTextViews() {
        TextView lyricsView = findViewById(R.id.response_text_view);
        lyricsView.setText("");

        TextView titleView = findViewById(R.id.title_text_view);
        titleView.setText("");
    }

    private AuthenticationRequest getAuthenticationRequest(AuthenticationResponse.Type type) {
        return new AuthenticationRequest.Builder(BuildConfig.CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[]{"user-read-currently-playing"})
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);

        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            mToken = response.getAccessToken();
            new SpotifyTrackController(this).start(mToken);
        }
    }

    private Uri getRedirectUri() {
        return new Uri.Builder()
                .scheme(getString(R.string.com_spotify_sdk_redirect_scheme))
                .authority(getString(R.string.com_spotify_sdk_redirect_host))
                .build();
    }
}
