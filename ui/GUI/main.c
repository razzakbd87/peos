/*
 * Initial main.c file generated by Glade. Edit as required.
 * Glade will not overwrite this file.
 */

#ifdef HAVE_CONFIG_H
#  include <config.h>
#endif

#include <gtk/gtk.h>
/* XXX - unistd.h under RH9 fails to declare get_current_dir_name correctly */
extern char *get_current_dir_name(void);
#include <unistd.h>
#include "interface.h"
#include "support.h"
#include "engine.h"
#include "glo.h"
#include "parse.h"
#include "htmllinkage.h"

int
main (int argc, char *argv[])
{
  GtkWidget *peosgui;
  int i;
  char *path;

  cwd = get_current_dir_name();
  if(cwd == NULL) {
  	perror("No current directory found. Aborting. \n");
	exit(1);
  }

  gtk_set_locale ();
  gconf_init (argc, argv, NULL);
  gtk_init (&argc, &argv);
  //gtk_rc_parse ("./gtk/gtkrc");

  exec_path = getPath();
  if(exec_path == NULL) {
  	printf ("PEOS executable can not be found!\n");
	exit (1);
  }

  path = (char *) malloc (sizeof (char) * MAX_LEN * 2);

  for (i = 0; i < MAX_PID; i++) {
  	table[i].process = NULL;
  }

  strcat ( strcpy (path, cwd), "/proc_table.dat.xml");

  if (access (path, R_OK) == 0) {
	parse();
  }

  for (i = 0; i < MAX_PID; i++) {
	if (table[i].process != NULL) {
		cur_pid = i;
		table[cur_pid].page.curr = table[cur_pid].list.action[0];
		break;
	}
  }
  del_cmd = NULL;


  /*
   * The following code was added by Glade to create one of each component
   * (except popup menus), just so that you see something after building
   * the project. Delete any components that you don't want shown initially.
   */
  Peos = NULL;
  peosgui = create_Peos ();
  set_selection(0);	/* with current action selected */
  gtk_widget_show_all (peosgui);
  gtk_widget_set_name (peosgui, "main window");

  set_glist_NULL();

  gtk_main ();

  freeAll();
  destroyDocumentStuff();
  free(path);
  path = NULL;
  free(exec_path);
  exec_path = NULL;
  free(cwd);
  cwd = NULL;

  if (del_cmd) free (del_cmd);
  return 0;
}

