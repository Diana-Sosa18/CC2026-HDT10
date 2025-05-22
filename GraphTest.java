import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GraphTest {
    private Graph graph;
    
    @BeforeEach
    public void setUp() {
        graph = new Graph(5);
        graph.addVertex(0, "Ciudad de Guatemala");
        graph.addVertex(1, "Zacapa");
        graph.addVertex(2, "Chiquimula");
        graph.addVertex(3, "Quetzaltenango");
        graph.addVertex(4, "Cobán");
    }
    
    @Test
    public void testAddVertex() {
        assertEquals("Ciudad de Guatemala", graph.getVertices()[0]);
        assertEquals("Zacapa", graph.getVertices()[1]);
        assertEquals("Chiquimula", graph.getVertices()[2]);
        assertEquals("Quetzaltenango", graph.getVertices()[3]);
        assertEquals("Cobán", graph.getVertices()[4]);
        
        assertThrows(IndexOutOfBoundsException.class, () -> {
            graph.addVertex(5, "Ciudad Inexistente");
        });
    }
    
    @Test
    public void testAddEdge() {
        graph.addEdge(0, 1, 3);
        graph.addEdge(0, 3, 7);
        
        int[][] matrix = graph.getAdjacencyMatrix();
        assertEquals(3, matrix[0][1]);
        assertEquals(7, matrix[0][3]);
        
        assertThrows(IndexOutOfBoundsException.class, () -> {
            graph.addEdge(0, 5, 10);
        });
    }
    
    @Test
    public void testRemoveEdge() {

        graph.addEdge(0, 1, 3);
        graph.addEdge(0, 3, 7);
        
        graph.removeEdge(0, 1);
        
        int[][] matrix = graph.getAdjacencyMatrix();
        assertEquals(Integer.MAX_VALUE, matrix[0][1]);
        assertEquals(7, matrix[0][3]);
        
        assertThrows(IndexOutOfBoundsException.class, () -> {
            graph.removeEdge(0, 5);
        });
    }
    
    @Test
    public void testFloydWarshall() {
        graph.addEdge(0, 1, 3);
        graph.addEdge(0, 3, 7);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 4, 8);
        graph.addEdge(2, 3, 2);
        graph.addEdge(3, 4, 3);
        graph.addEdge(4, 0, 4);
        
        int[][] distances = graph.floydWarshall();
        
        assertEquals(0, distances[0][0]); 
        assertEquals(3, distances[0][1]); 
        assertEquals(4, distances[0][2]); 
        assertEquals(6, distances[0][4]); 
        
        assertEquals(7, distances[1][0]); 
        assertEquals(0, distances[1][1]); 
    }
    
    @Test
    public void testFindGraphCenter() {
        graph.addEdge(0, 1, 3);
        graph.addEdge(0, 3, 7);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 4, 8);
        graph.addEdge(2, 3, 2);
        graph.addEdge(3, 4, 3);
        graph.addEdge(4, 0, 4);
        
        int center = graph.findGraphCenter();

        assertTrue(center >= 0 && center < 5);
    }
}