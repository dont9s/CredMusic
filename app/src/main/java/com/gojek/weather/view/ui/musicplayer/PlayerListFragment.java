package com.gojek.weather.view.ui.musicplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.gojek.weather.R;
import com.gojek.weather.adapter.SongAdapter;
import com.gojek.weather.databinding.FragmentPlayerListBinding;
import com.gojek.weather.helper.SongLoadStatus;
import com.gojek.weather.service.model.Song;
import com.gojek.weather.view.base.BaseFragment;
import com.gojek.weather.viewmodel.PlayerListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PlayerListFragment extends BaseFragment<PlayerListViewModel> {

    @Inject
    @VisibleForTesting
    ViewModelProvider.Factory factory;

    private RecyclerView rvSongList;
    private ShimmerFrameLayout loadingViewContainer;

    private PlayerListViewModel viewModel;
    private LinearLayoutManager layoutManager;
    private SongAdapter adapter;
    private List<Song> songs;

    private FragmentPlayerListBinding listBinding;

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


        getObservableAndSet();

        getObservaleSetVisibilities();

        return listBinding.getRoot();
    }

    private void initView() {
        rvSongList = listBinding.getRoot().findViewById(R.id.rv_songs);
        loadingViewContainer = listBinding.getRoot().findViewById(R.id.loading_view_container);

        layoutManager = new LinearLayoutManager(getActivity());
        songs = new ArrayList<>();
        adapter = new SongAdapter(songs);
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

                    animateRecyclerView();


                    songs.addAll(songList);
                    adapter.notifyDataSetChanged();

                } else {
                    viewModel.setSongLoadStatusLiveData(SongLoadStatus.LOAD_FAIL);

                }

            }
        });
    }

    private void animateRecyclerView() {
        Animation animation;
        animation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.bottom_to_original);
        rvSongList.setAnimation(animation);
    }

    @Override
    public void onPause() {
        listBinding.loadingViewContainer.stopShimmerAnimation();
        super.onPause();
    }


}
