package com.motazalbiruni.mynotes.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.motazalbiruni.mynotes.MyValues;
import com.motazalbiruni.mynotes.R;
import com.motazalbiruni.mynotes.database_room.NoteEntity;
import com.motazalbiruni.mynotes.ui.ReadActivity;

import java.util.ArrayList;
import java.util.List;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.MyHolder> {

    //initialize variables
    private final Activity activity;
    private final TextView tvEmpty;
    private MainViewModel mainViewModel;
    private boolean isEnable = false;
    private boolean isSelectAll = false;
    private ArrayList<NoteEntity> selectList = new ArrayList<>();
    private List<NoteEntity> list = new ArrayList<>();
    private final Context context;
    private static String textSize;//for using form constructor to class holder

    //create constructor
    public AdapterNotes(Context context,Activity activity,TextView tvEmpty) {
        this.context = context;
        this.activity = activity;
        this.tvEmpty = tvEmpty;
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyValues.SHARED_FILE_NAME, context.MODE_PRIVATE);
        textSize = sharedPreferences.getString(MyValues.TEXT_SIZE, MyValues.SMALL_TEXT);
    }

    public void setList(List<NoteEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }//end setList()

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());//initialize view
        View view = inflater.inflate(R.layout.list_notes_item, parent, false);
        mainViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(MainViewModel.class);
        return new MyHolder(view);//return view
    }//end onCreateViewHolder()

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {

        holder.txtTitle.setText(list.get(position).getTitle());
        holder.txtBody.setText(list.get(position).getBody());
        holder.txtDate.setText(list.get(position).getDate());
        //change background color
        //for type of background color
        int importantType = list.get(position).getType();
        changeBackground(holder, importantType);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //check conduction
                if (!isEnable){
                    //when action mode not enable
                    //initialize action mode
                    ActionMode.Callback callback = new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            //initialize menu inflater
                            MenuInflater menuInflater = mode.getMenuInflater();
                            //inflate menu
                            menuInflater.inflate(R.menu.delete_menu,menu);
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(final ActionMode mode, Menu menu) {
                            //when action mode is prepare
                            //set enable is true
                            isEnable = true;
                            //create method
                            clickItem(holder);
                            //set observer on to get text method
                            mainViewModel.getSelectedLiveData().observe((LifecycleOwner) activity,
                                    new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    //when text change
                                    //set text on action mode title
                                    mode.setTitle(String.format("%s Selected",s));
                                }
                            });
                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            //when click on action mode item
                            //get item id
                            int id = item.getItemId();
                            //use switch condition
                            switch (id){
                                case R.id.select_delete:
                                    //when click delete
                                    //use for loop
                                    for (NoteEntity noteSelect :selectList) {
                                        //remove select item from list
                                        list.remove(noteSelect);
                                        mainViewModel.deleteById(noteSelect.getId());
                                    }
                                    //check condition
                                    if (list.size()==0){
                                        //when list is empty
                                        //visible text view
                                        tvEmpty.setVisibility(View.VISIBLE);
                                    }
                                    mode.finish();
                                    break;
                                case R.id.select_all:
                                    //when click on select all
                                    //check condition
                                    if (selectList.size() == list.size()){
                                        //when all item selected
                                        //set isSelectAll false
                                        isSelectAll = false;
                                        //clear select list
                                        selectList.clear();
                                    }else {
                                        //when all item unselected
                                        //set isSelectAll true
                                        isSelectAll = true;
                                        //clear list
                                        selectList.clear();
                                        //add all values in select list
                                        selectList.addAll(list);
                                    }
                                    //set text in view mode
                                    mainViewModel.setSelectedLiveData(String.valueOf(selectList.size()));
                                    //notify adaptor
                                    notifyDataSetChanged();
                                    break;
                            }
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode mode) {
                            //when action mode destroy
                            //set isEnable false
                            isEnable = false;
                            //set isSelectAll false
                            isSelectAll = false;
                            //clear selectList
                            selectList.clear();
                            //notify adaptor
                            notifyDataSetChanged();
                        }
                    };
                    //start action mode
                    ((AppCompatActivity) v.getContext()).startActionMode(callback);
                }else{
                    //when action mode already enable
                    //call method
                    clickItem(holder);
                }
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check condition
                if (isEnable){
                    //when action mode is enable
                    //call method
                    clickItem(holder);
                }else {
                    Intent intent = new Intent(view.getContext(), ReadActivity.class);
                    intent.putExtra(MyValues.ID_KEY, list.get(position).getId());
                    context.startActivity(intent);
                }
            }
        });
        //check condition
        if (isSelectAll){
            //when all values selected
            //visible all check box image
            holder.iv_checkBox.setVisibility(View.VISIBLE);
        }else {
            //when all values unselected
            //hide all check box image
            holder.iv_checkBox.setVisibility(View.GONE);
        }
    }//end onBindViewHolder()

    private void clickItem(MyHolder holder) {
        //get selected item id
        NoteEntity note = list.get(holder.getAdapterPosition());
        //check condition
        if (holder.iv_checkBox.getVisibility() == View.GONE){
            //when item not select
            //visible check box image
            holder.iv_checkBox.setVisibility(View.VISIBLE);
            //add value to select list
            selectList.add(note);
        }else {
            //when item selected
            //hide check box image
            holder.iv_checkBox.setVisibility(View.GONE);
            //remove values from selected list
            selectList.remove(note);
        }
        //set text in view model
        mainViewModel.setSelectedLiveData(String.valueOf(selectList.size()));
    }//end clickItem

    @Override
    public int getItemCount() {
        if (list.size() == 0) {
            return 0;
        }
        return list.size();
    }//end getItemCount()

    private void changeBackground(MyHolder holder, int type){
        switch (type) {
            case 0:
                //change card view background color
                holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.lightColor_1));
                break;
            case 1:
                //change card view background color
                holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.lightColor_2));
                break;
            case 2:
                //change card view background color
                holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.lightColor_3));
                break;
            case 3:
                //change card view background color
                holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.lightColor_4));
                break;
            case 4:
                //change card view background color
                holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.lightColor_5));
                break;
            case 5:
                //change card view background color
                holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.lightColor_6));
                break;

        }//end switch
    }//end changeBackground()

    public static class MyHolder extends RecyclerView.ViewHolder {
        private final TextView txtTitle, txtDate, txtBody;
        private final ImageView iv_checkBox;
        private final CardView card;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitleList);
            txtDate = itemView.findViewById(R.id.txtDateList);
            txtBody = itemView.findViewById(R.id.txtBodyList);
            iv_checkBox = itemView.findViewById(R.id.iv_check);
            card = itemView.findViewById(R.id.card_row);

            switch (textSize) {
                case MyValues.SMALL_TEXT:
                    txtTitle.setTextSize(18);
                    txtBody.setTextSize(18);
                    break;
                case  MyValues.MEDIUM_TEXT:
                    txtTitle.setTextSize(20);
                    txtBody.setTextSize(20);
                    break;
                case  MyValues.LARGE_TEXT:
                    txtTitle.setTextSize(22);
                    txtBody.setTextSize(22);
                    break;
            }//end switch
        }//end MyHolder()
    }//end Class MyHolder

}//end AdapterNotes Class
