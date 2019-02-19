package models;

import modelsDTO.FatorRiscoDTO;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import services.FatorRiscoService;
import utils.antlr.SyntaxErrorListener;
import utils.antlr.laprLexer;
import utils.antlr.laprParser;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Objects;

import static org.antlr.v4.runtime.CharStreams.fromReader;

@Entity
@Table
public class FatorRisco implements Serializable {

    // Variáveis de Instância
    //================================================================

    private final FatorRiscoService fs = new FatorRiscoService();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Long version;
    @Column(nullable = false, unique = true)
    private String titulo;
    @Column(nullable = false)
    private String descricao;

    private String expressao;
    private Integer minEscala;
    private Integer maxEscala;

    // Construtores
    //================================================================

    public FatorRisco() {
        this.expressao = null;
        this.minEscala = null;
        this.maxEscala = null;
    }

    public FatorRisco(String titulo, String descricao) {
        if (titulo == null || descricao == null) {
            throw new IllegalArgumentException();
        }
        if (fs.existsOnDbByTitle(titulo)) {
            FatorRisco t = fs.getFatorRiscoByTitle(titulo);
            this.titulo = t.titulo;
            this.descricao = t.descricao;
            this.id = t.id;
            this.version = t.version;
            this.expressao = t.expressao;
            this.minEscala = t.minEscala;
            this.maxEscala = t.maxEscala;
        } else {
            this.titulo = titulo;
            this.descricao = descricao;
            this.expressao = null;
            this.minEscala = null;
            this.maxEscala = null;
        }
    }

    // Métodos
    //================================================================

    public Boolean atribuirExpressao(String expressao) {
        if (expressao != null) {
            try {
                StringReader stringReader = new StringReader(expressao.toLowerCase());
                CharStream charStream = fromReader(stringReader);
                laprLexer lexer = new laprLexer(charStream);
                CommonTokenStream token = new CommonTokenStream(lexer);
                laprParser parser = new laprParser(token);

                SyntaxErrorListener listener = new SyntaxErrorListener();
                parser.addErrorListener(listener);

                parser.valContador = 0;
                parser.minEscala = Integer.MAX_VALUE;
                parser.maxEscala = Integer.MIN_VALUE;

                parser.expressao();
                if (listener.toString().length() == 0) {
                    if (parser.valContador > 2) {
                        this.expressao = expressao.toLowerCase();
                        this.minEscala = parser.minEscala;
                        this.maxEscala = parser.maxEscala;
                        return true;
                    } else {
                        System.err.println("São necessários pelo menos três ocorrências da expressão 'val' para atribuir valor de Escala");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public Rating determinarEscala(Double testVal) {
        if (expressao != null && testVal != null) {
            try {
                StringReader stringReader = new StringReader(expressao.replace("val", testVal.toString()));
                CharStream charStream = fromReader(stringReader);
                laprLexer lexer = new laprLexer(charStream);
                CommonTokenStream token = new CommonTokenStream(lexer);
                laprParser parser = new laprParser(token);

                parser.escalaValores = new LinkedList<>();
                parser.expressao();
                return normalizarEscala(parser);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Rating.HIGH;
    }

    private Rating normalizarEscala(laprParser parser) {
        Double mediaEscala = 0D;
        for (Integer valorTmp : parser.escalaValores) {
            mediaEscala = mediaEscala + valorTmp;
        }
        mediaEscala = mediaEscala / parser.escalaValores.size();

        if (mediaEscala >= 1) {
            double midWay = ((double) this.maxEscala + (double) this.minEscala) / 2;
            // previous solution (still implemented for the case of 1 2 3 values)
            // adapted range solution
            double oneSixthRange = (this.maxEscala - this.minEscala) / 6;
            if (mediaEscala < midWay - oneSixthRange) {
                return Rating.LOW;
            } else if (mediaEscala > midWay + oneSixthRange) {
                return Rating.HIGH;
            } else {
                return Rating.MEDIUM;
            }
        }

        return Rating.HIGH;
    }

    @Override
    public String toString() {
        return "FatorRisco{" +
            "id=" + id +
            ", titulo='" + titulo + '\'' +
            ", descricao='" + descricao + '\'' +
            ", version=" + version +
            '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FatorRisco other = (FatorRisco) obj;
        return Objects.equals(this.titulo, other.titulo);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.titulo);
        return hash;
    }

    public FatorRiscoDTO toDTO() {
        return new FatorRiscoDTO(this.titulo, this.descricao);
    }

}
