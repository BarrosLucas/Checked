package com.example.checked.view.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.checked.infra.ItemListPersistence;
import com.example.checked.model.ItemTask;
import com.example.checked.databinding.FragmentItemBinding;

import java.util.List;

public class TaskItemRecyclerViewAdapter extends RecyclerView.Adapter<TaskItemRecyclerViewAdapter.ViewHolder> {

    public List<ItemTask> mValues;

    public TaskItemRecyclerViewAdapter(List<ItemTask> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mCheck.setChecked(mValues.get(position).isDone());
        holder.mCheck.setText(mValues.get(position).getTitle());

        holder.mCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                long idItem = mValues.get(holder.getAbsoluteAdapterPosition()).getId();

                (new ItemListPersistence(compoundButton.getContext())).updateChecklist(idItem,mValues.get(holder.getAbsoluteAdapterPosition()).getTitle(), b);
            }
        });
    }

    public boolean deleteItem(Context context, int position){
        long id = mValues.get(position).getId();
        if((new ItemListPersistence(context)).deleteChecklist(id)){
            mValues.remove(position);
            return true;
        }
        return false;
    }

    public boolean restoreItem(Context context, ItemTask itemTask, int position){
        if((new ItemListPersistence(context)).createItemlist(itemTask.getTitle(), itemTask.getIdList(),itemTask.isDone())){
            mValues.add(position, itemTask);
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final CheckBox mCheck;
        public ItemTask mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mCheck = binding.itemCheck;
        }


    }
}