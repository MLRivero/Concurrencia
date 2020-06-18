/*
 * principal.c
 *
 *  Created on: 4/4/2017
 *      Author: jose
 */

#include <stdio.h>
#include <stdlib.h>
#include<string.h>
#include "colaprio.h"

#define FICHERO "cola.bin"

int main() {
	TColaPrio cola;

	Crear_Cola(FICHERO, &cola);
	Mostrar(cola);
//  printf("\n");
//  Ejecutar(&cola, 2);
//  Mostrar(cola);
//  printf("\n");
//  Ejecutar_Max_Prio(&cola);
//  Mostrar(cola);
//  printf("\n");
//  Destruir(&cola);
//  Mostrar(cola);
//  printf("\n");
	return 0;
}

void Crear_Cola(char *nombre, TColaPrio *cp) {
	FILE *f;
	unsigned int numprocesos;
	unsigned int id;
	unsigned int prioridad;
	TColaPrio aux ;
	if ((f = fopen(nombre, "rb")) == NULL) {
		printf("ERROR\n");
		exit(-1);
	}

	fread(&numprocesos, sizeof(int), 1, f);
	printf("%d \n", numprocesos);
	*cp = (TColaPrio) malloc(sizeof(struct NodoCola)*numprocesos);
	//aux=(TColaPrio) malloc(sizeof(struct NodoCola));

	while(fread(&id, sizeof(int), 1, f)!=0){
		while((*cp)->sig!=NULL) {
			(*cp)=(*cp)->sig;
		}
			aux=*cp;
			aux->idproceso = id;
			fread(&prioridad, sizeof(int), 1, f);
			(aux)->prioridad= prioridad;
			(aux)->sig=NULL;

	}
			
	while (fread(&id, sizeof(int), 1, f) != 0) {
		if (*cp == NULL) {
			*cp =  malloc(sizeof(struct NodoCola));
			(*cp)->idproceso = id;
			fread(&prioridad, sizeof(int), 1, f);
		
			(*cp)->prioridad = prioridad;
			(*cp)->sig = NULL;
		} else {
			(*cp)->sig =  malloc(sizeof(struct NodoCola));
			while ((*cp)->sig != NULL) {
				*cp = (*cp)->sig;
			}
			(*cp)->sig = (TColaPrio) malloc(sizeof(struct NodoCola));
			fread(&id, sizeof(int), 1, f);
			
			(*cp)->sig->idproceso = id;
			fread(&prioridad, sizeof(int), 1, f);
		
			(*cp)->sig->prioridad = prioridad;
			(*cp)->sig->sig = NULL;
		}
	}
	fclose(f);
}



void Mostrar(TColaPrio cp) {
	TColaPrio ptr;
	ptr=cp;
	while (ptr != NULL) {
		printf("%d : %d ,", cp->idproceso, cp->prioridad);
	ptr = ptr->sig;
	}

	fflush(stdout);
}

void Destruir(TColaPrio *cp) {
	TColaPrio aux;
	while (*cp != NULL) {
		aux = (*cp)->sig;
		free(cp);
		(*cp) = aux;
	}
}
void Ejecutar_Max_Prio(TColaPrio *cp) {
	int max = 0;
	while (*cp != NULL) {
		if (((*cp)->prioridad) > max) {
			max = (*cp)->prioridad;
		}
	}
	Ejecutar(cp, max);
}
void Ejecutar(TColaPrio *cp, int prio) {
	TColaPrio ant, sig;

	while (*cp != NULL) {
		ant = *cp;
		sig = (*cp)->sig;
		if (((*cp)->prioridad) == prio) {
			free(*cp);
			ant->sig = sig;
		}
		*cp = sig;
	}
}

