
#define YYDEBUG

#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <unistd.h>
/* Kernel includes */
#include "events.h"
#include "process_table.h"
#include "pmlheaders.h"
#include "graph_engine.h"
#include "process.h"
#include "peos_util.h"

/* XXX This is a hack made possible by the fact that process_table_filename defined in process_table.c is not static. */
extern char *process_table_filename;

int create_process(char *model) {
    int pid;
    int num_resources;
    peos_resource_t *resources;
    char* res_file;
    
    resources = (peos_resource_t *) peos_get_resource_list(model,&num_resources);    //see events.c

    if (resources == NULL) {
        printf("error getting resources\n");
        return -1;
    }
    
    printf("Executing %s:\n", model);
    
    if ((pid = peos_run(model,resources,num_resources)) < 0) {    //see events.c
        printf("couldn't create process\n");
        return -1;
    }
    
    if ((res_file = peos_get_resource_file(model)))
        peos_bind_resource_file(pid, res_file);

    printf("Created pid = %d\n", pid);
    return 1;
}


void set_login_name(char *loginname)
{
    char *process_filename;

    process_filename = (char *) malloc((strlen(loginname) + strlen(".dat") +1) * sizeof(char));
    strcpy(process_filename, loginname);
    strcat(process_filename, ".dat"); 

    peos_set_process_table_file(process_filename);
    peos_set_loginname(loginname);
}


void emit_xml() {
    FILE *in;
    int c;
    char *xml_data_filename = (char *) calloc((strlen(process_table_filename) + strlen(".xml") + 1), sizeof(char));


    strcpy(xml_data_filename, process_table_filename);
    strcat(xml_data_filename, ".xml");
    printf("Content-type: text/xml; charset=UTF-8\r\n\r\n");
    if(in = fopen(xml_data_filename, "r")) {
	while ((c = getc(in)) != EOF) {
	    putchar(c);
	} 
	fclose(in);
    } else {
	printf("<process_table/>\r\n");
    }
}


int
main (int argc, char **argv)
{
    int status;
    char *pid;
    char *action;
    char *event;
    char *model;
    char *login = "proc_table"; /* default login name */
    char *res_file;
    char *request_method= getenv("REQUEST_METHOD") ;
    char *op, **cgivars = getcgivars();

    if (strcmp(request_method, "POST") == 0) {
	set_login_name(login);
	op = getvalue("op", cgivars);


	if (strcmp(op, "create") == 0) {
	    model = getvalue("model", cgivars);

	    if (create_process(model) != 1) {
		fprintf(stderr, "Could not Create Process\n");
		exit(EXIT_FAILURE);
	    }

	} else if (strcmp(op, "delete") == 0) {
	    pid = getvalue("pid", cgivars);

	    if (peos_delete_process_instance(atoi(pid)) < 0) {
		fprintf(stderr, "Could not delete process instance\n");
		exit(EXIT_FAILURE);
	    }
	} else if (strcmp(op, "start") == 0) {
	    pid = getvalue("pid", cgivars);
	    action  = getvalue("action", cgivars);

	    if ((status = peos_notify(atoi(pid), action, PEOS_EVENT_START)) == VM_ERROR 
		|| (status == VM_INTERNAL_ERROR)) {
		printf("process executed an illegal instruction and has been terminated\n");
		return -1;
	    }

	} else if (strcmp(op, "finish") == 0) {
	    pid = getvalue("pid", cgivars);
	    action  = getvalue("action", cgivars);

	    if ((status = peos_notify(pid, action,PEOS_EVENT_FINISH)) == VM_ERROR 
		|| status == VM_INTERNAL_ERROR) {
		printf("process executed an illegal instruction and has been terminated\n");
		return -1;
	    }

	} else if (strcmp(op, "abort") == 0) {
	    pid = getvalue("pid", cgivars);
	    action  = getvalue("action", cgivars);

	    if ((status = peos_notify(pid, action,PEOS_EVENT_ABORT)) == VM_ERROR 
		|| status == VM_INTERNAL_ERROR) {
		printf("process encountered an illegal event and has been terminated\n");
		return -1;
	    }

	} else if (strcmp(op, "suspend") == 0) {
	    pid = getvalue("pid", cgivars);
	    action  = getvalue("action", cgivars);

	    if ((status = peos_notify(pid, action,PEOS_EVENT_SUSPEND)) == VM_ERROR 
		|| status == VM_INTERNAL_ERROR) {
		printf("process encountered an illegal event and has been terminated\n");
		return -1;
	    }
	}
    }

    /* GET method means just emit XML. */
    emit_xml();
}

