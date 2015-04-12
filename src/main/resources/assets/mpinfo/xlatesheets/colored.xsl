<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:codes="xalan://org.blockartistry.mod.ModpackInfo.BBCodeColorInfo">
  <xsl:output method="text" indent="yes"/>
  <xsl:param name="modpack.name.begin"/>
  <xsl:param name="modpack.name.end"/>
  <xsl:param name="modpack.version.begin"/>
  <xsl:param name="modpack.version.end"/>
  <xsl:param name="modpack.website.begin"/>
  <xsl:param name="modpack.websiteURL.end"/>
  <xsl:param name="modpack.websiteURL.begin"/>
  <xsl:param name="modpack.website.end"/>
  <xsl:param name="modpack.description.begin"/>
  <xsl:param name="modpack.description.end"/>
  <xsl:param name="modpack.credits.begin"/>
  <xsl:param name="modpack.credits.end"/>
  <xsl:param name="modpack.authors.begin"/>
  <xsl:param name="modpack.authors.end"/>
  <xsl:param name="modpack.environment.begin"/>
  <xsl:param name="modpack.environment.end"/>
  <xsl:param name="mod.name.begin"/>
  <xsl:param name="mod.name.end"/>
  <xsl:param name="mod.version.begin"/>
  <xsl:param name="mod.version.end"/>
  <xsl:param name="mod.website.begin"/>
  <xsl:param name="mod.website.end"/>
  <xsl:param name="mod.websiteURL.begin"/>
  <xsl:param name="mod.websiteURL.end"/>
  <xsl:param name="mod.description.begin"/>
  <xsl:param name="mod.description.end"/>
  <xsl:param name="mod.credits.begin"/>
  <xsl:param name="mod.credits.end"/>
  <xsl:param name="mod.authors.begin"/>
  <xsl:param name="mod.authors.end"/>
  <xsl:param name="mod.id.begin"/>
  <xsl:param name="mod.id.end"/>
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
      <xsl:value-of select="$modpack.name.begin"/><xsl:value-of select="name"/><xsl:value-of select="$modpack.name.end"/><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:if test="version">
      <xsl:value-of select="$modpack.version.begin"/><xsl:text>Version </xsl:text><xsl:value-of select="version"/><xsl:value-of select="$modpack.version.end"/><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:if test="website">
      <xsl:value-of select="$modpack.website.begin"/><xsl:value-of select="website"/><xsl:value-of select="$modpack.website.end"/><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:if test="description">
      <xsl:value-of select="$newline"/><xsl:value-of select="$modpack.description.begin"/><xsl:value-of select="description"/><xsl:value-of select="$modpack.description.end"/><xsl:value-of select="$newline"/><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:if test="credits">
      <xsl:value-of select="$modpack.credits.begin"/><xsl:text>Modpack Credits: </xsl:text><xsl:value-of select="credits"/><xsl:value-of select="$modpack.credits.end"/><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:if test="authors">
      <xsl:value-of select="$modpack.authors.begin"/><xsl:text>Modpack Authors: </xsl:text><xsl:value-of select="authors"/><xsl:value-of select="$modpack.authors.end"/><xsl:value-of select="$newline"/>
    </xsl:if>
    <xsl:value-of select="$newline"/>
    <xsl:value-of select="$modpack.environment.begin"/><xsl:value-of select="$separator"/><xsl:value-of select="$modpack.environment.end"/>
    <xsl:value-of select="$newline"/>
  </xsl:template>
  <xsl:template match="ModpackInfo/modlist">
    <xsl:for-each select="Mod">
      <xsl:value-of select="$mod.name.begin"/><xsl:value-of select="@name"/><xsl:value-of select="$mod.name.end"/><xsl:value-of select="$mod.id.begin"/><xsl:text> (</xsl:text><xsl:value-of select="@id"/><xsl:text>)</xsl:text><xsl:value-of select="$mod.id.end"/><xsl:value-of select="$mod.version.begin"/><xsl:text> Version </xsl:text><xsl:value-of select="@version"/><xsl:value-of select="$mod.version.end"/><xsl:value-of select="$newline"/>
      <xsl:if test="description">
        <xsl:value-of select="$mod.description.begin"/><xsl:value-of select="description"/><xsl:value-of select="$mod.description.end"/><xsl:value-of select="$newline"/>
      </xsl:if>
      <xsl:if test="credits">
        <xsl:value-of select="$mod.credits.begin"/><xsl:text>Credits: </xsl:text><xsl:value-of select="credits"/><xsl:value-of select="$mod.credits.end"/><xsl:value-of select="$newline"/>
      </xsl:if>
      <xsl:if test="authors">
        <xsl:value-of select="$mod.authors.begin"/><xsl:text>Authors: </xsl:text><xsl:value-of select="authors"/><xsl:value-of select="$mod.authors.end"/><xsl:value-of select="$newline"/>
      </xsl:if>
      <xsl:value-of select="$mod.website.begin"/><xsl:text>Website: </xsl:text><xsl:value-of select="$mod.website.end"/><xsl:value-of select="$mod.websiteURL.begin"/><xsl:value-of select="url"/><xsl:value-of select="$mod.websiteURL.end"/><xsl:value-of select="$newline"/>
      <xsl:value-of select="$newline"/>
    </xsl:for-each>
  </xsl:template>
  <xsl:template match="Environment">
    <xsl:value-of select="$modpack.environment.begin"/>
    <xsl:value-of select="$separator"/>
    <xsl:text>OS: </xsl:text><xsl:value-of select="os"/><xsl:value-of select="$newline"/>
    <xsl:text>Java: </xsl:text><xsl:value-of select="java"/><xsl:value-of select="$newline"/>
    <xsl:value-of select="$separator"/>
    <xsl:value-of select="$modpack.environment.end"/>
  </xsl:template>
</xsl:stylesheet>
