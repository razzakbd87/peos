CC = gcc 

LDFLAGS = -g -L. -L/home/jnoll/lib/
LIBS = -lvr -linn
LIB = libvr.a 

OBJECTS = \
queryLinkedList.o \
resultLinkedList.o \
seekerTools.o \
FSseeker.o \
EMAILseeker.o \
setup_repositories.o \
vrepo.o \
debug.o 

all: $(LIB) search

$(LIB): $(OBJECTS)
	ar -r $@ $^

search: $(LIB) 
	$(CC) -o search search.c $(LDFLAGS) $(LIBS) 
	
install: 
	cp $(LIB) ../../lib

clean:
	-rm $(OBJECTS)
	-rm core*
	-rm libvr.a
	-rm search






