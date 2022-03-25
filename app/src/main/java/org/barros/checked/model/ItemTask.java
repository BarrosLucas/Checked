package org.barros.checked.model;

public class ItemTask {
    private String title;
    private boolean done;
    private long id;
    private long idList;

    public ItemTask(){}

    public ItemTask(String title){
        setTitle(title);
        setDone(false);
    }

    public ItemTask(String title, boolean done){
        setTitle(title);
        setDone(done);
    }

    public ItemTask(long id, String title, boolean done){
        setTitle(title);
        setDone(done);
        setId(id);
    }

    public long getIdList() {
        return idList;
    }

    public void setIdList(long idList) {
        this.idList = idList;
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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "ItemTask{" +
                "title='" + title + '\'' +
                '}';
    }
}
