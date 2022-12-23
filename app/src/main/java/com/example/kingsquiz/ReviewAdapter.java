package com.example.kingsquiz;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final ArrayList<Question> listRecyclerItem;

    public ReviewAdapter(ArrayList<Question> listRecyclerItem) {
        this.listRecyclerItem = listRecyclerItem;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView question;
        public TextView page;
        public Button option1;
        public Button option2;
        public Button option3;
        public Button option4;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.quiz_question);
            page = itemView.findViewById(R.id.question_number);
            option1 = itemView.findViewById(R.id.option_1);
            option2 = itemView.findViewById(R.id.option_2);
            option3 = itemView.findViewById(R.id.option_3);
            option4 = itemView.findViewById(R.id.option_4);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.question_item, parent, false);
        return new ItemViewHolder((layoutView));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        Question question = (Question) listRecyclerItem.get(position);
        itemViewHolder.question.setText(question.getQuestion());
        itemViewHolder.option1.setText(question.options[0]);
        itemViewHolder.option2.setText(question.options[1]);
        itemViewHolder.option3.setText(question.options[2]);
        itemViewHolder.option4.setText(question.options[3]);
        itemViewHolder.page.setText(position + 1 + " / " + listRecyclerItem.size());
        Button correctButton = itemViewHolder.option1.getText().equals(question.getCorrectAnswer()) ?
                itemViewHolder.option1 : itemViewHolder.option2.getText().equals(question.getCorrectAnswer()) ?
                itemViewHolder.option2 : itemViewHolder.option3.getText().equals(question.getCorrectAnswer()) ?
                itemViewHolder.option3 : itemViewHolder.option4;
        correctButton.setBackgroundColor(Color.GREEN);
    }

    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }
}
