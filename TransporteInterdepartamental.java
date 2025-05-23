/**
 * Clase principal que representa una aplicación para modelar rutas
 * de transporte interdepartamental en Guatemala usando un grafo dirigido.
 *
 * Utiliza la clase {@link Graph} para representar ciudades como vértices,
 * rutas como aristas con pesos (distancias en kilómetros) y el algoritmo
 * de Floyd-Warshall para calcular distancias mínimas entre todas las ciudades.
 *
 * Funcionalidades:
 * <ul>
 *   <li>Crear un grafo con 5 ciudades</li>
 *   <li>Agregar conexiones con distancias entre las ciudades</li>
 *   <li>Imprimir la matriz de adyacencia</li>
 *   <li>Aplicar el algoritmo de Floyd-Warshall</li>
 *   <li>Determinar el centro del grafo (ciudad con menor excentricidad)</li>
 *   <li>Mostrar las rutas más eficientes desde Ciudad de Guatemala</li>
 * </ul>
 * 
 * Las ciudades representadas son:
 * <ul>
 *   <li>0 - Ciudad de Guatemala</li>
 *   <li>1 - Zacapa</li>
 *   <li>2 - Chiquimula</li>
 *   <li>3 - Quetzaltenango</li>
 *   <li>4 - Cobán</li>
 * </ul>
 * 
 * @author Diana
 */
public class TransporteInterdepartamental {

    /**
     * Método principal que ejecuta la lógica del programa.
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        Graph graph = new Graph(5);
        
        graph.addVertex(0, "Ciudad de Guatemala");
        graph.addVertex(1, "Zacapa");
        graph.addVertex(2, "Chiquimula");
        graph.addVertex(3, "Quetzaltenango");
        graph.addVertex(4, "Cobán");
        
        graph.addEdge(0, 1, 3);
        graph.addEdge(0, 3, 7);  
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 4, 8);
        graph.addEdge(2, 3, 2);
        graph.addEdge(3, 4, 3);
        graph.addEdge(4, 0, 4);
        
        System.out.println("Matriz de adyacencia inicial:");
        graph.printMatrix();
        
        int[][] shortestPaths = graph.floydWarshall();
    
        System.out.println("\nMatriz de distancias mínimas (resultado de Floyd-Warshall):");
        for (int i = 0; i < graph.getSize(); i++) {
            for (int j = 0; j < graph.getSize(); j++) {
                if (shortestPaths[i][j] == Integer.MAX_VALUE) {
                    System.out.print("∞\t");
                } else {
                    System.out.print(shortestPaths[i][j] + "\t");
                }
            }
            System.out.println();
        }

        int center = graph.findGraphCenter();
        System.out.println("\nEl centro del grafo es: " + graph.getVertices()[center]);
        
        System.out.println("\nRutas más eficientes desde Ciudad de Guatemala:");
        for (int i = 0; i < graph.getSize(); i++) {
            if (i != 0) { 
                System.out.print("Hacia " + graph.getVertices()[i] + ": ");
                if (shortestPaths[0][i] == Integer.MAX_VALUE) {
                    System.out.println("No hay ruta disponible");
                } else {
                    System.out.println(shortestPaths[0][i] + " km");
                }
            }
        }
    }
}

