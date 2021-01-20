package com.motazalbiruni.mynotes.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.motazalbiruni.mynotes.R;
import com.motazalbiruni.mynotes.database_room.NoteEntity;
import com.motazalbiruni.mynotes.ui.ReadActivity;

import java.util.ArrayList;
import java.util.List;

import static com.motazalbiruni.mynotes.ui.ReadActivity.KEY_ID;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.MyHolder> {

    private List<NoteEntity> list = new ArrayList<>();
    private final Context context;

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
        View view = inflater.inflate(R.layout.list_notes_item, parent, false);
        return new MyHolder(view);

    }//end onCreateViewHolder()

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        // holder.textView.setText(MyHolders.get(position).getTitle());
        holder.txtTitle.setText(list.get(position).getTitle());
        int importantType = list.get(position).getType();
        switch (importantType){
            case 0:
                holder.txtTitle.setBackgroundResource(R.color.blue);
                break;
            case 1:
                holder.txtTitle.setBackgroundResource(R.color.green);
                break;
            case 2:
                holder.txtTitle.setBackgroundResource(R.color.orange);
                break;
        }

        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ReadActivity.class);
                intent.putExtra(KEY_ID,list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }//end onBindViewHolder()

    @Override
    public int getItemCount() {
        return list.size();
    }//end getItemCount()

    public static class MyHolder extends RecyclerView.ViewHolder {
        private final TextView txtTitle;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitleList);
        }
    }//end Class MyHolder
}//end Class
