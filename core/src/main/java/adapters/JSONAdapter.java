package adapters;

import java.util.List;

public class JSONAdapter<T> implements IO {

    private String filePath;

    public JSONAdapter(String filePath) {
        this.filePath = filePath;
    }


    @Override
    public List importObject(int expectedColumns) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean exportObject(Exporter obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
