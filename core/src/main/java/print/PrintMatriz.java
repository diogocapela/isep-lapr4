package print;

import models.matriz.Matriz;
import settings.Settings;

import java.util.List;

public class PrintMatriz {


    public void print(Matriz matriz) {
        StringBuilder stringBase = new StringBuilder();
        List<String[]> listaS = matriz.toExporterDTO();
        Integer celulaLength = listaS.get(0).length;

        stringBase.append("\n ==============================================================").
            append("\n|                      MATRIZ ").
            append((celulaLength.equals(Settings.CELULA_BASE_LENGTH)) ?
                "BASE" :
                (celulaLength.equals(Settings.CELULA_CARATERIZADA_LENGTH)) ?
                    "CARATERIZADA" :
                    "DETALHADA").append(" - ID: ").append(matriz.getMatrizID()).
            append("\n ==============================================================").
            append("\n|    COBERTURAS               ENVOLVENTES                      ").
            append("\n|                |---------------------------------------------").
            append("\n|                             FATORES DE RISCO                 ");

        String cob = " ";
        String env = " ";
        for (String[] s : listaS) {
            if (!s[0].equals(cob)) {
                stringBase.append("\n ==============================================================").
                    append("\n|    ").append(s[0]);
                cob = s[0];
            }

            if (!s[1].equals(env)) {
                stringBase.append("\n|                |---------------------------------------------").
                    append("\n|                                 ").append(s[1]);
                env = s[1];
            }

            stringBase.append("\n|                |---------------------------------------------").
                append("\n|                |      ").append(s[2]);

            if (celulaLength.equals(Settings.CELULA_CARATERIZADA_LENGTH)) {
                stringBase.append("\n|                |---------------------------------------------").
                    append("\n|                | PESO            |   ").append(s[3]).
                    append("\n|                |---------------------------------------------").
                    append("\n|                | NECESSIDADE     |   ").append(s[4]).
                    append("\n|                |---------------------------------------------").
                    append("\n|                | CONTRIBUIÇÂO    |   ").append(s[5]);
            }

            if (celulaLength.equals(Settings.CELULA_DETALHADA_LENGTH)) {
                stringBase.append("\n|                |---------------------------------------------").
                    append("\n|                | ESCALA          |  BAIXA  | ").append(s[6]).
                    append("\n|                |                 ----------------------------").
                    append("\n|                |                 |  MEDIA  | ").append(s[7]).
                    append("\n|                |                 ----------------------------").
                    append("\n|                |                 |  ALTA   | ").append(s[8]);

            }
        }
        stringBase.append("\n ==============================================================");

        System.out.println(stringBase);
    }

}
