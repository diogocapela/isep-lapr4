<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml" version="2.0">
    <xsl:output method="xml" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" indent="yes"/>
    <xsl:template match="/">

        <html xmlns="http://www.w3.org/1999/xhtml">
            <h1>OUTPUT</h1>
            <br />
            <h2>RESULT:</h2>
            <ul>
                <xsl:for-each select="//result"><li><xsl:value-of select="../result"/></li></xsl:for-each>
            </ul>
        </html>

    </xsl:template>
</xsl:stylesheet>
