package print;

import models.avaliacao.Caso;
import models.avaliacao.ObjetoSeguro;
import modelsDTO.AvaliacaoDTO;
import modelsDTO.CasoDTO;
import modelsDTO.ObjetoSeguroDTO;

import java.util.Iterator;
import java.util.List;

public class BuildPrintAvaliacao {

    public String buildResult(Caso caso, Long idMatriz) {
        AvaliacaoDTO avaliacaoDTO;
        Integer riskTotal = 0;

        StringBuilder res = new StringBuilder("======================================================================\n");
        res.append("                           AVALIACAO                                  \n").
            append("======================================================================\n").
            append("    Caso ").append(caso.getID()).append(" avaliado com matriz ").append(idMatriz).append("\n").
            append("======================================================================\n").
            append("    DESCRIÇÃO OBJECTO SEGURO:                                         \n").
            append("----------------------------------------------------------------------\n").
            append("    DEMONSTRACAO                                           |          \n").
            append("-----------------------------------------------------------|----------\n").
            append("                                                           |  INDICE  \n");

        int countObj = 0;
        for (ObjetoSeguro obj : caso.objetosSegurados()) {
            countObj++;
            res.append("======================================================================\n").
                append("   ").append(obj.getDescricao()).append("\n").
                append("----------------------------------------------------------------------\n");
            avaliacaoDTO = obj.getAvaliacao().toDTO();
            res.append(avaliacaoDTO.getDemonstracao()).append("\n").
                append("----------------------------------------------------------------------\n").
                append("                                                           |  ").append(avaliacaoDTO.getIndiceRisco()).append("\n");
            riskTotal += avaliacaoDTO.getIndiceRisco();
        }
        riskTotal /= countObj;
        res.append("======================================================================\n").
            append("                                                     TOTAL |  ").append(riskTotal).append("\n").
            append("======================================================================\n");

        return res.toString();
    }

    public String buildComparedResult(CasoDTO casoDTO, List<AvaliacaoDTO> listaDemoCaso1,
                                      List<AvaliacaoDTO> listaDemoCaso2,
                                      Long idMatriz1, Long idMatriz2) {

        Integer riskTotal1 = 0;
        Integer riskTotal2 = 0;

        StringBuilder res = new StringBuilder("==========================================================================================\n");
        res.append("                           AVALIACAO                                  \n").
            append("==========================================================================================\n").
            append("Caso ").append(casoDTO.getID()).append(" avaliado com matriz ").append(idMatriz1).append("\n").
            append("------------------------------------------------------------------------------------------\n").
            append("Caso ").append(casoDTO.getID()).append(" avaliado com matriz ").append(idMatriz2).append("\n").
            append("==========================================================================================\n").
            append("    DESCRIÇÃO OBJECTO SEGURO:                                         \n").
            append("------------------------------------------------------------------------------------------\n").
            append("            DEMONSTRACAO 1    ( Score Obtido / Score Máximo ) * 100                                             |          \n").
            append("------------------------------------------------------------------------------------------\n").
            append("                                                                           |  INDICE 1 \n").
            append("------------------------------------------------------------------------------------------\n").
            append("            DEMONSTRACAO 2    ( Score Obtido / Score Máximo ) * 100                                              |          \n").
            append("------------------------------------------------------------------------------------------\n").
            append("                                                                           |  INDICE 2 \n");

        int countObj = 0;
        Iterator<AvaliacaoDTO> it1 = listaDemoCaso1.iterator();
        Iterator<AvaliacaoDTO> it2 = listaDemoCaso2.iterator();
        Iterator<ObjetoSeguroDTO> it3 = casoDTO.getObjetosSegurados().iterator();
        while (it1.hasNext() && it2.hasNext()) {
            countObj++;
            AvaliacaoDTO av1 = it1.next();
            AvaliacaoDTO av2 = it2.next();
            ObjetoSeguroDTO objDTO = it3.next();

            res.append("==========================================================================================\n").
                append(objDTO.getDescricao()).append("\n").
                append("------------------------------------------------------------------------------------------\n").
                append(av1.getDemonstracao()).append("\n").
                append("------------------------------------------------------------------------------------------\n").
                append("                                                                           |  ").append(av1.getIndiceRisco()).append("\n").
                append(av2.getDemonstracao()).append("\n").
                append("------------------------------------------------------------------------------------------\n").
                append("                                                                           |  ").append(av2.getIndiceRisco()).append("\n");
            riskTotal1 += av1.getIndiceRisco();
            riskTotal2 += av2.getIndiceRisco();
        }

        riskTotal1 /= countObj;
        riskTotal2 /= countObj;

        res.append("==========================================================================================\n").
            append("                                                                     TOTAL |  ").append(riskTotal1).append("\n").
            append("------------------------------------------------------------------------------------------\n").
            append("                                                                     TOTAL |  ").append(riskTotal2).append("\n").
            append("==========================================================================================\n").
            append("OBS:    O risco é ");

        if (riskTotal1 > riskTotal2) {
            res.append("maior com a matriz ").append(idMatriz1).append(".\n");
        } else if (riskTotal1 < riskTotal2) {
            res.append("maior com a matriz ").append(idMatriz2).append(".\n");
        } else {
            res.append("igual com as duas matrizes.\n");
        }
        res.append("==========================================================================================\n");

        return res.toString();
    }

}
