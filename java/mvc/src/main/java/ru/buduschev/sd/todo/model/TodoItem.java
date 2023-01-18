package ru.buduschev.sd.todo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class TodoItem {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private int listId;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private TodoItemStatus status;

    public TodoItem(int id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = status == 2
                ? TodoItemStatus.DONE
                : status == 1
                    ? TodoItemStatus.IN_PROGRESS
                    : TodoItemStatus.TODO;
    }

    public TodoItem withListId(int listId) {
        this.listId = listId;
        return this;
    }
}
