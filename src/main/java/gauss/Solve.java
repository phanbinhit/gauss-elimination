package gauss;

public class Solve {
	
	public static void main(String[] args) {
		double[][] matrix = {{2, 1, -1, 3}, {1, -1, 2, 2}, {3, -2, 1, -1}};
		show(matrixRetangle(matrix));
	}
	
	private static void show(double[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0 ; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] +" ");
			}
			System.out.println();
		}
	}
	
	private static double[][] matrixRetangle(double[][] matrix) {
		double[] results = new double[matrix.length];
		int countPivot1 = 0;
		int countPivot2 = 0;
		for (int i = 0; i < matrix.length; i++) {
			double pivot1 = matrix[i][countPivot1++];
			for (int j = 0 ; j < matrix[i].length; j++) {
				matrix[i][j] = matrix[i][j] / pivot1;
			}
			
			for (int k = i + 1; k < matrix.length; k++) {
				double pivot2 = matrix[k][countPivot2];
				for (int j = 0; j < matrix[k].length; j++) {
					double before = matrix[i][j];
					matrix[k][j] = matrix[k][j] - before * pivot2;
				}
			}
			countPivot2++;
		}
		return matrix;
	}
	
}
