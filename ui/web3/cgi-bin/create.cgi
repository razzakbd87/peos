#!/bin/sh
# $Id: create.cgi,v 1.2 2007/04/29 07:14:43 jnoll Exp $ 
# Create process instance.

printf "Content-type: text/html\r\n"
printf "\r\n"

# Get form variables.
eval "`./parsecgi $*`"
echo "model: $FORM_model"

echo "<pre>"
if [ ${FORM_model} ] ; then
   if [ ${FORM_model}.pml ] ; then
      result=`./peos -c ${FORM_model}.pml 2>&1`
   fi
fi
echo "</pre>"

/usr/bin/xsltproc list_procs.xsl proc_table.dat.xml 2>&1
echo "<pre>"
echo "status: $? result: $result"
echo "</pre>"
