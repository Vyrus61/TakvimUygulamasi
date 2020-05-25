package com.example.firerectakvim;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.protobuf.StringValue;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteHolder> {



    private OnItemClickListener listener;
    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Note model) {
        holder.textViewTitle.setText(model.getTitle());
        holder.textViewDescription.setText(model.getDescription());
        holder.textViewPriority.setText(String.valueOf(model.getPriority()));
        holder.textViewTarih.setText(String.valueOf(model.getDay())+"/"+String.valueOf(model.getMonth())+"/"+String.valueOf(model.getYear()));
        holder.textViewSaat.setText(String.valueOf(model.getHour())+":"+String.valueOf(model.getMinute()));
        holder.textViewRepeaType.setText(model.getRepeatType());

        holder.textViewTarihE.setText(String.valueOf(model.getDayE())+"/"+String.valueOf(model.getMonthE())+"/"+String.valueOf(model.getYearE()));

    }
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,
                parent, false);
        return new NoteHolder(v);
    }
    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }
    class NoteHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewPriority;
        TextView textViewTarih;
        TextView textViewSaat;
        TextView textViewRepeaType;
        TextView textViewTarihE;

        ImageView infoImage;
        ImageView dateIamge;
        ImageView dateEImage;
        ImageView timeImage;
        ImageView repeatImage;
        ImageView repeatTypeImage;

        public  RelativeLayout container;
        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            textViewSaat=itemView.findViewById(R.id.text_View_Saat);
            textViewTarih=itemView.findViewById(R.id.text_View_Tarih);
            textViewRepeaType=itemView.findViewById(R.id.repeat_type_textI);
            textViewTarihE=itemView.findViewById(R.id.text_View_TarihE);
            container=itemView.findViewById(R.id.container);

            infoImage=itemView.findViewById(R.id.info_iconI);
            dateIamge=itemView.findViewById(R.id.date_iconI);
            dateEImage=itemView.findViewById(R.id.date_iconE);
            timeImage=itemView.findViewById(R.id.time_iconI);
            repeatImage=itemView.findViewById(R.id.repeat_iconI);
            repeatTypeImage=itemView.findViewById(R.id.repeat_type_iconI);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

            Switch sw = (Switch) itemView.findViewById(R.id.darkMode_switch);
            sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        container.setBackgroundResource(R.drawable.card_bg);
                        textViewTitle.setTextColor(Color.BLACK);
                        textViewDescription.setTextColor(Color.BLACK);
                        textViewPriority.setTextColor(Color.BLACK);
                        textViewSaat.setTextColor(Color.BLACK);
                        textViewRepeaType.setTextColor(Color.BLACK);
                        textViewTarih.setTextColor(Color.BLACK);
                        textViewTarihE.setTextColor(Color.BLACK);

                        infoImage.setImageResource(R.drawable.ic_info);
                        dateIamge.setImageResource(R.drawable.ic_view_day);
                        dateEImage.setImageResource(R.drawable.ic_update_black_24dp);
                        timeImage.setImageResource(R.drawable.ic_access_time);
                        repeatImage.setImageResource(R.drawable.ic_sync);
                        repeatTypeImage.setImageResource(R.drawable.ic_subject);

                    } else {

                        container.setBackgroundResource(R.drawable.card_bg_dark);
                        textViewTitle.setTextColor(Color.WHITE);
                        textViewDescription.setTextColor(Color.WHITE);
                        textViewPriority.setTextColor(Color.WHITE);
                        textViewSaat.setTextColor(Color.WHITE);
                        textViewRepeaType.setTextColor(Color.WHITE);
                        textViewTarih.setTextColor(Color.WHITE);
                        textViewTarihE.setTextColor(Color.WHITE);

                        infoImage.setImageResource(R.drawable.ic_info_outline_white_24dp);
                        dateIamge.setImageResource(R.drawable.ic_today_white_24dp);
                        dateEImage.setImageResource(R.drawable.ic_update_white_24dp);
                        timeImage.setImageResource(R.drawable.ic_access_time_white_24dp);
                        repeatImage.setImageResource(R.drawable.ic_sync_white_24dp);
                        repeatTypeImage.setImageResource(R.drawable.ic_subject_white_24dp);


                    }
                }
            });
        }


        private void setDarkTheme(){
            container.setBackgroundResource(R.drawable.card_bg_dark);

        }


    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}