package edu.nanodegreeprojects.capstone.travelendar.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import edu.nanodegreeprojects.capstone.travelendar.R;
import edu.nanodegreeprojects.capstone.travelendar.activities.MainActivity;
import edu.nanodegreeprojects.capstone.travelendar.data.ContentProviderContract;
import edu.nanodegreeprojects.capstone.travelendar.model.Trip;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripAdapterViewHolder> {

    private List<Trip> tripList = new ArrayList<>();
    private Context context;

    private final TripAdapterOnClickHandler mClickHandler;


    public interface TripAdapterOnClickHandler {
        void onClick(Trip trip);
    }
    public interface TripAdapterOnLongClickHandler {
        void onClick(Trip trip);
    }

    public TripAdapter(TripAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;

    }

    public class TripAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvTripToWhere;
        private final TextView tvTripFromWhere;
        private final TextView tvTripInitialDate;
        private final TextView tvTripEndDate;
        private final LinearLayout llRateStars;
        private final ImageView ivRateStar1;
        private final ImageView ivRateStar2;
        private final ImageView ivRateStar3;
        private final ImageView ivRateStar4;
        private final ImageView ivRateStar5;
        private final Drawable drwStarBlank;
        private final Drawable drwStarFilled;

        TripAdapterViewHolder(View view) {
            super(view);

            tvTripToWhere = view.findViewById(R.id.tv_trip_to_where_value);
            tvTripFromWhere = view.findViewById(R.id.tv_trip_from_where_value);
            tvTripInitialDate = view.findViewById(R.id.tv_trip_initial_date_value);
            tvTripEndDate = view.findViewById(R.id.tv_trip_end_date_value);
            llRateStars = view.findViewById(R.id.ll_rate_stars);
            ivRateStar1 = view.findViewById(R.id.iv_rate_star_1);
            ivRateStar2 = view.findViewById(R.id.iv_rate_star_2);
            ivRateStar3 = view.findViewById(R.id.iv_rate_star_3);
            ivRateStar4 = view.findViewById(R.id.iv_rate_star_4);
            ivRateStar5 = view.findViewById(R.id.iv_rate_star_5);
            drwStarBlank = view.getResources().getDrawable(R.drawable.star_blank, null);
            drwStarFilled = view.getResources().getDrawable(R.drawable.star_filled, null);

            view.setOnClickListener(this);
            context = view.getContext();
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(tripList.get(adapterPosition));
        }

    }


    @Override
    public TripAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trip_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new TripAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TripAdapterViewHolder tripAdapterViewHolder, int position) {

        tripAdapterViewHolder.tvTripToWhere.setText(tripList.get(position).getToWhere().getPlaceName());
        tripAdapterViewHolder.tvTripFromWhere.setText(tripList.get(position).getFromWhere().getPlaceName());
        tripAdapterViewHolder.tvTripInitialDate.setText(tripList.get(position).getInitialDate());
        tripAdapterViewHolder.tvTripEndDate.setText(tripList.get(position).getEndDate());

        List<ImageView> listStars = new ArrayList<>();
        listStars.add(tripAdapterViewHolder.ivRateStar1);
        listStars.add(tripAdapterViewHolder.ivRateStar2);
        listStars.add(tripAdapterViewHolder.ivRateStar3);
        listStars.add(tripAdapterViewHolder.ivRateStar4);
        listStars.add(tripAdapterViewHolder.ivRateStar5);

        if (tripList.get(position).getStatus().equals(ContentProviderContract.PATH_CONCLUDED_TRIPS)) {
            for (int i = 0; i < 5; i++) {
                if (i < tripList.get(position).getRate())
                    listStars.get(i).setImageDrawable(tripAdapterViewHolder.drwStarFilled);
                else
                    listStars.get(i).setImageDrawable(tripAdapterViewHolder.drwStarBlank);
            }
        } else {
            tripAdapterViewHolder.llRateStars.setVisibility(View.GONE);
        }

        loadAds(tripAdapterViewHolder.itemView);

    }

    @Override
    public int getItemCount() {
        if (tripList.isEmpty()) return 0;
        return tripList.size();
    }

    public void setTripData(List<Trip> tripList) {
        if (tripList != null && tripList.size() > 0) {
            this.tripList = tripList;
        } else {
            this.tripList.clear();
        }
        notifyDataSetChanged();
    }

    public void loadAds(View view)
    {
        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

    }

}