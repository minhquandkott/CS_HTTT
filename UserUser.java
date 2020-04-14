/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kNearestNeighbour;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 *
 * @author PC
 */
public class UserUser {

    public static void main(String[] args) {
        double[][] matrix = initNormalizedMatrix();
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                System.out.print(matrix[i][j]+"   ");
            }
            System.out.println("");
        }
        System.out.println("=========================================================");
        List<double[]> list = normalizeList(matrix);
        List<Double[]> listOfNewRows = new ArrayList<>();
        for (double[] matrix1 : matrix) {
            listOfNewRows.add(ratingOfRow(matrix1, list));
        }
        for(Double[] item:listOfNewRows){
            for(int i=0;i<item.length;i++){
                System.out.print(item[i]+"   ");
            }
            System.out.println("");
        }        
    }

    private static Double[] ratingOfRow(double[] row, List<double[]> normalizeList) {
        Map<Double, Double> map = new HashMap<>();
        List<Integer> indexEqualZero = new ArrayList<>();
        List<Integer> indexDifferentZero = new ArrayList<>();
        List<Double> itemChange = new ArrayList<Double>();
        List<Double> afterChange = new ArrayList<Double>();
        for (int i = 0; i < row.length; i++) {
            if (row[i] == 0) {
                indexEqualZero.add(i);
            } else {
                indexDifferentZero.add(i);
            }
        }
        for (int i = 0; i < indexEqualZero.size(); i++) {
            for (int j = 0; j < indexDifferentZero.size() - 1; j++) {
                map.put(row[j], cosineSimilarity(normalizeList.get(i), normalizeList.get(j)));
            }
            itemChange.add(rating(map));
            map.clear();
        }
        int count=0;
        for(int i=0;i<row.length;i++){
            if(row[i]==0){
                afterChange.add(itemChange.get(count++));
            } else {
                afterChange.add(row[i]);
            }
        }
        Double[] newRow = afterChange.stream().toArray(Double[]::new);
        return newRow;
    }

    private static double[][] initNormalizedMatrix() {
        double[][] m
                = {{1.75, 2.25, -0.5, -1.33, -1.5, 0, 0},
                {0.75, 0, 0, -1.33, 0, 0.5, 0},
                {0, 1.25, -1.5, 0, 0, -0.5, -2.33},
                {-1.25, -0.75, 0.5, 2.67, 1.5, 0, 0.67},
                {-1.25, -2.75, 1.5, 0, 0, 0, 1.67}};
        return m;
    }

    private static List<double[]> normalizeList(double[][] NormalizedMatrix) {
        List<double[]> l = new ArrayList<>();
        double rotateNormalizedMatrix[][] = rotateMatrix(NormalizedMatrix);
        for (int i = 0; i < rotateNormalizedMatrix.length; i++) {
            l.add(rotateNormalizedMatrix[i]);
        }
        return l;
    }

    private static double[][] rotateMatrix(double[][] matrix) {

        int totalRowsOfRotatedMatrix = matrix[0].length;
        int totalColsOfRotatedMatrix = matrix.length;

        double[][] rotatedMatrix = new double[totalRowsOfRotatedMatrix][totalColsOfRotatedMatrix];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                rotatedMatrix[j][(totalColsOfRotatedMatrix - 1) - i] = matrix[i][j];
            }
        }
        return rotatedMatrix;
    }

    private static double cosineSimilarity(double[] A, double[] B) {
        if (A == null || B == null || A.length == 0 || B.length == 0 || A.length != B.length) {
            return 2;
        }

        double sumProduct = 0;
        double sumASq = 0;
        double sumBSq = 0;
        for (int i = 0; i < A.length; i++) {
            sumProduct += A[i] * B[i];
            sumASq += A[i] * A[i];
            sumBSq += B[i] * B[i];
        }
        if (sumASq == 0 && sumBSq == 0) {
            return 2.0;
        }
        return sumProduct / (Math.sqrt(sumASq) * Math.sqrt(sumBSq));
    }

    private static double rating(Map<Double, Double> map) {
        map = map.entrySet()
                .stream()
                .sorted((Map.Entry.<Double, Double>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        List<Entry<Double, Double>> entryList = new ArrayList<Map.Entry<Double, Double>>(map.entrySet());
        Entry<Double, Double> last = entryList.get(entryList.size() - 1);
        Entry<Double, Double> nearlylast = entryList.get(entryList.size()-2);
        System.out.println("");
        double rating = (last.getKey() * last.getValue() + nearlylast.getKey() * nearlylast.getValue()) / (Math.abs(last.getValue()) + Math.abs(nearlylast.getValue()));
        return Math.round(rating * 100.0) / 100.0;
    }
}
