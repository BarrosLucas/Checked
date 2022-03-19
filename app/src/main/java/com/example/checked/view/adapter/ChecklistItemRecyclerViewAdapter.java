package com.example.checked.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.checked.databinding.FragmentItemChecklistsBinding;
import com.example.checked.infra.CheckListPersistence;
import com.example.checked.model.ItemChecklist;

import java.util.List;

public class ChecklistItemRecyclerViewAdapter extends RecyclerView.Adapter<ChecklistItemRecyclerViewAdapter.ViewHolder> {

    public List<ItemChecklist> mValues;
    private View.OnClickListener onClickItem;

    public ChecklistItemRecyclerViewAdapter(List<ItemChecklist> items, View.OnClickListener onClickItem) {
        mValues = items;
        this.onClickItem = onClickItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemChecklistsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.title.setText(mValues.get(position).getTitle());
        holder.itemList.setOnClickListener(onClickItem);

    }

    public boolean deleteItem(Context context, int position){
        long id = mValues.get(position).getId();
        if(!mValues.get(position).isDefault()) {
            if ((new CheckListPersistence(context)).deleteChecklist(id)) {
                mValues.remove(position);
                return true;
            }
        }
        return false;
    }

    public boolean restoreItem(Context context, ItemChecklist itemChecklist, int position){
        if((new CheckListPersistence(context)).createChecklist(itemChecklist.getTitle(),false)){
            mValues.add(position, itemChecklist);
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public ItemChecklist mItem;
        public LinearLayout itemList;

        public ViewHolder(FragmentItemChecklistsBinding binding) {
            super(binding.getRoot());
            title = binding.checklistName;
            itemList = binding.itemList;
        }
    }
}