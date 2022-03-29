package org.barros.checked.view.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.barros.checked.R;
import org.barros.checked.infra.CheckListPersistence;
import org.barros.checked.model.ItemChecklist;
import org.barros.checked.utils.SwipeToDeleteCallback;
import org.barros.checked.utils.ViewDialog;
import org.barros.checked.view.HomeActivity;
import org.barros.checked.view.adapter.ChecklistItemRecyclerViewAdapter;

import com.google.android.material.snackbar.Snackbar;

public class  ChecklistsFragment extends FragmentBase {
    private ChecklistItemRecyclerViewAdapter mAdapter;
    private RecyclerView recyclerView;

    public ChecklistsFragment() {

    }


    public static ChecklistsFragment newInstance() {
        ChecklistsFragment fragment = new ChecklistsFragment();
        return fragment;
    }

    @Override
    public void setTitle(){
        HomeActivity.titlePage.setText(getString(R.string.checklists));
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

        setTitle();
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
                            .make(viewHolder.itemView, getString(R.string.item_deleted), Snackbar.LENGTH_LONG);
                    updateList();
                    snackbar.setAction(getString(R.string.undo), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mAdapter.restoreItem(getContext(), item, position);
                            recyclerView.scrollToPosition(position);
                            updateList();
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }else{
                    updateList();
                    (new ViewDialog()).showMessageDialog(getActivity(), getString(R.string.error_on_delete), getString(R.string.error_delete_default));
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
        mAdapter = new ChecklistItemRecyclerViewAdapter((new CheckListPersistence(getContext())).selectAll(), HomeActivity.fragmentManager, HomeActivity.titlePage);
        recyclerView.setAdapter(mAdapter);
        enableSwipeToDelete();
    }

}