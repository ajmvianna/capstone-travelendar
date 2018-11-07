package edu.nanodegreeprojects.capstone.travelendar.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import edu.nanodegreeprojects.capstone.travelendar.model.Trip;
import travelendar.capstone.nanodegreeprojects.edu.travelendar.R;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripAdapterViewHolder> {

    private List<Trip> tripList = new ArrayList<>();
    private Context context;

    private final TripAdapterOnClickHandler mClickHandler;

    public interface TripAdapterOnClickHandler {
        void onClick(Trip trip);
    }

    public TripAdapter(TripAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class TripAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //private final ImageView iv;

        TripAdapterViewHolder(View view) {
            super(view);
            //iv = view.findViewById(R.id.iv_movie_item);
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
        //String movieThumbnail = movieList.get(position).getThumbnailPath();
        //Picasso.with(context).load(NetworkUtils.buildUrl(NetworkUtils.THUMBNAIL_QUERY_TYPE, movieThumbnail, null, null).toString()).into(movieAdapterViewHolder.ivMovieThumbnail);
    }

    @Override
    public int getItemCount() {
        if (tripList.isEmpty()) return 0;
        return tripList.size();
    }

//    public void setTripData(String tripData, ContentResolver contentResolver) {
//        List<Trip> movieList = new ArrayList<>();
//        if (!TripData.equals("")) {
//            try {
//                tripList = JsonParser.convertHTTPReturnToMovie(movieData);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            MovieDbHelper.insertMovieList(movieList, contentResolver);
//
//            this.movieList = movieList;
//        } else {
//            this.movieList.clear();
//        }
//        notifyDataSetChanged();
//    }

    public void setTripData(List<Trip> tripList) {
        if (tripList != null && tripList.size() > 0) {
            this.tripList = tripList;
        } else {
            this.tripList.clear();
        }
        notifyDataSetChanged();
    }
}