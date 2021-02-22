package io.github.addoncommunity.galactifun.util;

import lombok.Data;

/**
 * A container for three items
 *
 * @param <F> type of first item
 * @param <S> type of second item
 * @param <T> type of third item
 * @author Seggan
 */
@Data
public final class Three<F, S, T> {
    private final F first;
    private final S second;
    private final T third;
}
