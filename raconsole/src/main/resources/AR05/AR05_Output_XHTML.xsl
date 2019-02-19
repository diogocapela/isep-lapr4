<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns="http://www.w3.org/1999/xhtml" version="2.0">
    <xsl:output method="xml" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
                doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" indent="yes"/>

    <xsl:template match="/">
        <html xmlns="http://www.w3.org/1999/xhtml">
            <h1>Casos validados por:</h1>
            <h1>
                <xsl:value-of select="./user"/>
            </h1>
            <table style="width:70%">
                <tr>
                    <th>ID CASO</th>
                    <th>INDICE DE RISCO</th>
                    <th>DATA DE VALIDAÇÃO</th>
                    <th>TEMPO DE VALIDAÇÃO</th>
                </tr>
                <xsl:for-each select="//casos/caso">
                    <tr>
                        <td>
                            <xsl:value-of select="./idCaso"/>
                        </td>
                        <td>
                            <xsl:value-of select="./indiceRisco"/>
                        </td>
                        <td>
                            <xsl:value-of select="./dataValidacao"/>
                        </td>
                        <td>
                            <xsl:value-of select="./tempoValidacao"/>
                        </td>

                    </tr>
                </xsl:for-each>
            </table>
            <h1>Sumario:</h1>
            <xsl:for-each select="//sumario">
                <h5>
                    Analisou um total de <xsl:value-of select="./totalCasos"/> casos.
                </h5>
                <h5>
                    E demorou um tempo médio por caso de <xsl:value-of select="./tempoMedioAnalise"/> segundos.
                </h5>
            </xsl:for-each>
            <h2>Please come again!</h2>
        </html>
    </xsl:template>
</xsl:stylesheet>
