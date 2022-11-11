package practica;
import java.util.*;

public class Juego {
	//La matriz se pone fuera de las funciones, para que cualquier función tenga acceso a ella
	private static int[][] matriz = new int[3][3];

	public static void main(String[] args) {
		//Declarar las variables
		int casilla = 0;
		boolean turnoDeJugadorUno = true;
		Scanner scan = new Scanner(System.in);

		//Un ciclo para jugar todas las partidas que los jugadores quieran
		while(true)
		{
			iniciarJuego();
			
			//El máximo de jugadas que pueden haber es 9, cuando se llenan
			//todas las casillas, si eso pasa, hay empate. Si antes de las
			//9 jugadas alguien gana, el for de detiene antes con un break
			for(int i = 0; i < 9; i++) {
				String jugador = turnoDeJugadorUno ? "Jugador uno " : "Jugador dos ";
				String simbolo = turnoDeJugadorUno ? " (X)" : " (O)";
				System.out.println(">------Es turno del "+jugador+simbolo+"------<\n");			

				//Hacer un ciclo hasta validar que el jugador elija un cuadrante disponible
				boolean jugada = false;
				while (!jugada) {			
					
					//Elegir el cuadrante (del 1 al 9)
					System.out.print("Selecciona el cuadrante: ");
					casilla = validarNumero(scan);
					//Pasar el cuadrante a la función para 
					jugada = dibujarMatriz(casilla, turnoDeJugadorUno);
		
					//Si la función devuelve false, avisar que ese cuadrante ya se eligió antes
					if (!jugada)
						System.out.println("\n¡El cuadrante ("+casilla+") ya está ocupado! Elije otro...\n");
				}

				if (tresEnFila()) {
					//Si la función es verdadera, el jugador actual ya ganó, se rompe el for
					System.out.println(">-----"+jugador+"ganó la partida-----<");
					break;
				} else if (i == 8) {
					//Si el jugador no ganó, pero ya se llenaron todas las casillas, fue empate
					System.out.println(">-----¡EMPATE! Nadie ganó la partida-----<");
					break;
				} else {					
					//Si todavia no gana nadie o aun quedan casillas, se cambia el turno del jugador
					turnoDeJugadorUno = !turnoDeJugadorUno;
				}
			}
			
			//Cuando acaba el juego, se pregunta si quieren seguir jugando o ya no
			//Se valida que sólo se pueda responder S o N
			System.out.println("\n¿Iniciar otra partida? S/N");
			scan.nextLine();
			String res;
			do {
				res = scan.nextLine();
				if (!res.equalsIgnoreCase("S") && !res.equalsIgnoreCase("N"))
					System.out.println("¡Responde sólo con S o N!");
			} while(!res.equalsIgnoreCase("S") && !res.equalsIgnoreCase("N"));

			//Si quieren seguir jugando, se reinicia la matriz y el juego, sino, se rompe el while
			if (res.equalsIgnoreCase("N")) break;
		}

		//Aquí acaba el programa
		System.out.println(">--------¡Acabó el juego!--------<");
		scan.close();
	}

	private static void dibujarMatriz()
	{
		System.out.println("\n"); //Salto de línea
		String simbolo;
		for(int i = 0; i < 3; i++) {
			System.out.print("\t");  //Se pone un Tab a cada línea
			for(int j = 0; j < 3; j++) {
				//Si la casilla tiene un 1 dibuja "X", si tiene un 0 dibuja "O"
				//Si tiene otro número (cuando se reinicia el juego), se dibuja un guión
				simbolo = matriz[i][j] == 1 ? "X" : matriz[i][j] == 0 ? "O" : "-"; 
				System.out.print(" "+simbolo+" ");
				//Se pone un | para separar las casillas
				if (j != 2) System.out.print("|");
			}
			//Se pone un separados entre cada fila
			if (i != 2) System.out.println("\n\t-----------");
		}
		System.out.println("\n"); //Salto de línea
	}
	
	private static void dibujarEjemplo()
	{
		System.out.println("\n"); //Salto de línea
		for(int i = 0; i < 3; i++) {
			System.out.print("\t");  //Se pone un Tab a cada línea
			for(int j = 0; j < 3; j++) {
				//Mostrar los cuadrantes por número
				int cuadrante = (j+1) + i*3;
				System.out.print(" "+cuadrante+" ");
				//Se pone un | para separar las casillas
				if (j != 2) System.out.print("|");
			}
			//Se pone un separados entre cada fila
			if (i != 2) System.out.println("\n\t-----------");
		}
		System.out.println("\n"); //Salto de línea
	}

	private static boolean dibujarMatriz(int casilla, boolean jugadorUno) {
		
		int x=0, y=0;
		switch(casilla) {
		case 1:
			x = 0;
			y = 0;
			break;
		case 2:
			x = 0;
			y = 1;
			break;
		case 3:
			x = 0;
			y = 2;
			break;
		case 4:
			x = 1;
			y = 0;
			break;
		case 5:
			x = 1;
			y = 1;
			break;
		case 6:
			x = 1;
			y = 2;
			break;
		case 7:
			x = 2;
			y = 0;
			break;
		case 8:
			x = 2;
			y = 1;
			break;
		case 9:
			x = 2;
			y = 2;
			break;
		}
		
		if (matriz[x][y] == 0 || matriz[x][y] == 1) return false;
		matriz[x][y] = jugadorUno ? 1 : 0;
		dibujarMatriz();
		return true;
	}
	
	private static int validarNumero(Scanner scanner) {
		int n = 0;
		do {
			try {
				n = scanner.nextInt();
			} catch(InputMismatchException e) {
				n = 0;
				scanner.nextLine();
			}
			if (n<1 || n>3)
				System.out.println("¡Sólo puedes elegir un número entre 1 y 9!");
		} while(n<1 || n>9);
		return n;
	}
	
	private static void iniciarJuego()
	{
		System.out.println("------------------------------");
		System.out.println("|Bienvenido al juego del gato|");
		System.out.println("------------------------------");
		System.out.println("\t|Instrucciones|");
		System.out.println("Para seleccionar un cuadrante:");
		System.out.println("Escribe el número del cuadrante según se muestra a continuación:");
		
		dibujarEjemplo();
		
		System.out.print(">--------EL JUEGO COMENZÓ--------<");
		matriz = new int[][]{
			{-1, -1, -1}, 
			{-1, -1, -1}, 
			{-1, -1, -1}, 
		};
		dibujarMatriz();
	}

	private static boolean tresEnFila()
	{
		//Aquí se verifica si ya se hicieron tres en vertical, horizontal o diagonal
		boolean gano = false;
		for(int i = 0; i < 3; i++)
		{
			//Checar cada fila
			int uno = matriz[i][0];
			int dos = matriz[i][1];
			int tres = matriz[i][2];

			if (uno != -1 && uno == dos && dos == tres) {
				gano = true;
				break;
			}			

			//Checar cada columna
			uno = matriz[0][i];
			dos = matriz[1][i];
			tres = matriz[2][i];

			if (uno != -1 && uno == dos && dos == tres) {
				gano = true;
				break;
			}			

			//Checar una diagonal
			dos = matriz[1][1];
			uno = matriz[0][0];
			tres = matriz[2][2];

			if (uno != -1 && uno == dos && dos == tres) {
				gano = true;
				break;
			}			

			//Checar la otra diagonal
			uno = matriz[0][2];
			tres = matriz[2][0];

			if (uno != -1 && uno == dos && dos == tres)
				gano = true;
		}

		return gano;
	}
}