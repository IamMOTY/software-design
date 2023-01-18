package ru.buduschev.sd.todo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class TodoList {
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

}
