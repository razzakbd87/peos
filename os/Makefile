SUBDIRS=kernel repository

all: dummy
	set -e; for i in $(SUBDIRS); do $(MAKE) -C $$i; done

clean:
	set -e; for i in $(SUBDIRS); do $(MAKE) -C $$i clean; done
	rm  core *~

clobber: clean
	set -e; for i in $(SUBDIRS); do $(MAKE) -C $$i clobber; done


dummy:
