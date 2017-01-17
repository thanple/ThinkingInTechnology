package com.thanple.thinking.berkeleyDB.demoframework;

import java.io.Serializable;

/**
 * Created by Thanple on 2017/1/15.
 */
public class TTable<T> extends BasicAccessObject<TTable> implements Serializable{

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public T getById(Integer key){
        return null;
    }
}

class User extends TTable{

}
class Team extends TTable{

}
