package Model;

import java.util.Observable;

/**
 * Created by Akash-Mac on 2016-02-17.
 */
public class Model extends Observable {

    public Model () {
        super.setChanged();
    }


}
