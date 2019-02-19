<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text"/>
    <xsl:template match="/">
        {
        "output": {
            "titulo":"<xsl:value-of select="//titulo"/>",
            "local":{
                "descricaoObjeto":"<xsl:value-of select="//descricaoObjeto"/>",
                "latitude":"<xsl:value-of select="//latitude"/>",
                "longitude":"<xsl:value-of select="//longitude"/>"
            },
            "avaliacaoA":{
                "simulacaoA":"<xsl:value-of select="//simulacaoA"/>",
                "demonstracaoParte1":{
                    "demonstracaoA":[
                        <xsl:for-each select="//demonstracaoA">
                            "<xsl:value-of select="."/>"
                            <xsl:if test="position() != last()">,</xsl:if>
                        </xsl:for-each>
                    ]
                },
                "indiceRiscoA":"<xsl:value-of select="//indiceRiscoA"/>"
            },
            "avaliacaoB":{
                "simulacaoB":"<xsl:value-of select="//simulacaoB"/>",
                "demonstracaoParte2":{
                    "demonstracaoB":[
                        <xsl:for-each select="//demonstracaoB">
                            "<xsl:value-of select="."/>"
                            <xsl:if test="position() != last()">,</xsl:if>
                        </xsl:for-each>
                    ]
                },
                "indiceRiscoB":"<xsl:value-of select="//indiceRiscoB"/>"
            },
            "sumario":"<xsl:value-of select="//sumario"/>"
            }
        }
    </xsl:template>
</xsl:stylesheet>
