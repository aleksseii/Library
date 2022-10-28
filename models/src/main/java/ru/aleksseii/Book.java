package ru.aleksseii;

import org.jetbrains.annotations.NotNull;


public record Book(@NotNull Author author,
                   @NotNull String name) {
}
