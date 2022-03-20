package com.example.checked.view.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checked.R;
import com.example.checked.infra.CheckListPersistence;
import com.example.checked.model.ItemChecklist;
import com.example.checked.model.ItemTask;
import com.example.checked.utils.SwipeToDeleteCallback;
import com.example.checked.utils.ViewDialog;
import com.example.checked.view.adapter.ChecklistItemRecyclerViewAdapter;
import com.example.checked.view.adapter.TaskItemRecyclerViewAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class  ChecklistsFragment extends FragmentBase {
    private ChecklistItemRecyclerViewAdapter mAdapter;
    private RecyclerView recyclerView;
    private FragmentManager fragmentManager;
    private TextView title;

    public ChecklistsFragment(FragmentManager fragmentManager, TextView title) {
        this.fragmentManager = fragmentManager;
        this.title = title;
    }


    public static ChecklistsFragment newInstance(FragmentManager fragmentManager, TextView title) {
        ChecklistsFragment fragment = new ChecklistsFragment(fragmentManager, title);
        return fragment;
    }

    @Override
    public void setTitle(TextView title){
        title.setText(getString(R.string.checklists));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checklists, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list_checklists);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setTitle(title);
        updateList();

        return view;
    }

    private void enableSwipeToDelete(){
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()){
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                final ItemChecklist item = mAdapter.mValues.get(position);

                if(mAdapter.deleteItem(getContext(), position)){
                    Snackbar snackbar = Snackbar
                            .make(viewHolder.itemView, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                    updateList();
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mAdapter.restoreItem(getContext(), item, position);
                            recyclerView.scrollToPosition(position);
                            updateList();
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void addItem(){
        (new ViewDialog()).showDialogNewChecklist(getActivity()).setOnDismissListener(
                new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        updateList();
                    }
                }
        );
    }

    private void updateList(){
        mAdapter = new ChecklistItemRecyclerViewAdapter((new CheckListPersistence(getContext())).selectAll(), fragmentManager, title);
        recyclerView.setAdapter(mAdapter);
        enableSwipeToDelete();
    }

}