import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

import java.lang.Math;
import java.util.Random;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

public class BusquedaExponencial {

    private static void imprimeArreglo(String A) throws IOException {
	/** 
	    Recibe la representación en string de un arreglo 
	    y lo escribe en un archivo 
	*/
	
	try (ObjectOutputStream out =
	     new ObjectOutputStream(new FileOutputStream("arreglo.txt"))) {
	    out.writeObject(A);
	}
    }
    
    private static int[] generaArreglo(int n) {
	/**
	   Regresa un arreglo de n enteros pseudoaleatorios.
	   La diferencia entre dos elementos consecurivos del arreglo
	   se calcula pseudoaleatoriamente en un rango de 1 y 5.
	   El arreglo es creciente.
	*/
	
	System.out.printf("%n%nTamaño del arreglo: %d %n", n);

    	int[] A = new int[n];
    	A[0] = 1;
	
    	for (int i=1; i<n; i++){
    	    Random ran = new Random();

    	    // Distancia psudoaleatoria para el siguiente elemento del array
	    // La distancia está en un rango entre 1 y 5
    	    int next_rand = ran.nextInt(5) + 1;
	    A[i] = A[i-1] + next_rand;
	}

	// Imprimimos el arreglo en un archivo
	try {
	    imprimeArreglo(Arrays.toString(A));
	}
	catch(IOException e) {
	    e.printStackTrace();
	}
	
	return A;
    }

    private static int generaClave(int A[]) {
	/** 
	    Devuelve una clave pseudoaleatorioa, cuya búsqueda en A 
	    represente el peor caso de la complejidad de una búsqueda
	    exponencial.

	    Puesto que el peor caso de la búsqueda exponencial es cuando
	    la clave está en el último intervalo de búsqueda, primero se
	    obtiene el índice que representará el extermo inferior de dicho
	    intervalo. Después se obtiene, pseudoaleatoriamente, un elemento 
	    de dicho intervalo.
	*/
	int n = A.length;
	int i = 0;
	int inf = 0;
	while (inf <= n) {
	    inf = (int) Math.pow(2, i);
	    if (inf <= n)
		i++;
	}
	inf = inf/2;
	
	// El intervalo en el que debe estar la clave es [inf, n]
	int[] intervaloPeorCaso = Arrays.copyOfRange(A, inf, n);

	System.out.printf("Intervalo de búsqueda: [%d, %d]%n", inf, n);
	
	Random ran = new Random();
        int clave = intervaloPeorCaso[ran.nextInt(intervaloPeorCaso.length)];
	return clave;
    }
	
    private static int busquedaBinaria(int A[], int k, int low, int high) {
	/**
	   Implementación un algoritmo de búsqueda binaria.
	   Regresa el entero k en caso de que pertenezca al arreglo A.
	*/
	
	if (low > high)
	    return -1;
	
	else{
	    int mid = (int) Math.floor((low + high) / 2);
	    if (k == A[mid]) {
		System.out.printf("Índice %d, Clave %d %n", mid, A[mid]);
		return k;
	    }
	    else if (k < A[mid])
		return busquedaBinaria(A, k, low, mid-1);
	    else
		return busquedaBinaria(A, k, mid+1, high);
	}
    }

    private static int busquedaExponencial(int A[], int k) {
	/** 
	    Implementación de un algoritmo de búsqueda exponencial.
	    Regresa el entero k en caso de que pertenezca al arreglo A.
	*/
	
	int n = A.length;
	int inf = 1;
	int sup = 2;

	while (sup <= n){
	    int claveExtremo = A[sup];
	    if (k == claveExtremo) {
		System.out.printf("Índice %d, Clave %d %n", sup, A[sup]);
		return k;
	    }
	    else if (k < claveExtremo)
		return busquedaBinaria(A, k, inf, sup-1);
	    else{
		if (sup == n)
		    return -1;
		else{
		    inf = sup;
		    sup = sup * 2;
		}
	    }
	}
	return busquedaBinaria(A, k, inf, n);
    }
	    
    
    public static void main(String args[]) {

    	// Primero pedimos por terminal un entero 'k' entre 1 y 20
	Scanner input = new Scanner(System.in);
	System.out.println("Introduce un entero entre 1 y 20");
	int k = input.nextInt();

	// Generamos un arreglo de k * 50 enteros.
	k = k*50;
	int A[] = generaArreglo(k);
	
	// Generamos la clave pseudoaleatoria
	int clave = generaClave(A);

	
	// Hacemos la búsqueda exponencial
	int encontrado = busquedaExponencial(A, clave);
	if (encontrado == clave)
	    System.out.printf("\nCLAVE %d ENCONTRADA\n", clave);
	else
	    System.out.printf("\nCLAVE %d NO ENCONTRADA\n", clave);
    }
}
