package com.motazalbiruni.mynotes.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.motazalbiruni.mynotes.R;
import com.motazalbiruni.mynotes.database_room.NoteEntity;
import com.motazalbiruni.mynotes.ui.ReadActivity;

import java.util.ArrayList;
import java.util.List;

import static com.motazalbiruni.mynotes.ui.ReadActivity.KEY_ID;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.MyHolder> {

    private SharedPreferences sharedPreferences;
    private List<NoteEntity> list = new ArrayList<>();
    private final Context context;
    private static String textSize;

    public AdapterNotes(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("motazalbiruni.mynotes", context.MODE_PRIVATE);
        textSize = sharedPreferences.getString("textSize", "small");
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
        holder.txtBody.setText(list.get(position).getBody());
        holder.txtDate.setText(list.get(position).getDate());
        setFadeAnimation(holder.itemView);
        int importantType = list.get(position).getType();
        switch (importantType) {
            case 0:
                holder.txtTitle.setBackgroundResource(R.color.blue);
                holder.txtBody.setTextColor(ContextCompat.getColor(context, R.color.blue));
                holder.txtDate.setBackgroundResource(R.color.blue);
                break;
            case 1:
                holder.txtTitle.setBackgroundResource(R.color.green);
                holder.txtBody.setTextColor(ContextCompat.getColor(context, R.color.green));
                holder.txtDate.setBackgroundResource(R.color.green);
                break;
            case 2:
                holder.txtTitle.setBackgroundResource(R.color.orange);
                holder.txtBody.setTextColor(ContextCompat.getColor(context, R.color.orange));
                holder.txtDate.setBackgroundResource(R.color.orange);
                break;
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ReadActivity.class);
                intent.putExtra(KEY_ID, list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }//end onBindViewHolder()

    private void setFadeAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }//end setFadeAnimation()

    @Override
    public int getItemCount() {
        if (list.size() == 0) {
            return 0;
        }
        return list.size();
    }//end getItemCount()

    public static class MyHolder extends RecyclerView.ViewHolder {
        private final TextView txtTitle, txtDate, txtBody;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitleList);
            txtDate = itemView.findViewById(R.id.txtDateList);
            txtBody = itemView.findViewById(R.id.txtBodyList);

            switch (textSize) {
                case "small":
                    txtTitle.setTextSize(18);
                    txtBody.setTextSize(18);
                    break;
                case "medium":
                    txtTitle.setTextSize(20);
                    txtBody.setTextSize(20);
                    break;
                case "large":
                    txtTitle.setTextSize(22);
                    txtBody.setTextSize(22);
                    break;
            }//end switch
        }//end MyHolder()
    }//end Class MyHolder

}//end AdapterNotes Class
