import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;


public class Scheduler {
    
    private static int[] delMinStartingJob(ArrayList<int[]> WAIT){
	/** 
	    Devuelve la tarea cuyo tiempo de inicio es el menor
	    dentro de todo el array. También la elimina de la lista.

	    Es posible recibir una lista de tareas que no estén 
	    ordenadas de manera no decreciente, puesto que se está
	    iterando sobre el array buscando el mínimo.
	*/

	int mini[] = WAIT.get(0);
	int iMini = 0;
	for (int i=0; i<WAIT.size(); i++)
	    if (WAIT.get(i)[0] < mini[0]){
		iMini = i;
		mini = WAIT.get(i);
	    }

	WAIT.remove(iMini);
	return mini;
    }


    private static ArrayList<ArrayList<int[]>> minimumResoursesSchedule(ArrayList<int[]> T) {
	/** 
	    Devuelve una lista de listas correspondiente a la calendarización de T,
	    cuyo i-ésimo elemento define un conjunto compatible de tareas.
	*/

	int min = 1;
	ArrayList<int[]> WAIT = new ArrayList<>(T);
	ArrayList<ArrayList<int[]>> SCH = new ArrayList<ArrayList<int[]>>(){
		{
		    add(new ArrayList<int[]>());
		}
	    };

	while (WAIT.size() > 0) {

	    // Tomamos t, la tarea con el mínimo tiempo de inicio
	    int t[] = delMinStartingJob(WAIT);
	    
	    boolean asignado = false;
	    for(int j=0; j<min; j++) {
		ArrayList<int[]> SCH_j = SCH.get(j);
		boolean compatible = true;
		for(int k=0; k<SCH_j.size(); k++) {
		    if (SCH_j.get(k)[1] <= t[0] | t[1] <= SCH_j.get(k)[0]) {
			continue;
		    }
		    else {
			compatible = false;
			break;
		    }
		}
		if (compatible) {
		    SCH.get(j).add(t);
		    asignado = true;
		    break;
		}
	    }
	    if (!asignado) {
		// Si la tarea no se asignó, creamos una nueva entrada en SCH 
		min++;
		ArrayList<int[]> sch_t = new ArrayList<int[]>(){
			{
			    add(t);
			}
		    };	
		SCH.add(sch_t);
	    }
	}
	return SCH;
    }
    
    public static void main(String args[]) {

	// Primero pedimos por terminal, una a una, las tareas a calendarizar
	ArrayList<int[]> TAREAS = new ArrayList<int[]>();
	Scanner s = new Scanner(System.in);
	int numTarea = 1;
	System.out.format("Tarea %d:%n", numTarea++);
        while (s.hasNextLine()) {
	    System.out.format("Tarea %d:%n", numTarea++);
            String line = s.nextLine();
	    
            if (line != null && line.equalsIgnoreCase(""))
                break;
	    
	    TAREAS.add(new int[]{
		    Integer.parseInt(line.split(",")[0].trim()),
		    Integer.parseInt(line.split(",")[1].trim())
		});
        }

	// LLamamos la rutina para ejecutar la calendarización
	ArrayList<ArrayList<int[]>> SCH = minimumResoursesSchedule(TAREAS);

	// Mostramos el resultado
	System.out.println("\n\n  Scheduler:\n");
	for(int i = 0; i < SCH.size(); i++){
	    System.out.print("    SCH[" + i + "] ");
	    for(int j = 0; j < SCH.get(i).size(); j++) 
		System.out.print(" - " + Arrays.toString(SCH.get(i).get(j)));
	    System.out.println( '\n');
	}
    }
}
