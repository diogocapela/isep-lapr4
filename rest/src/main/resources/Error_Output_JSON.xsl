<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text"/>
    <xsl:template match="/">
        {
            "error": {
                "message": "<xsl:value-of select="//error"/>"
            }
        }
    </xsl:template>
</xsl:stylesheet>
