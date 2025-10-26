# Design and Analysis of Algorithms

---
## Assignment 3: Optimization of a City Transportation Network (Minimum Spanning Tree)
This project implements **Prim’s** and **Kruskal’s** algorithms to optimize a city’s transportation network.
The goal is to connect all city districts (graph vertices) with the minimum total construction cost (edge weights).

The project is implemented in **Java (Maven)** and includes:
- MST computation using Prim’s and Kruskal’s algorithms;
- Performance measurement (execution time, edge count, total cost);
- Visualization of graph structures (Bonus section);
- Automated tests for correctness and consistency.
---
### 1. Project Structure
src/main/java/ <br>
├── algorithms/ <br>
│ ├── KruskalAlgorithm.java <br>
│ └── PrimAlgorithm.java <br>
├── io/ <br>
│ └── JsonIO.java <br>
├── model/ <br>
│ ├── Edge.java <br>
│ ├── Graph.java <br>
│ └── MSTResult.java <br>
├── visualizer/ <br>
│ └── GraphVisualizer.java <br>
├── Main.java ← Calculates MST and exports CSV <br>
└── GraphViewer.java ← Visualizes any selected graph (bonus) <br>

src/main/resources/ <br>
├── input_extralarge.json <br>
├── input_large.json <br>
├── input_medium.json <br>
├── input_small.json <br>
└── output_table.csv

src/test/java/ <br>
├── AlgorithmComparisonTest <br>
├── MSTStructureTest <br>
├── PerformanceTest <br>
└── TestUtils
---

### 2. Input Data Description
```
Input data represents graphs in JSON format, where:

- `"vertices"` — number of city districts;
- `"edges"` — list of possible roads with construction costs.

Each JSON file includes several graphs:
| File | ID Range | Graph Size |
|------|-----------|-------------|
| input_small.json | 1–5 | 5–30 vertices |
| input_medium.json | 6–15 | 50–300 vertices |
| input_large.json | 16–25 | 350–800 vertices |
| input_extralarge.json | 26–28 | 1300–2000 vertices |
```
--- 
### 3. Implementation Details

### Prim’s Algorithm
- Uses a **priority queue** to select the smallest edge connecting visited and unvisited vertices.
- Complexity: **O(E log V)**.
- Efficient for **dense graphs**.

### Kruskal’s Algorithm
- Sorts all edges and connects components using **Union-Find** structure.
- Complexity: **O(E log E)**.
- Performs better for **sparse graphs**.

Both algorithms output:
- Total MST cost,
- Execution time (ms),
- Number of vertices.
--- 
### 4. Results Summary

#### Summary of Input Data and Results

The following table summarizes the execution results of both algorithms across all datasets.  
Each row shows the number of vertices, MST total cost, execution times, and operation counts  
for both Prim’s and Kruskal’s algorithms. This data forms the basis for the efficiency comparison below.

Results are stored in `output_table.csv`:

| File | GraphID | Vertices | Kruskal(ms) | Prim(ms) | Kruskal Ops | Prim Ops | MST Cost |
|------|----------|-----------|-------|-----------|----------|---|----|
| input_small.json | 1 | 5 | 0.538 | 0.958 | 45 | 13  | 94 |
| input_medium.json | 8 | 100 | 0.196 | 1.928 | 1825 |  689 | 2460 |
| input_medium.json | 9 | 125 | 0.024 | 0.021 | 179 |  26 |  58  |
| input_large.json | 20 | 550 | 1.091 | 5.149 | 10043 | 3802  | 15556   |
| input_extralarge.json | 28 | 2000 | 1.738  | 29.028 | 36767 | 13884  |  55581  |

**MST total cost is identical** for both algorithms, confirming correctness.  
Execution time grows with the **density** of the graph (not just vertex count).
--- 
### Performance Analysis

| Algorithm | Time Complexity | Works Best For | Observed Behavior |
|------------|----------------|----------------|--------------------|
| **Prim** | O(E log V) | Dense graphs | Time increases quickly as number of edges grows |
| **Kruskal** | O(E log E) | Sparse graphs | Faster for smaller or tree-like graphs |

#### Operation Count Clarification
- The operation count represents the total number of key algorithmic actions performed during MST construction:
- For Kruskal’s algorithm, it includes the number of union and find operations executed within the Union-Find data structure.
- For Prim’s algorithm, it counts edge relaxation and priority queue update operations used to select the smallest connecting edge.
- This metric provides a deeper understanding of each algorithm’s internal workload and helps explain why execution time may vary even between graphs of similar size.

#### Observations

- When the number of edges is small (sparse graph), **Kruskal** outperforms Prim (e.g., 125 vertices).
- For dense graphs (hundreds or thousands of edges), **Prim** performs comparably or slightly slower.
- Execution time spikes are caused primarily by **edge density** and **graph randomness**, not vertex count.
- MST cost remains identical for both algorithms, verifying correctness.

#### Theoretical vs Practical Performance

In theory, Prim’s algorithm is expected to perform better on dense graphs due to efficient edge selection  
via a priority queue, while Kruskal’s algorithm excels on sparse graphs because of the simpler edge sorting process.

In practice, the collected data confirms this trend:
- On sparse graphs (e.g., Graph ID 9), Kruskal achieved faster execution and fewer operations.
- On dense graphs (e.g., Graph ID 8 and 20), Prim’s runtime increased significantly due to frequent queue updates.

Thus, real-world performance closely aligns with theoretical expectations, though implementation  
details and graph density play a major role in the observed differences.

#### Example: 100 vs 125 vertices anomaly

In the dataset: <br>

| File | GraphID | Vertices | Kruskal(ms) | Prim(ms) | Kruskal Ops | Prim Ops | MST Cost |
|------|----------|-----------|--------------|-----------|--------------|-----------|----------|
| input_medium.json | 8 | 100 | 0.196 | 1.928 | 1825 | 689 | 2460 |
| input_medium.json | 9 | 125 | 0.024 | 0.021 | 179 | 26 | 87 |

At first glance, it looks paradoxical — the graph with **100 vertices** took **1.928 ms** for Prim’s algorithm,  
while the graph with **125 vertices** completed in only **0.021 ms** (almost 100× faster).

However, this behavior is **completely logical** when we consider **graph density** and **operation count**:

- The 100-vertex graph was **much denser**, performing **1825 Kruskal operations** and **689 Prim operations**.  
  Prim’s algorithm had to evaluate many candidate edges and perform frequent updates to the priority queue,  
  which significantly increased its runtime.
- The 125-vertex graph, on the other hand, was **sparse**, with only **179 Kruskal operations** and **26 Prim operations** —  
  essentially forming a tree-like structure. Both algorithms therefore executed extremely quickly.

This demonstrates that **execution time depends more on edge density and operation count than on vertex count.**  
A larger graph with fewer edges can execute faster than a smaller but densely connected one.


### Theoretical Insight

- Kruskal is simpler to implement and performs efficiently on sparse graphs.
- Prim is well-suited for dense graphs when optimized with adjacency structures and priority queues.
- In practice, both algorithms have identical MST costs, but performance varies with graph density and implementation details.
- For real-world transportation networks (moderately dense), **Prim** tends to scale better as connections increase. 
---
### Graph Visualization (Bonus)

Implemented `GraphVisualizer.java` and `GraphViewer.java` for bonus.

- Displays graph structure and highlights MST edges in red.
- Uses Java Swing for real-time visualization.
- Two windows open: one for Kruskal MST, one for Prim MST. 

#### Example: MST Visualization

Below you can see how the graph structure and its Minimum Spanning Tree (MST) look
after applying **Prim’s** and **Kruskal’s** algorithms.

<p align="center">
  <img src="/screenshots/graph_7_prim.png" alt="Prim MST Graph" width="45%">
  <img src="/screenshots/graph_7_kruskal.png" alt="Kruskal MST Graph" width="45%">
</p>

<p align="center">
  <em>Left: Prim’s MST | Right: Kruskal’s MST (Graph ID: 6)</em>
</p>

---
## Conclusion

- Both algorithms successfully compute the MST and produce identical total costs.
- Execution time grows with **graph density**, not strictly with vertex count.
- **Kruskal** is better for **sparse** networks, **Prim** for **dense** ones.
- Code is modular, testable, and follows OOP principles.
- Bonus visualization clearly demonstrates MST structure and differences between algorithms.