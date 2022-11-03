package ru.aleksseii;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@Getter
@NoArgsConstructor
public final class BookCell {

    private @Nullable Book book = null;

    private boolean isFilled = false;

    public BookCell(@NotNull Book book) {
        this.book = book;
        this.isFilled = true;
    }

    public void putBook(@NotNull Book book) {
        this.book = book;
        this.isFilled = true;
    }

    public @Nullable Book extractBook() {
        Book book = this.book;

        this.book = null;
        isFilled = false;

        return book;
    }
}
