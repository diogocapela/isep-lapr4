<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:raconsole.output method="text"/>
    <xsl:template match="/">
        {
        "Data de Inicio do servidor": {
        "Ano":"<xsl:value-of select="//serverstart/ano"/>",
        "Mes":"<xsl:value-of select="//serverstart/mes"/>",
        "Dia":"<xsl:value-of select="//serverstart/dia"/>",
        "Hora":"<xsl:value-of select="//serverstart/time"/>"
        },
        "Casos Aceites":"<xsl:value-of select="//aceptedcases"/>",
        "Casos Rejeitados":"<xsl:value-of select="//rejectedcases"/>",
        "Casos Activos":"<xsl:value-of select="//activecases"/>",
        "Tempo medio de resposta":"<xsl:value-of select="//averageresponsetime"/>"
        "Controlador de pedidos (Max 30/minuto))":"<xsl:value-of select="//maxCapacityPer60Seconds"/>"
        }
    </xsl:template>
</xsl:stylesheet>
