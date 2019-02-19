<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns="http://www.w3.org/1999/xhtml" version="2.0">
    <xsl:output method="xml" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
                doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" indent="yes"/>

    <xsl:template match="/">
        <html xmlns="http://www.w3.org/1999/xhtml">
            <h1>Caso</h1>
            <h1>
                <xsl:value-of select="./id"/>
            </h1>
            <table style="width:70%">
                <tr>
                    <th>Descricao</th>
                    <th>Latitude</th>
                    <th>Longitude</th>
                </tr>
                <xsl:for-each select="//objetoSeguro">
                    <tr>
                        <td>
                            <xsl:value-of select="./descricao"/>
                        </td>
                        <td>
                            <xsl:value-of select="./latitude"/>
                        </td>
                        <td>
                            <xsl:value-of select="./longitude"/>
                        </td>
                    </tr>
                </xsl:for-each>
            </table>
            <h1>
                <xsl:value-of select=".//estado"/>
            </h1>

            <h2>Please come again!</h2>
        </html>
    </xsl:template>
</xsl:stylesheet>
