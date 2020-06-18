/*
 * MProcesos.c
 *
 *  Created on: 18/06/2012
 *      Author: luisll
 */



#include "Mprocesos.h"
#include <stdio.h>
#include <stdlib.h>

void Crear(LProc *lroundrobin) {
	*lroundrobin=NULL;
}

void AnadirProceso(LProc lroundrobin, int idproc) {
	LProc aux=(LProc) malloc (sizeof(struct proc));
if((lroundrobin)==NULL){
	(lroundrobin)->inicio=idproc;
	lroundrobin->sig=lroundrobin;
} else {
	aux->inicio=idproc;
	aux->sig=lroundrobin->sig;
	lroundrobin->sig=aux;
}
}

void EjecutarProcesos(LProc lroundrobin) {
	unsigned n = lroundrobin->inicio;
	do{
		printf("%d, ", lroundrobin->inicio);
		lroundrobin=lroundrobin->sig;
	} while (lroundrobin->inicio!=n);

}


void EliminarProceso(int id, LProc *lista) {
	LProc ant;
	while((*lista)->inicio!=id){
		ant=*lista;
		*lista=(*lista)->sig;
	}
	ant->sig=(*lista)->sig;
	free(lista);
}

void EscribirFichero (char * nomf, LProc *lista) {
	FILE *f;
	int i= (*lista)->inicio;
	int j;
	int numproc=0;
	do{
		numproc++;
		(*lista)=(*lista)->sig;

	} while(i!=(*lista)->inicio);

	int procesos[numproc];

	for (j=0;j<numproc;j++){
		procesos[j]=(*lista)->inicio;
		(*lista)=(*lista)->sig;
	}


	f = fopen(nomf,"wb");
	fwrite(&numproc,sizeof(int),1,f);
	fwrite(procesos,sizeof(int),numproc,f);
	fclose(f);


}
