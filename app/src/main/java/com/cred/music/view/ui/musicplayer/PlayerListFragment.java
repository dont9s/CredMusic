package com.cred.music.view.ui.musicplayer;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.cred.music.R;
import com.cred.music.adapter.SongAdapter;
import com.cred.music.databinding.FragmentPlayerListBinding;
import com.cred.music.helper.SongLoadStatus;
import com.cred.music.service.model.Song;
import com.cred.music.view.base.BaseFragment;
import com.cred.music.viewmodel.PlayerListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PlayerListFragment extends BaseFragment<PlayerListViewModel> implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnInfoListener, View.OnTouchListener {

    @Inject
    @VisibleForTesting
    ViewModelProvider.Factory factory;

    private RecyclerView rvSongList;
    private ShimmerFrameLayout loadingViewContainer;
    private LinearLayout llPlayer;
    private FrameLayout flMain;
    private ImageButton ibOpenClose;
    private TextView tvSong, tvArtist;
    private ImageView ivCover;
    private Button btBuy;
    private SeekBar seekBar;
    private ImageButton ibPlayPause;
    private ProgressBar pbLoading;


    private MediaPlayer mediaPlayer;

    private PlayerListViewModel viewModel;
    private LinearLayoutManager layoutManager;
    private SongAdapter adapter;
    private List<Song> songs;

    private int lengthOfAudio;
    private boolean isCollapsed = true;
    private int rotationAngle = 0;
    private FragmentPlayerListBinding listBinding;

    private MutableLiveData<Pair<Song, Boolean>> currentSong = new MutableLiveData<>();


    int flag;
    private final Handler handler = new Handler();

    private final Runnable r = new Runnable() {
        @Override
        public void run() {
            updateSeekProgress();

        }
    };

    @Override
    public PlayerListViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(PlayerListViewModel.class);

        return viewModel;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_player_list, container, false);

        listBinding.setViewModel(viewModel);
        listBinding.setFragment(this);
        listBinding.setLifecycleOwner(this);

        viewModel.setSongLoadStatusLiveData(SongLoadStatus.LOADING);

        initView();
        setupMediaPlayer();

        getObservableAndSet();

        getObservaleSetVisibilities();

        return listBinding.getRoot();
    }

    private void setupMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnInfoListener(this);
        seekBar.setOnTouchListener(this);

        currentSong.observe(getViewLifecycleOwner(), new Observer<Pair<Song, Boolean>>() {
            @Override
            public void onChanged(Pair<Song, Boolean> pair) {
                setupBottomView(pair.second);
            }
        });

    }

    private void initView() {
        rvSongList = listBinding.getRoot().findViewById(R.id.rv_songs);
        loadingViewContainer = listBinding.getRoot().findViewById(R.id.loading_view_container);
        llPlayer = listBinding.getRoot().findViewById(R.id.ll_player);
        flMain = listBinding.getRoot().findViewById(R.id.fl_main);
        ibOpenClose = listBinding.getRoot().findViewById(R.id.ib_open_close);
        tvArtist = listBinding.getRoot().findViewById(R.id.tv_artist);
        tvSong = listBinding.getRoot().findViewById(R.id.tv_song);
        ivCover = listBinding.getRoot().findViewById(R.id.iv_cover);
        btBuy = listBinding.getRoot().findViewById(R.id.bt_buy);
        ibPlayPause = listBinding.getRoot().findViewById(R.id.ib_play_pause);
        seekBar = listBinding.getRoot().findViewById(R.id.seekbar);
        pbLoading = listBinding.getRoot().findViewById(R.id.pb_loading);
        layoutManager = new LinearLayoutManager(getActivity());
        songs = new ArrayList<>();
        adapter = new SongAdapter(songs, currentSong);
        rvSongList.setLayoutManager(layoutManager);
        rvSongList.setAdapter(adapter);
    }


    private void getObservaleSetVisibilities() {
        viewModel.getSongLoadStatusLiveData().observe(getViewLifecycleOwner(), new Observer<SongLoadStatus>() {
            @Override
            public void onChanged(SongLoadStatus songLoadStatus) {

                listBinding.loadingViewContainer.stopShimmerAnimation();
                listBinding.loadingViewContainer.setVisibility(View.GONE);
                listBinding.rvSongs.setVisibility(View.GONE);

                switch (songLoadStatus) {
                    case LOADING:
                        listBinding.loadingViewContainer.setVisibility(View.VISIBLE);
                        listBinding.loadingViewContainer.startShimmerAnimation();
                        break;
                    case SUCCESS_LOADING:

                        listBinding.rvSongs.setVisibility(View.VISIBLE);
                        break;


                    default:

                }

            }
        });
    }

    private void getObservableAndSet() {
        viewModel.getObservableSong().observe(getViewLifecycleOwner(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songList) {
                if (songList != null && songList.size() > 0) {

                    viewModel.setSongLoadStatusLiveData(SongLoadStatus.SUCCESS_LOADING);

                    setAnimationBottomToOriginal(rvSongList);


                    songs.addAll(songList);
                    adapter.notifyDataSetChanged();
                    currentSong.setValue(new Pair<>(songs.get(0), false));

                } else {
                    viewModel.setSongLoadStatusLiveData(SongLoadStatus.LOAD_FAIL);

                }

            }
        });
    }

    private void setupBottomView(boolean isPlayAlso) {
        Glide.with(getActivity())
                .load(currentSong.getValue().first.getCoverImage())
                .into(ivCover);
        tvSong.setText(currentSong.getValue().first.getSong());
        tvArtist.setText(currentSong.getValue().first.getArtists());

        if (isPlayAlso) {
            flag = 0;
            ibPlayPause.setSelected(true);
            seekBar.setProgress(0);
            pauseAudio();
            loadAndPlay();
        }
    }

    private void setAnimationBottomToOriginal(View view) {
        Animation animation;
        animation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.bottom_to_original);
        view.setAnimation(animation);
    }

    private void setAnimationOriginalToBottom(View view) {
        Animation animation;
        animation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.original_to_bottom);
        view.setAnimation(animation);
    }

    @Override
    public void onPause() {
        listBinding.loadingViewContainer.stopShimmerAnimation();
        super.onPause();
    }


    public void onPlayerClick(View view) {
        if (isCollapsed) {

            toggleAngle(ibOpenClose);

            flMain.setVisibility(View.VISIBLE);
            setAnimationBottomToOriginal(flMain);

        } else {
            toggleAngle(ibOpenClose);

            flMain.setVisibility(View.GONE);
            setAnimationOriginalToBottom(flMain);
        }
        isCollapsed = !isCollapsed;
    }

    public void onPlayPause(View view) {
        if (!view.isSelected()) {

            loadAndPlay();
        } else {
            pauseAudio();
        }
        view.setSelected(!view.isSelected());
    }

    private void loadAndPlay() {
        if (flag == 0) {
            loadNewSong();

        } else {
            if (mediaPlayer != null) {
                playAudio();
            }
        }
    }

    private void loadNewSong() {
        pbLoading.setVisibility(View.VISIBLE);
        try {
            mediaPlayer.setDataSource(currentSong.getValue().first.getUrl());
            mediaPlayer.prepareAsync();


            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    lengthOfAudio = mediaPlayer.getDuration();
                    pbLoading.setVisibility(View.GONE);
                    flag = 1;
                    playAudio();
                }

            });


        } catch (Exception e) {
            flag = 0;
        }
    }

    private void toggleAngle(View v) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation", rotationAngle, rotationAngle + 180);
        anim.setDuration(500);
        anim.start();
        rotationAngle += 180;
        rotationAngle = rotationAngle % 360;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setSecondaryProgress(percent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        seekBar.setProgress(0);
        ibPlayPause.setSelected(false);
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:


                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:


                break;
        }
        return true;
    }

    private void playAudio() {

        if (mediaPlayer != null) {
            mediaPlayer.start();
            updateSeekProgress();


        }
    }

    private void pauseAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    private void updateSeekProgress() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                seekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / lengthOfAudio) * 100));


                handler.postDelayed(r, 1000);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {

        if (mediaPlayer.isPlaying()) {
            SeekBar tmpSeekBar = (SeekBar) v;
            mediaPlayer.seekTo((lengthOfAudio / 100) * tmpSeekBar.getProgress());
        } else {
            SeekBar tmpSeekBar = (SeekBar) v;
            mediaPlayer.seekTo((lengthOfAudio / 100) * tmpSeekBar.getProgress());
        }
        return false;
    }
}
