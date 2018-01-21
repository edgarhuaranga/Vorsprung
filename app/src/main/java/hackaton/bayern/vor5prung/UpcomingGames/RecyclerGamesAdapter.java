package hackaton.bayern.vor5prung.UpcomingGames;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import hackaton.bayern.vor5prung.Model.Enemy;
import hackaton.bayern.vor5prung.R;

/**
 * Created by edhuar on 20.01.18.
 */

public class RecyclerGamesAdapter extends RecyclerView.Adapter<RecyclerGamesAdapter.GameViewHolder> {
    Context context;
    ArrayList<Enemy> upcomingGames;


    public RecyclerGamesAdapter (Context context, ArrayList<Enemy> enemies){
        this.context = context;
        this.upcomingGames = enemies;
    }

    @Override
    public RecyclerGamesAdapter.GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_games, parent, false);
        return new RecyclerGamesAdapter.GameViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerGamesAdapter.GameViewHolder holder, int position) {
        Enemy enemy = upcomingGames.get(position);

        GregorianCalendar calendar = new GregorianCalendar(2018, 0, 21+position*7);
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
        String dateFormatted = fmt.format(calendar.getTime());

        holder.dateOfMatch.setText(dateFormatted);
        if(position%2 == 0){
            holder.textViewLocalTeam.setText("FC Bayern Munich");
            holder.textViewAwayTeam.setText(enemy.getName());

            Glide.with(context)
                    .load(R.drawable.fcbayern)
                    .into(holder.imageViewLocalTeam);

            Glide.with(context)
                    .load(enemy.getLogoResource())
                    .into(holder.imageViewAwayTeam);
        }
        else{
            holder.textViewAwayTeam.setText("FC Bayern Munich");
            holder.textViewLocalTeam.setText(enemy.getName());

            Glide.with(context)
                    .load(R.drawable.fcbayern)
                    .into(holder.imageViewAwayTeam);

            Glide.with(context)
                    .load(enemy.getLogoResource())
                    .into(holder.imageViewLocalTeam);
        }
    }

    @Override
    public int getItemCount() {
        return upcomingGames.size();
    }

    public class GameViewHolder extends RecyclerView.ViewHolder{
        TextView textViewLocalTeam;
        TextView textViewAwayTeam;
        ImageView imageViewLocalTeam;
        ImageView imageViewAwayTeam;
        TextView dateOfMatch;

        public GameViewHolder(View itemView){
            super(itemView);
            textViewLocalTeam = (TextView) itemView.findViewById(R.id.textview_localteam);
            textViewAwayTeam  = (TextView) itemView.findViewById(R.id.textview_awayteam);
            imageViewLocalTeam = (ImageView) itemView.findViewById(R.id.imageview_localteam);
            imageViewAwayTeam = (ImageView) itemView.findViewById(R.id.imageview_awayteam);
            dateOfMatch = (TextView) itemView.findViewById(R.id.textview_match_date);
        }
    }
}
