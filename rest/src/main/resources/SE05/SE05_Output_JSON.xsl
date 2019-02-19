<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text"/>
    <xsl:template match="/">
        {
            "output": {
                "result": [
                    <xsl:for-each select="//result">"<xsl:value-of select="../result"/>"<xsl:if test="position() != last()">,</xsl:if></xsl:for-each>
                ]
            }
        }
    </xsl:template>
</xsl:stylesheet>
