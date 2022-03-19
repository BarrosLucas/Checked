package com.example.checked.view.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.checked.R;
import com.example.checked.infra.ItemListPersistence;
import com.example.checked.utils.ViewDialog;
import com.example.checked.view.adapter.TaskItemRecyclerViewAdapter;
import com.example.checked.infra.CheckListPersistence;
import com.example.checked.model.ItemTask;
import com.example.checked.utils.SwipeToDeleteCallback;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DefaultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefaultFragment extends FragmentBase {
    private TaskItemRecyclerViewAdapter mAdapter;
    private RecyclerView recyclerView;
    private long idChecklist;
    private TextView currentDay;

    public DefaultFragment(long idChecklist) {
        this.idChecklist = idChecklist;
    }


    public static DefaultFragment newInstance(long idChecklist) {
        DefaultFragment fragment = new DefaultFragment(idChecklist);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        currentDay = (TextView) view.findViewById(R.id.current_day);

        currentDay.setText(setDay());
        updateList();

        return view;
    }

    private void enableSwipeToDelete(){
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()){
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                final ItemTask item = mAdapter.mValues.get(position);

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
        (new ViewDialog()).showDialog(getActivity(),idChecklist).setOnDismissListener(
                new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        updateList();
                    }
                }
        );
    }

    private void updateList(){
        mAdapter = new TaskItemRecyclerViewAdapter((new CheckListPersistence(getContext())).selectDefault().getTaskList());
        recyclerView.setAdapter(mAdapter);
        enableSwipeToDelete();
    }

    private String setDay(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        String date = day+" de ";
        switch (month){
            case 1:
                date += "Janeiro de ";
                break;
            case 2:
                date += "Fevereiro de ";
                break;
            case 3:
                date += "Março de ";
                break;
            case 4:
                date += "Abril de ";
                break;
            case 5:
                date += "Maio de ";
                break;
            case 6:
                date += "Junho de ";
                break;
            case 7:
                date += "Julho de ";
                break;
            case 8:
                date += "Agosto de ";
                break;
            case 9:
                date += "Setembro de ";
                break;
            case 10:
                date += "Outubro de ";
                break;
            case 11:
                date += "Novembro de ";
                break;
            case 12:
                date += "Dezembro de ";
                break;

        }
        date += year;

        return date;
    }

}