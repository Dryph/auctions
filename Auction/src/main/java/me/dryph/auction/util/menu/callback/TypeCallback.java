package me.tulio.auction.util.menu.callback;

import java.io.Serializable;

public interface TypeCallback<T> extends Serializable {

    void callback(T data);

}
