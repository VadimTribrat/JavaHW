package org.vmk.dep508.stream.iris;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class App {
    public static void main(String[] args) throws IOException {
        App a = new App();
        a.test();
    }

    public void test() throws IOException {

        List<Iris> irisList = Files
                .lines(Paths.get("iris.data"))
                .map(Iris::parse)
                .collect(Collectors.toList());
        IrisDataSetHelper helper = new IrisDataSetHelper(irisList);

        //get average sepal width
        Double avgSetalLength = helper.getAverage(Iris::getSepalLength);
        System.out.println(avgSetalLength);

        //get average sepal Length
        Double avgPetalLength = helper.getAverage(Iris::getPetalLength);
        System.out.println(avgPetalLength);

        //get average petal square - petal width multiplied on petal length
        //Double avgPetalSquare = helper.getAverage(Iris::getPetalLength) * helper.getAverage(Iris::getPetalWidth);
        Double avgPetalSquare = helper.getAverage(x->x.getPetalLength()* x.getPetalWidth());
        System.out.println(avgPetalSquare);

        //get average petal square for flowers with sepal width > 4
        Double avgPetalSquareFiltered = helper.getAverageWithFilter(
                x -> x.getSepalWidth() > 4,
                x -> x.getPetalWidth() * x.getPetalLength());

        //get flowers grouped by Petal size (Petal.SMALL, etc.)
        Map groupsByPetalSize = helper.groupBy(Iris::classifyByPatel);
        System.out.println(groupsByPetalSize);

        //get max sepal width for flowers grouped by species
        Map maxSepalWidthForGroupsBySpecies = helper
                .maxFromGroupedBy(Iris::getSpecies, Iris::getSepalWidth);
        System.out.println(maxSepalWidthForGroupsBySpecies);
    }

}

