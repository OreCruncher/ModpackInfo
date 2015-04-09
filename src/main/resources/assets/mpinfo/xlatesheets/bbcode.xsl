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
      <xsl:text>[color=orange][size=6]</xsl:text><xsl:value-of select="name"/><xsl:text>[/size][/color]</xsl:text><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:if test="version">
      <xsl:text>[color=yellow][size=5]Version </xsl:text><xsl:value-of select="version"/><xsl:text>[/size][/color]</xsl:text><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:if test="website">
      <xsl:text>[color=cyan][url]</xsl:text><xsl:value-of select="website"/><xsl:text>[/url][/color]</xsl:text><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:if test="description">
      <xsl:value-of select="$newline"/><xsl:text>[color=lightblue]</xsl:text><xsl:value-of select="description"/><xsl:text>[/color]</xsl:text><xsl:value-of select="$newline"/><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:if test="credits">
      <xsl:text>[color=gold]Modpack Credits[/color]: [color=Khaki]</xsl:text><xsl:value-of select="credits"/><xsl:text>[/color]</xsl:text><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:if test="authors">
      <xsl:text>[color=gold]Modpack Authors[/color]: [color=Khaki]</xsl:text><xsl:value-of select="authors"/><xsl:text>[/color]</xsl:text><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:value-of select="$newline"/>
    <xsl:text>[color=lightgray]</xsl:text><xsl:value-of select="$separator"/><xsl:text>[/color]</xsl:text>
    <xsl:value-of select="$newline"/>
  </xsl:template>
  <xsl:template match="ModpackInfo/modlist">
    <xsl:for-each select="Mod">
      <xsl:text>[u][color=orange][size=5]</xsl:text><xsl:value-of select="@name"/><xsl:text>[/size] (</xsl:text><xsl:value-of select="@id"/><xsl:text>)[/color] [color=yellow]Version </xsl:text><xsl:value-of select="@version"/><xsl:text>[/color][/u]</xsl:text><xsl:value-of select="$newline"/>
      <xsl:if test="description">
        <xsl:text>[color=lightblue]</xsl:text><xsl:value-of select="description"/><xsl:text>[/color]</xsl:text><xsl:value-of select="$newline"/>
      </xsl:if>
      <xsl:if test="credits">
        <xsl:text>[color=gold]Credits[/color]: [color=Khaki]</xsl:text><xsl:value-of select="credits"/><xsl:text>[/color]</xsl:text><xsl:value-of select="$newline"/>
      </xsl:if>
      <xsl:if test="authors">
        <xsl:text>[color=gold]Authors[/color]: [color=Khaki]</xsl:text><xsl:value-of select="authors"/><xsl:text>[/color]</xsl:text><xsl:value-of select="$newline"/>
      </xsl:if>
      <xsl:text>[color=cyan]Website[/color]: [url]</xsl:text><xsl:value-of select="url"/><xsl:text>[/url]</xsl:text><xsl:value-of select="$newline"/>
      <xsl:value-of select="$newline"/>
    </xsl:for-each>
  </xsl:template>
  <xsl:template match="Environment">
    <xsl:text>[color=lightgray]</xsl:text>
    <xsl:value-of select="$separator"/>
    <xsl:text>OS: </xsl:text><xsl:value-of select="os"/><xsl:value-of select="$newline"/>
    <xsl:text>Java: </xsl:text><xsl:value-of select="java"/><xsl:value-of select="$newline"/>
    <xsl:value-of select="$separator"/>
    <xsl:text>[/color]</xsl:text>
  </xsl:template>
</xsl:stylesheet>
