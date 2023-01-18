package ru.buduschev.sd.todo.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
public class Pair<F, S> {
    @Getter
    @Setter
    private F first;
    @Getter
    @Setter
    private S second;

}
