#!/bin/sh
echo -n "$0..."

test -e proc_table.dat && rm proc_table.dat

export QUERY_STRING="name=proc_table&pw=test"
create_testtable
main.cgi > output

if !(grep Status output > /dev/null)
then
  echo
  echo Failed Status header.
  echo
fi
if !(grep Task output > /dev/null)
then
  echo
  echo Failed Task header.
  echo
fi
if !(grep Resource\(s\) output > /dev/null)
then
  echo
  echo Failed Resource header.
  echo
fi
if !(grep Description output > /dev/null)
then
  echo
  echo Failed Description header.
  echo
fi
if !(grep ready output > /dev/null)
then
  echo
  echo Failed ready state.
  echo
fi
if !(grep test_script output > /dev/null)
then
  echo
  echo Failed action name.
  echo
fi
if !(grep "this is a script" output > /dev/null)
then
  echo
  echo Failed script. 
  echo
fi

#test -e output && rm output
#test -e proc_table.dat && rm proc_table.dat

echo done
