package com.example.codeflux;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment {

    private List<Video> videoList;
    private VideoAdapter videoAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the title of the activity hosting the fragment
        requireActivity().setTitle("Video Library");

        // Initialize video list
        initializeVideoList();

        // Find views
        SearchView searchView = view.findViewById(R.id.searchView);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoAdapter = new VideoAdapter(videoList);
        recyclerView.setAdapter(videoAdapter);

        // Set up search view listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterVideos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterVideos(newText);
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    private void initializeVideoList() {
        videoList = new ArrayList<>();
        videoList.add(new Video("Php programming", "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/BUCiSSyIGGU?si=0myi1cWFiINm7PVn\" frameborder=\"0\" allowfullscreen></iframe>"));
        videoList.add(new Video("Java programming", "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/JPOzWljLYuU?si=DkcTHHfSn6IvikdW\" frameborder=\"0\" allowfullscreen></iframe>"));
        videoList.add(new Video("Javascript programming", "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/PkZNo7MFNFg?si=CWR7LkMlahqJHMiq\" frameborder=\"0\" allowfullscreen></iframe>"));
        videoList.add(new Video("C++ programming", "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/6y0bp-mnYU0?si=cvsT5Ud5sz_tMg6J\" frameborder=\"0\" allowfullscreen></iframe>"));
        videoList.add(new Video("Python programming", "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/chPhlsHoEPo?si=q7u9ypDmOnsHN-BA\" frameborder=\"0\" allowfullscreen></iframe>"));
    }

    private void filterVideos(String query) {
        List<Video> filteredVideos = new ArrayList<>();
        if (query == null || query.isEmpty()) {
            filteredVideos = videoList;
        } else {
            for (Video video : videoList) {
                if (video.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredVideos.add(video);
                }
            }
        }
        videoAdapter.updateVideos(filteredVideos);
    }

    private static class Video {
        private final String title;
        private final String embedCode;

        public Video(String title, String embedCode) {
            this.title = title;
            this.embedCode = embedCode;
        }

        public String getTitle() {
            return title;
        }

        public String getEmbedCode() {
            return embedCode;
        }
    }

    private class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

        private List<Video> videos;

        public VideoAdapter(List<Video> videos) {
            this.videos = videos;
        }

        @NonNull
        @Override
        public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
            return new VideoViewHolder(view);
        }

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
            Video video = videos.get(position);
            holder.titleTextView.setText(video.getTitle());
            holder.webView.loadData(video.getEmbedCode(), "text/html", "utf-8");
            holder.webView.getSettings().setJavaScriptEnabled(true);
            holder.webView.setWebChromeClient(new MyWebChromeClient(holder.webView));
            holder.webView.setWebViewClient(new WebViewClient());
        }

        @Override
        public int getItemCount() {
            return videos.size();
        }

        public void updateVideos(List<Video> updatedVideos) {
            this.videos = updatedVideos;
            notifyDataSetChanged();
        }

        class VideoViewHolder extends RecyclerView.ViewHolder {
            TextView titleTextView;
            WebView webView;

            public VideoViewHolder(@NonNull View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.videoTitle);
                webView = itemView.findViewById(R.id.webView);
            }
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        private final WebView mWebView;
        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;
        private boolean isFullScreen = false;

        MyWebChromeClient(WebView webView) {
            mWebView = webView;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            // If a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                onHideCustomView();
                return;
            }

            // Store the current state
            mCustomView = view;
            mOriginalSystemUiVisibility = requireActivity().getWindow().getDecorView().getSystemUiVisibility();
            mOriginalOrientation = requireActivity().getRequestedOrientation();

            // Hide the WebView and set the custom view to full screen
            mWebView.setVisibility(View.GONE);
            requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            FrameLayout decor = (FrameLayout) requireActivity().getWindow().getDecorView();
            decor.addView(mCustomView, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            mCustomViewCallback = callback;
            isFullScreen = true;
        }

        @Override
        public void onHideCustomView() {
            // Revert back to the original state
            FrameLayout decor = (FrameLayout) requireActivity().getWindow().getDecorView();
            decor.removeView(mCustomView);
            mCustomView = null;
            mWebView.setVisibility(View.VISIBLE);
            requireActivity().setRequestedOrientation(mOriginalOrientation);
            requireActivity().getWindow().getDecorView().setSystemUiVisibility(mOriginalSystemUiVisibility);

            // Call the custom view callback
            if (mCustomViewCallback != null) {
                mCustomViewCallback.onCustomViewHidden();
            }
            mCustomViewCallback = null;
            isFullScreen = false;
        }

        @Override
        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getResources(), android.R.color.black);
        }

        public boolean isFullScreen() {
            return isFullScreen;
        }
    }

}
