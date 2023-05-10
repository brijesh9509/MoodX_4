package com.moodX.app.nav_fragments;

import static com.moodX.app.utils.Constants.getDeviceId;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moodX.app.BuildConfig;
import com.moodX.app.utils.ApiResources;
import com.moodX.app.utils.MyAppClass;
import com.moodX.app.utils.PreferenceUtils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.moodX.app.AppConfig;
import com.moodX.app.MainActivity;
import com.moodX.app.R;
import com.moodX.app.adapters.GenreAdapter;
import com.moodX.app.models.CommonModels;
import com.moodX.app.models.home_content.AllGenre;
import com.moodX.app.network.RetrofitClient;
import com.moodX.app.network.apis.GenreApi;
import com.moodX.app.utils.NetworkInst;
import com.moodX.app.utils.SpacingItemDecoration;
import com.moodX.app.utils.ToastMsg;
import com.moodX.app.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class GenreFragment extends Fragment {

    ShimmerFrameLayout shimmerFrameLayout;
    private RecyclerView recyclerView;
    private final List<CommonModels> list = new ArrayList<>();
    private GenreAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CoordinatorLayout coordinatorLayout;
    private TextView tvNoItem;

    private MainActivity activity;

    private LinearLayout searchRootLayout;

    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    private ImageView menuIv, searchIv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.layout_country,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.genre));

        coordinatorLayout=view.findViewById(R.id.coordinator_lyt);
        shimmerFrameLayout=view.findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout=view.findViewById(R.id.swipe_layout);
        recyclerView=view.findViewById(R.id.recyclerView);
        tvNoItem=view.findViewById(R.id.tv_noitem);
        searchRootLayout=view.findViewById(R.id.search_root_layout);
        CardView searchBar = view.findViewById(R.id.search_bar);
        menuIv              = view.findViewById(R.id.bt_menu);
        TextView pageTitle = view.findViewById(R.id.page_title_tv);
        searchIv            = view.findViewById(R.id.search_iv);

        pageTitle.setText(getContext().getResources().getString(R.string.genre));


        if (activity.isDark) {
            pageTitle.setTextColor(activity.getResources().getColor(R.color.white));
            searchBar.setCardBackgroundColor(activity.getResources().getColor(R.color.black_window_light));
            menuIv.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_menu));
            searchIv.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_search_white));
        }

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(getActivity(), 10), true));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new GenreAdapter(activity, list,"genre", "");
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                    animateSearchBar(true);
                    controlsVisible = false;
                    scrolledDistance = 0;
                } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                    animateSearchBar(false);
                    controlsVisible = true;
                    scrolledDistance = 0;
                }

                if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
                    scrolledDistance += dy;
                }


            }
        });

        shimmerFrameLayout.startShimmer();



        if (new NetworkInst(getContext()).isNetworkAvailable()){
            getAllGenre();
        }else {
            tvNoItem.setText(getString(R.string.no_internet));
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            coordinatorLayout.setVisibility(View.VISIBLE);
        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                coordinatorLayout.setVisibility(View.GONE);

                recyclerView.removeAllViews();
                list.clear();
                mAdapter.notifyDataSetChanged();

                if (new NetworkInst(getContext()).isNetworkAvailable()){
                    getAllGenre();
                }else {
                    tvNoItem.setText(getString(R.string.no_internet));
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    coordinatorLayout.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        menuIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openDrawer();
            }
        });
        searchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.goToSearchActivity();
            }
        });

    }


    private void getAllGenre(){
        String userId = PreferenceUtils.getUserId(activity);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        GenreApi api = retrofit.create(GenreApi.class);
        Call<List<AllGenre>> call = api.getGenre(MyAppClass.API_KEY, BuildConfig.VERSION_CODE,userId,
                getDeviceId(activity));
        call.enqueue(new Callback<List<AllGenre>>() {
            @Override
            public void onResponse(Call<List<AllGenre>> call, retrofit2.Response<List<AllGenre>> response) {
                if (response.code() == 200){
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);

                    if (response.body().size() == 0){
                        coordinatorLayout.setVisibility(View.VISIBLE);
                    }else {
                        coordinatorLayout.setVisibility(View.GONE);
                    }

                    for (int i = 0; i < response.body().size(); i++) {
                        AllGenre genre = response.body().get(i);
                        CommonModels models = new CommonModels();
                        models.setId(genre.getGenreId());
                        models.setTitle(genre.getName());
                        models.setImageUrl(genre.getImageUrl());
                        list.add(models);
                    }
                    mAdapter.notifyDataSetChanged();
                }else if (response.code() == 412) {
                    try {
                        if (response.errorBody() != null) {
                            try {
                                ApiResources.openLoginScreen(response.errorBody().string(),
                                        activity);
                                activity.finish();
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(activity,
                                e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else {
                    swipeRefreshLayout.setRefreshing(false);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    new ToastMsg(getActivity()).toastIconError(getString(R.string.fetch_error));
                    coordinatorLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<AllGenre>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                new ToastMsg(getActivity()).toastIconError(getString(R.string.fetch_error));
                coordinatorLayout.setVisibility(View.VISIBLE);
            }
        });
    }


    boolean isSearchBarHide = false;

    private void animateSearchBar(final boolean hide) {
        if (isSearchBarHide && hide || !isSearchBarHide && !hide) return;
        isSearchBarHide = hide;
        int moveY = hide ? -(2 * searchRootLayout.getHeight()) : 0;
        searchRootLayout.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }



}

