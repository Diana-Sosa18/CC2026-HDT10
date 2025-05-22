import networkx as nx
import numpy as np

def main():
    G = nx.DiGraph()
    
    G.add_node(0, name="Ciudad de Guatemala")
    G.add_node(1, name="Zacapa")
    G.add_node(2, name="Chiquimula")
    G.add_node(3, name="Quetzaltenango")
    G.add_node(4, name="Cobán")
    
    G.add_edge(0, 1, weight=3)  
    G.add_edge(0, 3, weight=7)  
    G.add_edge(1, 2, weight=1)  
    G.add_edge(1, 4, weight=8) 
    G.add_edge(2, 3, weight=2)  
    G.add_edge(3, 4, weight=3)  
    G.add_edge(4, 0, weight=4)  
    
    print(f"Número de nodos: {G.number_of_nodes()}")
    print(f"Número de aristas: {G.number_of_edges()}")
    print("\nNodos:")
    for node in G.nodes(data=True):
        print(f"  {node[0]}: {node[1]['name']}")
    
    print("\nAristas:")
    for u, v, data in G.edges(data=True):
        print(f"  {G.nodes[u]['name']} -> {G.nodes[v]['name']}: {data['weight']}")
    
    distance_matrix = nx.floyd_warshall(G, weight='weight')

    nodes = list(G.nodes())
    n = len(nodes)
    result_matrix = np.zeros((n, n))
    
    for i in range(n):
        for j in range(n):
            if distance_matrix[i][j] == float('inf'):
                result_matrix[i][j] = float('inf')
            else:
                result_matrix[i][j] = distance_matrix[i][j]
    
    print("\nMatriz de distancias mínimas (resultado de Floyd-Warshall):")
    for i in range(n):
        for j in range(n):
            if result_matrix[i][j] == float('inf'):
                print("∞\t", end="")
            else:
                print(f"{result_matrix[i][j]:.0f}\t", end="")
        print()
    
    eccentricity = {}
    for node in G.nodes():
        max_distance = 0
        for target in G.nodes():
            if node != target and distance_matrix[node][target] != float('inf'):
                max_distance = max(max_distance, distance_matrix[node][target])
        eccentricity[node] = max_distance
    
    center = min(eccentricity.items(), key=lambda x: x[1])[0]
    print(f"\nEl centro del grafo es: {G.nodes[center]['name']}")
    
    print("\nRutas más eficientes desde Ciudad de Guatemala:")
    for target in range(n):
        if target != 0:  
            if distance_matrix[0][target] == float('inf'):
                print(f"Hacia {G.nodes[target]['name']}: No hay ruta disponible")
            else:
                print(f"Hacia {G.nodes[target]['name']}: {distance_matrix[0][target]:.0f} km")

if __name__ == "__main__":
    main()