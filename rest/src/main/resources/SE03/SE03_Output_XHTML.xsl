<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns="http://www.w3.org/1999/xhtml" version="2.0">
    <xsl:output method="xml" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" indent="yes"/>
    
    <xsl:template match="/">
        <html>
            <head>
                <style>
                    table {
                    border-collapse: collapse;
                    }

                    table, th, td {
                    border: 1px solid black;
                    }
                    td {
                        text-align: center;
                    }
                </style>
            </head>
            <h1>Resultado do pedido</h1>
            <a>Resultado da chamada de api: <strong><xsl:value-of select="//message"/></strong>
            </a>
            <p>
                <table style ="width: 100%;">
                    <tr>
                        <th>ID caso</th>
                        <th>Estado do pedido</th>
                        <th>Avaliacao de risco</th>
                    </tr>
                    <xsl:for-each select="//output">
                    <tr>
                        <td>
                        <xsl:value-of select="idCaso"></xsl:value-of>
                        </td>
                        <td>
                            <xsl:value-of select="estado"></xsl:value-of>
                        </td>
                        <td>
                            <xsl:value-of select="avaliacao"></xsl:value-of>
                        </td>
                    </tr>
                    </xsl:for-each>
                </table>
            </p>
        </html>
    </xsl:template>
</xsl:stylesheet>
