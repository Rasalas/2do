package rasalas.de.twodo.model;

import java.util.Date;

/**
 * Created by Jann on 22.04.2016.
 */
public class Todo {

    private long id;
    private String tag;
    private Date dueDate, createDate;
    private String description;
    private int priority;


    public Todo(String tag, Date createDate, Date dueDate, String description, int priority) {
        this.tag = tag;
        this.dueDate = dueDate;
        this.createDate = createDate;
        this.description = description;
        this.priority = priority;
    }


    // Getter / Setter
    public String getTag() {
        return tag;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
