<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:cv="http://fi.muni.cz/pb138/cvWebGen/xml" xmlns:xs="http://www.w3.org/2001/XMLSchema"  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text" encoding="utf-8"/>

<xsl:template match="cv:cv">
\documentclass[helvetica,openbib,totpages,slovak]{europecv}
\usepackage[T1]{fontenc}
\usepackage{graphicx}
\usepackage[a4paper,top=1.27cm,left=1cm,right=1cm,bottom=2cm]{geometry}
\usepackage[czech]{babel}
\usepackage{bibentry}
\usepackage{url}

\renewcommand{\ttdefault}{phv} % Uses Helvetica instead of fixed width font

<xsl:apply-templates select="cv:personal"/>
<xsl:apply-templates select="cv:address"/>
<xsl:apply-templates select="cv:contacts/phone" />
<xsl:apply-templates select="cv:contacts/email" />


\ecvfootnote{For more information go to \url{http://europass.cedefop.eu.int}\\
\textcopyright~European Communities, 2003.}

\begin{document}
\selectlanguage{english}


\begin{europecv}
\ecvpersonalinfo[5pt]

<!-- -->
<xsl:apply-templates select="cv:works"/>
<xsl:apply-templates select="cv:educations"/>
<xsl:apply-templates select="cv:languages"/>
<xsl:apply-templates select="cv:skills"/>

\end{europecv}

\end{document}       
   
</xsl:template>





<xsl:template match="cv:personal">
\ecvname{<xsl:value-of select="cv:firstName"/><xsl:text> </xsl:text><xsl:value-of select="cv:lastName"/>}
\ecvfootername{<xsl:value-of select="cv:firstName"/><xsl:text> </xsl:text><xsl:value-of select="cv:lastName"/>}
\ecvnationality{<xsl:value-of select="cv:nationality"/>}
\ecvdateofbirth{<xsl:value-of select="cv:dateofbirth"/>}
\ecvgender{<xsl:value-of select="cv:gender"/>}
%\ecvpicture[width=2cm]{mypicture}
</xsl:template>

<xsl:template match="cv:contacts/phone">
\ecvtelephone{<xsl:value-of select="." />}
</xsl:template>

<xsl:template match="cv:contacts/email">
\ecvemail{<xsl:value-of select="."/>}
</xsl:template>





<xsl:template match="cv:works">
    \ecvsection{Prax}
    <xsl:apply-templates />
</xsl:template>


<xsl:template match="cv:work">
    \ecvitem{Od - do}{<xsl:value-of select="cv:period/from/year"/><xsl:text> - </xsl:text><xsl:value-of select="cv:period/to/year"/>}
    \ecvitem{Pozícia}{<xsl:value-of select="cv:position"/>}
    \ecvitem{Zamestnávateľ}{<xsl:value-of select="cv:employer"/>}
    \ecvitem{Náplň práce}{<xsl:value-of select="cv:activities"/>}
    \ecvitem[15pt]{Obor}{<xsl:value-of select="cv:sector"/>}
</xsl:template>




<xsl:template match="cv:educations">
    \ecvsection{Vzdelanie a školenia}    
    <xsl:apply-templates />
</xsl:template>

<xsl:template match="cv:education">
\ecvitem{Od - do}{<xsl:value-of select="cv:period/from/year"/><xsl:text> - </xsl:text><xsl:value-of select="cv:period/to/year"/>}
\ecvitem{Názov}{<xsl:value-of select="cv:organisation"/>}
\ecvitem[15pt]{Popis}{<xsl:value-of select="cv:description"/>}
</xsl:template>


<xsl:template match="cv:languages">
\ecvsection{Jazyky}    
    <xsl:apply-templates />
</xsl:template>

<xsl:template match="cv:language">
    \ecvitem{<xsl:value-of select="."/>}{<xsl:value-of select="@level"/>}
</xsl:template>

<xsl:template match="cv:skills">
\ecvsection{Schopnosti}    
    <xsl:apply-templates />
</xsl:template>

<xsl:template match="cv:skill">
    \ecvitem{<xsl:value-of select="@name"/>}{<xsl:value-of select="."/>}
</xsl:template>



<xsl:template match="cv:address">
\ecvaddress{<xsl:value-of select="cv:street"/>,
            <xsl:value-of select="cv:zip"/>,
            <xsl:value-of select="cv:city"/>,
            <xsl:value-of select="cv:state"/>}
</xsl:template>

</xsl:stylesheet>
