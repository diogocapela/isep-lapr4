package adapters;

import models.Cobertura;
import models.Envolvente;
import models.FatorRisco;
import models.caracteristica.Contribuicao;
import models.caracteristica.Necessidade;
import models.caracteristica.Peso;
import models.celula.CelulaBase;
import models.celula.CelulaCaracterizada;
import models.celula.CelulaDetalhada;
import models.detalhe.Escala;
import models.matriz.Matriz;
import repository.CoberturaRepository;
import repository.EnvolventeRepository;
import repository.FatorRiscoRepository;
import settings.Settings;

import java.util.LinkedList;
import java.util.List;

public class MatrizIO {

    private AdapterSelector AS = new AdapterSelector();

    public Matriz importBase(String filePath) {
        IO adapter = AS.getAdapter(filePath);
        List<String[]> fields = adapter.importObject(Settings.IMPORT_BASE_LENGTH);

        LinkedList<CelulaBase> cbLst = new LinkedList<>();
        if (fields != null) {
            for (String[] f : fields) {
                //get cobertura
                CoberturaRepository cr = new CoberturaRepository();
                //get envolvente
                EnvolventeRepository er = new EnvolventeRepository();
                //get fatorrisco
                FatorRiscoRepository frr = new FatorRiscoRepository();

                for (Envolvente envTmp : er.findAllByTitle(f[Settings.ENVOLVENTE])) {
                    cbLst.add(
                        new CelulaBase(
                            cr.findByTitle(f[Settings.COBERTURA]),
                            envTmp,
                            frr.findByTitle(f[Settings.FATOR_RISCO])
                        )
                    );
                }
            }
            return new Matriz(cbLst);
        } else {
            return null;
        }


    }


    public Matriz importCaracterizacao(Matriz m, String filePath) {
        IO adapter = AS.getAdapter(filePath);

        List<String[]> fields = adapter.importObject(Settings.IMPORT_CARATERIZADA_LENGTH);


        LinkedList<CelulaBase> ccLst = new LinkedList<>();
        //get cobertura
        CoberturaRepository cr = new CoberturaRepository();
        //get envolvente
        EnvolventeRepository er = new EnvolventeRepository();
        //get fatorrisco
        FatorRiscoRepository frr = new FatorRiscoRepository();
        if (fields != null) {
            for (String[] str : fields) {
                for (Envolvente envTmp : er.findAllByTitle(str[Settings.ENVOLVENTE])) {
                    Cobertura cb = cr.findByTitle(str[0]);
                    FatorRisco fr = frr.findByTitle(str[2]);

                    ccLst.add(
                        new CelulaCaracterizada(
                            Peso.criarPeso(Integer.parseInt(str[3])),
                            Settings.POSITIVA.equals(str[5]) ? Contribuicao.contribuicaoPositiva() : Contribuicao.contribuicaoNegativa(),
                            Settings.OBRIGATORIO.equals(str[4]) ? Necessidade.necessidadeObrigatoria() : Necessidade.necessidadeFacultativa(),
                            cb, envTmp, fr
                        )
                    );
                }
            }
            return m.caracterizarMatriz(ccLst);
        } else {
            return null;
        }


    }


    public Matriz importDetalhe(Matriz m, String filePath) {
        IO adapter = AS.getAdapter(filePath);
        List<String[]> fields = adapter.importObject(Settings.IMPORT_DETALHADA_LENGTH);

        LinkedList<CelulaDetalhada> cdLst = new LinkedList<>();
        //get cobertura
        CoberturaRepository cr = new CoberturaRepository();
        //get envolvente
        EnvolventeRepository er = new EnvolventeRepository();
        //get fatorrisco
        FatorRiscoRepository frr = new FatorRiscoRepository();
        if (fields != null) {
            for (String[] str : fields) {
                for (Envolvente envTmp : er.findAllByTitle(str[Settings.ENVOLVENTE])) {
                    Cobertura cb = cr.findByTitle(str[0]);
                    FatorRisco fr = frr.findByTitle(str[2]);

                    cdLst.add(
                        new CelulaDetalhada(
                            Escala.criarEscala(
                                Integer.parseInt(str[3]),
                                Integer.parseInt(str[4]),
                                Integer.parseInt(str[5])),
                            Peso.criarPeso(0),
                            Contribuicao.contribuicaoPositiva(),
                            Necessidade.necessidadeObrigatoria(),
                            cb,
                            envTmp,
                            fr
                        )
                    );
                }
            }

            return m.detalharMatriz(cdLst);
        } else {
            return null;
        }
    }

    public boolean exportMatriz(Exporter matrizObj, String filePath) {
        IO adapter = AS.getAdapter(filePath);
        return adapter.exportObject(matrizObj);
    }
}
