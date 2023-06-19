package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import graph.*;
import kruskal.Kruskal;

/**
 * Application
 * This is the class containing the main
 * 
 * @author VO1D
 */
public class Application {
    private static Graph<String, Double> graph;

    private static void createGraph() throws IOException {
        graph = new Graph<>(false);

        String line = "";
        BufferedReader br = new BufferedReader(new FileReader("..\\csv\\italian_dist_graph.csv"));

        while ((line = br.readLine()) != null) {
            String[] arch = line.split(",");

            String source = arch[0];
            String destination = arch[1];
            Double label = Double.parseDouble(arch[2]);

            if (!graph.existingNode(source)) {
                graph.addNode(source);
            }

            if (!graph.existingNode(destination)) {
                graph.addNode(destination);
            }

            if (!graph.existingArch(source, destination)) {
                graph.addArch(source, destination, label);
            }
        }

        br.close();
    }
    
    private static void printGraph() {
        List<Arch<String, Double>> archList = graph.getArches();

        for (Arch<String, Double> arch : archList) {
            System.out.println("Source: " + arch.getSource() + " Destination: " + arch.getDestination() + " Label: " + arch.getLabel());
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("CREATING THE GRAPH FROM italian_dist_graph.csv\n");
            createGraph();
            
            Kruskal<String, Double> k = new Kruskal<>();
            Graph<String, Double> result;
            System.out.println("STARTING KRUSKAL ALGHORITHM\n");

            Instant start = Instant.now();
            result = k.minimumSpanningTree(graph);
            Instant end = Instant.now();

            List<Arch<String, Double>> resultArches = result.getArches();
            double weight = 0.0;
            for (Arch<String, Double> arch : resultArches) {
                weight += arch.getLabel();
            } 
            weight = weight/1000;

            System.out.println("Number of Nodes: " + result.getNodesCount());
            System.out.println("Number of Arches: " + result.getArchesCount());

            DecimalFormat df = new DecimalFormat("#");
            df.setMaximumFractionDigits(3);
            System.out.println("Weight of Graph: " + df.format(weight));

            Duration timeElapsed = Duration.between(start, end);
            System.out.println("Time taken: " + timeElapsed.toMillis() + " milliseconds");
            
        } catch (IOException e) {
            System.out.println("Problem reading the file... It might cannot be found");
        }
    }
}
