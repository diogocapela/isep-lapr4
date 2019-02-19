package adapters;


import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class CSVAdapter<T> implements IO {
    private final String filePath;

    CSVAdapter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<String[]> importObject(int expectedColumns) {
        List<String[]> fields = new LinkedList<>();
        try {
            int cline = 1;
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = br.readLine();
            while (line != null) {
                String[] splittedString = line.split(",");
                if (splittedString.length != expectedColumns) {
                    throw new Exception(String.format("Error parsing the file at line %d", cline));
                }
                fields.add(splittedString);
                line = br.readLine();
                cline++;
            }

        } catch (FileNotFoundException ex) {
            System.err.println(String.format("File %s not found!\nErr msg:%s", filePath, ex.getMessage()));
            return null;
        } catch (IOException ioe) {
            System.err.println(String.format("File %s error opening!\nErr msg:%s", filePath, ioe.getMessage()));
            return null;
        } catch (Exception ex) {
            System.err.println(String.format("%s\n", ex.getMessage()));
            return null;
        }


        return fields;
    }

    @Override
    public boolean exportObject(Exporter obj) {
        List<String[]> lines = obj.toExporterDTO();
        try (Writer writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] line : lines) {
                for (int i = 0; i < line.length; i++) {
                    if (i != line.length - 1) {
                        writer.write(line[i] + ",");
                    } else {
                        writer.write(line[i]);
                    }
                }
                writer.write("\n");
            }
        } catch (IOException ex) {
            System.err.println(String.format("IOException!\nErr msg:%s", ex.getMessage()));
            return false;
        }
        return true;
    }
}
