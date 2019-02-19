<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns="http://www.w3.org/1999/xhtml" version="2.0">
    <xsl:raconsole.output method="xml" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" indent="yes"/>

    <xsl:template match="/">
        <html xmlns="http://www.w3.org/1999/xhtml">
            <body>
                <h2>Server Status</h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>Server Started</th>
                        <th>Acepted Request</th>
                        <th>Rejected Request</th>
                        <th>Active Request</th>
                        <th>Average Request</th>
                        <th>Rate Controller (Max 30/m)</th>
                    </tr>
                    <tr>
                        <td><xsl:value-of select="raconsole.output/serverstart"/></td>
                        <td><xsl:value-of select="raconsole.output/aceptedcases"/></td>
                        <td><xsl:value-of select="raconsole.output/rejectedcases"/></td>
                        <td><xsl:value-of select="raconsole.output/activecases"/></td>
                        <td><xsl:value-of select="raconsole.output/averageresponsetime"/></td>
                        <td><xsl:value-of select="raconsole.output/maxCapacityPer60Seconds"/></td>
                    </tr>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
