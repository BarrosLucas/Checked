package org.barros.checked.model;

import java.util.ArrayList;
import java.util.List;

public class ItemChecklist {
    private String title;
    private List<ItemTask> taskList;
    private long id;
    private boolean isDefault;

    public ItemChecklist(){}

    public ItemChecklist(String title){
        setTitle(title);
        setTaskList(new ArrayList<>());
        setDefault(false);
    }

    public ItemChecklist(String title, List<ItemTask> taskList){
        setTitle(title);
        setTaskList(taskList);
        setDefault(false);
    }

    public ItemChecklist(long id, String title, List<ItemTask> taskList) {
        setId(id);
        setTitle(title);
        setTaskList(taskList);
        setDefault(false);
    }

    public ItemChecklist(long id, String title, List<ItemTask> taskList, boolean isDefault) {
        setId(id);
        setTitle(title);
        setTaskList(taskList);
        setDefault(isDefault);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ItemTask> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<ItemTask> taskList) {
        this.taskList = taskList;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public boolean isComplete() {
        for(ItemTask itemTask: taskList){
            if(!itemTask.isDone()){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "ItemChecklist{" +
                "title='" + title + '\'' +
                ", taskList=" + taskList +
                ", complete=" + isComplete() +
                '}';
    }
}
