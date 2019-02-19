<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text"/>
    <xsl:template match="/">
        {
            "root": {
                "outputs" : {
                    "output" : [
                        <xsl:for-each select="//output">
                        {
                        "message": "<xsl:value-of select="message"/>",
                        <xsl:if test="idCaso != ''">
                        "idCaso" : <xsl:value-of select="idCaso"></xsl:value-of>,
                        </xsl:if>
                            <xsl:if test="estado != ''">
                        "estado" : "<xsl:value-of select="estado"></xsl:value-of>"
                            </xsl:if>
                            <xsl:if test="avaliacao != ''">
                                ,
                        "avaliacao" : <xsl:value-of select="avaliacao"></xsl:value-of>
                            </xsl:if>
                        }<xsl:if test="position() != last()">,</xsl:if>
                        </xsl:for-each>
                    ]
                }
            }
        }
    </xsl:template>
</xsl:stylesheet>
