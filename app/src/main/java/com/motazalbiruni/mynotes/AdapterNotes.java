package com.motazalbiruni.mynotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.motazalbiruni.mynotes.ReadActivity.KEY_ID;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.MyHolder> {

    private List<NoteEntity> list = new ArrayList<>();
    private Context context;

    public AdapterNotes(Context context) {
        this.context = context;
    }

    public void setList(List<NoteEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }//end setList()

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_notes, parent, false);
        MyHolder MyHolder = new MyHolder(view);
        return MyHolder;
    }//end onCreateViewHolder()

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        // holder.textView.setText(MyHolders.get(position).getTitle());
        holder.txtTitle.setText(list.get(position).getTitle());
        int importantType = list.get(position).getType();
        switch (importantType){
            case 0:
                holder.cardRow.setCardBackgroundColor(Color.parseColor(context.getString(R.string.blue)));
                break;
            case 1:
                holder.cardRow.setCardBackgroundColor(Color.parseColor(context.getString(R.string.green)));
                break;
            case 2:
                holder.cardRow.setCardBackgroundColor(Color.parseColor(context.getString(R.string.orange)));
                break;
        }

        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ReadActivity.class);
                intent.putExtra(KEY_ID,list.get(position).getId());
                ((Activity)context).startActivity(intent);
            }
        });
    }//end onBindViewHolder()

    @Override
    public int getItemCount() {
        return list.size();
    }//end getItemCount()

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        CardView cardRow;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitleList);
            cardRow = itemView.findViewById(R.id.card_row);
        }
    }//end Class MyHolder
}//end Class
