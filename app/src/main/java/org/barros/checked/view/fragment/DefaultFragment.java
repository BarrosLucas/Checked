package org.barros.checked.view.fragment;

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

import org.barros.checked.R;

import org.barros.checked.model.ItemChecklist;
import org.barros.checked.utils.ViewDialog;
import org.barros.checked.view.adapter.TaskItemRecyclerViewAdapter;
import org.barros.checked.infra.CheckListPersistence;
import org.barros.checked.model.ItemTask;
import org.barros.checked.utils.SwipeToDeleteCallback;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

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
    private TextView title;

    public DefaultFragment(long idChecklist, TextView title) {
        this.idChecklist = idChecklist;
        this.title = title;
    }


    public static DefaultFragment newInstance(long idChecklist, TextView title) {
        DefaultFragment fragment = new DefaultFragment(idChecklist,title);
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

        setTitle(title);
        if((new CheckListPersistence(getContext())).selectByID(idChecklist).isDefault()){
            currentDay.setVisibility(View.VISIBLE);
        }else{
            currentDay.setVisibility(View.INVISIBLE);
        }


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
                    (new ViewDialog()).showMessageDialog(getActivity(), getString(R.string.error_on_delete), getString(R.string.error_delete));
                }

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void addItem(){
        (new ViewDialog()).showDialogNewTask(getActivity(),idChecklist).setOnDismissListener(
                new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        updateList();
                    }
                }
        );
    }

    @Override
    public void setTitle(TextView textView){
        ItemChecklist itemChecklist = (new CheckListPersistence(getContext()).selectByID(idChecklist));
        if(itemChecklist.isDefault()){
            textView.setText(getString(R.string.app_name));
        }else{
            textView.setText(itemChecklist.getTitle());
        }
    }

    private void updateList(){
        mAdapter = new TaskItemRecyclerViewAdapter((new CheckListPersistence(getContext())).selectByID(idChecklist).getTaskList(), recyclerView);
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