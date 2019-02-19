package utils;

import models.Cobertura;
import models.Envolvente;
import models.FatorRisco;
import models.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CSVImporter {

    /**
     * Format: titulo,descricao
     *
     * @param filePath : Caminho para o ficheiro csv
     * @return Lista de envolventes
     */
    public List<Cobertura> importCobertura(String filePath) {
        List<Cobertura> retCoberturas = new LinkedList<>();
        try {
            int cline = 1;
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = br.readLine();
            while (line != null) {
                //line will contain titulo;descricao
                String[] splittedString = line.split(",");
                if (splittedString.length != 2) {
                    throw new Exception(String.format("Error parsing the file at line %d", cline));
                }
                retCoberturas.add(new Cobertura(splittedString[0], splittedString[1]));
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

        return retCoberturas;

    }

    /**
     * Format: titulo,descricao
     *
     * @param filePath : Caminho para o ficheiro csv
     * @return Lista de envolventes
     */
    public List<Envolvente> importEnvolvente(String filePath) {
        List<Envolvente> retEnvolventes = new LinkedList<>();
        try {
            int cline = 1;
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = br.readLine();
            while (line != null) {
                //line will contain titulo;descricao
                String[] splittedString = line.split(",");
                if (splittedString.length != 4) {
                    throw new Exception(String.format("Error parsing the file at line %d", cline));
                }
                retEnvolventes.add(new Envolvente(splittedString[0], splittedString[1], Double.parseDouble(splittedString[2]), Double.parseDouble(splittedString[3])));
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

        return retEnvolventes;
    }

    /**
     * Format: titulo,descricao
     *
     * @param filePath : Caminho para o ficheiro csv
     * @return Lista de Fatores de Risco
     */
    public List<FatorRisco> importFatorRisco(String filePath) {
        List<FatorRisco> retFatoresRisco = new LinkedList<>();
        try {
            int cLine = 1;
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = br.readLine();
            while (line != null) {
                //line will contain titulo;descricao
                String[] splittedString = line.split(",");
                if (splittedString.length != 2) {
                    throw new Exception(String.format("Error parsing the file at line %d", cLine));
                }
                retFatoresRisco.add(new FatorRisco(splittedString[0], splittedString[1]));
                line = br.readLine();
                cLine++;
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
        return retFatoresRisco;
    }

    /**
     * Format: email,name
     *
     * @param filePath : Caminho para o ficheiro csv
     * @return Lista de Users
     */
    public List<User> importUser(String filePath) {
        List<User> retCoberturas = new LinkedList<>();
        try {
            int cline = 1;
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = br.readLine();
            while (line != null) {
                //line will contain titulo;descricao
                String[] splittedString = line.split(",");
                if (splittedString.length != 3) {
                    throw new Exception(String.format("Error parsing the file at line %d", cline));
                }
                retCoberturas.add(new User(splittedString[0], splittedString[1], splittedString[2]));
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

        return retCoberturas;

    }
}



