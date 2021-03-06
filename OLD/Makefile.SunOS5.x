#############################################################################

#MAKE=/usr/ucb/make
MAKE=/opt/gnu/bin/make
CC=/opt/gnu/bin/gcc

YACC=yacc
export SHELL_DIR
LEX=lex
LEX_LIB=l

MOTIF_LIB_DIR=/usr/dt/lib
MOTIF_INCLUDE_DIR=/usr/dt/include
UIL_BIN_DIR=/usr/dt/bin

#############################################################################

GSPN2BINS:=$(shell ./contrib/version.sh)
SOCKET_LIB=-lnsl -lsocket

MOTIF_LIB=Mrm Xm 

GREAT_REVISION=2.0.2
GREAT_PLATFORM=SunOS5x
SHELL_DIR  = $(CURDIR)/../bin

export GSPN2BINS CC GREAT_REVISION GREAT_PLATFORM MAKE YACC LEX LEX_LIB
export MOTIF_LIB_DIR MOTIF_INCLUDE_DIR MOTIF_LIB UIL_BIN_DIR SOCKET_LIB
export GSPN_INST_DIR
unexport CURDIR


makeall: greatspn swn algebra multisolve


greatspn:
	-cd contrib && $(MAKE) -e -f Makefile.SunOs5
	-cd gsrc2   && $(MAKE) -e -f Makefile.all
	-cd UIL     && $(MAKE) -e -f Makefile.all
	-cd greatsrc${GREAT_REVISION} && $(MAKE) -e -f Makefile.all
	-cd simsrc2 && $(MAKE) -e -f Makefile.all
swn:
	-cd WN      && $(MAKE) -e -f Makefile.all


algebra: ../$(GSPN2BINS)/algebra_obj ../$(GSPN2BINS)/remove_obj scripts 
	-cd algebra/Composition && $(MAKE) -e 
	-cd algebra/Remove &&  $(MAKE) -e 
../$(GSPN2BINS)/algebra_obj:
	mkdir -p ../$(GSPN2BINS)/algebra_obj
../$(GSPN2BINS)/remove_obj:
	mkdir -p ../$(GSPN2BINS)/remove_obj
scripts: contrib/algebra/algebra.sh contrib/algebra/remove.sh 
	cp contrib/algebra/algebra.sh ../bin/algebra ; chmod +x ../bin/algebra
	cp contrib/algebra/remove.sh ../bin/remove ; chmod +x ../bin/remove

multisolve: ../bin/multisolve  ../$(GSPN2BINS)/multisolve MultiSolve.java
../bin/multisolve: contrib/multisolve/multisolve.sh
	cp contrib/multisolve/multisolve.sh ../bin/multisolve ; chmod +x ../bin/multisolve
../$(GSPN2BINS)/multisolve:
	mkdir -p ../$(GSPN2BINS)/multisolve
MultiSolve.java:
	-cd multisolve && javac -d ../../$(GSPN2BINS)/multisolve NetFilter.java
	-cd multisolve && javac -d ../../$(GSPN2BINS)/multisolve Utils.java
	-cd multisolve && javac -d ../../$(GSPN2BINS)/multisolve MultiSolve.java
	cp multisolve/swn* ../$(GSPN2BINS)/multisolve/; chmod +x ../$(GSPN2BINS)/multisolve/swn*
	cp multisolve/gspn* ../$(GSPN2BINS)/multisolve/; chmod +x ../$(GSPN2BINS)/multisolve/gspn*
	cp multisolve/commands.el ../$(GSPN2BINS)/multisolve/; chmod +x ../$(GSPN2BINS)/multisolve/commands.el
	cp multisolve/GnuPlot ../$(GSPN2BINS)/multisolve/; chmod +x ../$(GSPN2BINS)/multisolve/GnuPlot








clean:
	-cd gsrc2   && $(MAKE) -e -f Makefile.all clean
	-cd greatsrc$(GREAT_REVISION) && $(MAKE) -e -f Makefile.all clean
	-cd simsrc2 && $(MAKE) -e -f Makefile.all clean
	-cd WN      && $(MAKE) -e -f Makefile.all clean


remove:
	-cd gsrc2   && $(MAKE) -e -f Makefile.all remove
	-cd simsrc2 && $(MAKE) -e -f Makefile.all remove 
