<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="text" indent="yes"/>
  <xsl:variable name="newline">
<xsl:text>
</xsl:text>
  </xsl:variable>
  <xsl:variable name="separator">
<xsl:text>=============================================================================
</xsl:text>
  </xsl:variable>
  <xsl:template match="ModpackInfo">
    <xsl:apply-templates/>
  </xsl:template>
  <xsl:template match="ModpackInfo/Pack">
    <xsl:if test="name">
      <xsl:value-of select="name"/><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:if test="version">
      <xsl:text>Version: </xsl:text><xsl:value-of select="version"/><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:if test="website">
      <xsl:value-of select="website"/><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:if test="description">
      <xsl:value-of select="$newline"/><xsl:value-of select="description"/><xsl:value-of select="$newline"/><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:if test="credits">
      <xsl:text>Modpack Credits: </xsl:text><xsl:value-of select="credits"/><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:if test="authors">
      <xsl:text>Modpack Authors: </xsl:text><xsl:value-of select="authors"/><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:value-of select="$newline"/>
    <xsl:value-of select="$separator"/>
    <xsl:value-of select="$newline"/>
  </xsl:template>
  <xsl:template match="ModpackInfo/modlist">
    <xsl:for-each select="Mod">
      <xsl:value-of select="@name"/><xsl:text> (</xsl:text><xsl:value-of select="@id"/><xsl:text>) Version </xsl:text><xsl:value-of select="@version"/><xsl:value-of select="$newline"/>
      <xsl:if test="description">
        <xsl:value-of select="description"/><xsl:value-of select="$newline"/>
      </xsl:if>
      <xsl:if test="credits">
        <xsl:text>Credits: </xsl:text><xsl:value-of select="credits"/><xsl:value-of select="$newline"/>
      </xsl:if>
      <xsl:if test="authors">
        <xsl:text>Authors: </xsl:text><xsl:value-of select="authors"/><xsl:value-of select="$newline"/>
      </xsl:if>
      <xsl:text>Website: </xsl:text><xsl:value-of select="url"/><xsl:value-of select="$newline"/>
      <xsl:value-of select="$newline"/>    
    </xsl:for-each>
  </xsl:template>
  <xsl:template match="Environment">
    <xsl:value-of select="$separator"/>
    <xsl:text>OS: </xsl:text><xsl:value-of select="os"/><xsl:value-of select="$newline"/>
    <xsl:text>Java: </xsl:text><xsl:value-of select="java"/><xsl:value-of select="$newline"/>
    <xsl:value-of select="$separator"/>
  </xsl:template>
</xsl:stylesheet>
