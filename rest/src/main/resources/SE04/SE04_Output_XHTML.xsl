<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns="http://www.w3.org/1999/xhtml" version="2.0">
    <xsl:output method="xml" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" indent="yes"/>

    <xsl:template match="/">
        <html xmlns="http://www.w3.org/1999/xhtml">
            <h1><xsl:value-of select="//titulo"/></h1>
            <p><b><xsl:value-of select="//descricaoObjeto"/></b></p>
            <p><b><xsl:value-of select="//latitude"/></b></p>
            <p><b><xsl:value-of select="//longitude"/></b></p>

            <table>
                <tr><th><xsl:value-of select="//simulacaoA"/></th></tr>
                <tr>
                    <xsl:for-each select="//demonstracaoA">
                        <td><xsl:value-of select="."/></td>
                    </xsl:for-each>
                </tr>
                <tr><td><xsl:value-of select="//indiceRiscoA"/></td></tr>

                <tr><th><xsl:value-of select="//simulacaoB"/></th></tr>
                <tr>
                    <xsl:for-each select="//demonstracaoB">
                        <td><xsl:value-of select="."/></td>
                    </xsl:for-each>
                </tr>
                <tr><td><xsl:value-of select="//indiceRiscoB"/></td></tr>
            </table>

            <h2><xsl:value-of select="//sumario"/></h2>
        </html>
    </xsl:template>
</xsl:stylesheet>
