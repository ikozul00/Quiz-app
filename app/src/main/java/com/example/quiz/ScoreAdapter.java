package com.example.quiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder>{

    private ArrayList<SingleScore> mScores;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView resultTextView;

        public ViewHolder(View itemView) {

            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.username);
            resultTextView = (TextView) itemView.findViewById(R.id.score);
        }
    }

    public ScoreAdapter(ArrayList<SingleScore> scores) {
        mScores = scores;
    }


    @NonNull
    @Override
    public ScoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflater custom layout
        View viewCell = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.score_line, parent, false);

        ViewHolder viewHolder = new ViewHolder(viewCell);
        return viewHolder;
    }

    //populating rows with data
    @Override
    public void onBindViewHolder(@NonNull ScoreAdapter.ViewHolder holder, int position) {
        SingleScore oneScore=mScores.get(position);
        TextView textView = holder.nameTextView;
        textView.setText(oneScore.getScoreUsername());
        TextView textView2 = holder.resultTextView;
        textView2.setText(oneScore.getScoreResult());
    }

    //getting number of items
    @Override
    public int getItemCount() {
        return mScores.size();
    }
}
