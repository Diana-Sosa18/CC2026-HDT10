/**
 * Representa un grafo dirigido con pesos utilizando una matriz de adyacencia.
 * Permite agregar vértices, aristas, eliminar aristas, imprimir la matriz,
 * calcular rutas mínimas con Floyd-Warshall y encontrar el centro del grafo.
 * 
 * El valor {@code INFINITY} representa la ausencia de conexión entre vértices.
 * 
 * @author [Diana Sosa]
 */
public class Graph {

    private int[][] adjacencyMatrix;
    private String[] vertices;
    private int size;
    private final int INFINITY = Integer.MAX_VALUE;

    /**
     * Crea un nuevo grafo con una cantidad fija de vértices.
     * 
     * @param size número total de vértices en el grafo.
     */
    public Graph(int size) {
        this.size = size;
        this.adjacencyMatrix = new int[size][size];
        this.vertices = new String[size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjacencyMatrix[i][j] = (i == j) ? 0 : INFINITY;
            }
        }
    }

    /**
     * Asigna un nombre a un vértice del grafo.
     * 
     * @param index índice del vértice.
     * @param name nombre del vértice.
     * @throws IndexOutOfBoundsException si el índice está fuera del rango.
     */
    public void addVertex(int index, String name) {
        if (index >= 0 && index < size) {
            vertices[index] = name;
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
    }

    /**
     * Agrega una arista dirigida entre dos vértices con un peso dado.
     * 
     * @param source vértice origen.
     * @param destination vértice destino.
     * @param weight peso de la arista.
     * @throws IndexOutOfBoundsException si los índices están fuera del rango.
     */
    public void addEdge(int source, int destination, int weight) {
        if (source >= 0 && source < size && destination >= 0 && destination < size) {
            adjacencyMatrix[source][destination] = weight;
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
    }

    /**
     * Elimina una arista dirigida entre dos vértices.
     * 
     * @param source vértice origen.
     * @param destination vértice destino.
     * @throws IndexOutOfBoundsException si los índices están fuera del rango.
     */
    public void removeEdge(int source, int destination) {
        if (source >= 0 && source < size && destination >= 0 && destination < size) {
            adjacencyMatrix[source][destination] = INFINITY;
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
    }

    /**
     * Devuelve la matriz de adyacencia del grafo.
     * 
     * @return matriz de adyacencia.
     */
    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    /**
     * Devuelve el arreglo con los nombres de los vértices.
     * 
     * @return arreglo de nombres de vértices.
     */
    public String[] getVertices() {
        return vertices;
    }

    /**
     * Devuelve el número total de vértices en el grafo.
     * 
     * @return número de vértices.
     */
    public int getSize() {
        return size;
    }

    /**
     * Ejecuta el algoritmo de Floyd-Warshall para calcular las rutas mínimas
     * entre todos los pares de vértices del grafo.
     * 
     * @return matriz de distancias mínimas.
     */
    public int[][] floydWarshall() {
        int[][] distances = new int[size][size];

        for (int i = 0; i < size; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, distances[i], 0, size);
        }

        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (distances[i][k] != INFINITY && distances[k][j] != INFINITY) {
                        int throughK = distances[i][k] + distances[k][j];
                        if (throughK < distances[i][j]) {
                            distances[i][j] = throughK;
                        }
                    }
                }
            }
        }

        return distances;
    }

    /**
     * Encuentra el centro del grafo, definido como el vértice con la menor
     * distancia máxima hacia los demás vértices.
     * 
     * @return índice del vértice centro del grafo.
     */
    public int findGraphCenter() {
        int[][] distances = floydWarshall();
        int[] maxDistances = new int[size];

        for (int i = 0; i < size; i++) {
            int maxDist = 0;
            for (int j = 0; j < size; j++) {
                if (distances[i][j] != INFINITY && distances[i][j] > maxDist) {
                    maxDist = distances[i][j];
                }
            }
            maxDistances[i] = maxDist;
        }

        int minIndex = 0;
        int minValue = maxDistances[0];

        for (int i = 1; i < size; i++) {
            if (maxDistances[i] < minValue) {
                minValue = maxDistances[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    /**
     * Imprime la matriz de adyacencia del grafo en consola. Usa el símbolo ∞
     * para representar conexiones inexistentes.
     */
    public void printMatrix() {
        System.out.println("Matriz de adyacencia:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (adjacencyMatrix[i][j] == INFINITY) {
                    System.out.print("∞\t");
                } else {
                    System.out.print(adjacencyMatrix[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }
}
