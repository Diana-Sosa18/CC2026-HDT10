public class Graph {
    private int[][] adjacencyMatrix;
    private String[] vertices;
    private int size;
    private final int INFINITY = Integer.MAX_VALUE;


    public Graph(int size) {
        this.size = size;
        this.adjacencyMatrix = new int[size][size];
        this.vertices = new String[size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    adjacencyMatrix[i][j] = 0;  
                } else {
                    adjacencyMatrix[i][j] = INFINITY;  
                }
            }
        }
    }
    

    public void addVertex(int index, String name) {
        if (index >= 0 && index < size) {
            vertices[index] = name;
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
    }
    
  
    public void addEdge(int source, int destination, int weight) {
        if (source >= 0 && source < size && destination >= 0 && destination < size) {
            adjacencyMatrix[source][destination] = weight;
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
    }
    
   
    public void removeEdge(int source, int destination) {
        if (source >= 0 && source < size && destination >= 0 && destination < size) {
            adjacencyMatrix[source][destination] = INFINITY;
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
    }
    
    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }
    
    public String[] getVertices() {
        return vertices;
    }
    
   
    public int getSize() {
        return size;
    }
    
 
    public int[][] floydWarshall() {
        int[][] distances = new int[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                distances[i][j] = adjacencyMatrix[i][j];
            }
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