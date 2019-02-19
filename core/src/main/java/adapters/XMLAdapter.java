package adapters;

import java.util.List;

public class XMLAdapter<T> implements IO {

    private String filepath;

    public XMLAdapter(String filepath) {
        this.filepath = filepath;
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
