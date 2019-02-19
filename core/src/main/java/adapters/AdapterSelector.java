package adapters;


public class AdapterSelector {

    public IO getAdapter(String fp) {
        if (fp.endsWith(".xml")) {
            return new XMLAdapter(fp);
        } else if (fp.endsWith(".csv")) {
            return new CSVAdapter(fp);
        } else if (fp.endsWith(".json")) {
            return new JSONAdapter(fp);
        } else {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

}
